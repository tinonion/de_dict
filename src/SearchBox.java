import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

class SearchBox extends JTextField implements ActionListener, FocusListener {

    private Dimension size;

    SearchBox() {
        super("Search");
        setFont(new Font("Serif", Font.ITALIC, 14));
        size = new Dimension(120, 20);

        addFocusListener(this);
        addActionListener(this);
    }

    public void focusGained(FocusEvent e) {
        setText("");
    }

    public void focusLost(FocusEvent e) {
        setText("Search");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TranslatorPanel parentPanel = (TranslatorPanel) getParent();
        parentPanel.resultsPanel.populateResults(Database.generalQuery(getText()));
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }

    @Override
    public Dimension getMaximumSize() {
        return size;
    }
}
