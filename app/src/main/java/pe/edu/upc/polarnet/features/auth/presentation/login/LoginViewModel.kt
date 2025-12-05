package pe.edu.upc.polarnet.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.auth.domain.models.User
import pe.edu.upc.polarnet.features.auth.domain.repositories.AuthRepository

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // Usuario autenticado globalmente
    private val _loggedUser = MutableStateFlow<User?>(null)
    val loggedUser: StateFlow<User?> = _loggedUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun updateEmail(value: String) {
        _email.value = value
        _errorMessage.value = null
    }

    fun updatePassword(value: String) {
        _password.value = value
        _errorMessage.value = null
    }

    fun login() {
        if (email.value.isBlank() || password.value.isBlank()) {
            _errorMessage.value = "Por favor, completa todos los campos"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                android.util.Log.d("LoginViewModel", "====================================")
                android.util.Log.d("LoginViewModel", "INICIANDO LOGIN")
                android.util.Log.d("LoginViewModel", "====================================")
                android.util.Log.d("LoginViewModel", "Email: ${email.value}")

                val result = repository.login(email.value, password.value)

                android.util.Log.d("LoginViewModel", "Resultado: $result")

                if (result != null) {
                    android.util.Log.d("LoginViewModel", "Login exitoso: ${result.fullName} (id=${result.id})")
                    _loggedUser.value = result
                } else {
                    android.util.Log.d("LoginViewModel", "Login falló - credenciales incorrectas o error de conexión")
                    _errorMessage.value = "Correo o contraseña incorrectos"
                }
            } catch (e: Exception) {
                android.util.Log.e("LoginViewModel", "Excepción en login: ${e.message}")
                e.printStackTrace()
                _errorMessage.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        _loggedUser.value = null
        _email.value = ""
        _password.value = ""
        _errorMessage.value = null
        println("🔴 Usuario cerró sesión")
    }
}
