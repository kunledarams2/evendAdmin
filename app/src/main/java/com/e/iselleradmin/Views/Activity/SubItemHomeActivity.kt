package com.e.iselleradmin.Views.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.R
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.PRODUCT_CATEGORY
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.PRODUCT_NAME
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import com.e.iselleradmin.Views.Fragment.subDashboard.SubDashboard

class SubItemHomeActivity : AppCompatActivity(), FragmentChanger {
    companion object {
        const val SUB_DASHBOARD = "Sub_board"
        const val ADD_ITEM = "Add Item"
        const val EDIT_ITEM = "Edit Item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_item_home)

        val bundle = intent.extras
        if (bundle != null) {
            val itemName = bundle.getString(PRODUCT_NAME)
            val itemCategory = bundle.getString(PRODUCT_CATEGORY)
            changeFragment(SubDashboard.newInstance(itemName!!, itemCategory!!))

        }

    }

    override fun changeFragment(fragmentTitle: FragmentTitle) {
        changeView(fragmentTitle)
    }

    private fun changeView(fragment: Fragment) {
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.frame, fragment).commit()
    }
}