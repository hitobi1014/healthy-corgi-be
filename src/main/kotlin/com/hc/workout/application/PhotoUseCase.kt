package com.hc.workout.application

import com.hc.workout.application.dto.PhotoMetadata
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface PhotoUseCase {
    fun verifyWorkoutPicture(picture: MultipartFile, workoutDate: LocalDate): PhotoMetadata
}