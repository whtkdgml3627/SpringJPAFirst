package org.zerock.j1.repository.search;

import java.util.List;

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

import com.querydsl.core.BooleanBuilder;
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

}
