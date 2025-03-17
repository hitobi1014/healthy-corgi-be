package com.hc.workout.ui.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.hc.workout.dto.request.SaveWorkoutRequest
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/workout")
// TODO workout 의존성 주입
class WorkoutController(
//    private val workoutUseCase: Workout
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/test")
    fun test(): String {
        println("테스트222")
        logger.info { "test" }
        return "테스트"
    }

    @PostMapping("/picture-verification", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun verifyImage(
        @RequestPart(value = "workoutData") request: SaveWorkoutRequest,
        @RequestPart(value = "photoList") photoList: List<MultipartFile>,
    ) {
        // TODO 사진 검증 반환타입
        println("여기타나")

        logger.info { "==============" }
    }


}