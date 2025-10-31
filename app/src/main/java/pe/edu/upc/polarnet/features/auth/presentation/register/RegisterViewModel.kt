package pe.edu.upc.polarnet.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.auth.domain.models.UserRole
import pe.edu.upc.polarnet.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(
        email: String,
        password: String,
        confirmPassword: String,
        fullName: String,
        role: UserRole,
        companyName: String,
        phone: String,
        location: String
    ) {
        // Validaciones de campos obligatorios
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank() || fullName.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Los campos básicos son obligatorios")
            return
        }

        // Validación específica para proveedores
        if (role == UserRole.PROVIDER && companyName.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "El nombre de la empresa es obligatorio para proveedores")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = _uiState.value.copy(error = "Email inválido")
            return
        }

        if (password.length < 6) {
            _uiState.value = _uiState.value.copy(error = "La contraseña debe tener al menos 6 caracteres")
            return
        }

        if (password != confirmPassword) {
            _uiState.value = _uiState.value.copy(error = "Las contraseñas no coinciden")
            return
        }

        // Validación de teléfono si se proporciona
        if (phone.isNotBlank() && phone.length < 7) {
            _uiState.value = _uiState.value.copy(error = "El teléfono debe tener al menos 7 dígitos")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val result = authRepository.register(
                    email = email,
                    password = password,
                    fullName = fullName,
                    role = role,
                    companyName = companyName.ifBlank { null },
                    phone = phone.ifBlank { null },
                    location = location.ifBlank { null }
                )

                if (result != null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRegistered = true,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "El email ya está registrado o hubo un error"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al registrarse"
                )
            }
        }
    }

    // Función removida - ya no es necesaria porque las validaciones se manejan al hacer submit
}

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val error: String? = null
)