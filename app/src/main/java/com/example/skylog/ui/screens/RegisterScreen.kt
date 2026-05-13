package com.example.skylog.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.FlightTakeoff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
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

// ── Palette ──────────────────────────────────────────────────────────────────
private val NavyDeep    = Color(0xFF0A0E1A)
private val NavyMid     = Color(0xFF0D1625)
private val CardBg      = Color(0xCC0F1630)   // 80 % opacity
private val AccentBlue  = Color(0xFF3A6BC8)
private val AccentLight = Color(0xFF6495ED)
private val TextPrimary = Color(0xFFE8ECF4)
private val TextMuted   = Color(0xFF8AA0C8)
private val BorderBlue  = Color(0x406495ED)
private val StrengthRed    = Color(0xFFE24B4A)
private val StrengthAmber  = Color(0xFFEF9F27)
private val StrengthBlue   = Color(0xFF3A6BC8)
private val StrengthGreen  = Color(0xFF1D9E75)

// ── Star data ─────────────────────────────────────────────────────────────────
private data class Star(val x: Float, val y: Float, val r: Float, val phase: Float)

private val stars: List<Star> = List(60) {
    Star(
        x     = Random.nextFloat(),
        y     = Random.nextFloat() * 0.88f,
        r     = Random.nextFloat() * 1.4f + 0.4f,
        phase = Random.nextFloat() * 6.28f
    )
}

// ── Screen ────────────────────────────────────────────────────────────────────
@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit) {
    val authViewModel: AuthViewModel = viewModel()

    var name     by remember { mutableStateOf("") }
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Twinkling animation
    val infiniteTransition = rememberInfiniteTransition(label = "stars")
    val twinkle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue  = 6.28f,
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
            stars.forEach { star ->
                val alpha = ((sin((twinkle + star.phase).toDouble()) * 0.35 + 0.65)).toFloat()
                drawCircle(
                    color  = Color.White.copy(alpha = alpha),
                    radius = star.r,
                    center = Offset(star.x * size.width, star.y * size.height)
                )
            }
            // horizon glow
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
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
                        text       = "Sky",
                        fontSize   = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text       = "Log",
                        fontSize   = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color      = AccentLight,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text       = "Create Account",
                    fontSize   = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                    modifier   = Modifier.align(Alignment.Start)
                )
                Text(
                    text     = "Start logging your flights today",
                    fontSize = 13.sp,
                    color    = TextMuted,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(Modifier.height(24.dp))

                // ── Full Name ──
                SkyTextField(
                    value         = name,
                    onValueChange = { name = it },
                    label         = "Full Name",
                    placeholder   = "Alex Mercer",
                    leadingIcon   = Icons.Outlined.Person
                )

                Spacer(Modifier.height(14.dp))

                // ── Email ──
                SkyTextField(
                    value         = email,
                    onValueChange = { email = it },
                    label         = "Email Address",
                    placeholder   = "alex@example.com",
                    leadingIcon   = Icons.Outlined.Email,
                    keyboardType  = KeyboardType.Email
                )

                Spacer(Modifier.height(14.dp))

                // ── Password ──
                SkyTextField(
                    value         = password,
                    onValueChange = { password = it },
                    label         = "Password",
                    placeholder   = "Min. 8 characters",
                    leadingIcon   = Icons.Outlined.Lock,
                    keyboardType  = KeyboardType.Password,
                    isPassword    = true,
                    passwordVisible      = passwordVisible,
                    onTogglePassword     = { passwordVisible = !passwordVisible }
                )

                // ── Strength bar ──
                Spacer(Modifier.height(8.dp))
                PasswordStrengthBar(password = password)

                Spacer(Modifier.height(24.dp))

                // ── Register button ──
                Button(
                    onClick = {
                        authViewModel.register(email = email, password = password) {
                            // onRegisterSuccess()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.RocketLaunch,
                        contentDescription = null,
                        modifier           = Modifier.size(18.dp),
                        tint               = TextPrimary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text       = "Register",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = TextPrimary,
                        letterSpacing = 0.3.sp
                    )
                }

                // ── Auth message ──
                val message = authViewModel.authMessage.value
                if (message.isNotBlank()) {
                    Spacer(Modifier.height(14.dp))
                    val isError = message.contains("error", ignoreCase = true) ||
                            message.contains("fail",  ignoreCase = true) ||
                            message.contains("invalid", ignoreCase = true)
                    val chipBg   = if (isError) StrengthRed.copy(alpha = 0.12f)
                    else StrengthGreen.copy(alpha = 0.12f)
                    val chipText = if (isError) StrengthRed else StrengthGreen
                    val chipIcon = if (isError) Icons.Outlined.ErrorOutline
                    else Icons.Outlined.CheckCircle

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape    = RoundedCornerShape(20.dp),
                        color    = chipBg,
                        border   = androidx.compose.foundation.BorderStroke(
                            1.dp, chipText.copy(alpha = 0.3f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector        = chipIcon,
                                contentDescription = null,
                                tint               = chipText,
                                modifier           = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(text = message, fontSize = 12.sp, color = chipText)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // ── Divider + sign-in link ──
                Row(
                    modifier             = Modifier.fillMaxWidth(),
                    verticalAlignment    = Alignment.CenterVertically
                ) {
                    Divider(modifier = Modifier.weight(1f), color = BorderBlue, thickness = 0.5.dp)
                    Text(
                        text     = "  already have an account?  ",
                        fontSize = 11.sp,
                        color    = TextMuted.copy(alpha = 0.5f)
                    )
                    Divider(modifier = Modifier.weight(1f), color = BorderBlue, thickness = 0.5.dp)
                }

                Spacer(Modifier.height(14.dp))

                TextButton(onClick = { /* navigate to login */ }) {
                    Text(
                        text       = "Sign in to SkyLog",
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
private fun SkyTextField(
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
        trailingIcon  = if (isPassword) ({
            IconButton(onClick = { onTogglePassword?.invoke() }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff
                    else Icons.Outlined.Visibility,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint     = TextMuted.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
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

// ── Password strength bar ─────────────────────────────────────────────────────
@Composable
private fun PasswordStrengthBar(password: String) {
    val score = remember(password) {
        var s = 0
        if (password.length >= 8)                          s++
        if (password.length >= 12)                         s++
        if (password.any { it.isUpperCase() } ||
            password.any { it.isDigit() })                 s++
        if (password.any { !it.isLetterOrDigit() })        s++
        s
    }
    val segmentColor = when {
        score <= 1 -> StrengthRed
        score == 2 -> StrengthAmber
        score == 3 -> StrengthBlue
        else       -> StrengthGreen
    }
    Row(
        modifier            = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (index < score) segmentColor
                        else Color.White.copy(alpha = 0.08f)
                    )
            )
        }
    }
}