package org.zerock.j1.dto;

/*
 * @JsonFormat = 데이터 맞춰주는것
 * @JsonIgnore = JSON 문자열로 만들지 않을 때 사용
 */

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardDTO {
  
  private Long bno;

  private String title;

  private String content;

  private String writer;

  @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
  private LocalDateTime regDate;

  @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
  private LocalDateTime modDate;
}
