package com.hc.member.domain.mapper

import com.hc.member.domain.aggregates.Member
import com.hc.member.domain.enum.Role
import com.hc.member.infrastructure.entity.MemberEntity

fun MemberEntity.toDomain(): Member {
    return Member(
        id = this.id,
        loginId = this.loginId,
        password = this.password,
        name = this.name,
        profileImageUrl = this.profileImageUrl,
        authCode = this.authCode,
        birthday = this.birthday,
        status = this.status,
        registeredAt = this.registeredAt,
        role = this.role,
    )
}

fun Member.toEntity(): MemberEntity {
    return MemberEntity(
        id = this.id,
        loginId = this.loginId,
        password = this.password,
        name = this.name!!,
        profileImageUrl = this.profileImageUrl,
        authCode = this.authCode,
        birthday = this.birthday,
        registeredAt = this.registeredAt,
        role = this.role ?: Role.Member
        // 추후 status 값 명시적으로 변환로직 필요하면 추가
    )
}