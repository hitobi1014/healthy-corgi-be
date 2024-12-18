package com.hc.workout.infrastructure.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Table(name = "hc_workout_photo")
class WorkoutPhotoEntity(

    @Comment("사진원본파일명")
    val originName: String,

    @Comment("사진URL")
    val photoUrl: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    val id: Long? = null,
)