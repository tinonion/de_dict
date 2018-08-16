import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.regex.Pattern;

class Verb {

    private static Pattern findRoot = Pattern.compile(
            "^(an|auf|aus|bei|ein|fort|mit|nach|vor|weg|zu)?(.+?)e?n$");

    static ArrayList<ArrayList<String>> generateVerbInfo(String infinitive) {
        ArrayList<ArrayList<String>> verbInfo = new ArrayList<>();

       // PreparedStatement queryVerb =
       return null;
    }
}
