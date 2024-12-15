package com.hc.member.application.command

import com.hc.member.domain.aggregates.Member
import com.hc.member.domain.mapper.toDomain
import com.hc.member.domain.mapper.toEntity
import com.hc.member.dto.request.CreateMemberRequest
import com.hc.member.infrastructure.repository.command.MemberCommandRepository
import com.hc.member.infrastructure.repository.query.MemberQueryRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MemberCommandUseCaseImpl(
    private val memberQueryRepository: MemberQueryRepository,
    private val memberCommandRepository: MemberCommandRepository,
) : MemberCommandUseCase {
    override fun createMemberByAdmin(dto: CreateMemberRequest): Member {
        val member = Member.addMemberByAdmin(dto, createAuthCode())

        val savedMember = memberCommandRepository.save(member.toEntity())

        return savedMember.toDomain()
    }


    /**
     * 회원 DB에 동일한 인증코드가 있는지 조회 후 있으면 재발행
     * @return 동일한 코드가 있으면
     */
    private fun createAuthCode(): String {
        var authCode: String

        // 값 있으면 다시 구동
        do {
            authCode = createUUID()
        } while (memberQueryRepository.isAuthCodeDuplicate(authCode))

        return authCode
    }

    /**
     * @return 16자리로 자른 uuid
     */
    private fun createUUID(): String {
        UUID.randomUUID()
            .toString()
            .replace("-", "")
            .let {
                return it.substring(0..15)
            }
    }
}