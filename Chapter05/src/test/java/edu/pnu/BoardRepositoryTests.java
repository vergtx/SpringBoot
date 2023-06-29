package edu.pnu;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;


@SpringBootTest
//@TestMethodOrder(OrderAnnotation.class)
class BoardRepositoryTests {
	
	@Autowired
	private BoardRepository boardRep;
	

//	@Test
	@Order(1)
	void contextLoads() {
		
		for (int i = 0 ; i < 10 ; i++) {
			boardRep.save(Board.builder()
					.title("tittle"+ i)
					.writer("writer" + i)
					.content("content" + i)
					.createDate(new Date())
					.cnt(0L)
					.build());
		}
	}
//	@Test
	public void testGetBoard() {
		
		Board board = boardRep.findById(1L).get();
		System.out.println(board);
	}
	
//	@Test
	public void testUpdateBorad() {
		System.out.println("=========2번 게시글 조회=========");
		 Board board = boardRep.findById(2L).get();
		 
		 System.out.println("=========2번 게시글 수정=========");
		 board.setTitle("제목을 수정 했습니다.");
		 boardRep.save(board);
	}
	
//	@Test
	public void testDeleteBoard() {
		
		boardRep.deleteById(3L);
		System.out.println("===========3번 게시글을 삭제 하였습니다. =======");
	}
	
//	@Test
	public void testFindAll() {
		List<Board> list = boardRep.findAll();
		
		System.out.println("모든데이터를 출력하였습니다.");
		
			for(Board b : list) {
				System.out.println(b);
			}
				
	}
	
	//@Test
	// Query어노테이션 위치기반 파라미터 사용
	public void testQueryAnnotationTest1() {
		//SELECT b from Board b WHERE b.title like %?1% ORDER BY b.seq DESC 이거 호출함.
		
		List<Board> list = boardRep.testQueryAnnotationTest1("title1");
		System.out.println("검색결과------------------");
		for(Board b : list) {
			System.out.println("---------->" + b);
		}
		
	}
	
//	@Test
	// Query어노테이션 이름 파라미터 사용
	public void testQueryAnnotationTest2() {
		//SELECT b from Board b WHERE b.title like %?1% ORDER BY b.seq DESC 이거 호출함.
		
		List<Board> list = boardRep.testQueryAnnotationTest2("title2");
		System.out.println("검색결과------------------");
		for(Board b : list) {
			System.out.println("---------->" + b);
		}
		
	}
	
	@Test
	// Query어노테이션 특정조건 파라미터 사용
	public void testQueryAnnotationTest3() {
		//SELECT b from Board b WHERE b.title like %?1% ORDER BY b.seq DESC 이거 호출함.
		
		List<Object[]> list = boardRep.testQueryAnnotationTest3("title5");
		System.out.println("검색결과------------------");
		for(Object[] b : list) {
			System.out.println(Arrays.toString(b));
		}
		
	}
	
	@Test
	public void testQueryAnnotationTest4() {
		Pageable paging = PageRequest.of(5, 5);
		List<Board> list = boardRep.testQueryAnnotationTest4(paging);
		for (Board b :list) {
			System.out.println("-------->" + b);
		}
	}
	
	
}
