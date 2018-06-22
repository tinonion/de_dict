import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class TranslatorPanel extends JPanel {

    private JPanel resultsPanel = new JPanel();

    TranslatorPanel(){
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.gray, 5));
        setPreferredSize(new Dimension(320, 500));

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(Box.createHorizontalStrut(10));

        SearchBox searchBox = new SearchBox();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(searchBox, c);

        resultsPanel.setLayout(new GridBagLayout());
        c.gridx = 1;
        c.gridy = 1;
        add(resultsPanel, c);
    }

    void populateResultsPanel(ArrayList<List<Translation>> results) {
        resultsPanel.removeAll();

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        results.sort((section1, section2) -> section2.get(0).popularity - section1.get(0).popularity);

        for (List<Translation> section : results) {
            String title = section.get(0).gramType;
            resultsPanel.add(new ResultsField(80, title), c);
            c.gridy++;

            for (int ind = 0; ind < section.size(); ind++) {
                c.gridx = 0;
                resultsPanel.add(new ResultsField(100, section.get(ind).root, ind), c);

                c.gridx = 1;
                resultsPanel.add(new ResultsField(30, section.get(ind).gender, ind), c);

                c.gridx = 2;
                resultsPanel.add(new ResultsField(120, section.get(ind).translation, ind), c);

                c.gridx = 3;
                resultsPanel.add(new ResultsField(50, String.valueOf(section.get(ind).popularity), ind), c);

                c.gridx = 0;
                c.gridy++;
            }
            resultsPanel.add(Box.createVerticalStrut(15), c);
            c.gridy++;
        }

        c.gridx = 1;
        c.gridy = 1;
        resultsPanel.add(Box.createHorizontalStrut(10));
        revalidate();
    }
}
