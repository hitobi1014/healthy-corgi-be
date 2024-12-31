package com.hc.workout.application.command

import com.hc.workout.application.PhotoUseCase
import com.hc.workout.domain.policy.validation.isWorkoutDateInCurrentWeek
import com.hc.workout.dto.request.SaveWorkoutRequest
import org.springframework.stereotype.Service

@Service
class WorkoutCommandUseCaseImpl(
    private val photoUseCase: PhotoUseCase,
) : WorkoutCommandUseCase {

    override fun saveWorkoutVerification(dto: SaveWorkoutRequest) {

        // step01. 사진 검증


        // step02. 운동일자가 인증 주간범위 안에 있는지 검증
//        isWorkoutDateInCurrentWeek(workoutDate)


        // step03. 운동 인증 저장


        TODO("Not yet implemented")
    }
}