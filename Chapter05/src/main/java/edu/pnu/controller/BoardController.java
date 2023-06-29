package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;

@RestController
public class BoardController {
	
	@Autowired
	BoardRepository boardRep;
	
	@GetMapping("/getBoardList")
	public List<Board> getBoardLists() {
		return boardRep.findAll();
	}
	
	@GetMapping("/getBoardList/{id}")
	public Board getBoardList(@PathVariable Long id) {
		return boardRep.findById(id).get();
	}
	
	@GetMapping("/insertBoard")
	public Board insertBoard(Board board) {
		return boardRep.save(board);
	}
	
	@GetMapping("/updateBoard")
	public Board updateBoard(Board board) {
		return boardRep.save(board);
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(@PathVariable Long id) {
		boardRep.deleteById(id);
		return String.format("seq 가 %d인 데이터가 삭제되었습니다.", id);
		
	}
	
}
