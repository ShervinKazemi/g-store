package com.example.gabistore.ui.features.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gabistore.model.data.Product
import com.example.gabistore.model.repository.cart.CartRepository
import com.example.gabistore.model.repository.product.ProductRepository
import com.example.gabistore.model.repository.user.UserRepository
import com.example.gabistore.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) :ViewModel() {
    val email = mutableStateOf("")
    val address = mutableStateOf("")
    val postalCode = mutableStateOf("")
    val loginTime = mutableStateOf("")

    val showLocationDialog = mutableStateOf(false)

    fun loadUserData() {

        email.value = userRepository.getUserName()!!
        address.value = userRepository.getUserLocation().first
        postalCode.value = userRepository.getUserLocation().second
        loginTime.value = userRepository.getUserLoginTime()

    }

    fun signOut() {
        userRepository.signOut()
    }

    fun setUserLocation(address :String , postalCode :String) {

        userRepository.saveUserLocation(address , postalCode)


    }



}