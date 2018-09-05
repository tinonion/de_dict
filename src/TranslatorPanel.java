import javax.swing.*;
import java.awt.*;

class TranslatorPanel extends JPanel {

    ResultsPanel resultsPanel = new ResultsPanel();

    TranslatorPanel(){
        setBackground(Color.CYAN);
        setLayout(new FlowLayout(FlowLayout.LEADING, 0, 3));

        add(new SearchBox());
        add(resultsPanel);
    }
}
