package com.example.skylog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DashboardNavy = Color(0xFF0A0E1A)
private val DashboardBlue = Color(0xFF3A6BC8)
private val DashboardLight = Color(0xFF6495ED)
private val DashboardText = Color(0xFFE8ECF4)

@Composable
fun DashboardScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        DashboardNavy,
                        Color(0xFF0D1625)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Text(
                text = "Pilot Dashboard",
                color = DashboardText,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Welcome back Captain ✈",
                color = DashboardLight
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = DashboardBlue.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Total Flight Hours",
                        color = DashboardText
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "125 Hours",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashboardLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    // Add flight screen later
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Add Flight Log")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Flight history later
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Flight,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("View Flight History")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Profile later
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Profile")
            }
        }
    }
}