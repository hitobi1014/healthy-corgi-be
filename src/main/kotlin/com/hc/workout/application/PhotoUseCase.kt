package com.hc.workout.application

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface PhotoUseCase {
    fun verifyWorkoutPicture(picture: MultipartFile, workoutDate: LocalDate)
}