package com.e.iselleradmin.Views.Fragment.subDashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.R
import com.e.iselleradmin.Util.Constants
import com.e.iselleradmin.Util.Constants.FEMALE_PRODUCT
import com.e.iselleradmin.Util.Constants.PRODUCT_PACKAGE
import com.e.iselleradmin.Util.IO
import com.e.iselleradmin.Views.Activity.AddProductActivity
import com.e.iselleradmin.Views.Activity.SubItemHomeActivity
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import kotlinx.android.synthetic.main.fragment_sub_dashboard.view.*


class SubDashboard : FragmentTitle(), View.OnClickListener {

    private  var fragmentChanger:FragmentChanger?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fragmentChanger =  activity as SubItemHomeActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view = inflater.inflate(R.layout.fragment_sub_dashboard, container, false)
        setContentView(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setContentView(view: View){
        view.dashboard_title.text = "$mItemName ( $mItemCategory ) Dashboard"
        view.add_item.setOnClickListener(this)
        view.nav_btn.setOnClickListener(this)
        view.edit_item.setOnClickListener(this)
    }

    companion object {
        var mItemName=String()
        var mItemCategory=String()

        @JvmStatic
        fun newInstance(itemName:String, itemCategory:String) =
            SubDashboard().apply {
                arguments = Bundle().apply {
                title = SubItemHomeActivity.SUB_DASHBOARD
                    mItemName= itemName
                    mItemCategory=itemCategory
                }
            }
    }

    override fun onClick(p0: View?) {
         when(p0!!.id){
             R.id.add_item->{
                 fragmentChanger!!.changeFragment(AddItem.newInstance(mItemName, mItemCategory))
             }
             R.id.nav_btn->{
                 startActivity(Intent(context!!, AddProductActivity::class.java)
                     .putExtra("fragment", IO.getData(context!!, PRODUCT_PACKAGE))
                     .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK))
                 activity!!.finish()
             }
             R.id.edit_item->{
                 fragmentChanger!!.changeFragment(EditItem.newInstance(mItemName, mItemCategory))
             }
         }
    }
}