package com.denis.sumpic.view.fragment2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.denis.sumpic.R

class GridAdapter(val context: Context, val images: ArrayList<UriImage>): BaseAdapter() {

    override fun getCount(): Int = images.size

    override fun getItem(position: Int): Any = images[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        if (view ==null){
             view = LayoutInflater.from(context).inflate(R.layout.favourite_pic_card,parent,false)
        }
        val s =this.getItem(position) as  UriImage

        val imageView: ImageView = view!!.findViewById(R.id.favourite_img)
        Glide.with(context)
            .load(s.uri)
            .into(imageView)
        return view
    }
}