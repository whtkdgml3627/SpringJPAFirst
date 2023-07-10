package org.zerock.j1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.j1.domain.Reply;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;
import org.zerock.j1.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

  //repository, modelmapper 의존성 주입
  private final ReplyRepository replyRepository;
  private final ModelMapper modelMapper;

  @Override
  public PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO requestDTO) {
    //마지막 번호를 찾으려고 ture 가져오기
    boolean last = requestDTO.isLast();

    //현재 페이지번호를 가져오는 변수
    int pageNum = requestDTO.getPage();

    //last가 true일 때 설정
    if(last){
      //게시물 번호bno의 총 댓글 개수 가져옴 @Query로 조회함
      long totalCount = replyRepository.getCountBoard(requestDTO.getBno());

      //55/50 = 1.1 그럼 2페이지
      pageNum = (int) (Math.ceil(totalCount/(double)requestDTO.getSize()));

      pageNum = pageNum <= 0 ? 1 : pageNum;
    }

    //페이징처리 of의 첫번째 페이지 불러오는 값은 0부터 시작이니 -1 해줌
    Pageable pageable = PageRequest.of(pageNum - 1, requestDTO.getSize(), Sort.by("rno").ascending());

    //Reply Entity로 값을 받아옴 페이징처리까지 pageable
    Page<Reply> result = replyRepository.listBoard(requestDTO.getBno(), pageable);

    log.info("----------------------------------------------------------------------------------");
    
    //getTotalElemnts 로 총 개수
    long totalReplyCount = result.getTotalElements();

    //Entity객체를 DTO로 변환해주는 작업
    List<ReplyDTO> dtoList =  result.get()
      .map(en -> modelMapper.map(en, ReplyDTO.class))
      .collect(Collectors.toList());

    //Page가 마지막페이지값이 제대로 들어가고 있지 않아서 객체로 선언해준 후
    //setPage로 pageNum을 넣어줘서 제대로된 페이지번호 가지게 해줌
    PageResponseDTO<ReplyDTO> responseDTO = new PageResponseDTO<>(dtoList, totalReplyCount, requestDTO);
    responseDTO.setPage(pageNum);
    return responseDTO;
  }
  
}
