import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Verb {

    // These lists of endings are ordered such that they correspond to following subjects respectively:
    // ich, du, er/sie/es, wir, ihr, Sie/sie
    private static List<String> prasensEndings = Arrays.asList("e", "st", "t", "en", "t", "en");
    private static List<String> weakPrateritumEndings = Arrays.asList("te", "test", "te", "ten", "tet", "ten");
    private static List<String> strongPrateritumEndings = Arrays.asList("", "st", "", "en", "t", "en");
    private static List<Integer> prasensSpecialColIndices = Arrays.asList(4, 5, 6, 7, 8, 9);
    private static List<Integer> prateritumSpecialColIndices = Arrays.asList(10, 11, 12, 13, 14, 15);
    private static Pattern findVerbComponents = Pattern.compile(
            "^(an|auf|aus|bei|ein|fort|mit|nach|vor|weg|zu)?(be|emp|ent|er|ge|miss|ver|zer)?(.+?)e?n$");

    static List<String> generateVerbInfo(String infinitive) {
        ArrayList<String> verbInfo = new ArrayList<>();

        Matcher m = findVerbComponents.matcher(infinitive);
        String sepPrefix;
        String insepPrefix;
        String verbRoot;
        if (m.find()) {
            sepPrefix = m.group(1);
            insepPrefix = m.group(2);
            verbRoot = m.group(3);

        } else {
            return null;
        }

        ResultSet irregVerb = Database.irregVerbQuery(infinitive);
        try {
            if (irregVerb.next()) {
                String pratRoot = irregVerb.getString(3);
                String pastParticiple = irregVerb.getString(2);

                if (irregVerb.getBoolean(5)) {
                    ResultSet specialVerb = Database.specialVerbQuery(infinitive);
                    if (specialVerb.next()) {
                        for (Integer colInd : prasensSpecialColIndices) {
                            verbInfo.add(specialVerb.getString(colInd));
                        }

                        for (Integer colInd : prateritumSpecialColIndices) {
                            verbInfo.add(specialVerb.getString(colInd));
                        }
                        verbInfo.add(pastParticiple);
                        return verbInfo;

                    } else {
                        System.out.println("SPECIAL VERB NOT FOUND " + infinitive);
                        return null;
                    }
                } else if (irregVerb.getBoolean(4)) {
                    verbInfo.addAll(appendVerbEndings(pratRoot, weakPrateritumEndings));

                } else {
                    verbInfo.addAll(appendVerbEndings(pratRoot, strongPrateritumEndings));
                }
                verbInfo.addAll(appendVerbEndings(verbRoot, prasensEndings));
                verbInfo.add(pastParticiple);
                return verbInfo;
             }

        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        }
        verbInfo.addAll(appendVerbEndings(verbRoot, prasensEndings));
        verbInfo.addAll(appendVerbEndings(verbRoot, weakPrateritumEndings));
        verbInfo.add(createRegularPastParticiple(sepPrefix, insepPrefix, verbRoot));
        return verbInfo;
    }

    private static ArrayList<String> appendVerbEndings(String verbRoot, List<String> endings) {
        ArrayList<String> verbConjugations = new ArrayList<>();

        for (String ending : endings) {
            if (verbRoot.charAt(verbRoot.length() - 1) == 't' && ending.charAt(0) == 't') {
                ending = "e" + ending;
            }
            verbConjugations.add(verbRoot + ending);
        }
        return verbConjugations;
    }

    private static String createRegularPastParticiple(String sepPrefix, String insepPrefix, String verbRoot) {
        if (verbRoot.charAt(verbRoot.length() - 1) == 't') {
            verbRoot += "e";
        }

        String completePrefix = "ge";
        if (sepPrefix != null) {
            completePrefix = sepPrefix + completePrefix;

        } else if (insepPrefix != null) {
            completePrefix = insepPrefix;
        }
        return completePrefix + verbRoot + "t";
    }

}
