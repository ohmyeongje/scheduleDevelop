package org.example.scheduledevelop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class ScheduleDevelop {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime creatAt; //작성일

    @LastModifiedDate
    private LocalDateTime modifiedAt; //수정일
}
