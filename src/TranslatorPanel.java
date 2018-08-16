import javax.swing.*;
import java.awt.*;

class TranslatorPanel extends JPanel {

    ResultsPanel resultsPanel = new ResultsPanel();

    TranslatorPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.gray, 5));
        setPreferredSize(new Dimension(320, 500));

        add(new SearchBox());
        add(Box.createRigidArea(new Dimension(10, 20)));
        add(resultsPanel);
    }
}
