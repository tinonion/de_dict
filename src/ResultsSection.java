import javax.swing.*;
import java.awt.*;

public class ResultsSection extends JPanel {

    ResultsSection() {
        setLayout(new GridLayout(4, 1));

        add(new JLabel("SectionName"));
        add(new JLabel("Result1"));
        add(new JLabel("Result2"));
        add(new JLabel("Result3"));
    }
}
