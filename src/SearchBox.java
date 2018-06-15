import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SearchBox extends JTextField implements ActionListener, FocusListener {

    SearchBox() {
        super("Search");
        setFont(new Font("Serif", Font.ITALIC, 14));
        setPreferredSize(getPreferredSize());

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
        parentPanel.constructResultsSection(Parser.search(getText()));
    }
}
