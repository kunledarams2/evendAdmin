package com.e.iselleradmin.Views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.R
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import com.e.iselleradmin.Views.Fragment.SignUp.RegistrationPage

class SignUpActivity : AppCompatActivity(),FragmentChanger {
    var currentFragment = String()
    companion object{
        const val  Register = "REGISTER_PAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        changeFragment(RegistrationPage.newInstance())
    }

     fun changeView(fragment: Fragment) {
        val fragmentManager:FragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame,fragment)
            .commit()
    }

    override fun changeFragment(fragmentTitle: FragmentTitle) {
        currentFragment =fragmentTitle.title
        changeView(fragmentTitle)
    }
}
