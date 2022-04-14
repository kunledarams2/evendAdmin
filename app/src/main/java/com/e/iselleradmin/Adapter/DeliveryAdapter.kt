package com.e.iselleradmin.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.e.iselleradmin.Database.FDatabase
import com.e.iselleradmin.Model.DeliveryInfo
import com.e.iselleradmin.Model.cartInfo
import com.e.iselleradmin.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.holder_cart_info.view.*
import kotlinx.android.synthetic.main.holder_container_orders.view.*

class DeliveryAdapter(context: Context) : RecyclerView.Adapter<DeliveryAdapter.ViewHolderClass>() {

    private var deliveryList = ArrayList<DeliveryInfo>()
    private var mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.holder_container_orders, parent, false)
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {
        return deliveryList.size
    }

    fun setData(data: ArrayList<DeliveryInfo>) {
        this.deliveryList = data
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.mBlind(deliveryList[position], mContext)

    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var fDatabase: FDatabase? = null
        private var cartInfoList = ArrayList<cartInfo>()
        private var isListOpen = false
        private var linearLayout: LinearLayout? = null


        fun mBlind(deliveryInfo: DeliveryInfo, context: Context) {
            fDatabase = FDatabase(context)
            linearLayout = itemView.findViewById(R.id.orderListView)

            fDatabase!!.getDataBase("User", deliveryInfo.customerId)!!
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        itemView.buyerName.text = "Buyer: ${dataSnapshot.child("username").value.toString()}"
                        itemView.buyerPhone.text = "Phone Nos: ${dataSnapshot.child("phone").value.toString()}"
                        itemView.buyerAddress.text ="Address: ${dataSnapshot.child("address").value.toString()}"
                        itemView.paymentOption.text ="PaymentOption: ${deliveryInfo.paymentOption}"
                    }
                })

//            itemView.paymentOption.text = deliveryInfo.paymentOption
//            itemView.morderDate.text = deliveryInfo.orderDate



            var i = 0
            var mcartInfo = cartInfo()
            while (deliveryInfo.cartInfo.size > i) {
                mcartInfo = deliveryInfo.cartInfo[i]
                cartInfoList.add(mcartInfo)

                i++
            }
            logMessage(cartInfoList.size.toString())


            for (itemCart in deliveryInfo.cartInfo){
                itemInfoBlind(itemCart,context)

            }
            itemView.openBtn.setOnClickListener {


//               itemView.itemInfoWrapper.visibility = View.VISIBLE
                linearLayout!!.visibility = View.VISIBLE
                itemView.closeBtn.visibility = View.VISIBLE
                itemView.openBtn.visibility = View.GONE

            }
            itemView.closeBtn.setOnClickListener {
//                itemView.itemInfoWrapper.visibility = View.GONE
                linearLayout!!.visibility = View.GONE
                itemView.closeBtn.visibility = View.GONE
                itemView.openBtn.visibility = View.VISIBLE
            }
        }

        private fun itemInfoBlind(cartInfo: cartInfo, context: Context) {
            val viewCart = LayoutInflater.from(context).inflate(R.layout.holder_cart_info, null)

//            for (cartInfo in cartInfom){
//
//            }

            Picasso.with(context).load(cartInfo.productId).into(viewCart.itemImage)
            viewCart.size.text = cartInfo.sizeSelected
            viewCart.productName.text = cartInfo.productName
            viewCart.color.text = cartInfo.colorSelected
            viewCart.quantity_ordered.text = cartInfo.noSelected
            viewCart.price.text = cartInfo.productPrices


            linearLayout!!.addView(viewCart)

        }

        private fun logMessage(mgs: String) {
            Log.d(DeliveryAdapter::class.java.simpleName, "---__-_-_-_-_-_-_---$mgs")
        }
    }


}