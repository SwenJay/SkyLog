package com.example.skylog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skylog.data.repository.FlightRepository
import io.github.jan.supabase.storage.UploadStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FlightViewModel : ViewModel() {
    private val repository = FlightRepository()
     var  flightMessage = mutableStateOf("")

    //function
    fun addFlight(
        flightDate: String,
        aircraftType: String,
        departureAirport: String,
        arrivalAirport: String,
        duration: String,
        pilotRole: String,
        nightHours: String,
        simulatorHours: String,
        remarks: String,
        onSuccess: () -> Unit
    ){
        when{
            flightDate.isBlank() -> {
                flightMessage.value = "Flight date is required"
                return
            }
            aircraftType.isBlank() -> {
                flightMessage.value = "Aircraft type is required"
                return
            }
            departureAirport.isBlank() -> {
                flightMessage.value = "Departure airport is required"
                return
            }
            arrivalAirport.isBlank() -> {
                flightMessage.value = "Arrival airport is required"
                return
            }
            duration.isBlank() -> {
                flightMessage.value = "Flight duration is required"
                return
            }
            duration.toDoubleOrNull() == null -> {
                flightMessage.value = "Enter valid duration"
                return
            }
            duration.toDouble() <= 0 -> {
                flightMessage.value = "Duration must be greater than 0"
                return
            }
            pilotRole.isBlank() -> {
                flightMessage.value = "Pilot role is required"
                return
            }
        }
        viewModelScope.launch {
            val result = repository.addFlightLog(
                flightDate = flightDate,
                aircraftType = aircraftType,
                departureAirport = departureAirport,
                arrivalAirport = arrivalAirport,
                duration = duration.toDouble(),
                pilotRole = pilotRole,
                nightHours = nightHours.toDoubleOrNull() ?: 0.0,
                simulatorHours = simulatorHours.toDoubleOrNull() ?: 0.0,
                remarks = remarks
            )

            if (result.isSuccess){
                flightMessage.value = "Flight logged Successfully"
                delay(1000)
                onSuccess()
            }else{
                flightMessage.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to save flight"
            }
        }
    }
}