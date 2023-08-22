package com.example.contactmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.contactmanager.R
import com.example.contactmanager.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var nav: NavController
    private lateinit var view: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentSignUpBinding.inflate(inflater, container, false)
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }

    private fun init(view: View) {
        nav = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

    private fun registerEvents() {
        view.btnsignup.setOnClickListener {

            val email = view.emailET.text.toString().trim()
            val password = view.passwordET.text.toString().trim()
            val validatePassword = view.retypepassET.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && validatePassword.isNotEmpty()) {
                if (password == validatePassword) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(context, "Registered Sucessfully", Toast.LENGTH_SHORT)
                                .show()
                            nav.navigate(R.id.action_signUpFragment_to_homeFragment)
                        } else {
                            Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            } else
                Toast.makeText(context, "Field cannot be empty", Toast.LENGTH_SHORT).show()
        }

        view.btnsignin.setOnClickListener {
            nav.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }
}