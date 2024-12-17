package com.hc.member.infrastructure.repository.command

import com.hc.common.CustomRepositoryTest
import com.hc.member.domain.enum.Status
import com.hc.member.infrastructure.entity.MemberEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@CustomRepositoryTest
class MemberCommandRepositoryTest @Autowired constructor(
    private val jpaMemberRepository: MemberCommandRepository,
) {
    // 암호화 자릿수
    val randomLength = 10

    // 랜덤값 생성
    private fun generateRandomCode(length: Int = randomLength): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    @Test
    @DisplayName("운영자->회원 추가, 랜덤코드")
    fun addMemberByAdmin() {
        //given
        val randomCode = generateRandomCode()
        val registeredAt = "241215"
        val member = MemberEntity(
            name = "한강",
            authCode = randomCode,
            registeredAt = registeredAt,
        )

        //when
        val savedMember = jpaMemberRepository.save(member)

        //then
        assertThat(savedMember.authCode).isEqualTo(randomCode)
        assertThat(savedMember.id).isNotNull
        assertThat(savedMember.status).isEqualTo(Status.PENDING)
        assertThat(savedMember.createdAt).isNotNull()
        assertThat(savedMember.isDeleted).isNotNull()
    }
}