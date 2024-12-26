package com.hc.workout.application

import org.springframework.web.multipart.MultipartFile

interface PhotoUseCase {
    fun verifyWorkoutPicture(picture: MultipartFile)
}