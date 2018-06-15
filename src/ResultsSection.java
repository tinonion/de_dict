import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

class ResultsSection extends JPanel {

    String gramType;
    int maxPopularity;
    private ArrayList<Translation> heldTranslations = new ArrayList<>();

    ResultsSection(Translation firstTran) {
        this.gramType = firstTran.gramType;
        this.maxPopularity = firstTran.popularity;

        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(80, 20));
        title.setBorder(BorderFactory.createLineBorder(Color.gray, 3));
        title.setFont(new Font("Serif", Font.PLAIN,16));
        switch (this.gramType) {
            case "adj":
                title.setText("Adjective");
                break;
            case "noun":
                title.setText("Noun");
                break;
            case "adv":
                title.setText("Adverb");
                break;
            case "verb":
                title.setText("Verb");
                break;
            case "pron":
                title.setText("Pronoun");
                break;
            default:
                title.setText("DEFAULT" + gramType);
                break;
        }
        add(title, c);
        addTranslation(firstTran);
    }

    void addTranslation(Translation translation) {
        heldTranslations.add(translation);

        if (translation.popularity > maxPopularity) {
            maxPopularity = translation.popularity;
        }

        JPanel newEntry = new JPanel();
        newEntry.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        if (heldTranslations.size() == 1) {System.out.println("BORDER ADDED");
            newEntry.setBorder(new MatteBorder(3, 0, 0, 0, Color.LIGHT_GRAY));
        }

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        newEntry.add(new JLabel(translation.root), c);

        c.gridx = 0;
        newEntry.add(Box.createHorizontalStrut(10), c);

        c.gridx = 1;
        newEntry.add(new JLabel(translation.gender), c);

        c.gridx = 2;
        newEntry.add(new JLabel(" " + translation.translation), c);

        c.gridx = 3;
        newEntry.add(Box.createHorizontalStrut(10), c);

        //c.gridx = 4;
        //newEntry.add(new JLabel(translation.collection));

        c.gridx = 4;
        newEntry.add(new JLabel(String.valueOf(translation.popularity)), c);

        c.gridx = 0;
        c.gridy = heldTranslations.size();
        this.add(newEntry, c);
    }
}
