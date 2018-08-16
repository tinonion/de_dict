class Translation {

    String root;
    String translation;
    String gender;
    String collection;
    String gramType;
    String contextAttr;
    int popularity;

    void setGramType() {
        switch (gramType) {
            case "adj":
                gramType = "Adjective";
                break;

            case "noun":
                gramType = "Noun";
                break;

            case "adv":
                gramType = "Adverb";
                break;

            case "verb":
                gramType = "Verb";
                break;

            case "pron":
                gramType = "Pronoun";
                break;

            default:
                gramType = null;
                break;
        }
    }

    void setGender() {
        if (gender.contains("{m}")) {
            gender = "der";

        } else if (gender.contains("{n}")) {
            gender = "das";

        } else if (gender.contains("{f}")) {
            gender = "die";

        } else if (gender.contains("{pl}")) {
            gender = "plur";

        } else {
            gender = "genError";
        }
    }
}
