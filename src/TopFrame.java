import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class TopFrame extends JFrame {

    TopFrame(){
        super("Test Frame");
        setSize(800, 600);
        setResizable(true);
        setVisible(true);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 5));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = c.weighty = 1;

        c.gridy = 0;
        c.gridx = 0;
        c.gridheight = 1;
        c.gridwidth = 3;
        contentPanel.add(new TranslatorPanel(), c);

        c.gridy = 1;
        c.gridx = 0;
        c.gridheight = 1;
        c.gridwidth = 3;
        JPanel bottomMenu = new JPanel();
        bottomMenu.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
        contentPanel.add(bottomMenu, c);

        setContentPane(contentPanel);
    }
}
