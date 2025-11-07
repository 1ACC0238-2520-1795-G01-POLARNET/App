package pe.edu.upc.polarnet.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ============================================
// COLOR SCHEMES - Material 3
// ============================================

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

// ============================================
// EXTENDED COLOR SCHEME - Colores semánticos personalizados
// ============================================

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified
)

/**
 * Colores extendidos de PolarNet para estados específicos de temperatura
 * y equipos. Accesibles vía MaterialTheme.colorScheme.polarNetColors
 */
@Immutable
data class PolarNetExtendedColors(
    // Estados de temperatura
    val success: ColorFamily,
    val warning: ColorFamily,
    val critical: ColorFamily,
    val info: ColorFamily,

    // Estados de equipos
    val offline: Color,
    val active: Color,
    val maintenance: Color,

    // Colores para gráficos
    val chartBlue: Color,
    val chartCyan: Color,
    val chartTeal: Color,
    val chartGreen: Color,
    val chartOrange: Color,
    val chartRed: Color,
    val chartPurple: Color,

    // Gradientes
    val gradientStart: Color,
    val gradientMiddle: Color,
    val gradientEnd: Color,
)

private val lightPolarNetExtendedColors = PolarNetExtendedColors(
    success = ColorFamily(
        color = successLight,
        onColor = onSuccessLight,
        colorContainer = successContainerLight,
        onColorContainer = onSuccessContainerLight
    ),
    warning = ColorFamily(
        color = warningLight,
        onColor = onWarningLight,
        colorContainer = warningContainerLight,
        onColorContainer = onWarningContainerLight
    ),
    critical = ColorFamily(
        color = criticalLight,
        onColor = onCriticalLight,
        colorContainer = criticalContainerLight,
        onColorContainer = onCriticalContainerLight
    ),
    info = ColorFamily(
        color = infoLight,
        onColor = onInfoLight,
        colorContainer = infoContainerLight,
        onColorContainer = onInfoContainerLight
    ),
    offline = offlineLight,
    active = activeLight,
    maintenance = maintenanceLight,
    chartBlue = chartBlue,
    chartCyan = chartCyan,
    chartTeal = chartTeal,
    chartGreen = chartGreen,
    chartOrange = chartOrange,
    chartRed = chartRed,
    chartPurple = chartPurple,
    gradientStart = gradientStart,
    gradientMiddle = gradientMiddle,
    gradientEnd = gradientEnd,
)

private val darkPolarNetExtendedColors = PolarNetExtendedColors(
    success = ColorFamily(
        color = successDark,
        onColor = onSuccessDark,
        colorContainer = successContainerDark,
        onColorContainer = onSuccessContainerDark
    ),
    warning = ColorFamily(
        color = warningDark,
        onColor = onWarningDark,
        colorContainer = warningContainerDark,
        onColorContainer = onWarningContainerDark
    ),
    critical = ColorFamily(
        color = criticalDark,
        onColor = onCriticalDark,
        colorContainer = criticalContainerDark,
        onColorContainer = onCriticalContainerDark
    ),
    info = ColorFamily(
        color = infoDark,
        onColor = onInfoDark,
        colorContainer = infoContainerDark,
        onColorContainer = onInfoContainerDark
    ),
    offline = offlineDark,
    active = activeDark,
    maintenance = maintenanceDark,
    chartBlue = chartBlue,
    chartCyan = chartCyan,
    chartTeal = chartTeal,
    chartGreen = chartGreen,
    chartOrange = chartOrange,
    chartRed = chartRed,
    chartPurple = chartPurple,
    gradientStart = gradientStart,
    gradientMiddle = gradientMiddle,
    gradientEnd = gradientEnd,
)

// CompositionLocal para acceder a los colores extendidos
val LocalPolarNetExtendedColors = staticCompositionLocalOf { lightPolarNetExtendedColors }

// Extension para acceder fácilmente a los colores personalizados
val MaterialTheme.polarNetColors: PolarNetExtendedColors
    @Composable
    get() = LocalPolarNetExtendedColors.current

// ============================================
// POLARNET THEME - Tema principal
// ============================================

/**
 * Tema principal de PolarNet con soporte para:
 * - Dynamic Color (Material You) en Android 12+
 * - Modo oscuro
 * - Colores semánticos extendidos
 * - Tipografía personalizada
 *
 * @param darkTheme Activa el modo oscuro
 * @param dynamicColor Activa colores dinámicos en Android 12+ (desactívalo si quieres colores fijos de marca)
 */
@Composable
fun PolarNetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,  // Cambiado a false por defecto para mantener identidad de marca
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkScheme
        else -> lightScheme
    }

    val extendedColors = if (darkTheme) {
        darkPolarNetExtendedColors
    } else {
        lightPolarNetExtendedColors
    }

    CompositionLocalProvider(
        LocalPolarNetExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

// ============================================
// UTILITY FUNCTIONS - Funciones de ayuda
// ============================================

/**
 * Determina el color apropiado para un estado de temperatura
 * @param temperature Temperatura actual
 * @param minTemp Temperatura mínima permitida
 * @param maxTemp Temperatura máxima permitida
 * @param warningThreshold Umbral de advertencia (porcentaje del rango)
 * @return ColorFamily apropiado (success, warning, critical)
 */
@Composable
fun getTemperatureStatusColor(
    temperature: Float,
    minTemp: Float,
    maxTemp: Float,
    warningThreshold: Float = 0.1f
): ColorFamily {
    val colors = MaterialTheme.polarNetColors

    return when {
        temperature < minTemp || temperature > maxTemp -> colors.critical
        temperature < minTemp + (maxTemp - minTemp) * warningThreshold ||
                temperature > maxTemp - (maxTemp - minTemp) * warningThreshold -> colors.warning
        else -> colors.success
    }
}

/**
 * Obtiene el color apropiado para un estado de equipo
 * @param isOnline Si el equipo está conectado
 * @param isActive Si el equipo está funcionando
 * @param inMaintenance Si el equipo está en mantenimiento
 */
@Composable
fun getEquipmentStatusColor(
    isOnline: Boolean,
    isActive: Boolean = false,
    inMaintenance: Boolean = false
): Color {
    val colors = MaterialTheme.polarNetColors

    return when {
        !isOnline -> colors.offline
        inMaintenance -> colors.maintenance
        isActive -> colors.active
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
}