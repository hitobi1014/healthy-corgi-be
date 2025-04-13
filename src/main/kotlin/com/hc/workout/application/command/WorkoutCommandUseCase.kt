package com.hc.workout.application.command

import com.hc.workout.dto.request.SaveWorkoutRequest
import org.springframework.web.multipart.MultipartFile

interface WorkoutCommandUseCase {
    fun saveWorkoutVerification(dto: SaveWorkoutRequest, photoList: List<MultipartFile>)
}