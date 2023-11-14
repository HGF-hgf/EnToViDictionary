//package com.example.dictionaryy;
//
//import java.sql.*;
//public class DatabaseConnection {
//    private static final String url = "jdbc::sqlite:src/dict_hh.db";
//
//    public static Connection connect() throws ClassNotFoundException, SQLException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection(url);
//        return conn;
//    }
//
//    public static void readData() throws SQLException, ClassNotFoundException {
//        Connection conn = connect();
//        String sql = "SELECT * FROM av";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//           String word = rs.getString("word");
//           String meaning = rs.getString("html");
//           Dictionary.words.put(word, meaning);
//        }
//        rs.close();
//        ps.close();
//        conn.close();
//    }
//
//    public static void insertData(String word_target, String word_explain) throws SQLException, ClassNotFoundException {
//        Connection con = connect();
//        String sql = "INSERT INTO av VALUES(?,?)";
//        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setString(1, word_target);
//        ps.setString(2, word_explain);
//        ps.execute();
//        ps.close();
//        con.close();
//    }
//
//    public static void deleteData(String word_target) throws SQLException, ClassNotFoundException {
//        Connection con = connect();
//        String sql = "DELETE FROM av WHERE word == ?";
//        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setString(1, word_target);
//        ps.execute();
//        ps.close();
//        con.close();
//    }
//
//    public static void updateData(String word_target, String word_explain) throws SQLException, ClassNotFoundException {
//        Connection con = connect();
//        String sql = "UPDATE av SET html = ? WHERE word == ?";
//        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setString(1, word_explain);
//        ps.setString(2, word_target);
//        ps.execute();
//        ps.close();
//        con.close();
//    }
//}
