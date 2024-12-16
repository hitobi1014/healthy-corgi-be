package com.hc.member.application.command

import com.hc.member.domain.aggregates.Member
import com.hc.member.domain.mapper.toDomain
import com.hc.member.domain.mapper.toEntity
import com.hc.admin.dto.request.CreateMemberRequest
import com.hc.member.domain.enum.Status
import com.hc.member.dto.request.SignupMemberRequest
import com.hc.member.dto.response.SignupInfoResponse
import com.hc.member.infrastructure.repository.command.MemberCommandRepository
import com.hc.member.infrastructure.repository.query.MemberQueryRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private const val randomLength: Int = 10
private val logger = LoggerFactory.getLogger("MemberCommandUseCaseImpl")

@Service
@Transactional
class MemberCommandUseCaseImpl(
    private val memberQueryRepository: MemberQueryRepository,
    private val memberCommandRepository: MemberCommandRepository,
    private val passwordEncoder: PasswordEncoder,

    ) : MemberCommandUseCase {
    override fun createMemberByAdmin(dto: CreateMemberRequest): Member {
        val member = Member.addMemberByAdmin(dto, createAuthCode())

        val savedMember = memberCommandRepository.save(member.toEntity())

        return savedMember.toDomain()
    }

    // TODO verifyAuthCode -> 테스트 코드
    override fun verifyAuthCode(authCode: String): SignupInfoResponse {
        // TODO 추후 회원 exception 변경
        val findMember = findMemberByAuthCode(authCode)

        /**
         * 1. 인증코드로 회원 정보 찾기
         * - 잘못된 인증코드나, 회원 상태가 Pending 외의 상태인 경우 예외 처리
         */
        if (findMember.status != Status.PENDING) {
            throw IllegalStateException("이미 가입했거나 탈퇴한 회원입니다. 운영자에게 문의바랍니다.")
        }

        return SignupInfoResponse(name = findMember.name, authCode = findMember.authCode)
    }

    // TODO signupMember -> 테스트 코드
    override fun signupMember(dto: SignupMemberRequest): Member {
        /*
        1. 찾은 회원 정보를 변경감지로 엔티티 수정
            - service transactional 설정
            - 클라이언트로부터 받은 입력값 수정 + 회원 상태 Active로 변경
         */
        val findMember = findMemberByAuthCode(dto.authCode)
        val password = passwordEncoder.encode(dto.password)

        // 회원 저장
        findMember.signupMember(dto.loginId, password, dto.birthday)
        // TODO 암호화
        TODO()
    }

    private fun findMemberByAuthCode(authCode: String) =
        (memberQueryRepository.findByAuthCode(authCode)
            ?: throw IllegalStateException("인증코드가 틀렸습니다."))


    /**
     * 회원 DB에 동일한 인증코드가 있는지 조회 후 있으면 재발행
     * @return 동일한 코드가 있으면
     */
    private fun createAuthCode(): String {
        var authCode: String

        // 값 있으면 다시 구동
        do {
            authCode = randomAuthCode()
            logger.debug("authCode: $authCode")

        } while (memberQueryRepository.isAuthCodeDuplicate(authCode))

        return authCode
    }

    /**
     * @return length 만큼 랜덤으로 만든 문자열
     */
    private fun randomAuthCode(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..randomLength)
            .map { charset.random() }
            .joinToString("")
    }
}