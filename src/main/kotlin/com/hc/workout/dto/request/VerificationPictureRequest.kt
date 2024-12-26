package com.hc.workout.dto.request

import org.jetbrains.annotations.NotNull
import org.springframework.web.multipart.MultipartFile

data class VerificationPictureRequest(
    @field:NotNull
    val photo: MultipartFile,
    val additionalInfo: String? = null,
)
