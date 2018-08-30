import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

class Database {

    private static Connection con;
    private static PreparedStatement generalQueryStatement;
    private static PreparedStatement queryVerbExceptions;

    static void initialize() throws SQLException, IOException {
        con = DriverManager.getConnection("jdbc:hsqldb:file:db/database", "SA", "");

        con.createStatement().execute(Main.readToString("sql/init_dict.sql"));
        con.createStatement().execute(Main.readToString("sql/init_verbs.sql"));
        con.createStatement().execute(Main.readToString("sql/populate_verbs.sql"));

        generalQueryStatement = con.prepareCall("SELECT * FROM DICT WHERE EN = ?;");
        queryVerbExceptions = con.prepareCall(
                "SELECT * FROM IRREG_VERBS, SPECIAL_VERBS WHERE INFINITIVE = ?;");
    }

    private static boolean isValidDictEntry(String[] valueArray) {
        return !valueArray[0].equals("") && !valueArray[4].equals("") && !valueArray[7].equals("");
    }

    static void generateDatabase(String fileName) throws SQLException,  IOException{
        con.createStatement().execute(Main.readToString("sql/init_dict.sql"));
        PreparedStatement insertDictEntry = con.prepareCall("INSERT INTO dict VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

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
