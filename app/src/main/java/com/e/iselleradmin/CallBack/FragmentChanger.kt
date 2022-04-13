package com.e.iselleradmin.CallBack

import androidx.fragment.app.Fragment
import com.e.iselleradmin.Views.Fragment.FragmentTitle

interface FragmentChanger {
    fun changeFragment(fragmentTitle: FragmentTitle)
}