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
                        ErrorType.EmptyEmail ->
                            binding.userEmailET.error = "Email can't be empty"
                        ErrorType.EmptyPassword ->
                            binding.userPasswordET.error = "Password can't be empty"
                        ErrorType.InvalidEmailFormat ->
                            binding.userEmailET.error = "Invalid Email"
                    }
                }
                LoginResult.Success -> {
                    requireActivity().finish()
                    viewModel.loginSuccessful()
                }
                LoginResult.WrongCredentials -> {
                    Toast.makeText(requireContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.loginNavigationLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    LoginNavigation.NavigateToHome -> findNavController().navigate(R.id.newsActivity)
                    LoginNavigation.NavigateToRegister -> findNavController().navigate(R.id.registerFragment)
                }
            }
        }
    }

    private fun doRegister() {
        viewModel.registerClicked()
    }

    private fun doLogin() {
        val email = binding.userEmailET.getTrimmedText()
        val password = binding.userPasswordET.getTrimmedText()
        viewModel.login(email, password)
    }
}
