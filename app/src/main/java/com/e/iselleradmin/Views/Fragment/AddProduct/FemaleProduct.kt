package com.e.iselleradmin.Views.Fragment.AddProduct

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.iselleradmin.R
import com.e.iselleradmin.Util.Constants
import com.e.iselleradmin.Util.Constants.BAGS
import com.e.iselleradmin.Util.Constants.DRESSES
import com.e.iselleradmin.Util.Constants.FEMALE_PRODUCT
import com.e.iselleradmin.Util.Constants.JEANS
import com.e.iselleradmin.Util.Constants.SHIRT
import com.e.iselleradmin.Util.Constants.SHOES
import com.e.iselleradmin.Util.Constants.WATCHES
import com.e.iselleradmin.Util.IO
import com.e.iselleradmin.Views.Activity.AddProductActivity
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.PRODUCT_CATEGORY
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.PRODUCT_NAME
import com.e.iselleradmin.Views.Activity.SubItemHomeActivity
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import kotlinx.android.synthetic.main.fragment_female_product.view.*


class FemaleProduct : FragmentTitle(), View.OnClickListener {

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
        val view = inflater.inflate(R.layout.fragment_female_product, container, false)
        setContentView(view)
        return  view
    }

    private fun setContentView(view: View){
        view.add_footwear_btn.setOnClickListener(this)
        view.add_t_shirt_btn.setOnClickListener(this)
        view.add_bags_btn.setOnClickListener(this)
        view.add_watches_btn.setOnClickListener(this)
        view.add_jean_btn.setOnClickListener(this)
        view.add_dresses_btn.setOnClickListener(this)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FemaleProduct().apply {
                arguments = Bundle().apply {
                    title= AddProductActivity.FEMALE_PRODUCT

                }
            }
    }

    override fun onClick(p: View?) {
        when(p!!.id){
            R.id.add_footwear_btn->{

                bundle.putString(PRODUCT_NAME, FEMALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, SHOES)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
            R.id.add_t_shirt_btn->{
                IO.setData(context!!, PRODUCT_NAME, FEMALE_PRODUCT)
                IO.setData(context!!, PRODUCT_CATEGORY, SHIRT)


                bundle.putString(PRODUCT_NAME, FEMALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, SHOES)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
            R.id.add_bags_btn->{

                bundle.putString(PRODUCT_NAME, FEMALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, BAGS)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
            R.id.add_watches_btn->{

                bundle.putString(PRODUCT_NAME, FEMALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, WATCHES)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
            R.id.add_jean_btn->{

                bundle.putString(PRODUCT_NAME, FEMALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, JEANS)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
            R.id.add_dresses_btn->{

                bundle.putString(PRODUCT_NAME, FEMALE_PRODUCT)
                bundle.putString(PRODUCT_CATEGORY, DRESSES)
                changeView(SubItemHomeActivity::class.java, bundle)
            }
        }
    }

    private fun changeView(classs:Class<*>, bundle: Bundle){
        startActivity(Intent(context, classs)
            .putExtras(bundle))
    }
}
