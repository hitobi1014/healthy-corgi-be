package com.hc.member.application.command

import com.hc.member.domain.aggregates.Member
import com.hc.admin.dto.request.CreateMemberRequest
import com.hc.member.dto.request.SignupMemberRequest
import com.hc.member.dto.response.SignupInfoResponse

interface MemberCommandUseCase {
    fun createMemberByAdmin(dto: CreateMemberRequest): Member
    fun verifyAuthCode(authCode: String): SignupInfoResponse
    fun signupMember(dto: SignupMemberRequest): Member
}