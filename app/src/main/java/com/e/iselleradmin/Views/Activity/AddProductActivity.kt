package com.e.iselleradmin.Views.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.R
import com.e.iselleradmin.Views.Fragment.AddProduct.FemaleProduct
import com.e.iselleradmin.Views.Fragment.AddProduct.MaleProduct
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import com.e.iselleradmin.Views.Fragment.subDashboard.AddItem2
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : AppCompatActivity(), FragmentChanger {

    var currentFragment= String()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        val bundle = intent.extras
        if (bundle!=null){
            when (bundle.getString("fragment")){
                FEMALE_PRODUCT->changeFragment(FemaleProduct.newInstance())
                MALE_PRODUCT->changeFragment(MaleProduct.newInstance())

            }
        }

//        changeFragment(AddItemFragmenrt.newInstance())
    }

    override fun changeFragment(fragmentTitle: FragmentTitle) {
        toolbar!!.title = fragmentTitle.title
        currentFragment=fragmentTitle.title
        changeView(fragmentTitle)
    }

    private fun changeView(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){
            if (currentFragment== FEMALE_PRODUCT){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
//            else{
//                changeFragment(AddItemFragmenrt.newInstance())
//            }
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode ==Activity.RESULT_OK){
            if (requestCode == AddItem2.RESULT_IMAGE_LOAD){
//                AddItem2(this@AddProductActivity)
//                    .getImages(data)
            }
        }
    }

    companion object{
        const val HOME_PAGE = "Add Item Categories"
        const val FEMALE_PRODUCT = "Women Product Category"
        const val MALE_PRODUCT = "Men Product Category"
        const val PRODUCT_NAME ="productName"
        const val PRODUCT_CATEGORY ="productCategory"
    }
}
