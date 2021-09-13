# --- !Ups

CREATE TABLE users (
  id int(20) NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  full_name varchar(255) NOT NULL,
  is_admin boolean NOT NULL,
  PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE users;