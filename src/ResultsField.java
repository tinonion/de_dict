import javax.swing.*;
import java.awt.*;

class ResultsField extends JTextPane {

    private Dimension size;

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
            setBackground(new Color(230, 220, 255));
        }
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
}
