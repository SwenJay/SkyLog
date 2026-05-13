package com.example.skylog.data.repository

import com.example.skylog.data.model.FlightLog
import com.example.skylog.data.remote.SupabaseClientProvider.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class FlightRepository {
    suspend fun addFlightLog(
        flightDate: String,
        aircraftType: String,
        departureAirport: String,
        arrivalAirport: String,
        duration: Double,
        pilotRole: String,
        nightHours: Double,
        simulatorHours: Double,
        remarks: String
    ): Result<Unit> {

        return try {
            val currentUser = supabase.auth.currentUserOrNull()

            if (currentUser == null) {
                return Result.failure(
                    Exception("User not logged in")
                )
            }

            val flightLog = FlightLog(
                user_id = currentUser.id,
                flight_date = flightDate,
                aircraft_type = aircraftType,
                departure_airport = departureAirport,
                arrival_airport = arrivalAirport,
                duration = duration,
                pilot_role = pilotRole,
                night_hours = nightHours,
                simulator_hours = simulatorHours,
                remarks = remarks
            )
            supabase
                .from("flight_logs")
                .insert(flightLog)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}