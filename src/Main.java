import javax.swing.*;
import java.awt.*;
import java.util.List;

class Main {

    public static void main(String[] args) {
        String query = "quickly";

        List<Translation> results = Parser.search(query);
        for (Translation result : results) {
            System.out.println("\nQUERY: " + query);
            System.out.println("ROOT: " + result.root + " GRAM TYPE: " + result.gramType + " CONTEXT: " + result.contextAttr);
            System.out.println("TRANSLATION: " + result.translation + " GENDER: " + result.gender + " POPULARITY: " + result.popularity);
            System.out.println("COLLECTION: " + result.collection);
        }

        new TopFrame();
    }
}
