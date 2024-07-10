package com.app.majuapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color.Transparent, secondary = PurpleGrey80, tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Transparent, secondary = PurpleGrey40, tertiary = Pink40
)


/* HomeScreen*/
val defaultPadding = 20.dp
val roundedCornerPadding = 20.dp


/* WalkScreen */
val dialogDefaultPadding = 26.dp
val dialogButtonRoundedCorner = 8.dp
val dialogCornerPadding = 14.dp

/* CultureScreen */
val cultureDetailTitleFontSize = 24.sp
const val cultureDetailTextWithIconSize = 18
val cultureDetailIntervalSize = 4.dp
val cultureDetailMediumSpacerSize = 16.dp
val cultureDetailSmallSpacerSize = 8.dp
val cultureDefaultPadding = 24.dp


@Composable
fun MajuAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //typography: CustomBodyTypography = MajuAppTheme(),
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = customTypoGraphy, content = content
    )
} // End of MajuAppTheme