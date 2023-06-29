package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Override
	public List<Board> getBoardList() {
		return boardRepo.findAll();
	}
	
	@Override
	
	public void insertBoard(Board board) {
		boardRepo.save(board);
	}

	@Override
	public Board getBoard(Board board) {
	    Board b = boardRepo.findById(board.getSeq()).orElse(null);
	    if (b != null) {
	        Long cnt = b.getCnt();
	        if (cnt == null) {
	            cnt = 0L; // cnt가 null인 경우 초기값 설정
	        }
	        b.setCnt(cnt + 1); // 조회수 추가
	        boardRepo.save(b);
	    }
	    return b;
	}



	
	@Override
	public void updateBoard(Board board) {
		Board findBoard= boardRepo.findById(board.getSeq()).get();
		
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());
		boardRepo.save(findBoard);
	}
	
	@Override
	public void deleteBoard(Board board) {
		boardRepo.deleteById(board.getSeq());
	}

}
