import javax.swing.*;
import java.awt.*;

class LayoutTester {

    static void runTest() {
        JFrame frame = new JFrame();

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        JPanel panelOne = new JPanel();
        panelOne.setPreferredSize(new Dimension(300, 300));
        panelOne.setBackground(Color.BLUE);
        c.gridy = 0;
        c.gridx = 0;
        content.add(panelOne, c);

        JPanel panelTwo = new JPanel();
        panelTwo.setPreferredSize(new Dimension(200, 300));
        panelTwo.setBackground(Color.ORANGE);
        c.weightx = 1;
        c.gridx = 1;
        content.add(panelTwo, c);

        frame.setContentPane(content);
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.println(panelOne.getSize());
        System.out.println(panelTwo.getSize());
    }
}
