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
import com.example.contactmanager.contactInfo
import com.example.contactmanager.databinding.FragmentAddContactBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddContactFragment : Fragment() {

    private lateinit var view : FragmentAddContactBinding
    private lateinit var databaseref : DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var nav: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentAddContactBinding.inflate(inflater, container, false)
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()

    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        nav= Navigation.findNavController(view)
    }

    private fun registerEvents() {
        view.addcontactbtn.setOnClickListener {
            val contactname = view.nameET.text
            val contactno = view.phonenoET.text

            if (contactname.toString().isNotEmpty() && contactno.toString().isNotEmpty()) {
                val info = contactInfo(contactname.toString(), contactno.toString())
                databaseref = FirebaseDatabase.getInstance().reference.child(auth.currentUser?.uid.toString())
                        .child(contactname.toString())
                databaseref.setValue(info).addOnSuccessListener {
                    Toast.makeText(context, "Contact Saved", Toast.LENGTH_SHORT).show()
                    contactname?.clear()
                    contactno?.clear()
                    nav.navigate(R.id.action_addContactFragment_to_homeFragment)
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}