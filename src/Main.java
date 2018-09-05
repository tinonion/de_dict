import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;

class Main {

    private static void constructAndShowGUI() {
        JFrame mainWindow = new JFrame("Swing Test");
        mainWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)  {
                Database.closeConnection();
            }
        });


        JMenuBar menuBar = new JMenuBar();

        JMenu databaseMenu = new JMenu("Dict Menu");
        JMenuItem createDatabase = new JMenuItem("Generate dictionary");

        createDatabase.addActionListener(e -> Database.generateDatabase());
        databaseMenu.add(createDatabase);
        menuBar.add(databaseMenu);
        mainWindow.setJMenuBar(menuBar);

        JPanel contentPanel = new TranslatorPanel();
        mainWindow.setContentPane(contentPanel);

        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.setSize(550, 400);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Database.verifyDatabase();
    }

    static String readToString(String fileName) throws IOException {
        File file = new File(fileName);
        return FileUtils.readFileToString(file, "utf-8");
    }

    public static void main(String[] args) {
        try {
            System.out.println(System.getProperty("user.dir"));
            Database.initialize();
            SwingUtilities.invokeLater(Main::constructAndShowGUI);

        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
