package com.hc.shared.util

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class S3Service(
    private val amazonS3: AmazonS3,
    @Value("\${cloud.aws.s3.bucket}") private val bucket: String,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 파일 S3에 업로드 후 URL 반환
     * @param file: 파일
     * @param dirName: 경로, 본 프로젝트에서는 기본적으로 인증용 사진으로 사용할것이기때문에 workout
     */
    fun upload(file: MultipartFile, dirName: String): String {
        val fileName = createFileName(file.originalFilename)
        val objectMetadata = createObjectMetadata(file)

        try {
            file.inputStream.use { inputStream ->
                amazonS3.putObject(
                    PutObjectRequest(bucket, "$dirName/$fileName", inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                )
            }
        } catch (e: Exception) {
            log.error("Failed to upload file to S3", e)
            throw StorageException("Failed to store file $fileName", e)
        }

        return getFileUrl("$dirName/$fileName")

    }

    fun deleteFile(fileName: String) {
        try {
            amazonS3.deleteObject(bucket, fileName)
        } catch (e: Exception) {
            log.error("Failed to delete file from S3", e)
            throw StorageException("Failed to delete file $fileName", e)
        }
    }


    fun getFileUrl(fileName: String): String {
        return amazonS3.getUrl(bucket, fileName).toString()
    }

    /**
     * 고유한 파일명 생성, 파일명에 공백은 _로 치환
     */
    private fun createFileName(originFileName: String?): String {
        return "${UUID.randomUUID()}_${originFileName?.replace(" ", "_")}"
    }

    /**
     * S3 메타데이터 생성
     */
    private fun createObjectMetadata(file: MultipartFile): ObjectMetadata {
        return ObjectMetadata().apply {
            contentType = file.contentType
            contentLength = file.size
        }
    }

    fun exists(fileName: String): Boolean {
        return try {
            amazonS3.doesObjectExist(bucket, fileName)
        } catch (e: Exception) {
            log.error("Failed to check file exists in S3", e)
            false
        }
    }

    fun download(fileName: String): ByteArray {
        try {
            val s3Object = amazonS3.getObject(bucket, fileName)
            return s3Object.objectContent.readAllBytes()
        } catch (e: Exception) {
            log.error("Failed to download file from S3", e)
            throw StorageException("Failed to download file $fileName", e)
        }
    }
}