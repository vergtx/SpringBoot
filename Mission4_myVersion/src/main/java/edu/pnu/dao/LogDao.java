package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogDao {

	private Connection con = null;

	public LogDao() {
		try {
			// JDBC 드라이버 로드
			Class.forName("org.h2.Driver");

			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Mission2", "scott", "tiger");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addLog(String method, String sqlString, boolean success) {
		try (
			PreparedStatement st = con.prepareStatement("INSERT INTO dblog (method, sqlstring, success) VALUES (?, ?, ?)")) {

			st.setString(1, method);
			st.setString(2, sqlString);
			st.setBoolean(3, success);

			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}