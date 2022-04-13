package com.e.iselleradmin.Views.Fragment.DashBoard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.Model.DeliveryInfo

import com.e.iselleradmin.R
import com.e.iselleradmin.Util.Constants
import com.e.iselleradmin.Util.IO
import com.e.iselleradmin.ViewModel.DeliveryViewModel
import com.e.iselleradmin.Views.Activity.AddProductActivity
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.FEMALE_PRODUCT
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.MALE_PRODUCT
import com.e.iselleradmin.Views.Activity.HomeActivity
import com.e.iselleradmin.Views.Fragment.AddProduct.AddItemFragmenrt
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : FragmentTitle(), View.OnClickListener {

    var  fragmentChanger:FragmentChanger?=null
    private var deliveryInfo=ArrayList<DeliveryInfo>()
    private var totalAmount=ArrayList<String>()

    private var deliveryInfom=DeliveryInfo()
    private var bundle = Bundle()

    private var deliveryViewModel:DeliveryViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fragmentChanger = activity as HomeActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view =inflater.inflate(R.layout.fragment_home, container, false)
        setUpView(view)
        return  view
    }

    private  fun setUpView(view: View){

//        view.addItemBtn.setOnClickListener(this)
        view.checkOrder.setOnClickListener(this)
        view.femaleProWrapper.setOnClickListener(this)
        view.maleProWrapper.setOnClickListener(this)
    }

    companion object {

        lateinit var totalAmount:String
        lateinit var dailySells:String
        lateinit var totalItemOrder:String
        lateinit var dailyOrder:String

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    title = HomeActivity.homeFragment
                }
            }
    }

    override fun onClick(view: View?) {

        when(view!!.id){
            R.id.femaleProWrapper->{
                bundle.putString("fragment",FEMALE_PRODUCT)
                changeView(AddProductActivity::class.java, bundle)
                IO.setData(context!!,Constants.PRODUCT_PACKAGE, FEMALE_PRODUCT)
            }

            R.id.checkOrder->{
                fragmentChanger!!.changeFragment(OrderList.newInstance())
            }
            R.id.maleProWrapper->{
                bundle.putString("fragment", MALE_PRODUCT)
                changeView(AddProductActivity::class.java, bundle)
                IO.setData(context!!,Constants.PRODUCT_PACKAGE, MALE_PRODUCT)

            }
        }
    }

    fun changeView(classe:Class<*>, bundle: Bundle){
        startActivity(Intent(context, classe).putExtras(bundle))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        deliveryViewModel=ViewModelProviders.of(this).get(DeliveryViewModel::class.java)
        deliveryViewModel!!.fetchItemByGroup("Data","DeliveryInfo")
        observeDeliveryViewModel(deliveryViewModel!!)
    }

    @SuppressLint("SetTextI18n")
    private fun observeDeliveryViewModel(deliveryViewModel: DeliveryViewModel){
        deliveryViewModel.mediatorLiveData.observe(this, (Observer {
            result->
            if(result.dataLists.isNotEmpty()){

                var i=0
                deliveryInfo =result.dataLists as ArrayList<DeliveryInfo>
                while (deliveryInfo.size >i){
                     deliveryInfom=deliveryInfo[i]

                    totalAmount.add(deliveryInfom.grandAmount)
                    i++
                }

                var sumAmount=0
                var a=0
                while (totalAmount.size>a){
                    sumAmount += totalAmount[a].toInt()

                    a++
                }
//                view!!.amount_figure2.text= "N${sumAmount}"
                view!!.amount_figure.text="N${deliveryInfom.grandAmount}"
//                view!!.orders_figure2.text = deliveryInfo.size.toString()
            }
        }))
    }
}
