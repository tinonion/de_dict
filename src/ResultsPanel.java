import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ResultsPanel extends JLayeredPane {

    private static Dimension paneSize = new Dimension(980, 360);
    private static int yBaseline = 10;
    private static int xBaseline = 10;
    private List<ResultsRow> rows;

    ResultsPanel() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setBackground(Color.blue);
        rows = new ArrayList<>();
    }

    private HashMap<String, ArrayList<ArrayList<String>>> generateSections(ResultSet results) {
        HashMap<String, ArrayList<ArrayList<String>>> sections = new HashMap<>();

        try {
            while (results.next() && sections.size() <= 3) {
                String gramType = results.getString(8);

                // Checks if sections are too large before continuing
                if (sections.containsKey(gramType) && sections.get(gramType).size() >= 5) {
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

        int sectionNum = 0;
        for (String sectionTitle : sections.keySet()) {
            int sectionX = 10 + (sectionNum * 330);

            ResultsField titleField = new ResultsField(80, sectionTitle);
            titleField.setBounds(sectionX, yBaseline, 80, 20);
            add(titleField, 2);

            for (int rowLoc = 0; rowLoc < sections.get(sectionTitle).size(); rowLoc++) {
                ArrayList<String> rowData = sections.get(sectionTitle).get(rowLoc);
                int rowY = 20 + yBaseline + (rowLoc * 20);

                ResultsRow resultsRow = new ResultsRow(rowData, rowLoc);
                resultsRow.setBounds(sectionX, rowY, 310, 20);
                add(resultsRow, 2);
                rows.add(resultsRow);
            }
            sectionNum++;
        }
        revalidate();
        repaint();
    }

    void expandRow(ResultsRow headerPanel) {
        // Placeholder until actual contents of rows are implemented (referring to rowCnt)
        int rowCnt = 2;
        List<ResultsRow> rowsToMove = findUnderRows(headerPanel);

        for (int r = 0; r < rowCnt; r++) {
            ResultsRow contentRow = new ResultsRow();
            contentRow.setBounds(headerPanel.getX(),
                    headerPanel.getY(),
                    310, 20);
            headerPanel.contentRows.add(contentRow);
            add(contentRow, 1);
            rowsToMove.add(contentRow);

            while (contentRow.getY() < (headerPanel.getY() + headerPanel.getHeight())) {
                slideContents(2, headerPanel, rowsToMove);
            }
        }
    }

    void collapseRow(ResultsRow headerPanel) {
        List<ResultsRow> rowsToMove = new ArrayList<>(headerPanel.contentRows);
        rowsToMove.addAll(findUnderRows(headerPanel));

        while (!headerPanel.contentRows.isEmpty()) {
            Component topRow = headerPanel.contentRows.get(headerPanel.contentRows.size() - 1);
            slideContents(-2, headerPanel, rowsToMove);

            if (topRow.getY() <= headerPanel.getY()) {
                remove(topRow);
                headerPanel.contentRows.remove(topRow);
                rowsToMove.remove(topRow);
            }
        }
    }

    private void slideContents(int slideInc, ResultsRow headerPanel, List<ResultsRow> rowsToMove) {
        for (ResultsRow row : rowsToMove) {
            remove(row);
            row.setBounds(row.getX(),
                    row.getY() + slideInc,
                    row.getWidth(),
                    row.getHeight());
            add(row, row.layer);
        }
        // Paints only the area underneath the header that is occupied by expanded rows
        Component bottomRow = findLowestRow(rowsToMove);
        paintImmediately(headerPanel.getX(),
                headerPanel.getY() + headerPanel.getHeight(),
                headerPanel.getWidth(),
                bottomRow.getY() + bottomRow.getHeight() - headerPanel.getHeight());

        try {
            Thread.sleep(5);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private List<ResultsRow> findUnderRows(ResultsRow selected) {
        List<ResultsRow> underHeaders = new ArrayList<>();

        boolean isUnderHeader = false;
        int sectionX = xBaseline;
        for (ResultsRow r : rows) {
            // If selected reach, add all following components
            if (r.equals(selected)) {
                isUnderHeader = true;
                sectionX = r.getX();
                continue;

            // If in slice and reaches title of next section, return
            } else if (isUnderHeader && r.getX() != sectionX) {
                return underHeaders;
            }

            if (isUnderHeader) {
                underHeaders.add(r);
                underHeaders.addAll(r.contentRows);
            }
        }
        return underHeaders;
    }

    private ResultsRow findLowestRow(List<ResultsRow> rows) {
        ResultsRow lowest = null;
        for (ResultsRow r : rows) {
            if (lowest == null || lowest.getY() < r.getY()) {
                lowest = r;
            }
        }
        return lowest;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getWidth(), 390);
    }

    @Override
    public Dimension getMinimumSize() {
        return paneSize;
    }

    @Override
    public Dimension getMaximumSize() {
        return paneSize;
    }
}
