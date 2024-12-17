package com.hc.shared.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Comment
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class DeleteEntity : BaseEntity() {

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    @Comment("삭제여부")
    var isDeleted: Boolean = false

    fun deleteData() {
        this.isDeleted = true
    }
}