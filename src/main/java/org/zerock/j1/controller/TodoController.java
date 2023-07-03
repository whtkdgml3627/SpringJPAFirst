package org.zerock.j1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;
import org.zerock.j1.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin
@Log4j2
public class TodoController {
  
  private final TodoService todoService;

  @GetMapping("/list")
  public PageResponseDTO<TodoDTO> list(){

    return todoService.getList();
  }

  @PostMapping("/")
  //@RequestBody = JSON으로 들어온 데이터를 DTO로 변경해줌
  public TodoDTO register(@RequestBody TodoDTO todoDTO){
    log.info("register-----------------------------------------");
    log.info(todoDTO);

    return todoService.register(todoDTO);
  }

}
