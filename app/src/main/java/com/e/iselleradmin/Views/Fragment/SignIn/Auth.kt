package com.e.iselleradmin.Views.Fragment.SignIn


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.e.iselleradmin.Database.FDatabase

import com.e.iselleradmin.R
import com.e.iselleradmin.Views.Activity.HomeActivity
import com.e.iselleradmin.Views.Activity.LoginActivity
import com.e.iselleradmin.Views.Activity.SignUpActivity
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_auth.view.*


class Auth : FragmentTitle() {

    private var fDB: FDatabase? = null
    private var progressDialog:ProgressDialog?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        setUpView(view)
        return view
    }


    private fun setUpView(view: View) {
        fDB = FDatabase(context!!)

        view.regBtn.setOnClickListener {

            val intent = Intent(context, SignUpActivity::class.java)
            startActivity(intent)
            (activity)!!.finish()
        }

        view.loginBtn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val getUsername = view!!.username.text.toString()
        val getPassword = view!!.password.text.toString()

        when {
            getUsername.isEmpty() -> {
                Toast.makeText(context!!, "Username is required...", Toast.LENGTH_LONG).show()
            }
            getPassword.isEmpty() -> {
                Toast.makeText(context!!, "Password is required...", Toast.LENGTH_LONG).show()
            }
            else -> {
                progressDialog = ProgressDialog.show(context, "Login", "Processing...", false, false)
                fDB!!.firebaseAuthm()!!.signInWithEmailAndPassword(getUsername, getPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            checkUserExist()
                        }
                    }
            }
        }
    }

    private fun checkUserExist(){
        val userId = fDB!!.firebaseAuthm()!!.currentUser!!.uid
        val user = fDB!!.firebaseAuthm()!!.currentUser

        fDB!!.getDataBase("Admin","Registration")!!.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.hasChild(userId)){
                    progressDialog!!.cancel()
                    user!!.isEmailVerified

                    startActivity(Intent(context, HomeActivity::class.java)
                        .addFlags
                    (Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    activity!!.finish()
                }
                else{
                    Toast.makeText(context, "Check your email or password... Or Register...", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            Auth().apply {
                //                title = Auth.THERAPY_TIME
                arguments = Bundle().apply {
                    title = LoginActivity.login
                }
            }
    }

}
