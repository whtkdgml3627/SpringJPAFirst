package org.zerock.j1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;
import org.zerock.j1.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  private final ModelMapper modelMapper;
  
  @Override
  public PageResponseDTO<TodoDTO> getList() {

    Pageable pageable = PageRequest.of(0, 20, Sort.by("tno").descending());

    Page<Todo> result = todoRepository.findAll(pageable);

    //entity를 DTO타입의 List로
    List<TodoDTO> dtoList = result.getContent().stream()
      .map(todo -> modelMapper.map(todo, TodoDTO.class))
      .collect(Collectors.toList());

    // PageResponseDTO<TodoDTO> response = new PageResponseDTO<>();
    // response.setDtoList(dtoList);

    // return response;
    return null;
  }

  @Override
  public TodoDTO register(TodoDTO dto) {

    Todo entity = modelMapper.map(dto, Todo.class);

    //번호가 따져있는 Todo
    Todo result = todoRepository.save(entity);

    return modelMapper.map(result, TodoDTO.class);
  }

  //Optional 어떻게 쓰는지, 작동기능이 뭔지 공부
  @Override
  public TodoDTO getOne(Long tno) {

    Optional<Todo> result = todoRepository.findById(tno);

    //예외를 던져주는 방식
    Todo todo = result.orElseThrow();

    //dto 타입으로 변환
    TodoDTO dto = modelMapper.map(todo, TodoDTO.class);

    return dto;
  }

  @Override
  public void remove(Long tno) {

    todoRepository.deleteById(tno);
  }

  @Override
  public void modify(TodoDTO dto) {
    //가져오고
    Optional<Todo> result = todoRepository.findById(dto.getTno());
    //예외던져주고
    Todo todo = result.orElseThrow();
    //타이틀바꿔주고
    todo.changeTitle(dto.getTitle());
    //그다음 저장
    todoRepository.save(todo);
  }
  
}
