package com.e.iselleradmin.Views.Fragment.SignUp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.e.iselleradmin.Database.FDatabase
import com.e.iselleradmin.Model.AdminReg
import com.e.iselleradmin.R
import com.e.iselleradmin.Views.Activity.HomeActivity
import com.e.iselleradmin.Views.Activity.SignUpActivity
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_registration_page.view.*


class RegistrationPage : FragmentTitle() {

    lateinit var username: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var company: String


    private var mAuth: FirebaseAuth? = null
    private var adminReg: AdminReg? = null
    private var db: FDatabase? = null

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
        val view = inflater.inflate(R.layout.fragment_registration_page, container, false)

        setContentView(view)
        return view
    }


    private fun setContentView(view: View) {
        db = FDatabase(context!!)

        view.submitBut.setOnClickListener { createAccount() }
    }

    private fun createAccount() {

        username = view!!.username.text.toString().trim()
        phone = view!!.editPhone.text.toString().trim()
        password = view!!.password.text.toString().trim()
        email = view!!.editEmail.text.toString().trim()
        confirmPassword = view!!.confirmpassword.text.toString().trim()
        company = view!!.company.text.toString().trim()

        when {
            username.isEmpty() -> {
                toastMessage("Username is required")
            }
            phone.isEmpty() -> {
                toastMessage("Phone is required")
            }
            !Patterns.PHONE.matcher(phone).matches() || phone.length < 11 || phone.length > 11 -> {
                toastMessage("Invalid phone number")
            }
            email.isEmpty() -> {
                toastMessage("Email is required")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                toastMessage("Invalid email")
            }
            password !=confirmPassword->{
                toastMessage("Email don't match")

            }
            else -> {
                adminReg = AdminReg()

                adminReg!!.email = email
                adminReg!!.password = password
                adminReg!!.phone = phone
                adminReg!!.username = username
                adminReg!!.company = company



                db!!.firebaseAuthm()!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val adminUserId = db!!.firebaseAuthm()!!.currentUser!!.uid
                        val adminDb = db!!.getDataBase("Admin", "Registration")!!.child(adminUserId)
                        adminDb.setValue(adminReg)

                        val intent= Intent(context, HomeActivity::class.java)
                        startActivity(intent)
                        (activity)!!.finish()

                    }
                }.addOnFailureListener { e->

                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RegistrationPage().apply {
                arguments = Bundle().apply {
                    title = SignUpActivity.Register
                }
            }
    }

    private fun toastMessage(mgs: String) {
        Toast.makeText(context, mgs, Toast.LENGTH_LONG).show()
    }
}
