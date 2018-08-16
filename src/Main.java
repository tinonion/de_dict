import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

class Main {

    private static void constructAndShowGUI()  {
        JFrame mainWindow = new JFrame("Swing Test");
        mainWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)  {
                Database.closeConnection();
            }
        });


        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 5));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;

        c.gridy = 0;
        c.gridx = 1;
        TranslatorPanel translatorPanel = new TranslatorPanel();
        contentPanel.add(translatorPanel, c);

        c.gridx = 0;
        JPanel leftMenu = new JPanel();
        leftMenu.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
        contentPanel.add(leftMenu, c);

        mainWindow.setContentPane(contentPanel);
        mainWindow.setSize(600, 500);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static String readToString(String fileName) throws IOException {
        File file = new File(fileName);
        String string = FileUtils.readFileToString(file, "utf-8");
        return string;
    }

    public static void main(String[] args) {
        try {
            Database.initialize();
            //Database.generateDatabase("dict.txt");

            SwingUtilities.invokeLater(Main::constructAndShowGUI);

        } catch (SQLException | IOException ex) {
            System.out.println(ex.getCause());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
