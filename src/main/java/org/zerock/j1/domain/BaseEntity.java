package org.zerock.j1.domain;

/*
 * BaseEntity는 추상클래스로 만들어줌
 * @MappedSuperclass //테이블로 만들어지지 않게 설정해주는 어노테이션
 * AuditingEntityListener.class 감시,감독 (추가 설정 필요 config에서 설정해줌)
 * @EnableJpaAuditing Application.java에서 설정해줘야 완료
 */

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass //테이블로 만들어지지 않게 설정해주는 어노테이션
@Getter
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class BaseEntity {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime regDate;

  @LastModifiedDate
  private LocalDateTime modDate;

}
