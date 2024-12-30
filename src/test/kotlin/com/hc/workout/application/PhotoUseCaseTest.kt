package com.hc.workout.application

import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import java.io.File
import java.io.FileInputStream

class PhotoUseCaseTest {
    private lateinit var photoUseCase: PhotoUseCase


    // TODO 아이폰 사진 테스트

    // TODO 갤럭시 사진 테스트

    @Test
    fun extractMetaInfo_whenGalaxyPicture_shouldReturnPhotoMetadata() {
        //given

        //when

        //then
    }


}

fun main() {
    val photoUseCaseImpl = PhotoUseCaseImpl()

    val imageFile = File("src/test/resources/test-image/20241226_065853.jpg")
    val inputStream = FileInputStream(imageFile)

    val multipart = MockMultipartFile(
        "file",
        imageFile.name,
        "image/jpeg",
        inputStream
    )

    photoUseCaseImpl.extractMetaInfo(multipart)
}