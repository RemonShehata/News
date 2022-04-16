@file: Suppress("WildcardImport", "NoWildcardImports")

package com.example.newsapp.features.auth.register

import androidx.lifecycle.*
import com.example.newsapp.data.entities.UserDto
import com.example.newsapp.data.repos.UserRepository
import com.example.newsapp.utils.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val registrationResultMutableLiveData = MutableLiveData<RegisterResult>()
    val registrationResultLiveData: LiveData<RegisterResult>
        get() = registrationResultMutableLiveData

    private val registerNavigationMutableLiveData =
        MutableLiveData<SingleEvent<RegisterNavigation>>()
    val registerNavigationLiveData: LiveData<SingleEvent<RegisterNavigation>>
        get() = registerNavigationMutableLiveData

    fun register(user: UserDto) {
        if (user.name.isEmpty()) {
            registrationResultMutableLiveData.value =
                RegisterResult.InvalidData(ErrorType.EmptyName)
        } else if (user.email.isEmpty()) {
            registrationResultMutableLiveData.value =
                RegisterResult.InvalidData(ErrorType.EmptyEmail)
        } else if (!user.email.isValidEmailFormat()) {
            registrationResultMutableLiveData.value =
                RegisterResult.InvalidData(ErrorType.InvalidEmailFormat)
        } else if (user.password.isEmpty()) {
            registrationResultMutableLiveData.value =
                RegisterResult.InvalidData(ErrorType.EmptyPassword)
        } else if (!user.password.isValidPasswordFormat()) {
            registrationResultMutableLiveData.value =
                RegisterResult.InvalidData(ErrorType.InvalidPasswordFormat)
        } else if (user.phoneNumber.isEmpty()) {
            registrationResultMutableLiveData.value =
                RegisterResult.InvalidData(ErrorType.EmptyPhoneNumber)
        } else if (!user.phoneNumber.isValidPhoneNumberFormat()) {
            registrationResultMutableLiveData.value =
                RegisterResult.InvalidData(ErrorType.InvalidPhoneNumberFormat)
        } else {
            // all data is valid
            viewModelScope.launch(ioDispatcher) {
                when (userRepository.registerUser(user.toEntity())) {
                    true -> {
                        registrationResultMutableLiveData.postValue(RegisterResult.RegisterSuccessful)
                    }
                    false -> {
                        registrationResultMutableLiveData.postValue(RegisterResult.RegisterError)
                    }
                }
            }
        }
    }

    fun successfulRegistration() {
        registerNavigationMutableLiveData.value = SingleEvent(RegisterNavigation.NavigateToHome)
    }

    fun navigateToLogin() {
        registerNavigationMutableLiveData.value = SingleEvent(RegisterNavigation.NavigateToLogin)
    }
}

sealed class RegisterResult {
    object RegisterSuccessful : RegisterResult()
    object RegisterError : RegisterResult()
    data class InvalidData(val error: ErrorType) : RegisterResult()
}

sealed class ErrorType {
    object EmptyEmail : ErrorType()
    object InvalidEmailFormat : ErrorType()
    object EmptyPassword : ErrorType()
    object InvalidPasswordFormat : ErrorType()
    object EmptyPhoneNumber : ErrorType()
    object InvalidPhoneNumberFormat : ErrorType()
    object EmptyName : ErrorType()
}

sealed class RegisterNavigation {
    object NavigateToHome : RegisterNavigation()
    object NavigateToLogin : RegisterNavigation()
}

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(userRepository, ioDispatcher) as T
    }
}
