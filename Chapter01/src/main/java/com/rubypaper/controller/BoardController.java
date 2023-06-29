package com.rubypaper.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.BoardVO;

@RestController 
public class BoardController {
	
	public BoardController() {
		System.out.println("=".repeat(50));
		System.out.println("BoardController가 실행되었습니다.");
		System.out.println("=".repeat(50));
	}
	
	@GetMapping("/Hello")
	public@ResponseBody String hello(String name) {
		return "Hello : " + name;
	}
	@PostMapping("/Hello")
	public String hello1(String name) {
		return "Post Hello : " + name;
	}
	@PutMapping("/Hello")
	public String hello2(String name) {
		return "Put Hello : " + name;
	}
	@DeleteMapping("/Hello")
	public String hello3(String name) {
		return "Delete Hello : " + name;
	}

	@GetMapping("/getBoard")
		public BoardVO getBoard() {
			BoardVO board =  new BoardVO();
			board.setSeq(1); 
			board.setTitle("텍스트제목");
			board.setWriter("테스터");
			board.setContent("테스트내용입니다.");
			board.setCreateDate(new Date());
			board.setCnt(0);
			return board;
		}
	@GetMapping("/getBoard1")
	public BoardVO getBoard1() {
		BoardVO board = new BoardVO(
				1,
				"텍스트제목",
				"테스터",
				"테스트내용입니다.",
				new Date(),
				0);
			return board;
	}
	@GetMapping("/getBoard2")
	public BoardVO getBoard2() {
				return BoardVO.builder()
					.seq(1)
					.title("텍스트제목")
					.writer("텍스트내용입니다.")
					.createDate(new Date())
					.cnt(0)
					.build();
	}
	@GetMapping("/getBoardist")
	public List<BoardVO> getBoardlist() {
		List<BoardVO> boardlist = new ArrayList<BoardVO>();
		for (int i = 0 ; i <= 10 ; i++) {
			boardlist.add(BoardVO.builder()
				.seq(i)
				.title("텍스트제목"+i)
				.writer(i+"번 텍스트내용입니다.")
				.createDate(new Date())
				.cnt(0)
				.build());
	}
		return boardlist;
	}
	
	@PostMapping("/board")
	public @ResponseBody BoardVO board(BoardVO b) {
		System.out.println("Board" + b);
		return b;
	}

}
