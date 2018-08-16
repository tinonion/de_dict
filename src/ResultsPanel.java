import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

class ResultsPanel extends JPanel {

    ResultsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(300, 270));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setAlignmentX(LEFT_ALIGNMENT);
    }

    private HashMap<String, ArrayList<ArrayList<String>>> generateSections(ResultSet results) {
        HashMap<String, ArrayList<ArrayList<String>>> sections = new HashMap<>();

        try {
            while (results.next()) {
                String gramType = results.getString(8);

                // Checks if sections are too large before continuing
                if (sections.containsKey(gramType) && sections.get(gramType).size() >= 3) {
                    continue;

                } else if (!sections.containsKey(gramType)) {
                    sections.put(gramType, new ArrayList<>());
                }

                ArrayList<String> rowData = new ArrayList<>();

                rowData.add(results.getString(5));
                rowData.add(results.getString(3));
                rowData.add(results.getString(1));
                rowData.add("N/A");

                sections.get(gramType).add(rowData);
            }
            return sections;

        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            return null;
        }
    }

    void populateResults(ResultSet results) {
        removeAll();
        HashMap<String, ArrayList<ArrayList<String>>> sections = generateSections(results);

        for (String gramTypeTitle : sections.keySet()) {
            add(new ResultsField(80, gramTypeTitle));

            for (int rowLoc = 0; rowLoc < sections.get(gramTypeTitle).size(); rowLoc++) {
                ArrayList<String> rowData = sections.get(gramTypeTitle).get(rowLoc);

                JPanel resultsRow = new JPanel();
                resultsRow.setLayout(new BoxLayout(resultsRow, BoxLayout.X_AXIS));
                resultsRow.setMaximumSize(new Dimension(310, 20));
                setAlignmentX(LEFT_ALIGNMENT);

                resultsRow.add(new ResultsField(120, rowData.get(0), rowLoc));
                resultsRow.add(new ResultsField(40, rowData.get(1), rowLoc));
                resultsRow.add(new ResultsField(120, rowData.get(2), rowLoc));
                resultsRow.add(new ResultsField(50, rowData.get(3), rowLoc));

                add(resultsRow);
            }
            add(Box.createRigidArea(new Dimension(200, 15)));
        }
        revalidate();
    }
}
