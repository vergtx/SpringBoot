package edu.pnu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.pnu.domain.MemberVO;

public class MemberDaoH2Implement implements MemberInterface {

	private Connection con = null;

	public MemberDaoH2Implement() {

		try {
			// JDBC 드라이버 로드
			Class.forName("org.h2.Driver");

			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Mission2", "scott", "tiger");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MemberVO> getmembers() {
		Statement st = null;
		ResultSet rs = null;
		List<MemberVO> list = new ArrayList<>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from member order by id asc");
			while (rs.next()) {
				MemberVO m = new MemberVO();
				m.setId(rs.getInt("id"));
				m.setPass(rs.getString("pass"));
				m.setName(rs.getString("name"));
				m.setRegidata(rs.getDate("regidate"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public MemberVO getMember(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = con.prepareStatement("select * from member where id=?");
			st.setInt(1, id);
			rs = st.executeQuery();
			rs.next();
			MemberVO m = new MemberVO();
			m.setId(rs.getInt("id"));
			m.setPass(rs.getString("pass"));
			m.setName(rs.getString("name"));
			m.setRegidata(rs.getDate("regidate"));
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
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
			rs.next();
			return rs.getInt(1) + 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}

	@Override
	public MemberVO addMember(MemberVO member) {
		// TODO Auto-generated method stub
		int id = getNextId();

		PreparedStatement st = null;
		try {
			st = con.prepareStatement("insert into member (id,name,pass,regidate) values (?,?,?,?)");
			st.setInt(1, id);
			st.setString(2, member.getName());
			st.setString(3, member.getPass());
			st.setDate(4, new Date(System.currentTimeMillis()));
			st.executeUpdate();

			return getMember(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public MemberVO updateMember(MemberVO member) {
		PreparedStatement st = null;
		try {
			st = con.prepareStatement("update member set name=?,pass=? where id=?");
			st.setString(1, member.getName());
			st.setString(2, member.getPass());
			st.setInt(3, member.getId());
			st.executeUpdate();

			return getMember(member.getId());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public MemberVO deleteMember(Integer id) {

		PreparedStatement st = null;
		try {

			MemberVO deletedMember = getMember(id); // getMember(id) 메서드를 호출 id에 해당하는 회원의 정보를 데이터베이스에서 가져옴 MemberVO
													// 객체인 deletedMember에 저장 <<< 삭제전 id 를 저장 cuz 삭제 후 에는 id 내용 조회 안됙 때문
			// ==>이렇게 되면 deletedMember에는 삭제된 회원의 정보가 저장되어 있습니다. 이 정보를 활용하여 필요한 작업을 수행
			st = con.prepareStatement("delete from member where id=?");
			st.setInt(1, id);  //st.setInt(1, id)를 호출하여 SQL 문의 매개변수 ?에 id 값을 설정합니다.
			
			int rowsAffected = st.executeUpdate(); //st.executeUpdate()는 SQL 문을 실행하고, 해당 문이 데이터베이스에 영향을 미친 행(row)의 수를 반환합니다. 
		
			if (rowsAffected == 1) // rowsAffected 변수의 값이 1인지 확인 -> 삭제된 행의 수가 정확히 1인 경우를 의미
				return deletedMember; // 삭제된 행의 수(rowsAffected)가 1인 경우에는 삭제가 성공적으로 이루어졌으므로, 삭제된  회원의 정보(MemberVO)를 출력
									// rowsAffected 값이 1인 경우는 삭제한 회원이 정확히 1명일 때를 의미하며, 해당 회원의 정보를 반환
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null; // 삭제 실패 시 null 반환
	}
	// ==>> 이 방법으로도 입력된 id 값 받아 올 수 있다 getMember(id) 호출하는데신에 입력된 id 값 들고 올 수 있다.
	
//	public MemberVO deleteMember(Integer id) {
//		PreparedStatement st = null;
//		try {
//			st = con.prepareStatement("delete from member where id=?");
//			st.setInt(1, id);
//			int rowsAffected = st.executeUpdate();
//			if (rowsAffected == 1) {
//				MemberVO deletedMember = new MemberVO();
//				deletedMember.setId(id);
//				return deletedMember;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				st.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}

}