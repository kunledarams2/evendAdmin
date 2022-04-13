package com.e.iselleradmin.Views.Fragment.AddProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.e.iselleradmin.R


class ElectronicsProduct : Fragment() {


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
        return inflater.inflate(R.layout.fragment_electronics_product, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ElectronicsProduct().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
