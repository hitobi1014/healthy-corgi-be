package com.hc.workout.infrastructure.entity

import com.hc.member.infrastructure.entity.MemberEntity
import com.hc.shared.common.BaseEntity
import com.hc.workout.domain.enum.WorkoutType
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDate

@Entity
@Table(name = "hc_workout_verification")
class WorkoutVerificationEntity(
    @Comment("운동 종류")
    val type: WorkoutType,

    @Column(length = 20)
    @Comment("운동 이름")
    val name: String,

    @Comment("운동 날짜")
    val date: LocalDate,

    @Comment("운동 만족도")
    val satisfaction: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: MemberEntity,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "workout_verification_id")
    val photos: MutableList<WorkoutPhotoEntity> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    val id: Long? = null,
) : BaseEntity()