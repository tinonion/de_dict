import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

class ResultsPanel extends JPanel {

    ResultsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setBackground(Color.blue);


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
                rowData.add(results.getString(7));
                rowData.add(results.getString(3));
                rowData.add(results.getString(1));
                rowData.add(results.getString(4));
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

                ResultsRow resultsRow = new ResultsRow(rowData, rowLoc);
                add(resultsRow);
            }
            add(Box.createRigidArea(new Dimension(200, 15)));
        }
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getWidth(), 390);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(getParent().getWidth(), 390);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(getParent().getWidth(), 390);
    }
}
