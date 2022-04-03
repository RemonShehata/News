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
import com.example.newsapp.data.entities.UserDto
import com.example.newsapp.databinding.FragmentRegisterBinding
import com.example.newsapp.di.NewsManager
import com.example.newsapp.utils.getTrimmedText

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by viewModels {
        MyViewModelFactory(NewsManager.userRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater).apply {
            registerBtn.setOnClickListener {
                doRegister()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.registrationResultLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RegisterResult.InvalidData -> {
                    when (result.error) {
                        ErrorType.EmptyEmail -> {
                            binding.emailET.error = "Email can't be empty"
                        }
                        ErrorType.EmptyName -> {
                            binding.userNameET.error = "Name can't be empty"
                        }
                        ErrorType.EmptyPassword -> {
                            binding.passwordET.error =
                                "Password can't be empty"
                        }
                        ErrorType.EmptyPhoneNumber -> {
                            binding.phoneNumberET.error =
                                "Phone number can't be empty"
                        }
                        ErrorType.InvalidEmailFormat -> {
                            binding.emailET.error =
                                "Invalid email address"
                        }
                        ErrorType.InvalidPasswordFormat -> {
                            binding.passwordET.error = "password must be more than 6 chars"
                        }
                        ErrorType.InvalidPhoneNumberFormat -> {
                            binding.phoneNumberET.error =
                                "Invalid phone number"
                        }
                    }
                }
                RegisterResult.RegisterError -> {
                    Toast.makeText(
                        requireContext(),
                        "Email is already registered!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                RegisterResult.RegisterSuccessful -> {
                    requireActivity().finish()
                    findNavController().navigate(R.id.newsActivity)
                }
            }
        }
    }

    private fun doRegister() {
        val email = binding.emailET.getTrimmedText()
        val password = binding.passwordET.getTrimmedText()
        val userName = binding.userNameET.getTrimmedText()
        val phoneNumber = binding.phoneNumberET.getTrimmedText()

        val userDto =
            UserDto(email = email, name = userName, password = password, phoneNumber = phoneNumber)
        viewModel.register(userDto)
    }
}
