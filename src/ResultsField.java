import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

class ResultsField extends JTextPane implements MouseInputListener {

    private Dimension size;
    private int rowLoc; // Used primarily to determine what color to paint field
    public String text;

    ResultsField(int preferredWidth, String text) {
        setEditable(false);
        setFont(new Font("Lato", Font.PLAIN, 14));
        setText(text);
        size = new Dimension(preferredWidth, 20);
    }

    ResultsField(int preferredWidth, String text, int rowLoc) {
        setEditable(false);
        setFont(new Font("Lato", Font.PLAIN, 14));
        setText(text);
        size = new Dimension(preferredWidth, 20);

        if (rowLoc == 0) {
            setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.DARK_GRAY));

        } else {
            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        }

        if (rowLoc % 2 == 0) {
            setBackground(new Color(210, 200, 255));
        }
    }

    public void highlight() {
        if (rowLoc % 2 == 0) {
            setBackground(new Color(190, 180, 245));

        } else {
            setBackground(new Color(245, 245, 245));
        }
    }

    public void unhighlight() {
        if (rowLoc % 2 == 0) {
            setBackground(new Color(210, 200, 255));

        } else {
            setBackground(new Color(255, 255, 255));
        }
    }

    @Override
    public void setText(String t) {
        super.setText(t);
        this.text = t;
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    @Override
    public Dimension getMaximumSize() {
        return size;
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println('c');
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("df");
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
