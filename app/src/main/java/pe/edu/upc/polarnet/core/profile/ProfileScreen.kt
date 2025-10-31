package pe.edu.upc.polarnet.core.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.polarnet.features.auth.presentation.login.LoginViewModel

@Composable
fun ProfileScreen(loginViewModel: LoginViewModel) {
    val loggedUser = loginViewModel.loggedUser.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Avatar
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Nombre
        Text(
            text = loggedUser?.fullName ?: "Usuario desconocido",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Rol dinámico (CLIENT o PROVIDER)
        Text(
            text = when (loggedUser?.role?.name) {
                "CLIENT" -> "Cliente"
                "PROVIDER" -> "Proveedor"
                else -> "Rol desconocido"
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Datos básicos
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Correo: ${loggedUser?.email ?: "No disponible"}")
                loggedUser?.phone?.let { Text("Teléfono: $it") }
                loggedUser?.location?.let { Text("Ubicación: $it") }
                loggedUser?.companyName?.let { Text("Empresa: $it") }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de cerrar sesión (opcional)
        Button(
            onClick = { /* TODO: implementar logout */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Cerrar sesión")
        }
    }
}
