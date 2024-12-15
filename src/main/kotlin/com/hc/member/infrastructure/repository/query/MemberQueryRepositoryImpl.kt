package com.hc.member.infrastructure.repository.query

import com.hc.member.infrastructure.entity.QMemberEntity.memberEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MemberQueryRepository {

    override fun isAuthCodeDuplicate(authCode: String): Boolean {
        return jpaQueryFactory.selectOne()
            .from(memberEntity)
            .where(memberEntity.authCode.eq(authCode))
            .fetchFirst() != null
    }
}