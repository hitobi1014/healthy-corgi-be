package com.hc.workout.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import com.hc.workout.domain.policy.validation.*
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import java.time.LocalDate


class WorkoutDateDomainTest {

    @Test
    fun isSameDateTest() {
        //given
        val pictureDate = LocalDate.of(2024, 12, 24)
        val workoutDate = LocalDate.of(2024, 12, 24)

        //when
        val result = isSameDate(pictureDate, workoutDate)

        //then
        assertThat(result).isTrue
    }

    @Test
    fun isNotSameDate() {
        //given
        val pictureDate = LocalDate.of(2024, 12, 24)
        val workoutDate = LocalDate.of(2024, 12, 23)

        //when
        //then
        assertThatThrownBy { isSameDate(pictureDate, workoutDate) }
            .isInstanceOf(IllegalStateException::class.java)
    }
}