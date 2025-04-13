package com.hc.workout.application

import com.hc.workout.domain.policy.validation.*

import com.hc.workout.application.dto.PhotoMetadata
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.mock.web.MockMultipartFile
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PhotoUseCaseTest {
    private lateinit var photoUseCase: PhotoUseCase

    @BeforeEach
    fun setup() {
        photoUseCase = mockk()
    }

    @Test
    @DisplayName("정상적으로 검증, 사진 촬영시간과 운동일자가 동일하고, 사진 촬영일이 인증 주간 범위 안에 있음")
    fun verifyWorkoutPicture_whenPictureAndWorkoutDate_shouldReturnPhotoMetadata() {
        //given
        val workoutDate = LocalDate.of(2024, 12, 26)
        val mockMultipartFile = makeMockMultipartFile()
//        val multipartList = listOf(mockMultipartFile)
        val fileName = "20241226_065800.jpg"

        val expectedMetadata = PhotoMetadata(
            fileName = fileName,
            fileSize = 4L,
            mimeType = "image/jpeg",
            deviceModel = "galaxy s24",
            deviceMaker = "samsung",
            originalTime = LocalDateTime.of(2024, 12, 26, 6, 58, 0)
        )
        val metadataList = listOf(expectedMetadata)

        every {
            photoUseCase.verifyWorkoutPicture(
//                multipartList,
                mockMultipartFile,
                workoutDate
            )
//        } returns metadataList
        } returns expectedMetadata

        //when
//        val result = photoUseCase.verifyWorkoutPicture(multipartList, workoutDate)
        val result = photoUseCase.verifyWorkoutPicture(mockMultipartFile, workoutDate)

        //then
        assertNotNull(result)
        assertEquals(fileName, result.fileName)
        assertTrue(result.fileSize > 0)
        assertEquals("image/jpeg", result.mimeType)
        assertEquals(workoutDate, result.originalTime.toLocalDate())
    }

    @Test
    @DisplayName("촬영일자와 운동일자가 다른경우")
    fun isSamePicDateAndWorkoutDate_whenWrongPictureDate_shouldThrowIllegalStateException() {
        //given
        val workoutDate = LocalDate.of(2024, 12, 20)
        val pictureDate = LocalDate.of(2024, 12, 26)

        //when & then
        assertThrows<IllegalStateException> {
            isSamePicDateAndWorkoutDate(pictureDate, workoutDate)
        }
    }


    private fun makeMockMultipartFile(): MockMultipartFile {
        return MockMultipartFile(
            "picture",
            "20241226_065800.jpg",
            "image/jpeg",
            byteArrayOf(1, 2, 3, 4)
        )
    }
}