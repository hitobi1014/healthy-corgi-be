package com.hc.workout.domain

import com.hc.workout.domain.policy.validation.isSamePicDateAndWorkoutDate
import com.hc.workout.domain.policy.validation.isWorkoutDateInCurrentWeek
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate


class WorkoutDateDomainTest {

    @Test
    @DisplayName("운동사진 촬영일자와 운동일자가 일치할때")
    fun isSameDate_whenPictureDateAndWorkoutDateIsSame_shouldReturnTrue() {
        //given
        val pictureDate = LocalDate.of(2024, 12, 24)
        val workoutDate = LocalDate.of(2024, 12, 24)

        //when
        val result = isSamePicDateAndWorkoutDate(pictureDate, workoutDate)

        //then
        assertThat(result).isTrue
    }

    @Test
    @DisplayName("운동사진 촬영일자와 운동일자가 불일치할때")
    fun isSameDate_whenPictureDateAndWorkoutDateIsNotSame_shouldThrowIllegalStateException() {
        //given
        val pictureDate = LocalDate.of(2024, 12, 24)
        val workoutDate = LocalDate.of(2024, 12, 23)

        //when
        //then
        assertThatThrownBy { isSamePicDateAndWorkoutDate(pictureDate, workoutDate) }
            .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    @DisplayName("운동일자가 이번주 범위안에 있는경우")
    fun isWorkoutDateInCurrentWeek_whenDateIsInRange_shouldReturnTrue() {
        //given
        val workoutDate = LocalDate.of(2024, 12, 25)

        //when
        val result = isWorkoutDateInCurrentWeek(workoutDate)

        //then
        assertThat(result).isTrue
    }

    @Test
    @DisplayName("운동일자가 이번주 범위를 벗어난경우")
    fun isInCurrentWeekRange_whenDateOutInRange_shouldThrowIllegalStateException() {
        //given
        val workoutDate = LocalDate.of(2024, 12, 21)

        //when
        //then
        assertThatThrownBy { isWorkoutDateInCurrentWeek(workoutDate) }
            .isInstanceOf(IllegalStateException::class.java)
    }
}