package org.zerock.j1.domain;

import jakarta.persistence.Column;
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
@Table(name = "tbl_todo2")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
//Setter를 가능하면 만들지 마라
public class Todo {
  
  @Id
  //오토 인크리먼트 지정 (DB에서 지정해줌)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long tno;

  //varchar(300) not null 설정
  @Column(length = 300, nullable = false)
  private String title;

  public void changeTitle(String title){
    this.title = title;
  }


}
