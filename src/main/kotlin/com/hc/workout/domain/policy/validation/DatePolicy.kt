package com.hc.workout.domain.policy.validation

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

/**
 * 1. 운동일자와 사진 촬영일이 같은지 비교
 * 2. 운동일자가 today 기준 이전 월요일 ~ 이후 일요일 사이에 있는지 범위 검증
 * 3.
 */


/**
 * 운동일자가 현재일기준 직전 월요일 ~ 직후 일요일 범위 안에 있는지 검증
 * @param 운동일
 * @return 날짜범위 안에 있으면 true, 아니면 false
 */
fun isWorkoutDateInCurrentWeek(workoutDate: LocalDate): Boolean {
    val today = LocalDate.now()
    val previousMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val nextSunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

    if (!workoutDate.isBefore(previousMonday) && !workoutDate.isAfter(nextSunday)) {
        return true
    }

    throw IllegalStateException("이번주에 진행한 운동만 등록이 가능합니다.")
}

fun isSameDate(pictureDate: LocalDate, workoutDate: LocalDate): Boolean {
    if (pictureDate.isEqual(workoutDate)) {
        return true
    }
    throw IllegalStateException("촬영일자와 운동일자가 다릅니다.")
}