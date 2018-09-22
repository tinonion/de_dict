import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

class ResultsField extends JTextPane {
    private Color unhighlighted;
    private Dimension size;
    public String text;

    // Constructor for generic ResultsField
    ResultsField(int width, String text) {
        setEditable(false);
        setFont(new Font("Lato", Font.PLAIN, 14));
        setText(text);
        size = new Dimension(width, 20);
    }

    void highlight() {
        unhighlighted = getBackground();
        int red = (int) Math.round(unhighlighted.getRed() * 0.8);
        int green = (int) Math.round(unhighlighted.getGreen() * 0.8);
        int blue = (int) Math.round(unhighlighted.getBlue() * 0.8);

        setBackground(new Color(red, green, blue));
    }

    void unhighlight() {
        setBackground(unhighlighted);
    }

    @Override
    public void setText(String t) {
        super.setText(t);
        this.text = t;
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
