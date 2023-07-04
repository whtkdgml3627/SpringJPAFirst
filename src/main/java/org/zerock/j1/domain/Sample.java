package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_sample")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Sample {
  
  //Entity 클래스
  //Entity 객첼르 바꾸는건 DB를 바꿔주는것 이기 때문에 주로 Getter만 설계를 진행함
  //모든 Entity는 Id라는 PK가 있어야함
  //database의 예약어에 위반대지 않는 변수명으로 설정
  //Entity객체는 DB임 Service에서 객체로 바꿔줘야함
  @Id
  private String keyCol;

  private String first;

  private String last;

  private String zipCode;

  private String city;

  private String zipcode2;

}
