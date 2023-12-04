package com.example.dictionary.core;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection extends Dictionary {
    private static final String url = "jdbc:sqlite:src/dict_hh.db";
    private static Connection con = null;

    /**
     * Connect to Sqlite database.
     *
     * @throws SQLException           if a database access error occurs
     * @throws ClassNotFoundException if the class cannot be located
     */
    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection(url);
        System.out.println("Connection to SQLite has been established.");
    }

    /**
     * Close connection to Sqlite database.
     *
     * @param conn Connection variable
     */

    private static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Close PreparedStatement.
     *
     * @param ps PreparedStatement variable
     */
    private static void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close ResultSet.
     *
     * @param rs ResultSet variable
     */
    private static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all words from result set of the given Sqlite query.
     *
     * @param ps the SQL query included in PreparedStatement
     * @return ArrayList of Words
     * @throws SQLException exception
     */
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

    /** Connect to Sqlite database. Add all words on the database into Trie data structure. */
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

    /** Close connection to Sqlite database. */
    @Override
    public void close() {
        close(con);
        System.out.println("Connection to SQLite has been closed.");
    }

    /**
     * Lookup the word `target` and return the corresponding definition.
     *
     * @param word the lookup word
     * @return the definition, if not found "404" is returned as a String.
     */
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

    /**
     * Insert a new word to dictionary.
     *
     * @param word    the word
     * @param meaning the definition
     * @return true if `target` hasn't been added yet, false otherwise
     */
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

    /**
     * Delete a word from dictionary.
     *
     * @param word the word
     * @return true if `target` has been deleted, false otherwise
     */
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

    /**
     * Update the Vietnamese definition of `target` to `definition`.
     *
     * @param word    the word
     * @param meaning the new definition
     * @return true if successfully updated, false otherwise
     */
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

    /**
     * Get all words into an `ArrayList(Word)>`.
     *
     * @return an 'ArrayList(Word)' include all the words from the database
     */
    @Override
    public ArrayList<Words> getAllWords() { // Method to get all Words objects from the 'av' table in the database.
        final String query = "SELECT * FROM av"; // SQL query to select all rows from the 'av' table.
        try {
            PreparedStatement ps = con.prepareStatement(query); // Prepare the SQL query.
            return getWordsFromDB(ps); // Execute the query and get the Words objects from the database.
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if a SQLException is thrown.
        }
        return new ArrayList<>(); // Return an empty ArrayList if an exception is thrown.
    }

    /**
     * Get all words into an `ArrayList(String)>`.
     *
     * @return an 'ArrayList(String)' include all the words from the database
     */
    @Override
    public ArrayList<String> getWords() { // Method to get all words (as Strings) from the 'av' table in the database.
        final String query = "SELECT * FROM av"; // SQL query to select all rows from the 'av' table.
        try {
            PreparedStatement ps = con.prepareStatement(query); // Prepare the SQL query.
            try {
                ResultSet rs = ps.executeQuery(); // Execute the query and get the ResultSet.
                try {
                    ArrayList<String> words = new ArrayList<>(); // Initialize an ArrayList to store the words.
                    while (rs.next()) { // Iterate over each row in the ResultSet.
                        words.add(rs.getString("word")); // Add the word from the current row to the ArrayList.
                    }
                    return words; // Return the ArrayList of words.
                } finally {
                    close(rs); // Close the ResultSet.
                }
            } finally {
                close(ps); // Close the PreparedStatement.
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace if a SQLException is thrown.
        }
        return new ArrayList<>(); // Return an empty ArrayList if an exception is thrown.
    }

}
