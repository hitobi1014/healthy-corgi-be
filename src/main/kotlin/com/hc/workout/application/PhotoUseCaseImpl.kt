package com.hc.workout.application

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifIFD0Directory
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.hc.workout.application.dto.PhotoMetadata
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.hc.workout.domain.policy.validation.*
import java.time.LocalDate

@Service
class PhotoUseCaseImpl : PhotoUseCase {
    private val log = LoggerFactory.getLogger(PhotoUseCaseImpl::class.java)

    override fun verifyWorkoutPicture(
        picture: MultipartFile,
        workoutDate: LocalDate,
    ): PhotoMetadata {
        // step00. 메타정보 추출
        val photoMetadata = extractMetaInfo(picture)

        // step01. 사진 촬영시각이 운동일자와 동일한지 검증
        isSamePicDateAndWorkoutDate(photoMetadata.originalTime.toLocalDate(), workoutDate)

        return photoMetadata
    }

    /**
     * 표준 EXIF 메타 정보를 추출해서 DTO로 변환
     * @param 모바일로 업로드한 사진파일
     * @return 추출된 메타정보 -> PhotoMetaData
     */
    private fun extractMetaInfo(file: MultipartFile): PhotoMetadata {
        val metadata = ImageMetadataReader.readMetadata(file.inputStream)

        // EXIF 메타데이터 디렉토리 가져오기
        val exifSubIfd = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
        val exifIfd0 = metadata.getFirstDirectoryOfType(ExifIFD0Directory::class.java)

        val originalDate = exifSubIfd?.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)
            ?: exifIfd0?.getString(ExifIFD0Directory.TAG_DATETIME)

        if (originalDate == null) {
            throw IllegalStateException("촬영시간이 존재하지 않습니다.")
        }

        // 디바이스 정보 확인
        val deviceMake = exifIfd0?.getString(ExifIFD0Directory.TAG_MAKE)
        val deviceModel = exifIfd0?.getString(ExifIFD0Directory.TAG_MODEL)

        val takenDate = convertToDateTimeByTimeString(originalDate)
        
        val contentType = file.contentType ?: ""
        val originalFilename = file.originalFilename ?: ""
        val fileSize = file.size // 저장은 byte 단위, 클라이언트 출력할때 해당 화면에서 변환해서 보이기

        log.info("원본 촬영시간(String) : $originalDate")
        log.info("toDateTime 촬영시간: $takenDate")
        log.info("파일명 : $originalFilename")
        log.info("파일사이즈 : $fileSize")
        log.info("기기 모델 : $deviceModel")
        log.info("mimeType : $contentType")
        log.info("기기 제조사 : $deviceMake")

        return PhotoMetadata(
            fileName = originalFilename,
            fileSize = fileSize,
            mimeType = contentType,
            deviceModel = deviceModel,
            originalTime = takenDate
        )
    }

    private fun convertToDateTimeByTimeString(originalTimeString: String): LocalDateTime {
        return originalTimeString.let { timeStr ->
            LocalDateTime.parse(
                timeStr.replace("T", " "),
                DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss")
            )
        }
    }
}