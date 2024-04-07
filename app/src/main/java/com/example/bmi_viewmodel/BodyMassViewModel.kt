package com.example.bmi_viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BodyMassViewModel: ViewModel() {

    val heightInput: MutableState<String> = mutableStateOf("")
    val weightInput: MutableState<String> = mutableStateOf("")

    private var bmi: MutableState<Float> = mutableStateOf(0.0f)
    private var bmiStatus: MutableState<String> = mutableStateOf("")

    // Methods for updating member variables when data changes
    fun updateHeightInput(newHeight: String) {
        heightInput.value = newHeight.replace(',', '.')
        calculateBMI()
    }

    fun updateWeightInput(newWeight: String) {
        weightInput.value = newWeight.replace(',', '.')
        calculateBMI()
    }

    // Getter for the BMI result
    fun getBMI(): MutableState<Float> {
        return bmi
    }

    // Getter for the BMI status
    fun getBMIStatus(): MutableState<String> {
        return bmiStatus
    }

    // Private method for calculation
    private fun calculateBMI() {
        val heightInCm = heightInput.value.toFloatOrNull() ?: 0.0f
        val weightInKg = weightInput.value.toFloatOrNull() ?: 0.0f

        // Convert height from cm to meters
        val heightInMeters = heightInCm / 100

        bmi.value = if (weightInKg > 0 && heightInMeters > 0) {
            weightInKg / (heightInMeters * heightInMeters)
        } else {
            0.0f
        }

        updateBMIStatus()
    }

    // Method to update BMI status
    private fun updateBMIStatus() {
        val bmiValue = bmi.value
        bmiStatus.value = when {
            bmiValue < 18.5f -> "Underweight"
            bmiValue < 24.9f -> "Normal weight"
            bmiValue < 29.9f -> "Overweight"
            else -> "Obese"
        }
    }
}
