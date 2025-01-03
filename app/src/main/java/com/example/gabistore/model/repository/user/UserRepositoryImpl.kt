package com.example.gabistore.model.repository.user

import android.content.SharedPreferences
import com.example.gabistore.model.net.ApiService
import com.example.gabistore.model.repository.TokenInMemory
import com.example.gabistore.util.VALUE_SUCCESS
import com.google.gson.JsonObject

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPref: SharedPreferences
) : UserRepository {

    override suspend fun signUp(name: String, username: String, password: String): String {

        val jsonObject = JsonObject().apply {
            addProperty("name", name)
            addProperty("email", username)
            addProperty("password", password)
        }

        val result = apiService.signUp(jsonObject)
        if (result.success) {
            TokenInMemory.refreshToken(username, result.token)
            saveToken(result.token)
            saveUserName(username)
            saveUserLoginTime()
            return VALUE_SUCCESS
        } else {
            return result.message
        }

    }

    override suspend fun signIn(username: String, password: String): String {

        val jsonObject = JsonObject().apply {
            addProperty("email", username)
            addProperty("password", password)
        }

        val result = apiService.signIn(jsonObject)
        if (result.success) {
            TokenInMemory.refreshToken(username, result.token)
            saveToken(result.token)
            saveUserName(username)
            saveUserLoginTime()
            return VALUE_SUCCESS
        } else {
            return result.message
        }

    }

    override fun signOut() {
        TokenInMemory.refreshToken(null, null)
        sharedPref.edit().clear().apply()
    }

    override fun loadToken() {
        TokenInMemory.refreshToken(getUserName(), getToken())
    }

    override fun saveToken(newToken: String) {
        sharedPref.edit().putString("token", newToken).apply()
    }

    override fun getToken(): String? {
        return sharedPref.getString("token", null)
    }

    override fun saveUserName(username: String) {
        sharedPref.edit().putString("username", username).apply()
    }

    override fun getUserName(): String? {
        return sharedPref.getString("username", null)
    }

    override fun saveUserLocation(address: String, postalCode: String) {

        sharedPref.edit().apply {
            putString("address" , address)
            putString("postalCode" , postalCode)
        }.apply()

    }

    override fun getUserLocation(): Pair<String, String> {

        val address = sharedPref.getString("address" , "Click to add")!!
        val postalCode = sharedPref.getString("postalCode" , "Click to add")!!

        return Pair(address , postalCode)

    }

    override fun saveUserLoginTime() {

        val now = System.currentTimeMillis()
        sharedPref.edit().putString("login_time" , now.toString()).apply()

    }

    override fun getUserLoginTime(): String {

        return sharedPref.getString("login_time" , "0")!!

    }

}