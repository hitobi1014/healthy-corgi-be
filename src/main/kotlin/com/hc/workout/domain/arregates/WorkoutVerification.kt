package com.hc.workout.domain.arregates

import com.hc.workout.application.dto.PhotoMetadata
import com.hc.workout.domain.enum.WorkoutType
import com.hc.workout.dto.request.SaveWorkoutRequest
import java.time.LocalDate

data class WorkoutVerification(
    val id: Long?,
    val type: WorkoutType,
    val name: String,
    val date: LocalDate,
//    val satisfaction: Int,
    val photos: MutableList<WorkoutPhoto>,
) {
    companion object {
        fun addWorkoutVerification(
            dto: SaveWorkoutRequest,
            photoInfoList: MutableList<PhotoUploadInfo>,
        ): WorkoutVerification {
            return WorkoutVerification(
                id = null,
                type = dto.workoutType,
                name = dto.workoutName,
                date = dto.workoutDateTime.toLocalDate(),
//                satisfaction = dto.workoutSatisfaction,
                photos = WorkoutPhoto.addWorkoutPhotoList(photoInfoList)
            )
        }
    }
}


data class WorkoutPhoto(
    val id: Long?,
    val originName: String,
    val photoUrl: String,
    val fileSize: Long,
    val deviceMaker: String,
    val deviceModel: String,
) {
    companion object {
        fun addWorkoutPhotoList(photoInfoList: MutableList<PhotoUploadInfo>): MutableList<WorkoutPhoto> {
            return photoInfoList.map { photoInfo ->
                addWorkoutPhoto(photoInfo.metadata, photoInfo.savedUrl)
            }.toMutableList()
        }

        fun addWorkoutPhoto(dto: PhotoMetadata, savedUrl: String): WorkoutPhoto {
            return WorkoutPhoto(
                id = null,
                originName = dto.fileName,
                photoUrl = savedUrl,
                fileSize = dto.fileSize,
                deviceMaker = dto.deviceMaker,
                deviceModel = dto.deviceModel ?: ""
            )
        }
    }
}
