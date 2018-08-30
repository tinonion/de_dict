import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Dict {

    private static Pattern gramTypePat = Pattern.compile("(noun|verb|adj|adv|prefix|pron|pres-p|conj|prep|past-p)");
    private static Pattern validEntryPat = Pattern.compile("^to\\s");
    private static Pattern shortEntryPat = Pattern.compile("^(?:jd|sb|etw|sth)\\.\\s((?:to\\s)?\\w+)$");

    private static String findEnclosedString(String str, char start, char end) {
        StringBuilder sb = new StringBuilder();
        sb.append(start);

        for (int ind = 1; ind < str.length(); ind++) {
            if (str.charAt(ind) == start) {
                String nested = findEnclosedString(str.substring(ind), start, end);
                ind += nested.length() - 1;
                sb.append(nested);

            } else if (str.charAt(ind) == end) {
                return sb.append(end).toString();

            } else {
                sb.append(str.charAt(ind));
            }
        }
        return sb.append(end).toString();
    }

    private static boolean isValidElement(String element) {
        // is valid if it is only one word, not including an initial 'to ' i.e. "to walk" is valid even though
        // the entry is technically two words
        Matcher m = validEntryPat.matcher(element);
        return !element.contains(" ") || m.find();
    }

    private static void interpretChunk(String[] valueArray, String chunk, int chunkInd) {
        StringBuilder newEle = new StringBuilder();

        for (int charInd = 0; charInd < chunk.length(); charInd++) {
            char c = chunk.charAt(charInd);

            String enclosed = "";
            if ("{(<[".contains(Character.toString(c))) {
                switch (c) {
                    case '{':
                        enclosed = findEnclosedString(chunk.substring(charInd), '{', '}');
                        valueArray[2] = enclosed;

                        break;
                    case '(':
                        enclosed = findEnclosedString(chunk.substring(charInd), '(', ')');
                        if (chunkInd == 0) {
                            valueArray[1] = enclosed;

                        } else {
                            valueArray[5] = enclosed;
                        }

                        break;
                    case '[':
                        enclosed = findEnclosedString(chunk.substring(charInd), '[', ']');
                        if (chunkInd == 0) {
                            valueArray[3] += enclosed;

                        } else {
                            valueArray[6] += enclosed;
                        }

                        break;
                    case '<':
                        // These tokens contain elements that are completely ignored, skip is set
                        // to allow loop to skip over this area of the chunk completely
                        enclosed = findEnclosedString(chunk.substring(charInd), '<', '>');
                        break;
                }
                charInd += enclosed.length();

            } else {
                newEle.append(c);
            }
        }


        String ele = newEle.toString().trim();
        Matcher m = gramTypePat.matcher(ele);
        if (chunkInd == 0 && isValidElement(ele)) {
            valueArray[0] = ele;

        } else if (chunkInd == 1 && isValidElement(ele)) {
            valueArray[4] = ele;

        } else if (m.find()) {
            valueArray[7] = m.group(1);
        }
    }

    private static String[] parseFullEntry(String[] chunkArray) {
        String[] valueArray = new String[8];
        Arrays.fill(valueArray, "");

        for (int ind = 0; ind < chunkArray.length; ind++) {
            interpretChunk(valueArray, chunkArray[ind], ind);

        }
        return valueArray;
    }

    private static String[] parseConjugation(String[] chunkArray) {
        String[] valueArray = new String[8];
        Arrays.fill(valueArray, "");
        valueArray[7] = "verb";

        for (int ind = 0; ind < chunkArray.length; ind++) {
            Matcher m = shortEntryPat.matcher(chunkArray[ind].toLowerCase());

            if (m.find()) {
                if (ind == 0) {
                    valueArray[3] = m.group(1).trim();

                } else {
                    valueArray[6] = m.group(1).trim();
                }
            }
        }
        return valueArray;
    }

    static String[] parseLine(String line) {
        String[] chunkArray = line.split("\\t");
        if (chunkArray.length == 3) {
            return parseFullEntry(chunkArray);

        }/* else {
            return parseConjugation(chunkArray);
        }*/
        return null;
    }
}
