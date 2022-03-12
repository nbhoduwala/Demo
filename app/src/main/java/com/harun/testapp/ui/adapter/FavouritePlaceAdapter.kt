package com.harun.testapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harun.testapp.R

class FavouritePlaceAdapter : RecyclerView.Adapter<FavouritePlaceAdapter.MyViewHolder>() {

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemVeiw = LayoutInflater.from(parent.context).inflate(R.layout.favourite_row,parent,false)
        return MyViewHolder(itemVeiw)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {

        return 10
    }
}