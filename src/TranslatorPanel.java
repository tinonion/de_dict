import javax.swing.*;
import java.awt.*;

class TranslatorPanel extends JPanel {

    TranslatorPanel(){
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.gray, 5));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = c.weighty = 1;

        TextField searchBox = new TextField();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        add(searchBox, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 4;
        add(new ResultsSection(), c);

        c.gridx = 3;
        add(new ResultsSection(), c);

        c.gridx = 6;
        add(new ResultsSection(), c);
    }
}
