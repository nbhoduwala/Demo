package com.harun.testapp.ui.home

import android.icu.lang.UCharacter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harun.testapp.databinding.FragmentHomeBinding
import com.harun.testapp.ui.adapter.FavouritePlaceAdapter
import com.harun.testapp.ui.adapter.PlaceAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rvPlace: RecyclerView = binding.rvPlace
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvPlace.layoutManager = layoutManager
        val placeAdapter = PlaceAdapter()
        rvPlace.adapter = placeAdapter

        val rv_favourite_place: RecyclerView = binding.rvFavouritePlace
        val layoutManager1 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_favourite_place.layoutManager = layoutManager1
        val favouritePlaceAdapter =FavouritePlaceAdapter()
        rv_favourite_place.adapter = favouritePlaceAdapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}