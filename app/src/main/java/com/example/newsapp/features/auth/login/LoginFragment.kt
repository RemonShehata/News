package com.example.newsapp.features.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentLoginBinding
import com.example.newsapp.di.NewsManager
import com.example.newsapp.utils.getTrimmedText

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels {
        MyViewModelFactory(NewsManager.userRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater).apply {
            loginBtn.setOnClickListener {
                doLogin()
            }

            registerBtn.setOnClickListener {
                doRegister()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResultLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoginResult.InvalidData -> {
                    when (result.error) {
                        ErrorType.EmptyEmail -> TODO()
                        ErrorType.EmptyPassword -> TODO()
                        ErrorType.InvalidEmailFormat -> TODO()
                    }
                }
                LoginResult.Success -> {
                    requireActivity().finish()
                    findNavController().navigate(R.id.newsActivity)
                }
                LoginResult.WrongCredentials -> {
                    TODO()
                }
            }
        }
    }

    private fun doRegister() {
        findNavController().navigate(R.id.registerFragment)
    }

    private fun doLogin() {
        val email = binding.userEmailET.getTrimmedText()
        val password = binding.userPasswordET.getTrimmedText()
        viewModel.login(email, password)
    }
}
