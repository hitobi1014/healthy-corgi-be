package com.hc.workout.application.command

import com.hc.member.infrastructure.entity.MemberEntity
import com.hc.member.infrastructure.repository.command.MemberCommandRepository
import com.hc.workout.application.PhotoUseCase
import com.hc.workout.domain.arregates.PhotoUploadInfo
import com.hc.workout.domain.arregates.WorkoutVerification
import com.hc.workout.domain.mapper.toEntity
import com.hc.workout.domain.policy.validation.isWorkoutDateInCurrentWeek
import com.hc.workout.dto.request.SaveWorkoutRequest
import com.hc.workout.infrastructure.repository.command.WorkoutCommandRepository
import org.springframework.stereotype.Service

@Service
class WorkoutCommandUseCaseImpl(
    private val photoUseCase: PhotoUseCase,
    private val workoutCommandRepository: WorkoutCommandRepository,
    private val memberCommandRepository: MemberCommandRepository, // 나중에 회원 정보 가져오는 메소드로 변경하기
) : WorkoutCommandUseCase {

    override fun saveWorkoutVerification(dto: SaveWorkoutRequest) {

        val workoutDate = dto.workoutDateTime.toLocalDate()

        // step01. 사진 검증
        val photoMetadata =
            photoUseCase.verifyWorkoutPicture(dto.photo, workoutDate)

        // step02. 운동일자가 인증 주간범위 안에 있는지 검증
        isWorkoutDateInCurrentWeek(workoutDate)

        // step03. 사진 저장 -> S3 이용
        // TODO S3 연결 후 사진 저장 테스트
        val photoInfoList: MutableList<PhotoUploadInfo> = mutableListOf()

        // step04. 운동 인증 저장
        // TODO 회원 정보 가져오는 메소드로 변경하기
        val member: MemberEntity = memberCommandRepository.findById(1).get() // 임시
        val workoutVerificationEntity =
            WorkoutVerification.addWorkoutVerification(dto, photoInfoList).toEntity(member)
        workoutCommandRepository.save(workoutVerificationEntity)
    }
}