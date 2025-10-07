package pe.edu.upc.polarnet.features.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.polarnet.core.ui.theme.PolarNetTheme
import pe.edu.upc.polarnet.features.auth.domain.models.UserRole
import pe.edu.upc.polarnet.features.auth.presentation.di.PresentationModule.getLoginViewModel

@Composable
fun Login(
    viewModel: LoginViewModel,
    onLoginCliente: () -> Unit,
    onLoginProveedor: () -> Unit
) {
    val username = viewModel.username.collectAsState()
    val password = viewModel.password.collectAsState()
    val user = viewModel.user.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    val isVisible = remember { mutableStateOf(false) }

    // Navegar según el rol cuando el login sea exitoso
    LaunchedEffect(user.value) {
        user.value?.let { loggedUser ->
            when (loggedUser.role) {
                UserRole.CLIENTE -> onLoginCliente()
                UserRole.PROVEEDOR -> onLoginProveedor()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username.value,
            onValueChange = {
                viewModel.updateUsername(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
            placeholder = {
                Text(text = "Username")
            },
            enabled = !isLoading.value
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                viewModel.updatePassword(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null)
            },
            placeholder = {
                Text("Password")
            },
            visualTransformation = if (isVisible.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        isVisible.value = !isVisible.value
                    }
                ) {
                    Icon(
                        if (isVisible.value) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = null
                    )
                }
            },
            enabled = !isLoading.value
        )

        Button(
            onClick = {
                viewModel.login()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            enabled = !isLoading.value && username.value.isNotEmpty() && password.value.isNotEmpty()
        ) {
            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                Text(text = "Login")
            }
        }

        errorMessage.value?.let { error ->
            Text(
                text = error,
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val viewModel = getLoginViewModel()
    PolarNetTheme {
        Login(viewModel, {}, {})
    }
}