package com.hc.workout.domain.arregates

import com.hc.workout.application.dto.PhotoMetadata

data class PhotoUploadInfo(
    val metadata: PhotoMetadata,
    val savedUrl: String,
)
