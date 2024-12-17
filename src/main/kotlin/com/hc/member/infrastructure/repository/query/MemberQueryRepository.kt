package com.hc.member.infrastructure.repository.query

import com.hc.member.infrastructure.entity.MemberEntity

interface MemberQueryRepository {
    fun isAuthCodeDuplicate(authCode: String): Boolean

    /**
     * 회원 인증코드로 DB 조회
     * @return 인증코드로 찾은 회원엔티티
     */
    fun findByAuthCode(authCode: String): MemberEntity?
}