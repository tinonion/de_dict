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


        JPanel contentPanel = new TranslatorPanel();
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 5));

        mainWindow.setContentPane(contentPanel);
        mainWindow.setSize(520, 425);
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
            Database.generateDatabase("dict.txt");
            //Database.closeConnection();

            SwingUtilities.invokeLater(Main::constructAndShowGUI);

        } catch (SQLException | IOException ex) {
            System.out.println(ex.getCause());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
