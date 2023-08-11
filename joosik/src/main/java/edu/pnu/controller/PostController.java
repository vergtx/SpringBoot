package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Post;
import edu.pnu.dto.ApiResponse;
import edu.pnu.dto.PostRequest;
import edu.pnu.service.PostService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	private final PostService postService;

	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public List<Post> getPostsByStockCode(@RequestParam String stockCode) {
		return postService.getPostsByStockCode(stockCode);
	}

	@PostMapping
	public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest postRequest) {
		Post createdPost = postService.createPost(postRequest);
		if (createdPost != null) {
			return ResponseEntity.ok(new ApiResponse(true, "Post created successfully"));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create post"));
		}
	}
	//@PathVariable경로 변수인 postId를 메서드 매개변수로 받음 
	//@Valid 애노테이션은 입력값의 유효성 검사를 요청 여기서는 PostRequest매개변수로 전달되는 데이터에 대해 유효성 검사가 수행
	//@RequestBody는 HTTP 요청의 본문에 담긴 데이터를 메서드 매개변수로 받음 클라이언트가 POST 또는 PUT과 같은 HTTP 메서드를 사용하여 데이터를 요청 본문에 담아 서버로 보낼 때 사용
	@PreAuthorize("#postRequest.writer == authentication.name") // 작성자와 로그인한 사용자의 이름 비교
	@PutMapping("/update/{postId}") 
	public ResponseEntity<?> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequest postRequest) {
		Post updatedPost = postService.updatePost(postId, postRequest);
		if (updatedPost != null) {
			return ResponseEntity.ok(new ApiResponse(true, "Post updated successfully"));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to update post"));
		}
	}
	@PreAuthorize("@postService.isPostOwner(#postId, authentication.name)") // 커스텀 메서드 사용 PostService 와 PostServiceImpl에 추가
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Long postId) {
		postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse(true, "Post deleted successfully"));
	}
}
