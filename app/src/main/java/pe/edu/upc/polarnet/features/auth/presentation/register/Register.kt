package pe.edu.upc.polarnet.features.auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun Register(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(UserRole.CLIENT) }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val colors = MaterialTheme.polarNetColors

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            onRegisterSuccess()
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
                    .height(160.dp)
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
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Crear Cuenta",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Únete a PolarNet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                    )
                }
            }

            // Formulario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-30).dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Selector de tipo de usuario
                    Text(
                        text = "Tipo de cuenta",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedRole == UserRole.CLIENT,
                            onClick = { selectedRole = UserRole.CLIENT },
                            label = { Text("Cliente") },
                            leadingIcon = if (selectedRole == UserRole.CLIENT) {
                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                            } else null,
                            modifier = Modifier.weight(1f)
                        )

                        FilterChip(
                            selected = selectedRole == UserRole.PROVIDER,
                            onClick = { selectedRole = UserRole.PROVIDER },
                            label = { Text("Proveedor") },
                            leadingIcon = if (selectedRole == UserRole.PROVIDER) {
                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                            } else null,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Divider(modifier = Modifier.padding(vertical = 4.dp))

                    // Nombre completo
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nombre completo") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        singleLine = true,
                        enabled = !uiState.isLoading,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Correo electrónico") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        singleLine = true,
                        enabled = !uiState.isLoading,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Contraseñas en fila
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Contraseña") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            visualTransformation = if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            singleLine = true,
                            enabled = !uiState.isLoading,
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Confirmar") },
                            visualTransformation = if (confirmPasswordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                    Icon(
                                        if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            singleLine = true,
                            enabled = !uiState.isLoading,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Campos opcionales según rol
                    if (selectedRole == UserRole.PROVIDER) {
                        OutlinedTextField(
                            value = companyName,
                            onValueChange = { companyName = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Nombre de empresa *") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Business,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            singleLine = true,
                            enabled = !uiState.isLoading,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Teléfono y ubicación en fila
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Teléfono") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            singleLine = true,
                            enabled = !uiState.isLoading,
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Ciudad") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Place,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            singleLine = true,
                            enabled = !uiState.isLoading,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Mensaje de error
                    uiState.error?.let { error ->
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

                    Spacer(modifier = Modifier.height(4.dp))

                    // Botón de registro
                    Button(
                        onClick = {
                            viewModel.register(
                                email = email,
                                password = password,
                                confirmPassword = confirmPassword,
                                fullName = fullName,
                                role = selectedRole,
                                companyName = companyName,
                                phone = phone,
                                location = location
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = !uiState.isLoading,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Registrarse",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Divider
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
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

                    // Botón de login
                    TextButton(
                        onClick = onNavigateToLogin,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "¿Ya tienes cuenta? Inicia sesión",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}