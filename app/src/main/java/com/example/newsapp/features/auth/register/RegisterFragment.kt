package com.example.newsapp.features.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newsapp.data.entities.UserDto
import com.example.newsapp.databinding.FragmentRegisterBinding
import com.example.newsapp.di.NewsManager
import com.example.newsapp.features.auth.MyViewModelFactory
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
                        ErrorType.EmptyEmail -> TODO()
                        ErrorType.EmptyName -> TODO()
                        ErrorType.EmptyPassword -> TODO()
                        ErrorType.EmptyPhoneNumber -> TODO()
                        ErrorType.InvalidEmailFormat -> TODO()
                        ErrorType.InvalidPasswordFormat -> TODO()
                        ErrorType.InvalidPhoneNumberFormat -> TODO()
                    }
                }
                RegisterResult.RegisterError -> {
                    TODO()
                }
                RegisterResult.RegisterSuccessful -> {
                    TODO()
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
