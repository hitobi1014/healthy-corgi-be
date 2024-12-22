package com.hc.workout.infrastructure.repository.query

import com.hc.workout.infrastructure.entity.QWorkoutVerificationEntity.workoutVerificationEntity
import com.hc.workout.infrastructure.entity.WorkoutVerificationEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class WorkoutQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : WorkoutQueryRepository {

    override fun findWorkoutByDateBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<WorkoutVerificationEntity>? {

        return jpaQueryFactory.selectFrom(workoutVerificationEntity)
            .where(workoutVerificationEntity.date.between(startDate, endDate))
            .fetch()
    }
}