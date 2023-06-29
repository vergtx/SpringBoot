package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.Board;


public interface BoardRepository extends JpaRepository<Board, Long> { // <T>는 클래스명 <id>는 아이디 타입
	
	List<Board> findByTitleLike(String Serchkeyword);
	
	List<Board> findByTitleContainingAndCntGreaterThan(String title, int  cnt);

	
	@Query("SELECT b from Board b WHERE b.title like %?1% ORDER BY b.seq DESC")
			List<Board> testQueryAnnotationTest1(String searchKeyword);
//			from 다음에 Board는 클래스명이라서 대소문자 구분
//			Select 다음에 b와 Board 다음에 b 넣는거는 문법이라서 알파벳 넣어줘야 함
//			알파벳 두개는 일치 해야 하고 a라고 해도 상관없음
	
	@Query("SELECT b from Board b"
			+ " WHERE b.title like %:searchKeyword% "
			+ "ORDER BY b.seq DESC")
	List<Board> testQueryAnnotationTest2(String searchKeyword);
	
	
	@Query("SELECT b.seq, b.title, b.writer, b.createDate "
			+ "from Board b "
			+ "where b.title like %:searchKeyword% "
			+ "ORDER BY b.seq DESC")
	List<Object[]> testQueryAnnotationTest3(String searchKeyword);
	
	
	@Query("SELECT b from Board b ORDER BY b.seq DESC")
	List<Board> testQueryAnnotationTest4(Pageable paging);
}
