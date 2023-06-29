package edu.pnu.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board { //h2테이블 자동 생성해줌
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	//@GeneratedValue(strategy = GenerationType.IDENTITY)  는 오토인크래먼트 하는 것.  IDENTITY 대신에 시퀀스 흐면 오라클과h2만 지원함 
	private Long seq;
	private String title;
	private String writer;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	private Long cnt;

}
