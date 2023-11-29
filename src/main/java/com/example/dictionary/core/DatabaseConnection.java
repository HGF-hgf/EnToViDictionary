package com.example.dictionary.core;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection extends Dictionary {
    private static final String url = "jdbc:sqlite:src/dict_hh.db";
    private static Connection con = null;

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection(url);
        System.out.println("Connection to SQLite has been established.");
    }

    private static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Words> getWordsFromDB(PreparedStatement ps) throws SQLException {
        try {
            ResultSet rs = ps.executeQuery();
            try {
                ArrayList<Words> words = new ArrayList<>();
                while (rs.next()) {
                    words.add(new Words(rs.getString("word"), rs.getString("html")));
                }
                return words;
            } finally {
                close(rs);
            }
        } finally {
            close(ps);
        }

    }

    public ArrayList<Words> getWordsPartial(int start, int end) {
        final String query = "SELECT * FROM dictionary WHERE id >= ? AND id <= ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, start);
            ps.setInt(2, end);
            return getWordsFromDB(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void initialize() throws SQLException {
        try {
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> words = getWords();
        for (String w : words) {
            Trie.insert(w);
        }
    }

    @Override
    public void close() {
        close(con);
        System.out.println("Connection to SQLite has been closed.");
    }

    @Override
    public String search(final String word) {
        final String query = "SELECT html FROM av WHERE word == ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, word);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        return rs.getString("html");
                    } else {
                        return "<h1 style=\"text-align: center;\">No word found</h1>";
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "<h1 style=\"text-align: center;\">No word found</h1>";
    }

    @Override
    public boolean insert(final String word, final String meaning) {
        final String query = "INSERT INTO av VALUES (?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, word);
            ps.setString(2, meaning);
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                return false;
            } finally {
                close(ps);
            }
            Trie.insert(word);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(final String word) {
        final String query = "DELETE FROM av WHERE word == ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, word);
            try {
                if (ps.executeUpdate() == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
            Trie.delete(word);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(final String word, final String meaning) {
        final String query = "UPDATE av SET html = ? WHERE word == ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, meaning);
            ps.setString(2, word);
            try {
                if (ps.executeUpdate() == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public  ArrayList<Words> getAllWords(){
        final String query = "SELECT * FROM av";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            return getWordsFromDB(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public  ArrayList<String> getWords(){
        final String query = "SELECT * FROM av";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    ArrayList<String> words = new ArrayList<>();
                    while (rs.next()) {
                        words.add(rs.getString("word"));
                    }
                    return words;
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
