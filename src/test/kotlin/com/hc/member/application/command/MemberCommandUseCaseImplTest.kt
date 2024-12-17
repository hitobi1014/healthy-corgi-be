package com.hc.member.application.command

import com.hc.admin.dto.request.CreateMemberRequest
import com.hc.member.infrastructure.entity.MemberEntity
import com.hc.member.infrastructure.repository.command.MemberCommandRepository
import com.hc.member.infrastructure.repository.query.MemberQueryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.assertEquals

//@CustomSpringBootTest
class MemberCommandUseCaseImplTest {
    private lateinit var memberCommandUseCase: MemberCommandUseCaseImpl

    private val memberQueryRepository = mockk<MemberQueryRepository>()
    private val memberCommandRepository = mockk<MemberCommandRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()

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
            authCode = "qwert12345" // 10자리
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
}