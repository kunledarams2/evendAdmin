package com.e.iselleradmin.Views.Fragment.subDashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.iselleradmin.Adapter.ItemsAdapter
import com.e.iselleradmin.CallBack.ClickListener
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.Model.AddItemModel
import com.e.iselleradmin.R
import com.e.iselleradmin.ViewModel.ItemsViewModel
import com.e.iselleradmin.Views.Activity.SubItemHomeActivity
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import kotlinx.android.synthetic.main.fragment_edit_item.view.*


class EditItem : FragmentTitle() {

    private var itemsAdapter: ItemsAdapter? = null
    private var itemsViewModel: ItemsViewModel? = null
    private var toolBar: Toolbar? = null
    private var fragmentChanger: FragmentChanger? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            itemsAdapter = ItemsAdapter()
            fragmentChanger = activity as SubItemHomeActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_item, container, false)
        setContentView(view)
        return view
    }

    private fun setContentView(view: View) {

        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.setHasFixedSize(true)
        view.recyclerView.adapter = itemsAdapter
        (activity as SubItemHomeActivity).setSupportActionBar(view.toolbar)
        (activity as SubItemHomeActivity).supportActionBar

        view.toolbar.setNavigationOnClickListener {
            fragmentChanger!!.changeFragment(
                SubDashboard.newInstance(
                    itemBranch,
                    itemCat
                )
            )
        }

        itemsAdapter!!.setEditClickListener(object : ClickListener<AddItemModel> {
            override fun onClick(model: AddItemModel) {
                setUpEditView(view, model)
            }

        })

    }

    private fun setUpEditView(view: View, model: AddItemModel) {
        view.item_list_wrapper.visibility = View.GONE
        view.edit_wrapper.visibility = View.VISIBLE
        view.nav_btn.setOnClickListener {
            view.item_list_wrapper.visibility = View.VISIBLE
            view.edit_wrapper.visibility = View.GONE
        }

        view.product_name.setText(model.itemName)
        view.product_price.setText(model.itemPrice)

        log(model.itemSize.toString())
        val arrayAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, model.itemSize.toList())
        view.product_available_size.setAdapter(arrayAdapter)
        view.product_available_size.threshold =1
        view.product_available_size.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        view.product_available_size.setText(model.itemSize.toString())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        itemsViewModel = ViewModelProviders.of(this)[ItemsViewModel::class.java]
        itemsViewModel!!.fetchAddItems("Data", itemBranch, itemCat)
        observeView(itemsViewModel!!)

    }

    private fun observeView(itemsViewModel: ItemsViewModel) {
        itemsViewModel.getMediatorLiveData().observe(this, Observer {
            if (!it.dataLists.isNullOrEmpty()) {
                itemsAdapter!!.setData(it.dataLists as ArrayList<AddItemModel>)
            }
        })
    }

    companion object {
        var itemBranch = String()
        var itemCat = String()

        @JvmStatic
        fun newInstance(itemName: String, itemCategory: String) =
            EditItem().apply {
                arguments = Bundle().apply {
                    title = SubItemHomeActivity.EDIT_ITEM
                    itemBranch = itemName
                    itemCat = itemCategory
                }
            }
    }

    private fun log(msg:String){
        Log.d(EditItem::class.java.simpleName , "---_-----_----_--$msg")
    }
}