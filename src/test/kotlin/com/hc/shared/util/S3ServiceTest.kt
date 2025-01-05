package com.hc.shared.util

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.PutObjectResult
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectInputStream
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.net.URL

class S3ServiceTest {

    private lateinit var amazonS3: AmazonS3
    private lateinit var s3Service: S3Service
    private val bucket = "test-bucket"

    @BeforeEach
    fun setUp() {
        amazonS3 = mockk(relaxed = true)
        s3Service = S3Service(
            amazonS3,
            bucket
        )
    }

    @Nested
    @DisplayName("upload 메서드")
    inner class UploadTest {

        @Test
        @DisplayName("파일 업로드 후 URL 반환")
        fun uploadS3Test() {
            //given
            val file = createMockMultipartFile()
            val dirName = "workout"
            val expectedUrl = "https://$bucket.s3.ap-northeast-2.amazonaws.com/$dirName/test.jpg"

            every {
                amazonS3.putObject(any())
            } returns PutObjectResult()

            every { amazonS3.getUrl(any(), any()) } returns URL(expectedUrl)

            //when
            val result = s3Service.upload(file, dirName)

            //then
            assertThat(result).isEqualTo(expectedUrl)
            verify(exactly = 1) {
                amazonS3.putObject(any())
            }
        }

        @Test
        @DisplayName("업로드 실패 시 StorageException 던진다")
        fun upload_failure() {
            //given
            val file = createMockMultipartFile()
            val dirName = "workout"

            every { amazonS3.putObject(any()) } throws StorageException("S3 upload failed")

            //when
            //then
            assertThrows<StorageException> {
                s3Service.upload(file, dirName)
            }
        }
    }

    @Nested
    @DisplayName("deleteFile 메서드")
    inner class DeleteFileTest {
        @Test
        @DisplayName("파일을 성공적으로 삭제한다.")
        fun delete_success() {
            //given
            val fileName = "workout/test.jpg"
            every { amazonS3.deleteObject(bucket, fileName) } returns Unit

            //when
            //then
            assertDoesNotThrow { s3Service.deleteFile(fileName) }
            verify(exactly = 1) { amazonS3.deleteObject(bucket, fileName) }
        }

        @Test
        @DisplayName("삭제 실패 시 StorageException을 던진다.")
        fun delete_failure() {
            //given
            val fileName = "workout/test.jpg"
            every {
                amazonS3.deleteObject(
                    bucket,
                    fileName
                )
            } throws StorageException("S3 delete failed")
            //when
            //then
            assertThrows<StorageException> {
                s3Service.deleteFile(fileName)
            }
        }
    }

    @Nested
    @DisplayName("download 메서드")
    inner class DownloadTest {
        @Test
        @DisplayName("파일을 성공적으로 다운로드한다")
        fun download_success() {
            //given
            val fileName = "workout/test.jpg"
            val content = "test content".toByteArray()
            val s3Object = mockk<S3Object>(relaxed = true)
            val inputStream = ByteArrayInputStream(content)
            val s3ObjectInputStream = S3ObjectInputStream(inputStream, null)

            every { amazonS3.getObject(bucket, fileName) } returns s3Object
            every { s3Object.objectContent } returns s3ObjectInputStream

            //when
            val result = s3Service.download(fileName)

            //then
            assertThat(result).isEqualTo(content)
            verify(exactly = 1) {
                amazonS3.getObject(
                    bucket,
                    fileName
                )
            }
        }

        @Test
        @DisplayName("다운로드 실패 시 StorageException을 던진다")
        fun download_failure() {
            // given
            val fileName = "workout/test.jpg"
            every {
                amazonS3.getObject(bucket, fileName)
            } throws RuntimeException("Download failed")

            // when & then
            assertThrows<StorageException> {
                s3Service.download(fileName)
            }
        }
    }


    private fun createMockMultipartFile(
        name: String = "test.jpg",
        contentType: String = "image/jpeg",
        content: String = "test image content",
    ): MultipartFile {
        return MockMultipartFile(
            name,
            name,
            contentType,
            content.toByteArray()
        )
    }
}