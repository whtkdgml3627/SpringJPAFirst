package org.zerock.j1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
 * JpaRepository<Board, Long> 를 무조건 상속받는다
 * 제너릭에는 entity, Id 값을 넣어준다
 * JPQL(Java Persistence Query Language)
 * JPQL은 엔티티 객체를 조회하는 객체지향 쿼리다.
 * 테이블을 대상으로 쿼리하는 것이 아니라 엔티티 객체를 대상으로 쿼리한다.
 * 
 * JOOQ Access Layer = Spring Boot에서 공식적으로 지원하는 DB통신 라이브러리
 * 한국에선 QueryDSL을 사용
 * 
 * 검색은 무조건 동적으로 처리한다!! 아주 중요!!
 * JPA 방법중 한가지 -> QueryDsl
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j1.domain.Board;
import org.zerock.j1.repository.search.BoardSearch;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
  //여러개 검색할 땐 List
  //복잡한 쿼리문을 만들 수 없기 때문에 많이 사용되지 않음
  List<Board> findByTitleContaining(String title);

  //@Query 어노테이션 추가! 안에 문자열로 작성
  // *를 사용할 수 없음 Entity를 별칭지정해주고 from앞에 넣어줘야함
  //insert, delete, update는 사용하지 않는게 더 좋다
  @Query("select b from Board b where b.title like %:title%")
  List<Board> listTitle(@Param("title") String title);

  //slect해서 컬럼명을 가공해서 사용하고 싶을 때는 제네릭에 Object[]를 선언해줌!
  @Query("select b.bno, b.title from Board b where b.title like %:title%")
  List<Object[]> listTitle2(@Param("title") String title);
  
  //paging 추가! 파라미터 Pageable 리턴타입 Page 무조건!
  //타입이 달라서 Override 됨
  @Query("select b.bno, b.title from Board b where b.title like %:title%")
  Page<Object[]> listTitle2(@Param("title") String title, Pageable pageable);

  //update
  //@Modifying 들어가야 insert, update가 가능
  @Modifying
  @Query("update Board b set b.title = :title where b.bno = :bno")
  int modifyTitle(@Param("title") String title, @Param("bno") Long bno);

  //Page타입으로 설정해줘서 Pagable를 파라미터로 선언
  //파라미터에 Pageable을 넣어주면 자동으로 count로 paging처리를 해줌!! 개꿀
  Page<Board> findByContentContaining(String content, Pageable pageable);

  //
  @Query(value = "select * from t_board", nativeQuery = true)
  List<Object[]> listNative();

}
