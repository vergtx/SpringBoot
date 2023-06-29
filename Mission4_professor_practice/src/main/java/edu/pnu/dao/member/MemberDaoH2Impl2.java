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

import javax.naming.spi.DirStateFactory.Result;

import edu.pnu.domain.MemberVO;

public class MemberDaoH2Impl2 implements MemberInterface {

	private Connection con = null;

	public MemberDaoH2Impl2() {
		try {
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Mission2", "scott", "tiger");

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	@Override
	public Map<String, Object> getMembers() {
		Statement st = null;
		ResultSet rs = null;
		String sqlString = "select * from member order by id asc";
		try {
			List<MemberVO> list = new ArrayList<>();
			st = con.createStatement();
			rs = st.executeQuery(sqlString);

			while (rs.next()) {
				MemberVO m = new MemberVO();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("pass"));
				m.setPass(rs.getString("pass"));
				m.setRegidate(rs.getDate("regidate"));

				list.add(m);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("spl", sqlString);
			map.put("data", list);

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();

			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> getMember(Integer id) {
		Statement st = null;
		ResultSet rs = null;

		try {
			String sqlString = String.format("Select * from member where id = %d", id);
			st = con.createStatement();
			rs = st.executeQuery(sqlString);

			rs.next();

			MemberVO m = new MemberVO();

			m.setId(rs.getInt("id"));
			m.setPass(rs.getString("pass"));
			m.setName(rs.getString("name"));
			m.setRegidate(rs.getDate("regidata"));

			Map<String, Object> map = new HashMap<>();

			map.put("spl", sqlString);
			map.put("data", m);

			return map;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	private int getNextID() {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select max(id) from member");
			rs.next();
			return rs.getInt(1) + 1;
		} catch (Exception e) { // 1을 통해 현재 최댓값에 1을 더하여 새로운 고유한 ID 값을 생성
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}

	@Override
	public Map<String, Object> addMember(MemberVO member) {

		int id = getNextID();

		Statement st = null;

		try {
			String sqlString = String.format("insert into member (id, name, psas, regidate) values(%d,'%s','%s','%s')",
					id, member.getName(), member.getPass(),
					new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));

			st = con.createStatement();

			if (st.executeUpdate(sqlString) == 1) {

				Map<String, Object> map = getMember(id);
				map.put("sql", sqlString);
				return map;
			}

			Map<String, Object> ret = new HashMap<>();
			ret.put("sql", sqlString);
			ret.put("data", null);
			return ret;

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
		return null;
	}

	@Override
	public Map<String, Object> updateMember(MemberVO member) {
		int id = getNextID();

		Statement st = null;

		try {
			String sqlString = String.format("update member set name='%s',pass='%s' where id=%d", member.getName(),
					member.getPass(), member.getId());

			st = con.createStatement();

			if (st.executeUpdate(sqlString) == 1) {

				Map<String, Object> map = getMember(id);
				map.put("sql", sqlString);
				// map.put("data", map.get("data"));
				return map;
			}

			Map<String, Object> ret = new HashMap<>();
			ret.put("sql", sqlString);
			ret.put("data", null);
			return ret;

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
		return null;
	}

	@Override
	public Map<String, Object> deleteMember(Integer id) {
		
		Statement st = null;
		
		 try {
		        String sqlString = String.format("delete from member where id = %d", id);
		        st = con.createStatement();
		        
		        Map<String, Object> map = getMember(id);
		        if (map.get("data") == null)
		            return null;
		        
		        Map<String, Object> ret = new HashMap<>();
		        if (st.executeUpdate(sqlString) == 1) {
		            ret.put("sql", sqlString);
		        } else {
		            ret.put("sql", sqlString);
		            ret.put("data", null);
		            return ret;
		        }
		        
		        return ret;
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
		    
		    return null;
		}
}
