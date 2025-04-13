package com.hc.workout.domain.mapper

import com.hc.member.infrastructure.entity.MemberEntity
import com.hc.workout.domain.arregates.WorkoutPhoto
import com.hc.workout.domain.arregates.WorkoutVerification
import com.hc.workout.infrastructure.entity.WorkoutPhotoEntity
import com.hc.workout.infrastructure.entity.WorkoutVerificationEntity

fun WorkoutVerificationEntity.toDomain(): WorkoutVerification {
    return WorkoutVerification(
        id = this.id!!,
        type = this.type,
        name = this.name,
        date = this.date,
//        satisfaction = this.satisfaction,
        photos = this.photos.toDomainList()
    )
}

fun WorkoutVerification.toEntity(memberEntity: MemberEntity): WorkoutVerificationEntity {
    return WorkoutVerificationEntity(
        id = this.id,
        type = this.type,
        name = this.name,
        date = this.date,
//        satisfaction = this.satisfaction,
        photos = this.photos.toEntityList(),
        member = memberEntity
    )
}

fun List<WorkoutPhotoEntity>.toDomainList(): MutableList<WorkoutPhoto> {
    return this.map { it.toDomain() }.toMutableList()
}

fun WorkoutPhotoEntity.toDomain(): WorkoutPhoto {
    return WorkoutPhoto(
        id = this.id!!,
        originName = this.originName,
        photoUrl = this.photoUrl,
        fileSize = this.fileSize,
        deviceMaker = this.deviceMaker,
        deviceModel = this.deviceModel,
    )
}

fun List<WorkoutPhoto>.toEntityList(): MutableList<WorkoutPhotoEntity> {
    return this.map { it.toEntity() }.toMutableList()
}

fun WorkoutPhoto.toEntity(): WorkoutPhotoEntity {
    return WorkoutPhotoEntity(
        id = this.id,
        originName = this.originName,
        photoUrl = this.photoUrl,
        fileSize = this.fileSize,
        deviceMaker = this.deviceMaker,
        deviceModel = this.deviceModel,
    )
}