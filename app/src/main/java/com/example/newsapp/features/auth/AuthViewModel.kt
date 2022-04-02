@file: Suppress("WildcardImport", "NoWildcardImports")

package com.example.newsapp.features.auth

import android.util.Patterns
import androidx.lifecycle.*
import com.example.newsapp.data.entities.User
import com.example.newsapp.data.repos.UserRepository
import com.example.newsapp.utils.PHONE_REGEX
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val registrationResultMutableLiveData = MutableLiveData<Boolean>()
    val registrationResultLiveData: LiveData<Boolean>
        get() = registrationResultMutableLiveData

    private val loginResultMutableLiveData = MutableLiveData<Boolean>()
    val loginResultLiveData: LiveData<Boolean>
        get() = loginResultMutableLiveData

    private val changePasswordResultMutableLiveData = MutableLiveData<Boolean>()
    val changePasswordResultLiveData: LiveData<Boolean>
        get() = changePasswordResultMutableLiveData

    fun login(email: String, password: String) = viewModelScope.launch(ioDispatcher) {
        loginResultMutableLiveData.postValue(userRepository.login(email, password))
    }

    fun register(user: User) = viewModelScope.launch(ioDispatcher) {
        registrationResultMutableLiveData.postValue(userRepository.registerUser(user))
    }

    fun changePassword(email: String, password: String) = viewModelScope.launch(ioDispatcher) {
        changePasswordResultMutableLiveData.postValue(
            userRepository.changePassword(
                email,
                password
            )
        )
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhoneNumber(phone: String): Boolean {
        return phone.matches(Regex(PHONE_REGEX))
    }
}

//@Suppress("UNCHECKED_CAST")
//class MyViewModelFactory(
//    private val userRepository: UserRepository,
//    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return AuthViewModel(userRepository, ioDispatcher) as T
//    }
//}
