package com.hc.workout.ui.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.hc.workout.dto.request.SaveWorkoutRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ModelAttribute

@RestController
@RequestMapping("/api/workout")
// TODO workout 의존성 주입
class WorkoutController(
//    private val workoutUseCase: Workout
) {

    @PostMapping("/picture-verification", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun verifyImage(@ModelAttribute request: SaveWorkoutRequest) {
        // TODO 사진 검증 반환타입


    }
}