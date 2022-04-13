package com.e.iselleradmin.Views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.R
import com.e.iselleradmin.Views.Fragment.DashBoard.HomeFragment
import com.e.iselleradmin.Views.Fragment.FragmentTitle

class HomeActivity : AppCompatActivity(),FragmentChanger {

    var currentFragment = String()
    companion object{
        const val homeFragment="HOMEPAGE"
        const val Add_item ="ADD_ITEM"
        const val ORDER_LIST =" Orders"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        changeFragment(HomeFragment.newInstance())
    }

    private fun changeView(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame, fragment)
            .commit()
    }


    override fun changeFragment(fragmentTitle: FragmentTitle) {
        currentFragment = fragmentTitle.title
        changeView(fragmentTitle)
    }
}
