package pe.edu.upc.polarnet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import pe.edu.upc.polarnet.core.navigation.AppNavigation
import pe.edu.upc.polarnet.core.ui.theme.PolarNetTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PolarNetTheme {
                AppNavigation()
            }
        }
    }
}
