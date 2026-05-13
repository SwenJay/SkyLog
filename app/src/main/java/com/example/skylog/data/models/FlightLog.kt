package com.example.skylog.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FlightLog(
    val id: String? = null,
    val user_id: String,
    val flight_date: String,
    val aircraft_type: String,
    val departure_airport: String,
    val arrival_airport: String,
    val duration: Double,
    val pilot_role: String,
    val night_hours: Double = 0.0,
    val simulator_hours: Double = 0.0,
    val remarks: String? = null
)