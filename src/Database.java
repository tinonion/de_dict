import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

class Database {

    private static Connection con;
    private static PreparedStatement generalQueryStatement;
    private static PreparedStatement queryVerbExceptions;

    static void initialize() throws SQLException, IOException {
        con = DriverManager.getConnection("jdbc:hsqldb:file:database", "SA", "");

        // Following block is used in executable jar version of project
//        InputStream initDict = Database.class.getResourceAsStream("init_dict.sql");
//        InputStream initVerbs = Database.class.getResourceAsStream("init_verbs.sql");
//        con.createStatement().execute(IOUtils.toString(initDict, StandardCharsets.UTF_8.name()));
//        con.createStatement().execute(IOUtils.toString(initVerbs, StandardCharsets.UTF_8.name()));

        con.createStatement().execute(Main.readToString("init_dict.sql"));
        con.createStatement().execute(Main.readToString("init_verbs.sql"));

        // Checks if verb tables are populated, if not, run sql script to populate them
        ResultSet verbsPopulated = con.createStatement().executeQuery("SELECT * FROM IRREG_VERBS");
        if (!verbsPopulated.next()) {
            con.createStatement().execute(Main.readToString("populate_verbs.sql"));
        }

        generalQueryStatement = con.prepareCall("SELECT * FROM DICT WHERE EN = ?;");
        queryVerbExceptions = con.prepareCall(
                "SELECT * FROM IRREG_VERBS, SPECIAL_VERBS WHERE INFINITIVE = ?;");
    }

    static void verifyDatabase() {
        try {
            ResultSet dbCheck = con.createStatement().executeQuery("SELECT * FROM DICT LIMIT 1;");
            if (!dbCheck.next()) {
                int dbNotFoundResult = JOptionPane.showConfirmDialog(null,
                        "Would you like to generate the dictionary database?",
                        "Dictionary Database Not Found", JOptionPane.YES_NO_OPTION);

                if (dbNotFoundResult == 0) {
                    Database.generateDatabase();
                }
            }
        } catch (SQLException ex) {
            System.out.println("EX while verifying database");
        }
    }

    private static boolean isValidDictEntry(String[] valueArray) {
        return !valueArray[0].equals("") && !valueArray[4].equals("") && !valueArray[7].equals("");
    }

    static void generateDatabase() {
        try {
            PreparedStatement insertDictEntry = con.prepareCall("INSERT INTO dict VALUES (?, ?, ?, ?, ?, ?, ?, ?);");


            // Following block is used for executable jar version of project
//            InputStream dictStream = Database.class.getResourceAsStream("dict.txt");
//            byte[] buffer = IOUtils.toByteArray(dictStream);
//            Reader targetReader = new CharSequenceReader(new String(buffer));

            String fileName = "dict.txt";
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] foundDictValues = Dict.parseLine(line);

                if (foundDictValues != null && isValidDictEntry(foundDictValues)) {
                    // Checks if verb is already in the verb exception tables
                    if (foundDictValues[7].equals("verb")) {
                        queryVerbExceptions.setString(1, foundDictValues[0]);

                        ResultSet verbExceptionCheck = queryVerbExceptions.executeQuery();
                        if (verbExceptionCheck.next()) {
                            continue;
                        }
                    }

                    insertDictEntry.clearParameters();

                    for (int ind = 0; ind < 8; ind++) {
                        insertDictEntry.setString(ind + 1, foundDictValues[ind]);
                    }
                    insertDictEntry.execute();
                }
            }

        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static ResultSet generalQuery(String query) {
        try {
            generalQueryStatement.clearParameters();
            generalQueryStatement.setString(1, query);

            return generalQueryStatement.executeQuery();

        } catch (SQLException ex) {
            System.out.println("SQL EXCEPTION ON GENERAL QUERY");
            return null;
        }
    }

    static void closeConnection() {
        System.out.println("CONNECTION CLOSED");

        try {
            con.close();

        } catch (SQLException ex) {
            System.out.println("SQL EXCEPTION ON CLOSE");
        }
    }
}
