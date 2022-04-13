package com.e.iselleradmin.Views.Fragment.subDashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.iselleradmin.R


class EditItem2 : Fragment() {


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
        return inflater.inflate(R.layout.fragment_edit_item2, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EditItem2().apply {
                arguments = Bundle().apply {

                }
            }
    }
}