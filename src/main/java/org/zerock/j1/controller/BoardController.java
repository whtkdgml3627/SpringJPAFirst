package org.zerock.j1.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.j1.dto.BoardDTO;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
public class BoardController {
  
  private final BoardService boardService;

  //list
  //consumes 데이터타입 소비
  // @GetMapping(value = "/list", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @GetMapping(value = "/list")
  public PageResponseDTO<BoardListRcntDTO> list(@ParameterObject PageRequestDTO requestDTO){

    log.info(requestDTO);

    return boardService.listRcnt(requestDTO);
  }

  //read
  @GetMapping("/{bno}")
  public BoardDTO get(@PathVariable("bno") Long bno){
    
    return boardService.getOne(bno);
  }

}
