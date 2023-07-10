package org.zerock.j1.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;
import org.zerock.j1.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
@CrossOrigin
@Log4j2
public class ReplyController {

  private final ReplyService replyService;

  @GetMapping("/{bno}/list")
  public PageResponseDTO<ReplyDTO> list(
    @PathVariable("bno") Long bno, ReplyPageRequestDTO requestDTO
  ){
    log.info("bno ----" + bno);
    log.info(requestDTO);

    return replyService.list(requestDTO);
  }

  @PostMapping("/")
  public Map<String, Long> register(
    @RequestBody ReplyDTO replyDTO
  ){
    log.info("ReplyController...............................");
    log.info(replyDTO);

    Long newRno = replyService.register(replyDTO);

    return Map.of("result", newRno);
  }
  
}
