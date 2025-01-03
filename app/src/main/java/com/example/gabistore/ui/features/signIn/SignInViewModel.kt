package com.example.gabistore.ui.features.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gabistore.model.repository.user.UserRepository
import com.example.gabistore.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    fun signInUser(onLogging: (String) -> Unit) {

        viewModelScope.launch(coroutineExceptionHandler) {
            val result = userRepository.signIn(email.value!!, password.value!!)
            onLogging(result)
        }

    }

}