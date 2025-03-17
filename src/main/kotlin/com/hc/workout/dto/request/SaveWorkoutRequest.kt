package com.hc.workout.dto.request

import com.hc.workout.domain.enum.WorkoutType
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class SaveWorkoutRequest(
    /**
     * 운동인증 사진
     * 운동일자
     * 운동종류
     * 만족도
     */

    @field:NotNull(message = "운동일시는 필수입니다.")
    val workoutDateTime: LocalDateTime,

//    @field:Min(1, message = "만족도는 1 이상이어야 합니다.")
//    @field:Max(5, message = "만족도는 5 이하여야 합니다.")
//    val workoutSatisfaction: Int = 1,

    @field:NotNull(message = "운동 종류는 필수입니다.")
    val workoutType: WorkoutType,
    // WorkoutType에 정의된 외 운동 선택시 사용자가 직접 운동명 입력
    val workoutName: String,

    )
