package com.e.iselleradmin.Views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.R
import com.e.iselleradmin.Views.Fragment.SignIn.Auth
import com.e.iselleradmin.Views.Fragment.FragmentTitle

class LoginActivity : AppCompatActivity(), FragmentChanger {

    var currentFragment = String()
    companion object{
        const val  login ="LOGIN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        changeFragment(Auth.newInstance())

    }

    override fun changeFragment(fragmentTitle: FragmentTitle) {
        currentFragment = fragmentTitle.title
        changeView(fragmentTitle)
    }
    fun changeView(fragment: Fragment){
        val fragmentManager:FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }

}
