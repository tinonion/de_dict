import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Parser {

    private static Translation constructTranslation(Element info) {
        Translation tran = new Translation();

        try {
            Element collEle = info.selectFirst("td.td7nl > dfn");
            tran.collection = collEle.attr("title");
            collEle.remove();

        } catch (NullPointerException ex) {
            tran.collection = "NONE";
        }

        Element rootEle = info.selectFirst("td.td7nl");
        tran.contextAttr = rootEle.select("kbd").text();
        tran.gramType = rootEle.select("var").text();
        rootEle.select("kbd, abbr, var").remove();

        String[] rootTags = rootEle
                .text()
                .split("\\s");

        Element tranEle = info.selectFirst("td.td7nl + td.td7nl");
        tran.gender = tranEle.select("var").text();
        tranEle.select("kbd, abbr, var").remove();

        String[] tranTags = tranEle
                .text()
                .split("\\s");


        for (String tag : rootTags) {
            if (tag.equals("to")) {
                tran.gramType = "verb";

            } else {
                tran.root = tag;
            }
        }

        for (String tag : tranTags) {
            Matcher m = Pattern.compile("(\\d+)?([^\\d]+)?")
                    .matcher(tag);
            m.find();

            if (m.group(1) != null) {
                tran.popularity = Integer.parseInt(m.group(1));
            }

            if (m.group(2) != null) {
                tran.translation = m.group(2);
            }
        }

        tran.gramType = tran.gramType.replace("{", "");
        tran.gramType = tran.gramType.replace("}", "");

        if (!tran.gender.equals("")) {
            tran.gramType = "noun";
            tran.assignGender();

        } else if (tran.gramType.equals("verb")) {
            tran.root = "to ".concat(tran.root);
        }

        tran.assignGramType();
        return tran;
    }

    static ArrayList<List<Translation>> search(String query){
        Document doc = null;

        try {
            doc = Jsoup.connect("https://www.dict.cc/?s=" + query).get();

        } catch (IOException ex) {
            System.out.println("IOEXCEPTION THROWN");
        }

        Elements headCells = doc.select(
                "table[cellspacing='1'] > tbody > tr:has(td.td2, td.noline, td.td6):not(:matches(Wörter)) + tr[id]");

        List<Integer> headIDs = new ArrayList<>();
        headCells.forEach(cell -> headIDs.add(Integer.parseInt(cell
            .id()
            .substring(2))));

        ArrayList<List<Translation>> results = new ArrayList<>();
        for (int i = 0; i < headCells.size(); i++) {
            List<Translation> section = new ArrayList<>();
            section.add(constructTranslation(headCells.get(i)));

            for (int j = headIDs.get(i) + 1; j < headIDs.get(i) + 3; j++) {
                if (!headIDs.contains(j)) {
                    Element foundCell = doc.selectFirst(
                            "table[cellspacing='1'] > tbody > tr[id=tr" + String.valueOf(j) + "]");

                    if (foundCell != null) {
                        section.add(constructTranslation(foundCell));
                    }

                } else {
                    break;
                }
            }

            for (Translation translation : new ArrayList<>(section)) {
                if (translation.gramType == null) {
                    section.remove(translation);
                }
            }

            if (section.size() > 0) {
                results.add(section);
            }
        }

        return results;
    }
}
