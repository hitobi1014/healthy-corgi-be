package com.hc.admin.ui.controller

import com.hc.admin.dto.request.CreateMemberRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val memberUseCase: MemberCommandUseCase,
) {
    @PostMapping("/member")
    fun createMember(dto: CreateMemberRequest) {
        // TODO 공통응답 설정후 변경하기
        memberUseCase.createMemberByAdmin(dto)
    }
}