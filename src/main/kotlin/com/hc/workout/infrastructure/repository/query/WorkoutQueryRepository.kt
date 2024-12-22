package com.hc.workout.infrastructure.repository.query

import com.hc.workout.infrastructure.entity.WorkoutVerificationEntity
import java.time.LocalDate

interface WorkoutQueryRepository {

    /**
     * 운영자 -> 회원 운동인증 목록 조회
     * @param startDate 조회 시작일
     * @param endDate 조회 종료일
     * @return 해당 기간내에 인증한 운동인증 목록
     */
    fun findWorkoutByDateBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<WorkoutVerificationEntity>?
}