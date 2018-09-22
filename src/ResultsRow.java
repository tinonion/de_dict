import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

class ResultsRow extends JPanel implements MouseListener {

    static Color lightBlue = new Color(210, 200, 255);
    static Border firstRowBorder
            = BorderFactory.createMatteBorder(2, 0, 0, 0, Color.DARK_GRAY);
    static Border placeholderBorder
            = BorderFactory.createEmptyBorder(2, 0, 0, 0);
    private List<String> rowData;
    List<ResultsRow> contentRows;
    int layer;
    private boolean expanded;
    private Component[] fields;

    enum verbData {
        PRES_ICH, PRES_DU, PRES_ER, PRES_WIR, PRES_IHR, PRES_SIE, PAST_ICH, PAST_DU, PAST_ER, PAST_WIR, PAST_IHR,
        PAST_SIE, PAST_PART
    }

    // Constructer for header rows
    ResultsRow(ArrayList<String> resultData, Color color, Border border) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);

        add(new ResultsField(120, resultData.get(ResultInfo.EN.ordinal())));
        add(new ResultsField(20, resultData.get(ResultInfo.GENDER.ordinal())));
        add(new ResultsField(120, resultData.get(ResultInfo.DE.ordinal())));
        add(new ResultsField(50, resultData.get(ResultInfo.POPULARITY.ordinal())));

        this.rowData = resultData;
        layer = 2;
        contentRows = new ArrayList<>();
        expanded = false;
        fields = getComponents();

        for (Component label : fields) {
            label.setBackground(color);
            label.addMouseListener(this);
        }
    }

    // Constructor for placeholder rows
    ResultsRow() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);
        layer = 1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String germanTranslation = rowData.get(ResultInfo.DE.ordinal());
        String gramType = rowData.get(ResultInfo.GRAM_TYPE.ordinal());
        List<ResultsRow> contentRows = new ArrayList<>();

        switch (gramType) {
            case "verb":
                Color contentColor = new Color(50, 205, 50);
                List<String> verbInfo = Verb.generateVerbInfo(germanTranslation);

                ResultsRow row1 = new ResultsRow();
                row1.add(new ResultsField(180, rowData.get(ResultInfo.EN_IMPLIED.ordinal())));
                row1.add(new ResultsField(130, verbInfo.get(verbData.PAST_PART.ordinal())));

                ResultsRow row2 = new ResultsRow();
                row2.add(new ResultsField(25, "ich"));
                row2.add(new ResultsField(130,
                        verbInfo.get(verbData.PRES_ICH.ordinal()) + "/" + verbInfo.get(verbData.PAST_ICH.ordinal())));
                row2.add(new ResultsField(25, "wir"));
                row2.add(new ResultsField(130,
                        verbInfo.get(verbData.PRES_WIR.ordinal()) + "/" + verbInfo.get(verbData.PAST_WIR.ordinal())));

                ResultsRow row3 = new ResultsRow();
                row3.add(new ResultsField(25, "du"));
                row3.add(new ResultsField(130,
                        verbInfo.get(verbData.PRES_DU.ordinal()) + "/" + verbInfo.get(verbData.PAST_DU.ordinal())));
                row3.add(new ResultsField(25, "ihr"));
                row3.add(new ResultsField(130,
                        verbInfo.get(verbData.PRES_IHR.ordinal()) + "/" + verbInfo.get(verbData.PAST_IHR.ordinal())));

                ResultsRow row4 = new ResultsRow();
                row4.add(new ResultsField(25, "er"));
                row4.add(new ResultsField(130,
                        verbInfo.get(verbData.PRES_ER.ordinal()) + "/" + verbInfo.get(verbData.PAST_ER.ordinal())));
                row4.add(new ResultsField(25, "Sie"));
                row4.add(new ResultsField(130,
                        verbInfo.get(verbData.PRES_SIE.ordinal()) + "/" + verbInfo.get(verbData.PAST_SIE.ordinal())));

                // Content rows must be added in reverse since rows are expanded from header from bottom row to top row
                contentRows.add(row4);
                contentRows.add(row3);
                contentRows.add(row2);
                contentRows.add(row1);

                for (ResultsRow row : contentRows) {
                    for (Component field : row.getComponents()) {
                        field.setBackground(contentColor);
                    }
                }
                break;
            default:
                contentRows.add(new ResultsRow());
                break;
        }

        ResultsPanel parent = (ResultsPanel) getParent();
        if (expanded) {
            parent.collapseRow(this, contentRows);

        } else {
            parent.expandRow(this, contentRows);
        }
        expanded = !expanded;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (Component label : fields) {
            ((ResultsField) label).highlight();
        }

        revalidate();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (Component label : fields) {
            ((ResultsField) label).unhighlight();
        }

        revalidate();
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(310, 20);
    }
}
