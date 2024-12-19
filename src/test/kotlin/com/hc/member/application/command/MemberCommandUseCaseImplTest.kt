package com.hc.member.application.command

import com.hc.admin.dto.request.CreateMemberRequest
import com.hc.common.CustomSpringBootTest
import com.hc.member.domain.enum.Status
import com.hc.member.dto.request.SignupMemberRequest
import com.hc.member.dto.response.SignupInfoResponse
import com.hc.member.infrastructure.entity.MemberEntity
import com.hc.member.infrastructure.repository.command.MemberCommandRepository
import com.hc.member.infrastructure.repository.query.MemberQueryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import kotlin.test.assertEquals

//@CustomSpringBootTest
class MemberCommandUseCaseImplTest {
    private lateinit var memberCommandUseCase: MemberCommandUseCaseImpl

    private val randomLength = 10

    private val memberQueryRepository = mockk<MemberQueryRepository>()
    private val memberCommandRepository = mockk<MemberCommandRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()

    /**
     * @return length 만큼 랜덤으로 만든 문자열
     */
    private fun randomAuthCode(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..randomLength)
            .map { charset.random() }
            .joinToString("")
    }

    @BeforeEach
    fun setUp() {
        memberCommandUseCase =
            MemberCommandUseCaseImpl(
                memberQueryRepository,
                memberCommandRepository,
                passwordEncoder
            )
    }

    @Test
    fun `운영자가 회원 생성시 정상적으로 저장`() {
        //given
        val request = CreateMemberRequest(
            name = "한강",
            registeredAt = "241215"
        )

        every { memberQueryRepository.isAuthCodeDuplicate(any()) } returns false


        val memberEntity = MemberEntity(
            id = 1,
            name = request.name,
            registeredAt = request.registeredAt,
            authCode = randomAuthCode() // 10자리
        )
        every { memberCommandRepository.save(any()) } returns memberEntity

        //when
        val result = memberCommandUseCase.createMemberByAdmin(request)

        //then
        verify { memberQueryRepository.isAuthCodeDuplicate(any()) }
        verify { memberCommandRepository.save(any()) }

        //검증
        assertEquals(request.name, result.name)
        assertEquals(request.registeredAt, result.registeredAt)
    }

    @Test
    fun `회원 상태가 Pending일 때 인증 성공`() {
        //given
        val authCode = randomAuthCode()
        val memberEntity = MemberEntity(
            id = 1,
            name = "한강",
            authCode = authCode,
            status = Status.PENDING,
            registeredAt = "241217"
        )

        every { memberQueryRepository.findByAuthCode(authCode) } returns memberEntity

        //when
        val result = memberCommandUseCase.verifyAuthCode(authCode)

        //then
        assertEquals(SignupInfoResponse(name = "한강", authCode = authCode), result)
    }

    @Test
    fun `회원 상태가 Pending이 아닌 경우 예외 발생`() {
        // given
        val authCode = randomAuthCode()
        val memberEntity = MemberEntity(
            id = 2,
            name = "탈퇴된 사용자",
            authCode = authCode,
            status = Status.WITHDRAWAL, // 상태가 PENDING이 아님
            registeredAt = "241215"
        )
        every { memberQueryRepository.findByAuthCode(authCode) } returns memberEntity

        // when & then
        val exception = assertThrows<IllegalStateException> {
            memberCommandUseCase.verifyAuthCode(authCode)
        }
        assertEquals("이미 가입했거나 탈퇴한 회원입니다. 운영자에게 문의바랍니다.", exception.message)
    }

    @Test
    fun `잘못된 인증코드로 예외 발생`() {
        // given
        val invalidAuthCode = "invalidAuthCode"
        every { memberQueryRepository.findByAuthCode(invalidAuthCode) } returns null // 없는 인증코드

        // when & then
        val exception = assertThrows<IllegalStateException> {
            memberCommandUseCase.verifyAuthCode(invalidAuthCode)
        }
        assertEquals("인증코드가 틀렸습니다.", exception.message)
    }

    @Test
    fun `회원가입 성공 - 회원 상태가 Active로 변경되고 정보가 저장된다`() {
        // given
        val authCode = randomAuthCode()
        val encodedPassword = "encodedPassword"
        val signupRequest = SignupMemberRequest(
            loginId = "testLoginId",
            password = "testPassword",
            name = "광한",
            authCode = authCode,
            birthday = LocalDate.of(1995, 3, 5)
        )
        val existingMember = MemberEntity(
            id = 1,
            name = "광한",
            authCode = authCode,
            status = Status.PENDING,
            registeredAt = "241217"
        )

        // Mock 행동 설정
        every { memberQueryRepository.findByAuthCode(authCode) } returns existingMember
        every { passwordEncoder.encode(signupRequest.password) } returns encodedPassword

        // when
        val result = memberCommandUseCase.signupMember(signupRequest)

        // then
        // Mock 검증
        verify { memberQueryRepository.findByAuthCode(authCode) }
        verify { passwordEncoder.encode(signupRequest.password) }
        verify(exactly = 0) { memberCommandRepository.save(any()) }

        // 회원 정보 검증
        assertEquals("testLoginId", result.loginId)
        assertEquals(LocalDate.of(1995, 3, 5), result.birthday)
        assertEquals(Status.ACTIVE, existingMember.status)
        assertEquals(encodedPassword, existingMember.password)
    }
}