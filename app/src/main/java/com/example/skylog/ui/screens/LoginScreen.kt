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
import kotlin.math.sin
import kotlin.random.Random

// ── Palette ───────────────────────────────────────────────────────────────────
private val NavyDeep    = Color(0xFF0A0E1A)
private val NavyMid     = Color(0xFF0D1625)
private val CardBg      = Color(0xCC0F1630)
private val AccentBlue  = Color(0xFF3A6BC8)
private val AccentLight = Color(0xFF6495ED)
private val TextPrimary = Color(0xFFE8ECF4)
private val TextMuted   = Color(0xFF8AA0C8)
private val BorderBlue  = Color(0x406495ED)

// ── Star data ─────────────────────────────────────────────────────────────────
private data class LoginStar(val x: Float, val y: Float, val r: Float, val phase: Float)

private val loginStars: List<LoginStar> = List(60) {
    LoginStar(
        x     = Random.nextFloat(),
        y     = Random.nextFloat() * 0.88f,
        r     = Random.nextFloat() * 1.4f + 0.4f,
        phase = Random.nextFloat() * 6.28f
    )
}

// ── Screen ────────────────────────────────────────────────────────────────────
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email           by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "stars")
    val twinkle by infiniteTransition.animateFloat(
        initialValue  = 0f,
        targetValue   = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing)
        ),
        label = "twinkle"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(NavyDeep, NavyMid, Color(0xFF0D1B3E)))
            ),
        contentAlignment = Alignment.Center
    ) {

        // ── Starfield ──
        Canvas(modifier = Modifier.fillMaxSize()) {
            loginStars.forEach { star ->
                val alpha = (sin((twinkle + star.phase).toDouble()) * 0.35 + 0.65).toFloat()
                drawCircle(
                    color  = Color.White.copy(alpha = alpha),
                    radius = star.r,
                    center = Offset(star.x * size.width, star.y * size.height)
                )
            }
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color(0xFF0D1B3E)),
                    startY = size.height * 0.72f,
                    endY   = size.height
                )
            )
        }

        // ── Card ──
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            shape  = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardBg),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderBlue)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ── Logo row ──
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier              = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AccentBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector        = Icons.Outlined.FlightTakeoff,
                            contentDescription = null,
                            tint               = AccentLight,
                            modifier           = Modifier.size(22.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text          = "Sky",
                        fontSize      = 20.sp,
                        fontWeight    = FontWeight.Bold,
                        color         = TextPrimary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text          = "Log",
                        fontSize      = 20.sp,
                        fontWeight    = FontWeight.Bold,
                        color         = AccentLight,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text       = "Welcome Back",
                    fontSize   = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                    modifier   = Modifier.align(Alignment.Start)
                )
                Text(
                    text     = "Sign in to continue your journey",
                    fontSize = 13.sp,
                    color    = TextMuted,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(Modifier.height(24.dp))

                // ── Email ──
                LoginTextField(
                    value         = email,
                    onValueChange = { email = it },
                    label         = "Email Address",
                    placeholder   = "alex@example.com",
                    leadingIcon   = Icons.Outlined.Email,
                    keyboardType  = KeyboardType.Email
                )

                Spacer(Modifier.height(14.dp))

                // ── Password ──
                LoginTextField(
                    value                = password,
                    onValueChange        = { password = it },
                    label                = "Password",
                    placeholder          = "Your password",
                    leadingIcon          = Icons.Outlined.Lock,
                    keyboardType         = KeyboardType.Password,
                    isPassword           = true,
                    passwordVisible      = passwordVisible,
                    onTogglePassword     = { passwordVisible = !passwordVisible }
                )

                // ── Forgot password ──
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    TextButton(
                        onClick      = { /* TODO: forgot password */ },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text       = "Forgot password?",
                            fontSize   = 12.sp,
                            color      = AccentLight.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // ── Login button ──
                Button(
                    onClick  = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Login,
                        contentDescription = null,
                        modifier           = Modifier.size(18.dp),
                        tint               = TextPrimary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text          = "Sign In",
                        fontSize      = 15.sp,
                        fontWeight    = FontWeight.SemiBold,
                        color         = TextPrimary,
                        letterSpacing = 0.3.sp
                    )
                }

                Spacer(Modifier.height(20.dp))

                // ── Divider + register link ──
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier  = Modifier.weight(1f),
                        color     = BorderBlue,
                        thickness = 0.5.dp
                    )
                    Text(
                        text     = "  new to skylog?  ",
                        fontSize = 11.sp,
                        color    = TextMuted.copy(alpha = 0.5f)
                    )
                    HorizontalDivider(
                        modifier  = Modifier.weight(1f),
                        color     = BorderBlue,
                        thickness = 0.5.dp
                    )
                }

                Spacer(Modifier.height(14.dp))

                TextButton(onClick = onRegisterClick) {
                    Text(
                        text       = "Create an Account",
                        fontSize   = 13.sp,
                        color      = AccentLight,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// ── Reusable styled text field ────────────────────────────────────────────────
@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePassword: (() -> Unit)? = null
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        modifier      = Modifier.fillMaxWidth(),
        label         = { Text(label, fontSize = 12.sp) },
        placeholder   = { Text(placeholder, color = TextMuted.copy(alpha = 0.4f), fontSize = 13.sp) },
        singleLine    = true,
        shape         = RoundedCornerShape(12.dp),
        leadingIcon   = {
            Icon(
                imageVector        = leadingIcon,
                contentDescription = null,
                tint               = AccentLight.copy(alpha = 0.6f),
                modifier           = Modifier.size(20.dp)
            )
        },
        trailingIcon = if (isPassword) ({
            IconButton(onClick = { onTogglePassword?.invoke() }) {
                Icon(
                    imageVector        = if (passwordVisible) Icons.Outlined.VisibilityOff
                    else Icons.Outlined.Visibility,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint               = TextMuted.copy(alpha = 0.5f),
                    modifier           = Modifier.size(20.dp)
                )
            }
        }) else null,
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor        = TextPrimary,
            unfocusedTextColor      = TextPrimary,
            focusedBorderColor      = AccentBlue,
            unfocusedBorderColor    = BorderBlue,
            focusedLabelColor       = AccentLight,
            unfocusedLabelColor     = TextMuted.copy(alpha = 0.6f),
            cursorColor             = AccentLight,
            focusedContainerColor   = AccentBlue.copy(alpha = 0.06f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.03f)
        )
    )
}