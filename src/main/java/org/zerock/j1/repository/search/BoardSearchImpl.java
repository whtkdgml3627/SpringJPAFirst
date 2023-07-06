package org.zerock.j1.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/*
 * QuerydslRepositorySupport를 상속받았고 부모클래스에 생성자가 있어서 super로 상속 받아야함
 * QueryDSL의 목표는 JAVA로 동적으로 쿼리를 생성해주는 라이브러리!!!
 * QueryDSL에서는 DTO Entity 처리를 이 안에서 가능하게 해줌!!
 * BooleanBuilder 는 괄호로 감싸줌 (우선순위 연산자)
 */

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.QBoard;
import org.zerock.j1.domain.QReply;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
  
  //super로 상속 받아야함 반드시!
  public BoardSearchImpl(){
    super(Board.class);
  }

  @Override
  public Page<Board> search1(String searchType, String keyword, Pageable pageable) {
    
    //QBoard 파일이 있어야함
    //board에서 쿼리를 날리려는 목적 Q가 들어간애
    QBoard board = QBoard.board;

    //SQL문을 객체화 시켜놓은 상태
    JPQLQuery<Board> query = from(board);

    //1이 들어가있는 title 조회
    // query.where(board.title.contains("1"));

    if(keyword != null && searchType != null){

      //배열로 받고 split로 쪼개면 글자열 하나하나씩 쪼개짐
      //tc -> [t,c]
      String[] searchArr = searchType.split("");

      //( )
      BooleanBuilder searchBuilder = new BooleanBuilder();

      for (String type : searchArr) {
        switch(type){
          case "t" -> searchBuilder.or(board.title.contains(keyword));
          case "c" -> searchBuilder.or(board.content.contains(keyword));
          case "w" -> searchBuilder.or(board.writer.contains(keyword));
        }
      }//end for

      query.where(searchBuilder);
    }

    //paging
    this.getQuerydsl().applyPagination(pageable, query);

    //fetch 목록데이터 실제로 가져오는것
    List<Board> list = query.fetch();

    //count를 가져오는것
    long count = query.fetchCount();

    log.info("----------------------------------------------------------------------------");
    log.info("list: " + list);
    log.info("count: " + count);

    return new PageImpl<>(list, pageable, count);
  }

  //댓글 카운트 포함된 list
  @Override
  public Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable) {

    //Querydsl 사용하기 위한 선언
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;

    //JPQL을 사용하겠다!!
    JPQLQuery<Board> query = from(board);

    //left outer join
    query.leftJoin(reply).on(reply.board.eq(board));

    //검색 조건 설정
    if(keyword != null && searchType != null){

      //배열로 받고 split로 쪼개면 글자열 하나하나씩 쪼개짐
      //tc -> [t,c]
      String[] searchArr = searchType.split("");

      //( )를 만들어주는 객체생성
      BooleanBuilder searchBuilder = new BooleanBuilder();

      for (String type : searchArr) {
        switch(type){
          case "t" -> searchBuilder.or(board.title.contains(keyword));
          case "c" -> searchBuilder.or(board.content.contains(keyword));
          case "w" -> searchBuilder.or(board.writer.contains(keyword));
        }
      }//end for

      query.where(searchBuilder);
    }

    //group by 선언 하고 where줘도 지가 알아서 찾아서 where문 실행해줌
    //board 별로 group by 해줌
    query.groupBy(board);

    //return type JPQL<Tuple>
    //가져올 컬럼 select
    //count 뽑아오는 메소드가 있음
    //countDistinct() rno를 기준으로 뽑아서 안전함!
    //as 별칭 주는애
    JPQLQuery<Tuple> tuplQuery = query.select(board.bno, board.title, reply.countDistinct());

    //paging 처리!!
    this.getQuerydsl().applyPagination(pageable, tuplQuery);

    //list로 tuple로 반환
    List<Tuple> tuples = tuplQuery.fetch();

    List<Object[]> arrList = tuples.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

    log.info(tuples);

    //tuple에서 총 개수
    Long count = tuplQuery.fetchCount();

    log.info("count: " + count);

    //PageImpl 타입으로 return
    return new PageImpl<>(arrList, pageable, count);
  }

  //V3
  @Override
  public PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO) {

    Pageable pageable = makePageable(requestDTO);
    //Querydsl 사용하기 위한 선언
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;

    //JPQL을 사용하겠다!!
    JPQLQuery<Board> query = from(board);

    //left outer join
    query.leftJoin(reply).on(reply.board.eq(board));

    //PageRequestDTO에 있는 값으로 가져옴
    String keyword = requestDTO.getKeyword();
    String searchType = requestDTO.getType();

    //검색 조건 설정
    if(keyword != null && searchType != null){

      //배열로 받고 split로 쪼개면 글자열 하나하나씩 쪼개짐
      //tc -> [t,c]
      String[] searchArr = searchType.split("");

      //( )를 만들어주는 객체생성
      BooleanBuilder searchBuilder = new BooleanBuilder();

      for (String type : searchArr) {
        switch(type){
          case "t" -> searchBuilder.or(board.title.contains(keyword));
          case "c" -> searchBuilder.or(board.content.contains(keyword));
          case "w" -> searchBuilder.or(board.writer.contains(keyword));
        }
      }//end for

      query.where(searchBuilder);
    }

    //paging
    this.getQuerydsl().applyPagination(pageable, query);

    //group by 선언 하고 where줘도 지가 알아서 찾아서 where문 실행해줌
    //board 별로 group by 해줌
    query.groupBy(board);

    //Projections를 사용하여 바로 BoardListDTO를 사용가능함
    JPQLQuery<BoardListRcntDTO> listQuery = query.select(Projections.bean(
        BoardListRcntDTO.class,
        board.bno,
        board.title,
        board.writer,
        reply.countDistinct().as("replyCount")
      )
    );

    List<BoardListRcntDTO> list = listQuery.fetch();

    log.info("----------------------------------------------------------------");
    log.info(list);

    //total 가져오기
    long totalCount = listQuery.fetchCount();

    log.info(totalCount);

    return new PageResponseDTO<>(list, totalCount, requestDTO);
  }

}
