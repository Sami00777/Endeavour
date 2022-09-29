package com.xanroid.endeavour.adaptor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xanroid.endeavour.R
import com.xanroid.endeavour.main_list_data.ProductModel

class SavedListAdaptor: RecyclerView.Adapter<SavedListAdaptor.MainListViewHolder>() {

    private val productList: MutableList<ProductModel> = mutableListOf()

    var onFavClick: ((ProductModel) -> Unit)? = null
    var onItemClick: ((ProductModel, View) -> Unit)? = null

    class MainListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val txtTitle: TextView = itemView.findViewById(R.id.txt_name)
        val txtPrice: TextView = itemView.findViewById(R.id.txt_price)
        val favView: LinearLayout = itemView.findViewById(R.id.btnFavourite)
        val imgView: ImageView = itemView.findViewById(R.id.imageView)
        val cardView: ConstraintLayout = itemView.findViewById(R.id.cardView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_rec_view, parent, false)
        return MainListViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        val item = productList[position]
        holder.txtTitle.text = item.name
        holder.txtPrice.text = "$"+String.format("%.2f", item.price)
        Picasso.get().load(item.image).error(R.drawable.image_error).into(holder.imgView)

        holder.favView.setOnClickListener {
            onFavClick?.invoke(item)
        }

        holder.cardView.setOnClickListener {
            onItemClick?.invoke(item, it)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateList(pList: List<ProductModel>) {
        productList.clear()
        productList.addAll(pList)
        notifyDataSetChanged()
    }


}