package edu.pnu.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.pnu.domain.Member;

public class MemberDao {
    private static final String driver = "org.h2.Driver";
    private static final String url = "jdbc:h2:tcp://localhost/~/test";
    private static final String username = "sa";
    private static final String password = "tiger";

    private Connection con;

    public MemberDao() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertMember(Member member) {
        try {
            String sql = "INSERT INTO Member (name, age, nickname) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getName());
            pstmt.setInt(2, member.getAge());
            pstmt.setString(3, member.getNickname());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Member";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Member member = Member.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .age(rs.getInt("age"))
                        .nickname(rs.getString("nickname"))
                        .build();
                members.add(member);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public Member getMember(Long id) {
        Member member = null;
        try {
            String sql = "SELECT * FROM Member WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                member = Member.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .age(rs.getInt("age"))
                        .nickname(rs.getString("nickname"))
                        .build();
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }
}
