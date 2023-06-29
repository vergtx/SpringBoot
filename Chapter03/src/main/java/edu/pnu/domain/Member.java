package edu.pnu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
	
	private Long id;
	private String name;
	private Integer age;
	private String nickname;

}


