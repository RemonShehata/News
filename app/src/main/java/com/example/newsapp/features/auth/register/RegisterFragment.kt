package com.example.newsapp.features.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.data.entities.User
import com.example.newsapp.databinding.FragmentRegisterBinding
import com.example.newsapp.di.NewsManager
import com.example.newsapp.features.auth.AuthViewModel
import com.example.newsapp.features.auth.MyViewModelFactory

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    // test release branch

    private val viewModel: AuthViewModel by viewModels {
        MyViewModelFactory(NewsManager.userRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerBtn.setOnClickListener {
            doRegister()
        }

        viewModel.registrationResultLiveData.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().finish()
                findNavController().navigate(R.id.newsActivity)
            }
        }
    }

    private fun doRegister() {
        val email = binding.emailET.text.toString().trim()
        val password = binding.passwordET.text.toString().trim()
        val userName = binding.userNameET.text.toString().trim()
        val phoneNumber = binding.phoneNumberET.text.toString().trim()
        if (email.isEmpty() || password.isEmpty() ||
            userName.isEmpty() || phoneNumber.isEmpty()
        ) {
            Toast.makeText(requireContext(), "please fill all the fields!", Toast.LENGTH_SHORT)
                .show()
        } else if (!viewModel.isValidEmail(email)) {
            Toast.makeText(requireContext(), "please Enter a valid email!", Toast.LENGTH_SHORT)
                .show()
        } else if (!viewModel.isValidPhoneNumber(phoneNumber)) {
            Toast.makeText(requireContext(), "please Enter a valid phone number!", Toast.LENGTH_SHORT)
                .show()

        } else {
            val user = User(
                email = email,
                name = userName,
                phoneNumber = phoneNumber,
                password = password
            )
            viewModel.register(user)
        }
    }
}