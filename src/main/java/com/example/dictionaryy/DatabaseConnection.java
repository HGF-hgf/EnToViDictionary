package com.example.dictionaryy;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
////import android.os.StrictMode;
//
//public class DatabaseConnection {
//    public Connection databaseLink;
//    public Connection getConnection(){
//
//        String databaseName = "dictionary";
//        String databaseUser = "root";
//        String databasePassword = "password";
//        String url = "jdbc:mysql://127.0.0.1:3306/" + databaseName;
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause();
//        }
//        return databaseLink;
//    }
//}

 //   preparedStatement.setString(1, "hi");
//            preparedStatement.setString(2, "yui");
//            preparedStatement.executeUpdate();
//
//            preparedStatement.setString(1, "bye");
//            preparedStatement.setString(2, "cac");
//            preparedStatement.executeUpdate();
//
//            preparedStatement.setString(1, "chao");
//            preparedStatement.setString(2, "leo1");
//            preparedStatement.executeUpdate();
//
//            preparedStatement.setString(1, "good");
//            preparedStatement.setString(2, "leo2");
//            preparedStatement.executeUpdate();
//
//            preparedStatement.setString(1, "sub");
//            preparedStatement.setString(2, "leo3");
//            preparedStatement.executeUpdate();
