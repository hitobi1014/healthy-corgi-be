package com.hc.member.domain.enum

enum class Status(val description: String) {
    ACTIVE("정상"),
    PENDING("대기"),
    IDLE("휴면"),
    WITHDRAWAL("탈퇴"),
    BLOCK("추방")
}