CREATE TABLE IF NOT EXISTS irreg_verbs (
  infinitive VARCHAR(255),
  past_p VARCHAR(255),
  prat VARCHAR(255),
  mixed BOOLEAN,
  special BOOLEAN /* used to denote modal or auxiliary verbs (or otherwise strange verbs like "wissen") */
);

CREATE TABLE IF NOT EXISTS special_verbs (
  infinitive VARCHAR(255),
  modal BOOLEAN,
  auxillary BOOLEAN,
  ich VARCHAR(255),
  du VARCHAR(255),
  er VARCHAR(255),
  wir VARCHAR(255),
  ihr VARCHAR(255),
  sie VARCHAR(255),
  endings TINYINT /* 0 refers to weak, 1 to strong, 2 to regular präsens */
);