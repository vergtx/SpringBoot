package edu.pnu;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import edu.pnu.domain.Board;

public class JPAClient {

	public static void main(String[] args) {
		// 무슨역할 하는지 공부 할 것.
		
		System.out.println("JPAClient실행");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chaptor04"); //Chaptor04라는 이름의 Persistence Unit을 사용
		// JPA의 EntityManagerFactory를 생성
		//데이터베이스 연결 설정과 EntityManager의 생성을 담당
		
		EntityManager em = emf.createEntityManager();
		//위에서 생성한 EntityManagerFactory를 사용하여 EntityManager를 생성
		//EntityManager는 JPA에서 데이터베이스와의 상호 작용을 담당
		
//		EntityTransaction tx= em.getTransaction();
//		tx.begin();
//						
//		Board b = new Board();
//		
//		b.setTitle("Title");
//		b.setWriter("Writer");
//		b.setContent("Content");
//		b.setCreateDate(new Date());
//		b.setCnt(0L);
//		
//		em.persist(b);
//		tx.commit();
		
//		inserData(em);
//		updateData(em);
		deleteData(em);
		
		
		
		
		em.close();
		emf.close();
	}
	
	static void updateData(EntityManager em) {

		Board b = em.find(Board.class, 9L);

		b.setTitle("새로운 타이틀입니다.");
		
		EntityTransaction tx =em.getTransaction();
		try{
			tx.begin();
			
			em.persist(b);
			tx.commit();
					
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			// TODO: handle exception
		}

	}
	
	static void deleteData(EntityManager em) {

		Board b = em.find(Board.class, 15L);

		
		
		EntityTransaction tx =em.getTransaction();
		try{
			tx.begin();
			em.remove(b);
			tx.commit();
						
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			// TODO: handle exception
		}

	}
	
	static void inserData(EntityManager em) {
		
		//1. 트랜젝션 생성
		EntityTransaction tx =em.getTransaction();
		
		try {
			tx.begin();
			
			for(int i = 0; i < 11 ; i++ ) {
				Board b = new Board();
				b.setTitle("Title"+i);
				b.setWriter("Writer"+i);
				b.setContent("Content"+i);
				b.setCreateDate(new Date());
				b.setCnt(0L);
				em.persist(b);
				
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			// TODO: handle exception
		}
		
	}

}
