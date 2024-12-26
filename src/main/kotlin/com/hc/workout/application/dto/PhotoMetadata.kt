package com.hc.workout.application.dto

import java.time.LocalDateTime

data class PhotoMetadata(
    val findName: String,
    val fileSize: Long,
    val mimeType: String,
    val deviceModel: String?,
    val originalTime: LocalDateTime,
)
