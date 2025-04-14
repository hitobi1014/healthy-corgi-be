//package com.hc.workout.infrastructure.repository.query
//
//import com.hc.common.CustomRepositoryTest
//import com.hc.member.infrastructure.entity.MemberEntity
//import com.hc.member.infrastructure.repository.command.MemberCommandRepository
//import com.hc.workout.domain.enum.WorkoutType
//import com.hc.workout.infrastructure.entity.WorkoutVerificationEntity
//import com.hc.workout.infrastructure.repository.command.WorkoutCommandRepository
//import org.assertj.core.api.Assertions
//import org.assertj.core.api.Assertions.*
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import java.time.LocalDate
//
//@CustomRepositoryTest
//class WorkoutQueryRepositoryImplTest @Autowired constructor(
//    private val workoutQueryRepository: WorkoutQueryRepository,
//    private val workoutCommandRepository: WorkoutCommandRepository,
//    private val memberCommandRepository: MemberCommandRepository,
//) {
//    @Test
//    fun givenSaveWorkoutWhenDateVerifyThenTrue() {
//        //given
//        val member =
//            MemberEntity(name = "한강", authCode = "qwert12345", registeredAt = "241222")
//        val savedMember = memberCommandRepository.save(member)
//        val workout = WorkoutVerificationEntity(
//            type = WorkoutType.GYM, name = WorkoutType.GYM.name, date = LocalDate.now(),
//            satisfaction = 3, member = savedMember
//        )
//        workoutCommandRepository.save(workout)
//
//        val startDate = LocalDate.of(2024, 12, 16)
//        val endDate = LocalDate.of(2024, 12, 22)
//
//        //when
//        val workoutList =
//            workoutQueryRepository.findWorkoutByDateBetween(startDate, endDate)
//
//        //then
//        assertThat(workoutList).hasSize(1)
//        assertThat(workoutList?.get(0)!!.member.name).isEqualTo("한강")
//    }
//}