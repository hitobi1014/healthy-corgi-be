package com.hc.workout.infrastructure.repository.command

import com.hc.workout.infrastructure.entity.WorkoutVerificationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WorkoutCommandRepository : JpaRepository<WorkoutVerificationEntity, Long>