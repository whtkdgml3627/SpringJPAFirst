package org.zerock.j1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
 * JpaRepository<Board, Long> 를 무조건 상속받는다
 * 제너릭에는 entity, Id 값을 넣어준다
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.j1.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
  //여러개 검색할 땐 List
  //복잡한 쿼리문을 만들 수 없기 때문에 많이 사용되지 않음
  List<Board> findByTitleContaining(String title);

  //Page타입으로 검색하면 검색 후 페이징 처리까지 다 만들어줌
  Page<Board> findByContentContaining(String content, Pageable pageable);

}
