package com.example.skylog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skylog.data.repository.AuthRepository
import io.github.jan.supabase.storage.UploadStatus
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()
    var  authMessage = mutableStateOf("")

    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ){
        viewModelScope.launch {
            val  result = repository.signUp(email,password)

            if (result.isSuccess){
                authMessage.value = "You have been registered successfully"
                kotlinx.coroutines.delay(1500)
                //onSuccess()
            }else{
                authMessage.value =
                    result.exceptionOrNull()?.message ?: "Registration failed"
            }
        }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ){
        viewModelScope.launch {
            val result = repository.login(email, password)

            if (result.isSuccess){
                authMessage.value = "Login Successful"
                onSuccess()
            }else{
                authMessage.value =
                    result.exceptionOrNull()?.message ?: "Login failed"
            }
        }
    }
}