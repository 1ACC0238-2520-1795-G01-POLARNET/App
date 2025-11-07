package pe.edu.upc.polarnet.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pe.edu.upc.polarnet.core.ui.theme.polarNetColors
import pe.edu.upc.polarnet.features.auth.domain.models.UserRole

@Composable
fun Login(
    viewModel: LoginViewModel,
    onLoginCliente: () -> Unit,
    onLoginProveedor: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val email = viewModel.email.collectAsState()
    val password = viewModel.password.collectAsState()
    val user = viewModel.loggedUser.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    val isVisible = remember { mutableStateOf(false) }
    val colors = MaterialTheme.polarNetColors

    LaunchedEffect(user.value) {
        user.value?.let { loggedUser ->
            when (loggedUser.role) {
                UserRole.CLIENT -> onLoginCliente()
                UserRole.PROVIDER -> onLoginProveedor()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header con gradiente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                colors.gradientStart,
                                colors.gradientMiddle,
                                colors.gradientEnd
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "PolarNet",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Sistema de Gestión de Refrigeración",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                    )
                }
            }

            // Formulario de login
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .offset(y = (-40).dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo de email
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = viewModel::updateEmail,
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Email",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        label = { Text("Correo electrónico") },
                        placeholder = { Text("usuario@ejemplo.com") },
                        enabled = !isLoading.value,
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )

                    // Campo de contraseña
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = viewModel::updatePassword,
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Contraseña",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        label = { Text("Contraseña") },
                        placeholder = { Text("Ingresa tu contraseña") },
                        visualTransformation = if (isVisible.value)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { isVisible.value = !isVisible.value }) {
                                Icon(
                                    if (isVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (isVisible.value) "Ocultar contraseña" else "Mostrar contraseña",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        enabled = !isLoading.value,
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )

                    // Mensaje de error
                    errorMessage.value?.let { error ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = colors.critical.colorContainer
                        ) {
                            Text(
                                text = error,
                                color = colors.critical.onColorContainer,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón de login
                    Button(
                        onClick = { viewModel.login() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = !isLoading.value && email.value.isNotEmpty() && password.value.isNotEmpty(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        if (isLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Iniciar Sesión",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Divider
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f))
                        Text(
                            text = "o",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f))
                    }

                    // Botón de registro
                    TextButton(
                        onClick = onNavigateToRegister,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "¿No tienes cuenta? Regístrate aquí",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Texto de pie de página
            Text(
                text = "PolarNet v1.0 - Lima, Perú",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}