package org.zerock.j1.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface BoardReadDTO {
  
  Long getBno();
  String getTitle();
  String getContent();
  String getWriter();
  @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
  LocalDateTime getRegDate();
  LocalDateTime getModDate();

}
