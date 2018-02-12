package com.keepcoding.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Shop
import com.squareup.picasso.Picasso

class ItemRecyclerViewAdapter(val items: List<Shop>) : RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder>() {
    var onClickListener: View.OnClickListener? = null

    /*adapter.onClickListener = View.OnClickListener { v: View ->
        *//*val position = itemsList.getChildAdapterPosition(v)
        val tableIndex = arguments.getInt(TableListFragment.EXTRA_TABLE_INDEX, 0)

        // Start ItemDetailActivity
        startActivity(ItemDetailActivity.intent(activity, position, tableIndex))*//*
        Log.d("HOLA", "Pressed list button")
    }*/

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.content_item, parent, false)
        view.setOnClickListener(onClickListener)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        if (items != null) {
            holder?.bindItem(items[position], position)
        }
    }

    override fun getItemCount() = items.size ?: 0

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.item_name)
        val image = itemView.findViewById<ImageView>(R.id.item_image)

        fun bindItem(item: Shop, position: Int) {
            name.text = item.name

            /*itemView.setOnClickListener {
                Log.d("Hola", "Pressed button list")
            }*/

            Picasso.
                    with(this.itemView.context).
                    load(item.logo).
                    placeholder(android.R.drawable.ic_delete).
                    into(image)
        }
    }
}