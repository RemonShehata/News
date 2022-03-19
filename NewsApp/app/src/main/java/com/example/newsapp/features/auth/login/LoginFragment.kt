package com.example.newsapp.features.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentLoginBinding
import com.example.newsapp.di.NewsManager
import com.example.newsapp.features.auth.AuthViewModel
import com.example.newsapp.features.auth.MyViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: AuthViewModel by viewModels {
        MyViewModelFactory(NewsManager.userRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            doLogin()
        }

        binding.registerBtn.setOnClickListener {
            doRegister()
        }

        viewModel.loginResultLiveData.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().finish()
                findNavController().navigate(R.id.newsActivity)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter valid email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun doRegister() {
        findNavController().navigate(R.id.registerFragment)
    }

    private fun doLogin() {
        val email = binding.userEmailET.text.toString().trim()
        val password = binding.userPasswordET.text.toString().trim()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "please fill all the fields!", Toast.LENGTH_SHORT)
                .show()
        } else {
            viewModel.login(email, password)
        }
    }
}