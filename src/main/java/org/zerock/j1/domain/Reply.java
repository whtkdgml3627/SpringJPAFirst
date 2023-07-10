package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t_reply")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
//board를 빼고 toString을 해라
//연관관계를 걸때는 ToString에서 무조건 제외해줘라!!!!!!!!!!!! Default
@ToString(exclude = "board")
public class Reply {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rno;

  private String replyText;

  private String replyFile;

  private String replyer;

  //fetch
  //연관관계 - 하나의 댓글에는 하나의 게시글이 있음 (ManyToOne)
  //fetch는 데이터를 가져오는것
  //(fetch = FetchType.LAZY) = 데이터를 Lazy하게 가져오는거 (내가 필요한 순간까지는 조회하지 않는것)
  //연관관계를 걸때는 FetchType.LAZY를 기본으로 사용해야함!!!!!!!!!!!! Default
  @ManyToOne(fetch = FetchType.LAZY)
  private Board board;

  public void changeText(String text){
    this.replyText = text;
  }
  public void changeFile(String fileName){
    this.replyFile = fileName;
  }

}
