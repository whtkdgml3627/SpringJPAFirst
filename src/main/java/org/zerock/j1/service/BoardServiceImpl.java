package org.zerock.j1.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
  
  private final BoardRepository boardRepository;
  private final ModelMapper modelMapper;
  
  @Override
  public PageResponseDTO<BoardListRcntDTO> listRcnt(PageRequestDTO pageRequestDTO) {

    log.info("--------------------------------------------------------------------");
    log.info(pageRequestDTO);

    return boardRepository.searchDTORcnt(pageRequestDTO);
  }
  
}
