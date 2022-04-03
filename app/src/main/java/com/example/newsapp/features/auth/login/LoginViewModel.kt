@file: Suppress("WildcardImport", "NoWildcardImports")

package com.example.newsapp.features.auth.login

import androidx.lifecycle.*
import com.example.newsapp.data.repos.UserRepository
import com.example.newsapp.utils.isValidEmailFormat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val loginResultMutableLiveData = MutableLiveData<LoginResult>()
    val loginResultLiveData: LiveData<LoginResult>
        get() = loginResultMutableLiveData

    fun login(email: String, password: String) {
        if (email.isEmpty()) {
            loginResultMutableLiveData.value = LoginResult.InvalidData(ErrorType.EmptyEmail)
        } else if (!email.isValidEmailFormat()) {
            loginResultMutableLiveData.value = LoginResult.InvalidData(ErrorType.InvalidEmailFormat)
        } else if (password.isNullOrEmpty()) {
            loginResultMutableLiveData.value = LoginResult.InvalidData(ErrorType.EmptyPassword)
        } else {
            viewModelScope.launch(ioDispatcher) {
                when (userRepository.login(email, password)) {
                    true -> {
                        loginResultMutableLiveData.postValue(LoginResult.Success)
                    }
                    false -> {
                        loginResultMutableLiveData.postValue(LoginResult.WrongCredentials)
                    }
                }
            }
        }
    }
}

sealed class LoginResult {
    object Success : LoginResult()
    object WrongCredentials : LoginResult()
    data class InvalidData(val error: ErrorType) : LoginResult()
}

sealed class ErrorType {
    object EmptyEmail : ErrorType()
    object InvalidEmailFormat : ErrorType()
    object EmptyPassword : ErrorType()
}

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(userRepository, ioDispatcher) as T
    }
}
