package org.zerock.j1.domain;

import jakarta.persistence.Column;

/*
 * BaseEntity 상속
 * @Entity 있으면 반드시 @Id
 * @Getter만 선언!
 * Entity에서는 Setter 대신 change메소드를 선언
 * @Query -> 메소드의 이름 자체가 query가됨
 */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t_board")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Board extends BaseEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bno;

  @Column(length = 200, nullable = false)
  private String title;

  @Column(length = 1000, nullable = false)
  private String content;

  @Column(length = 50, nullable = false)
  private String writer;

  public void changeTitle(String title){
    this.title = title;
  }
}
