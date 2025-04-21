package com.hc.member.ui.controller

import com.hc.member.dto.response.SignupInfoResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberUseCase: MemberCommandUseCase,
) {
    @PostMapping("/auth-code")
    fun verifyAuthCode(authCode: String): SignupInfoResponse {
        val memberInfo = memberUseCase.verifyAuthCode(authCode)
        return memberInfo
    }
}