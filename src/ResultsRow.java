import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

class ResultsRow extends JPanel implements MouseListener {

    List<ResultsRow> contentRows;
    int layer;
    private boolean expanded;
    private Component[] fields;
    private String deContext;
    private String enContext;

    ResultsRow(ArrayList<String> rowData, int rowLoc) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);

        add(new ResultsField(120, rowData.get(0), rowLoc));
        add(new ResultsField(20, rowData.get(2), rowLoc));
        add(new ResultsField(120, rowData.get(3), rowLoc));
        add(new ResultsField(50, rowData.get(5), rowLoc));

        layer = 2;
        contentRows = new ArrayList<>();
        expanded = false;
        fields = getComponents();
        enContext = rowData.get(1);
        deContext = rowData.get(4);

        for (Component label : fields) {
            label.addMouseListener(this);
        }
    }

    ResultsRow() {
        setBackground(Color.yellow);
        layer = 1;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        ResultsField germanTranslation = (ResultsField) fields[3];
        java.util.List<ArrayList<String>> verbInfo = Verb.generateVerbInfo(germanTranslation.text);

        ResultsPanel parent = (ResultsPanel) getParent();
        if (expanded) {
            parent.collapseRow(this);

        } else {
            parent.expandRow(this);
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

        repaint();
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
