package com.e.iselleradmin.Views.Fragment.AddProduct

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.e.iselleradmin.R
import com.e.iselleradmin.Util.Constants.MALE_PRODUCT
import com.e.iselleradmin.Util.Constants.PANT_TROUSER
import com.e.iselleradmin.Util.Constants.SHIRT
import com.e.iselleradmin.Util.Constants.SHOES
import com.e.iselleradmin.Util.Constants.T_SHIRTS
import com.e.iselleradmin.Util.Constants.WATCHES
import com.e.iselleradmin.Views.Activity.AddProductActivity
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.PRODUCT_CATEGORY
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.PRODUCT_NAME
import com.e.iselleradmin.Views.Activity.SubItemHomeActivity
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import kotlinx.android.synthetic.main.fragment_male_product.view.*


class MaleProduct : FragmentTitle(), View.OnClickListener {

    private var bundle=Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_male_product, container, false)
        setContentView(view)
        return  view
    }

    private fun setContentView(view: View){

        view.shoeWrapper.setOnClickListener(this)
        view.t_shirtWrapper.setOnClickListener(this)
        view.pantWrapper.setOnClickListener(this)
        view.watchWrapper.setOnClickListener(this)
        view.shirtWrapper.setOnClickListener(this)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MaleProduct().apply {
                arguments = Bundle().apply {
                    title = AddProductActivity.MALE_PRODUCT
                }
            }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){

            R.id.shoeWrapper->{
              /*  IO.setData(context!!, PRODUCT_NAME, MALE_PRODUCT)
                IO.setData(context!!, PRODUCT_CATEGORY, SHOES)

                AddItem(activity as AddProductActivity).show()*/
                bundle.putString(PRODUCT_NAME, MALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, SHOES)
                changeView(SubItemHomeActivity::class.java, bundle)

            }
            R.id.t_shirtWrapper->{
                bundle.putString(PRODUCT_NAME, MALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, SHIRT)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
            R.id.pantWrapper->{

                bundle.putString(PRODUCT_NAME, MALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, PANT_TROUSER)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
            R.id.watchWrapper->{

                bundle.putString(PRODUCT_NAME, MALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, WATCHES)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
            R.id.shirtWrapper->{
                bundle.putString(PRODUCT_NAME, MALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, T_SHIRTS)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
        }
    }

    private fun changeView(classs:Class<*>, bundle: Bundle){
        startActivity(Intent(context, classs)
            .putExtras(bundle))
    }
}
