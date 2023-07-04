package org.zerock.j1.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.j1.domain.Board;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

  @Autowired
  private BoardRepository boardRepository;
  
  @Test
  public void testInsert(){

    for(int i = 0; i < 100; i++){
      Board board = Board.builder()
        .title("Sample Title" + i)
        .content("Sample Content" + i)
        .writer("user" + (i % 10))
        .build();

      boardRepository.save(board);
    }
  }

  /*
   * findById 실행 시 쿼리문실행 후 toStriong으로 콘솔에 찍힘 (즉시 로딩 eager loding)
   * lazyinitializationexception 에러가 났을 시 -> 트랜잭션 처리가 필수!!!
   * getReferenceById -> 자기가 필요한 순간이 오기전까지 쿼리 실행을 하지 않음!!!
   * (지연 로딩 lazy loding) - DB의 자원을 아껴쓰기 위해 데이터가 필요할 떄 까지 미뤄둠
   * ex) toString으로 값을 찍어야 하니까 그때서야 쿼리문이 실행됨!
   */
  @Test
  public void testRead(){

    Long bno = 1L;

    Optional<Board> result = boardRepository.findById(bno);

    Board board = result.orElseThrow();

    log.info("--------------------------------------------------------");
    log.info(board);
  }

  /*
   * update는 먼저 조회 -> 수정 -> 저장
   * 조회하지 않고 수정하면 기존 데이터가 사라짐
   */
  @Test
  public void testUpdate(){
    //조회
    Long bno = 1L;

    Optional<Board> result = boardRepository.findById(bno);

    Board board = result.orElseThrow();
    //수정
    board.changeTitle("Update Title");
    //저장
    boardRepository.save(board);
  }

  //쿼리 테스트 조회
  @Test
  public void testQuery1(){

    List<Board> list = boardRepository.findByTitleContaining("1");

    log.info("--------------------------------------------------------------------------");
    log.info(list.size());
    log.info(list);
  }
  
  //@Query 쿼리 테스트 조회
  @Test
  public void testQuery1_1(){

    List<Board> list = boardRepository.listTitle("1");

    log.info("--------------------------------------------------------------------------");
    log.info(list.size());
    log.info(list);
  }
  
  //@Query 쿼리 테스트 조회
  @Test
  public void testQuery1_2(){

    List<Object[]> list = boardRepository.listTitle2("1");

    log.info("--------------------------------------------------------------------------");
    log.info(list.size());
    list.forEach(arr -> log.info(Arrays.toString(arr)));
  }
  
  //@Query 쿼리 테스트 페이징처리까지 조회
  @Test
  public void testQuery1_3(){

    Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

    Page<Object[]> result = boardRepository.listTitle2("1", pageable);

    log.info("--------------------------------------------------------------------------");
    log.info(result);
  }

  //쿼리 테스트 페이징처리까지 조회
  @Test
  public void testQuery2(){

    Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

    Page<Board> result = boardRepository.findByContentContaining("1", pageable);
    
    log.info("--------------------------------------------------------------------------");
    log.info(result);
  }

  //@Query 쿼리 테스트 수정처리
  //modify는 Transactional 필수
  //test에서 진행하면 Default가 rollback
  @Commit
  @Transactional
  @Test
  public void testModify(){

    Long bno = 100L;
    String title = "Modified Title 100";

    int count = boardRepository.modifyTitle(title, bno);

    log.info("----------------------------------------------" + count);
  }

  //@Query -> NativeQuery (SQL문으로 작성해서 사용)
  @Test
  public void testNative(){

    List<Object[]> result = boardRepository.listNative();
    result.forEach(arr -> log.info(Arrays.toString(arr)));
  }

  //
  @Test
  public void testSearch1(){

    Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

    Page<Board> result = boardRepository.search1(null, "1", pageable);

    log.info(result.getTotalElements());

    result.get().forEach(b -> log.info(b));
  }

}
