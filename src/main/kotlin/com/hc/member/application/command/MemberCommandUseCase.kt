package com.hc.member.application.command

import com.hc.member.domain.aggregates.Member
import com.hc.member.dto.request.CreateMemberRequest

interface MemberCommandUseCase {
    fun createMemberByAdmin(dto: CreateMemberRequest): Member
}