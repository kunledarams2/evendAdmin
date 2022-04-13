package com.e.iselleradmin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.iselleradmin.CallBack.ClickListener
import com.e.iselleradmin.Model.AddItemModel
import com.e.iselleradmin.R
import kotlinx.android.synthetic.main.holder_item_edit.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.VHClass>() {

    private var itemsData = ArrayList<AddItemModel>()
    private var editItemClickListener: ClickListener<AddItemModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHClass {
        return VHClass(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_item_edit, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemsData.size
    }

    override fun onBindViewHolder(holder: VHClass, position: Int) {
        holder.bindView(itemsData[position])
        holder.mEditClickListener = editItemClickListener
    }

    fun setData(data: ArrayList<AddItemModel>) {
        this.itemsData = data
        notifyDataSetChanged()
    }

    fun setEditClickListener(click: ClickListener<AddItemModel>) {
        this.editItemClickListener = click
    }

    class VHClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mEditClickListener: ClickListener<AddItemModel>? = null

        fun bindView(addItemModel: AddItemModel) {
            itemView.item_name.text = addItemModel.itemName
            itemView.edit_btn.setOnClickListener { mEditClickListener!!.onClick(addItemModel) }
        }
    }
}