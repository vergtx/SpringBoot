package edu.pnu.dao.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pnu.domain.MemberVO;

public class MemberDaoH2Impl implements MemberInterface {
	private Connection con = null;
	
	public MemberDaoH2Impl() {
        try {
            // JDBC 드라이버 로드
            Class.forName("org.h2.Driver");
            
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Mission2", "scott", "tiger");
        }
        catch (Exception e) {            
            e.printStackTrace();
        }
	}
	
	@Override
	public Map<String, Object> getMembers() {
		Statement st = null;
		ResultSet rs = null;
		String sqlString = "select * from member order by id asc";
		try {
			// MemberVO 객체의 리스트를 생성하기 위해 ArrayList를 사용하고, 이 리스트를 List 타입의 변수인 list에 할당한다
			List<MemberVO> list = new ArrayList<>();
			st = con.createStatement();
			rs = st.executeQuery(sqlString); //st.executeQuery(sqlString)를 호출하여 SQL 쿼리를 실행하고 결과를 ResultSet 객체인 rs에 저장합니다.
			
			while(rs.next() ) { //rs.next()는 결과 집합에서 다음 행으로 이동 , 다음 행이 존재하면 true를 반환 //while 루프를 사용하여 결과 집합의 모든 행을 반복하면서 회원 정보를 추출
				MemberVO m = new MemberVO(); //rs.getInt("id"), rs.getString("pass"), rs.getString("name"), rs.getDate("regidate")를 사용하여 해당 열의 값을 가져와서 MemberVO 객체에 설정
				m.setId(rs.getInt("id"));
				m.setPass(rs.getString("pass"));
				m.setName(rs.getString("name"));
				m.setRegidate(rs.getDate("regidate"));
				
				list.add(m); //그리고 이 객체를 list에 추가합니다.
			}
			Map<String, Object> map = new HashMap<>(); //모든 행을 반복한 후, HashMap 객체인 map을 생성
			map.put("sql", sqlString); //map.put("sql", sqlString)을 사용하여 SQL 쿼리 문자열을 map에 추가
			map.put("data", list); //map.put("data", list)를 사용하여 회원 목록인 list를 map에 추가
			
			return map; //map을 반환하여 조회된 "회원 목록"과 "SQL 쿼리 문자열"을 "함께" 반환
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (st != null)	st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> getMember(Integer id) {
		
		Statement st = null;
		ResultSet rs = null;
		try {
			String sqlString = String.format("select * from member where id=%d", id);
			st = con.createStatement();
			rs = st.executeQuery(sqlString);
			
			rs.next();
			
			MemberVO m = new MemberVO();
			
			m.setId(rs.getInt("id"));
			m.setPass(rs.getString("pass"));
			m.setName(rs.getString("name"));
			m.setRegidate(rs.getDate("regidate"));
			
			Map<String, Object> map = new HashMap<>();
			
			map.put("sql", sqlString);
			
			map.put("data", m);
			
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (st != null)	st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private int getNextId() {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select max(id) from member");
			rs.next(); //next()를 호출하여 첫 번째 행으로 이동
			return rs.getInt(1) + 1; //rs.getInt(1)을 호출하여 현재 행의 첫 번째 열의 값을 정수로 가져움 최댓값을 가져왔으므로 최댓값
		} catch (Exception e) {     //1을 통해 현재 최댓값에 1을 더하여 새로운 고유한 ID 값을 생성
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (st != null)	st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;		
	}
	
	@Override
	public Map<String, Object> addMember(MemberVO member) {
		
		int id = getNextId();
		
		Statement st = null;
		try {
			String sqlString = String.format("insert into member (id,name,pass,regidate) values (%d,'%s','%s','%s')",
				id, member.getName(), member.getPass(), new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
			st = con.createStatement();

			Map<String, Object> ret = new HashMap<>();
			if (st.executeUpdate(sqlString) == 1) {
				Map<String, Object> map = getMember(id);
				ret.put("sql", sqlString);
				ret.put("data", map.get("data"));
			}
			else {
				ret.put("sql", sqlString);
				ret.put("data", null);
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)	st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> updateMember(MemberVO member) {
		Statement st = null;
		try {
			String sqlString = String.format("update member set name='%s',pass='%s' where id=%d",
					member.getName(), member.getPass(), member.getId());
			st = con.createStatement();
			
			Map<String, Object> ret = new HashMap<>();
			if (st.executeUpdate(sqlString) == 1) {
				Map<String, Object> map = getMember(member.getId());
				ret.put("sql", sqlString);
				ret.put("data", map.get("data"));
			}
			else {
				ret.put("sql", sqlString);
				ret.put("data", null);
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)	st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> deleteMember(Integer id) {
		Statement st = null;
		try {
			String sqlString = String.format("delete from member where id=%d", id);
			st = con.createStatement();

			Map<String, Object> map = getMember(id);
			if (map.get("data") == null) 
				return null;

			Map<String, Object> ret = new HashMap<>();
			if (st.executeUpdate(sqlString) == 1) {
				ret.put("sql", sqlString);
				ret.put("data", map.get("data"));
			}
			else {
				ret.put("sql", sqlString);
				ret.put("data", null);
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)	st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
