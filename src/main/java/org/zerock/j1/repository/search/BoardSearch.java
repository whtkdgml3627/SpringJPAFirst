package org.zerock.j1.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.j1.domain.Board;

/*
 * BoardSearc로 만들었으면 반드시 뒤에 Impl만 붙여서 Class를 만들어줘야됨!!!!!!
 * 1. 메소드 구현
 * 2. Impl Override
 */

public interface BoardSearch {
  
  //간단한 list
  Page<Board> search1(String searchType, String keyword, Pageable pageable);

  //댓글까지 포함된 list
  Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable);

}
