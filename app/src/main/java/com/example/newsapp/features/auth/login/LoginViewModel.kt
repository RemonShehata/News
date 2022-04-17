@file: Suppress("WildcardImport", "NoWildcardImports")

package com.example.newsapp.features.auth.login

import androidx.lifecycle.*
import com.example.newsapp.data.repos.UserRepo
import com.example.newsapp.utils.SingleEvent
import com.example.newsapp.utils.isValidEmailFormat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepo: UserRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val loginResultMutableLiveData = MutableLiveData<LoginResult>()
    val loginResultLiveData: LiveData<LoginResult>
        get() = loginResultMutableLiveData

    private val loginNavigationMutableLiveData = MutableLiveData<SingleEvent<LoginNavigation>>()
    val loginNavigationLiveData: LiveData<SingleEvent<LoginNavigation>>
        get() = loginNavigationMutableLiveData

    fun login(email: String, password: String) {
        if (email.isEmpty()) {
            loginResultMutableLiveData.value = LoginResult.InvalidData(ErrorType.EmptyEmail)
        } else if (!email.isValidEmailFormat()) {
            loginResultMutableLiveData.value = LoginResult.InvalidData(ErrorType.InvalidEmailFormat)
        } else if (password.isNullOrEmpty()) {
            loginResultMutableLiveData.value = LoginResult.InvalidData(ErrorType.EmptyPassword)
        } else {
            viewModelScope.launch(ioDispatcher) {
                when (userRepo.login(email, password)) {
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

    fun loginSuccessful(){
        loginNavigationMutableLiveData.value = SingleEvent(LoginNavigation.NavigateToHome)
    }

    fun registerClicked(){
        loginNavigationMutableLiveData.value = SingleEvent(LoginNavigation.NavigateToRegister)
    }
    // random change for tests
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

sealed class LoginNavigation {
    object NavigateToRegister : LoginNavigation()
    object NavigateToHome : LoginNavigation()
}

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(
    private val userRepo: UserRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(userRepo, ioDispatcher) as T
    }
}
