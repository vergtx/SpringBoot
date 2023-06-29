package edu.pnu.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.pnu.domain.Board;
import edu.pnu.service.BoardService;

@Controller
public class BoardController {
	
//	@RequestMapping("/getBoardList")
//	public String getBoardList(Model model) {
//		List<Board> boardList = new ArrayList<Board>();
//		for( int i = 1; i <=10 ; i++) {
//			boardList.add(Board.builder()
//		
//			.seq((long)i)
//			.title("title" + i)
//			.writer("writer" + i)
//			.content("content" + i)
//			.createDate(new Date())
//			.cnt(0L).build());
//			
//			
//		}
//		model.addAttribute("boardList",boardList);
//		return "getBoardList";
//	}
	
	
	@Autowired
	private BoardService boardService;
	
	
	@GetMapping("/getBoardList")
	public String getBoardList(Model model) {
		model.addAttribute("boardList", boardService.getBoardList());
		return "getBoardList";
	}
	
	@GetMapping("/insertBoard")
	public String insertBoard() {
		return "insertBoard"; //webinf 내 baord 내 insetboard 호출
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(Board board) {
	
	    boardService.insertBoard(board);
	    return "redirect:getBoardList";
	}
	
	@GetMapping("getBoard")
	public String getBoard(Long seq, Model model) {
		Board board = boardService.getBoard(Board.builder().seq(seq).build());
		model.addAttribute("board", board);
		return "getBoard";
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(Board board) {
		boardService.updateBoard(board);
		return "redirect:getBoardList";
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board) {
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}
	
	
	
//	@GetMapping("/home")  
	public String home() {
		
		return "home";
	}
//	@GetMapping("/home1")
	public void home1() {
		
	
	}
	
//	@GetMapping("/model")
	public String model(Model model) {
		model.addAttribute("data" , "홍길동");
	//	model.addAttribute("list", list);
		return "model";
	
	}

}
