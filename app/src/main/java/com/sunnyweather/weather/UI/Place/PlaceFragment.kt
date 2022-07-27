package com.sunnyweather.weather.UI.Place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.weather.MainActivity
import com.sunnyweather.weather.R
import com.sunnyweather.weather.UI.Weather.WeatherActivity
import com.sunnyweather.weather.databinding.FragmentPlaceBinding

class PlaceFragment:Fragment(){
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var adapter: PlaceAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerView)
        val imageView = requireActivity().findViewById<ImageView>(R.id.imageView)
        super.onActivityCreated(savedInstanceState)
        if(activity is MainActivity&&viewModel.isPlaceSaved()){
            val place=viewModel.getSavedPlace()
            val intent= Intent(context,WeatherActivity::class.java).apply{
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        val layoutManager=LinearLayoutManager(activity)
        recyclerView.layoutManager=layoutManager
        adapter= PlaceAdapter(this,viewModel.placeList)
       recyclerView.adapter=adapter
        val searchPlaceEdit = requireActivity().findViewById<EditText>(R.id.searchPlaceEdit)
        searchPlaceEdit.addTextChangedListener{
                editable ->
            val content=editable.toString()
            if(content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                recyclerView.visibility=View.GONE
                imageView.visibility=View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result->
            val places=result.getOrNull()
            if(places!=null){
                recyclerView.visibility=View.VISIBLE
                     imageView.visibility=View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
    }