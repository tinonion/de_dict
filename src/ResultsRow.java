import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class ResultsRow extends JLabel implements MouseListener {

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
        fields = getComponents();

        for (Component label : fields) {
            label.addMouseListener(this);
        }

        enContext = rowData.get(1);
        deContext = rowData.get(4);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        ResultsField germanTranslation = (ResultsField) fields[3];
        java.util.List<ArrayList<String>> verbInfo = Verb.generateVerbInfo(germanTranslation.text);

        for (ArrayList<String> tense : verbInfo) {
            for (String conjugation : tense) {
                System.out.print(conjugation + ", ");
            }
            System.out.println();
        }
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

        repaint();
        revalidate();
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(310, 20);
    }
}
