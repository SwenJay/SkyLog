package com.example.skylog.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skylog.viewmodel.AuthViewModel
import kotlin.math.sin
import kotlin.random.Random

private val NavyDeep = Color(0xFF0A0E1A)
private val NavyMid = Color(0xFF0D1625)
private val CardBg = Color(0xCC0F1630)
private val AccentBlue = Color(0xFF3A6BC8)
private val AccentLight = Color(0xFF6495ED)
private val TextPrimary = Color(0xFFE8ECF4)
private val TextMuted = Color(0xFF8AA0C8)
private val BorderBlue = Color(0x406495ED)

private data class LoginStar(
    val x: Float,
    val y: Float,
    val r: Float,
    val phase: Float
)

private val loginStars: List<LoginStar> = List(60) {
    LoginStar(
        x = Random.nextFloat(),
        y = Random.nextFloat() * 0.88f,
        r = Random.nextFloat() * 1.4f + 0.4f,
        phase = Random.nextFloat() * 6.28f
    )
}

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val authViewModel: AuthViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "stars")

    val twinkle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing)
        ),
        label = "twinkle"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        NavyDeep,
                        NavyMid,
                        Color(0xFF0D1B3E)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            loginStars.forEach { star ->
                val alpha =
                    (sin((twinkle + star.phase).toDouble()) * 0.35 + 0.65).toFloat()

                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    radius = star.r,
                    center = Offset(
                        star.x * size.width,
                        star.y * size.height
                    )
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardBg)
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "SkyLog Login",
                    color = TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                LoginTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    placeholder = "Enter email",
                    leadingIcon = Icons.Outlined.Email
                )

                Spacer(modifier = Modifier.height(14.dp))

                LoginTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    placeholder = "Enter password",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onTogglePassword = {
                        passwordVisible = !passwordVisible
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val cleanEmail = email.trim()
                        val cleanPassword =  password.trim()

                        when {
                            email.isBlank() -> {
                                authViewModel.authMessage.value = "Email is required"
                            }

                            !android.util.Patterns.EMAIL_ADDRESS
                                .matcher(cleanEmail)
                                .matches() -> {

                                authViewModel.authMessage.value =
                                    "Enter a valid email"
                            }

                            cleanPassword.isBlank() -> {
                                authViewModel.authMessage.value =
                                    "Password is required"
                            }

                            cleanPassword.length < 8 -> {
                                authViewModel.authMessage.value =
                                    "Password must be at least 8 characters"
                            }

                            else -> {
                                authViewModel.login(
                                    email = cleanEmail,
                                    password = cleanPassword
                                ) {
                                    onLoginClick()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Login,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Login")
                }

                Spacer(modifier = Modifier.height(12.dp))

                val message = authViewModel.authMessage.value

                if (message.isNotBlank()) {
                    Text(
                        text = message,
                        color = AccentLight
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = onRegisterClick
                ) {
                    Text("Create Account")
                }
            }
        }
    }
}

@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePassword: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        trailingIcon = if (isPassword) ({
            IconButton(
                onClick = {
                    onTogglePassword?.invoke()
                }
            ) {
                Icon(
                    imageVector = if (passwordVisible)
                        Icons.Outlined.VisibilityOff
                    else
                        Icons.Outlined.Visibility,
                    contentDescription = null
                )
            }
        }) else null,
        visualTransformation = if (
            isPassword && !passwordVisible
        ) PasswordVisualTransformation()
        else VisualTransformation.None
    )
}