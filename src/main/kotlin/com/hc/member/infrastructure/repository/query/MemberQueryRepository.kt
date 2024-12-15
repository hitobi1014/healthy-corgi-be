package com.hc.member.infrastructure.repository.query

interface MemberQueryRepository {
    fun isAuthCodeDuplicate(authCode: String): Boolean
}