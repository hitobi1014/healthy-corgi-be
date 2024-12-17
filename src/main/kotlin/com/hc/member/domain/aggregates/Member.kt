package com.hc.member.domain.aggregates

import com.hc.member.domain.enum.Status
import com.hc.admin.dto.request.CreateMemberRequest
import com.hc.member.domain.enum.Role
import java.time.LocalDate

data class Member(
    val id: Int?,
    val loginId: String?,
    val password: String?,
    val name: String?,
    val profileImageUrl: String?,
    val authCode: String,
    val birthday: LocalDate?,
    val status: Status?,
    val registeredAt: String,
    val role: Role?,
) {
    companion object {
        fun addMemberByAdmin(dto: CreateMemberRequest, authCode: String): Member {
            return Member(
                id = null,
                loginId = null,
                password = null,
                name = dto.name,
                profileImageUrl = null,
                authCode = authCode,
                birthday = null,
                status = null,
                registeredAt = dto.registeredAt,
                role = null
            )
        }
    }
}