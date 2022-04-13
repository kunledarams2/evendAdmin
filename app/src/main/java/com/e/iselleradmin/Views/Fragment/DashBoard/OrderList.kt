package com.e.iselleradmin.Views.Fragment.DashBoard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.iselleradmin.Adapter.DeliveryAdapter
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.Model.DeliveryInfo
import com.e.iselleradmin.R
import com.e.iselleradmin.ViewModel.DeliveryViewModel
import com.e.iselleradmin.Views.Activity.HomeActivity
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import kotlinx.android.synthetic.main.fragment_order_list.view.*


class OrderList : FragmentTitle() {

    private var deliveryAdapter: DeliveryAdapter? = null
    private var deliveryViewModel: DeliveryViewModel? = null
    private var recyclerView:RecyclerView?=null
    private var fragmentChanger:FragmentChanger?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fragmentChanger = activity as HomeActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_list, container, false)
        setContentView(view)
        return view
    }


    private fun setContentView(view: View) {

        recyclerView = view.findViewById(R.id.orderRecycler)
        val llm= LinearLayoutManager(context)
        recyclerView!!.layoutManager = llm

        deliveryAdapter = DeliveryAdapter(context!!)

        view.toolbar.setNavigationOnClickListener { fragmentChanger!!.changeFragment(HomeFragment.newInstance()) }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        deliveryViewModel = ViewModelProviders.of(this).get(DeliveryViewModel::class.java)
        deliveryViewModel!!.fetchItemByGroup("Data","DeliveryInfo")
        observeViewModel(deliveryViewModel!!)
    }

    private fun observeViewModel(deliveryViewModel: DeliveryViewModel){
        deliveryViewModel.mediatorLiveData.observe(this, (Observer {result->
            if(result.dataLists.isNotEmpty()){
                logMesssage(result.dataLists.toString())
                deliveryAdapter!!.setData(result.dataLists as ArrayList<DeliveryInfo>)
                recyclerView!!.adapter = deliveryAdapter
            }
        }))
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            OrderList().apply {
                arguments = Bundle().apply {
                    title = HomeActivity.ORDER_LIST
                }
            }
    }

    private fun logMesssage(mgs:String){
        Log.d(OrderList::class.java.simpleName, "--_--_-_-___---_-_-_-$mgs")
    }
}
