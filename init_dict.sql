DROP TABLE IF EXISTS dict;

CREATE TABLE IF NOT EXISTS dict (
  de VARCHAR(255),
  de_implied VARCHAR(255),
  gender VARCHAR(25),
  de_context VARCHAR(255),
  en VARCHAR(255),
  en_implied VARCHAR(255),
  en_context VARCHAR(255),
  gramType VARCHAR(15)
);