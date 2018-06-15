import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class TranslatorPanel extends JPanel {

    private ArrayList<ResultsSection> currentSections = new ArrayList<>();

    TranslatorPanel(){
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.gray, 5));

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1;

        SearchBox searchBox = new SearchBox();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(searchBox, c);
    }

    void constructResultsSection(ArrayList<Translation> results) {
        currentSections.forEach(this::remove);
        ArrayList<ResultsSection> sections = new ArrayList<>();

        ArrayList<String> seenGramTypes = new ArrayList<>();
        for (Translation result : results) {
            if (!seenGramTypes.contains(result.gramType) && !result.gramType.equals("")) {
                seenGramTypes.add(result.gramType);
                ResultsSection newSection = new ResultsSection(result);

                if (sections.size() == 0) {
                    sections.add(newSection);
                } else {
                    for (int ind = 0; ind < sections.size(); ind++) {
                        if (newSection.maxPopularity > sections.get(ind).maxPopularity) {
                            sections.add(ind, newSection);
                            break;
                        }
                    }
                }
            } else {
                for (ResultsSection section : sections) {
                    if (section.gramType.equals(result.gramType)) {
                        section.addTranslation(result);
                    }
                }
            }
        }

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.gridheight = 3;
        for (int ind = 0; ind < 3 && ind < sections.size(); ind++) {
            c.gridy = ind * 4 + 2;
            add(sections.get(ind), c);
            currentSections.add(sections.get(ind));
        }

        for (ResultsSection section : sections) {
            System.out.println("TYPE: " + section.gramType);
        }

        revalidate();
    }
}
