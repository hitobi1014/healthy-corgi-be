package com.hc.workout.application.command

import com.hc.member.infrastructure.entity.MemberEntity
import com.hc.shared.security.MemberContext
import com.hc.shared.util.S3Constants
import com.hc.shared.util.S3Service
import com.hc.workout.application.PhotoUseCase
import com.hc.workout.domain.arregates.PhotoUploadInfo
import com.hc.workout.domain.arregates.WorkoutVerification
import com.hc.workout.domain.mapper.toEntity
import com.hc.workout.domain.policy.validation.isWorkoutDateInCurrentWeek
import com.hc.workout.dto.request.SaveWorkoutRequest
import com.hc.workout.infrastructure.repository.command.WorkoutCommandRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class WorkoutCommandUseCaseImpl(
    private val photoUseCase: PhotoUseCase,
    private val workoutCommandRepository: WorkoutCommandRepository,
    private val memberContext: MemberContext,
    private val s3Service: S3Service,
) : WorkoutCommandUseCase {

    // TODO 사진 저장 테스트
    override fun saveWorkoutVerification(dto: SaveWorkoutRequest, photoList: List<MultipartFile>) {

        val workoutDate = dto.workoutDateTime.toLocalDate()

        // step01. 운동일자가 인증 주간범위 안에 있는지 검증
        isWorkoutDateInCurrentWeek(workoutDate)

        // step02. 사진 검증 & 사진 S3에 저장
        // 사진 메타정보, URL -> PhotoUploadInfo (List)
//        val photoInfoList = dto.photoList.map { photo ->
        val photoInfoList = photoList.map { photo ->
            // step02-01. 사진 검증
            val photoMetadata =
                photoUseCase.verifyWorkoutPicture(photo, workoutDate)

            // step02-02. 사진 저장
            val savedUrl = s3Service.upload(photo, S3Constants.WORKOUT_DIR)

            PhotoUploadInfo(metadata = photoMetadata, savedUrl = savedUrl)
        }.toMutableList()

        // step03. 운동 인증 저장
        // TODO 회원 정보 가져오는 메소드로 변경하기
        val member: MemberEntity = memberContext.getPrincipal()
        val workoutVerificationEntity =
            WorkoutVerification.addWorkoutVerification(dto, photoInfoList).toEntity(member)
        workoutCommandRepository.save(workoutVerificationEntity)
    }
}