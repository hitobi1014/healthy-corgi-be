package com.hc.workout.application.command

import com.hc.workout.dto.request.SaveWorkoutRequest

interface WorkoutCommandUseCase {
    fun saveWorkoutVerification(dto: SaveWorkoutRequest)
}