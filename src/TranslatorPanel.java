import javax.swing.*;
import java.awt.*;

class TranslatorPanel extends JPanel {

    ResultsPanel resultsPanel = new ResultsPanel();

    TranslatorPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.gray, 5));
        setAlignmentX(LEFT_ALIGNMENT);

        add(new SearchBox());
        add(resultsPanel);
    }
}
