import javax.swing.*;
import java.awt.*;

class Main {

    private static void constructAndShowGUI(){
        JFrame frame = new JFrame("Swing Test");

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 5));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;

        c.gridy = 0;
        c.gridx = 1;
        c.weightx = 3;
        contentPanel.add(new TranslatorPanel(), c);

        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 5;
        JPanel bottomMenu = new JPanel();
        bottomMenu.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
        contentPanel.add(bottomMenu, c);

        frame.setContentPane(contentPanel);
        frame.setSize(1000, 700);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::constructAndShowGUI);
    }
}
