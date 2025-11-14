package pe.edu.upc.polarnet.core.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.polarnet.core.ui.theme.polarNetColors
import pe.edu.upc.polarnet.features.auth.presentation.login.LoginViewModel

@Composable
fun ProfileScreen(
    loginViewModel: LoginViewModel,
    onLogout: () -> Unit = {}
) {
    val loggedUser = loginViewModel.loggedUser.collectAsState().value
    val colors = MaterialTheme.polarNetColors

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header con gradiente
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            colors.gradientStart,
                            colors.gradientMiddle,
                            colors.gradientEnd
                        )
                    )
                )
                .padding(horizontal = 24.dp, vertical = 48.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Avatar circular
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.onPrimary,
                    shadowElevation = 8.dp
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            modifier = Modifier.size(60.dp),
                            tint = colors.gradientMiddle
                        )
                    }
                }

                // Nombre
                Text(
                    text = loggedUser?.fullName ?: "Usuario desconocido",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Rol con badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            when (loggedUser?.role?.name) {
                                "CLIENT" -> Icons.Default.PersonOutline
                                "PROVIDER" -> Icons.Default.Business
                                else -> Icons.Default.Help
                            },
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = when (loggedUser?.role?.name) {
                                "CLIENT" -> "Cliente"
                                "PROVIDER" -> "Proveedor"
                                else -> "Rol desconocido"
                            },
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }

        // Contenido scrolleable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sección: Información Personal
            SectionHeader(
                icon = Icons.Default.Info,
                title = "Información Personal"
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileInfoItem(
                        icon = Icons.Default.Email,
                        label = "Correo electrónico",
                        value = loggedUser?.email ?: "No disponible"
                    )

                    loggedUser?.phone?.let {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
                        ProfileInfoItem(
                            icon = Icons.Default.Phone,
                            label = "Teléfono",
                            value = it
                        )
                    }

                    loggedUser?.location?.let {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
                        ProfileInfoItem(
                            icon = Icons.Default.LocationOn,
                            label = "Ubicación",
                            value = it
                        )
                    }

                    loggedUser?.companyName?.let {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
                        ProfileInfoItem(
                            icon = Icons.Default.Business,
                            label = "Empresa",
                            value = it
                        )
                    }
                }
            }

            // Sección: Cuenta
            SectionHeader(
                icon = Icons.Default.Settings,
                title = "Configuración de Cuenta"
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileInfoItem(
                        icon = Icons.Default.Badge,
                        label = "ID de Usuario",
                        value = loggedUser?.id?.toString() ?: "N/A"
                    )

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )

                    ProfileInfoItem(
                        icon = Icons.Default.VerifiedUser,
                        label = "Estado de cuenta",
                        value = "Activa"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de cerrar sesión
            Button(
                onClick = {
                    loginViewModel.logout()
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.critical.color
                )
            ) {
                Icon(
                    Icons.Default.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cerrar Sesión",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionHeader(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun ProfileInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}