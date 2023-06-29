package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.pnu.domain.Member;

public class MemberDao {

	private String driver = "org.h2.Driver";
	private String url = "jdbc:h2:tcp://localhost/~/Mission2";
	private String username = "scott";
	private String password = "tiger";

	private Connection con;

	public MemberDao() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName(driver);

			con = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Member getMember(Long id) {
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(String.format("Select * from Member where id = %d", id));

			rs.next();

			Member m = Member.builder()
					.id(rs.getLong("id"))
					.pass(rs.getString("pass"))
					.name(rs.getString("name"))
					.regidate(rs.getDate("regidate"))
					.build();

			rs.close();
			stmt.close();

			return m;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int insertMember(Member member) {
	
		member.setRegidate(new Date());
		try {
		    String sql = "INSERT INTO member (pass, name) VALUES (?, ?)";
		    PreparedStatement psmt = con.prepareStatement(sql);
		  
		    psmt.setString(1, member.getPass());
		    psmt.setString(2, member.getName());
		
		    int ret = psmt.executeUpdate();
		    
		    
		    psmt.close();
		    
		    return ret;
		    
		} catch (SQLException e) {
		    e.printStackTrace();
		} 
	  return 0;
	}

	public List<Member> getMembers() {
	    List<Member> members = new ArrayList<>();

	    try {
	    	Statement stmt = con.createStatement();

	        String sql = "SELECT * FROM member";  // 쿼리문 작성
	        
	        ResultSet rs = stmt.executeQuery(sql);

	        while (rs.next()) {
	            Long id = rs.getLong("id");
	            String pass = rs.getString("pass");
	            String name = rs.getString("name");
	            Date regidate = rs.getDate("regidate");

	            Member member = new Member(id, pass, name, regidate);
	            members.add(member);  // members 리스트에 member 객체 추가
	        }

	        rs.close();
	        stmt.close();
	  
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return members;
	}

	

	public int deleteMember(Long id) {
	    try {
	        Statement stmt = con.createStatement();
	        String sql = String.format("DELETE FROM Member WHERE id = %d", id);
	        int affectedRows = stmt.executeUpdate(sql);
	        stmt.close();
	        return affectedRows;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

	public int updateMember(Member member) {
	    try {
	        String sql = "UPDATE Member SET pass = ?, name = ? WHERE id = ?";
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, member.getPass());
	        pstmt.setString(2, member.getName());
	        pstmt.setLong(3, member.getId());
	        int affectedRows = pstmt.executeUpdate();
	        pstmt.close();
	        return affectedRows;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

}