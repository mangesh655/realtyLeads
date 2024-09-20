package com.example.reltyleads.theme

import android.graphics.Color
import android.os.Build
import androidx.activity.SystemBarStyle
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.reltyleads.common.theme.model.ComposeTheme

/** Light default theme color scheme. */
val LightColorScheme = lightColorScheme(
    primary = primary,
    primaryContainer = primaryContainer,
    secondary = secondary,
    onPrimary = onPrimary,
    background = background
)

/** Dark default theme color scheme. */
val DarkColorScheme = darkColorScheme()


@Composable
fun RealtyTheme(
    enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit = { _, _ -> },
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val darkTheme: Boolean = isSystemInDarkTheme()

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    ComposeTheme(
        colorScheme = colorScheme,
        detectDarkMode = darkTheme
    )

    enableEdgeToEdge(
        SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { darkTheme },
        SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { darkTheme }
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography
    ) {
        CompositionLocalProvider(LocalRippleTheme provides RealtyRippleTheme) {
            content()
        }
    }
}