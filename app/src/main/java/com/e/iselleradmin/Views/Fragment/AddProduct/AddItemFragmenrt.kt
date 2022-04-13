package com.e.iselleradmin.Views.Fragment.AddProduct

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.R
import com.e.iselleradmin.Views.Activity.AddProductActivity
import com.e.iselleradmin.Views.Activity.HomeActivity
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import kotlinx.android.synthetic.main.fragment_add_item_fragmenrt.view.*


class AddItemFragmenrt : FragmentTitle(),View.OnClickListener {

    private var fragmentChanger: FragmentChanger? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            fragmentChanger = activity as AddProductActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_item_fragmenrt, container, false)
        setContentView(view)
        return view
    }

    private fun setContentView(view: View) {
        view.women_cat_Wrapper.setOnClickListener(this)
        view.man_cat_Wrapper.setOnClickListener(this)
//        view.toolbar.setNavigationOnClickListener {
//            startActivity(Intent(context, HomeActivity::class.java))
//            activity!!.finish()
//        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            AddItemFragmenrt().apply {
                arguments = Bundle().apply {
                    title = AddProductActivity.HOME_PAGE
                }
            }
    }

    override fun onClick(p: View?) {

        when(p!!.id){
            R.id.women_cat_Wrapper->{fragmentChanger!!.changeFragment(FemaleProduct.newInstance())}
            R.id.man_cat_Wrapper->{fragmentChanger!!.changeFragment(MaleProduct.newInstance())}
        }
    }
}
