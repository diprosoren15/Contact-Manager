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
import com.example.contactmanager.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var nav: NavController
    private lateinit var view: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentSignInBinding.inflate(inflater, container, false)
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

        view.signinbtn.setOnClickListener {

            val email = view.emailET.text.toString().trim()
            val password = view.passwordET.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Signed In Successfully", Toast.LENGTH_SHORT)
                            .show()
                        nav.navigate(R.id.action_signInFragment_to_homeFragment)
                    } else
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT)
                            .show()
                }
            } else {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        view.signupclick.setOnClickListener {
            nav.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
}