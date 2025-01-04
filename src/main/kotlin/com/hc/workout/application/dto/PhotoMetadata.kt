package com.hc.workout.application.dto

import java.time.LocalDateTime

data class PhotoMetadata(
    val fileName: String,
    val fileSize: Long,
    val mimeType: String,
    val deviceMaker: String,
    val deviceModel: String?,
    val originalTime: LocalDateTime,
)
