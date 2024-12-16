package com.hc.member.dto.request

import java.time.LocalDate

data class SignupMemberRequest(
    val loginId: String,
    val password: String,
    val birthday: LocalDate,
    val name: String,
    val authCode: String,
)
