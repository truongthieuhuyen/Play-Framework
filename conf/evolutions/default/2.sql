# --- !Ups

CREATE TABLE item (
  item_id int(20) NOT NULL AUTO_INCREMENT,
  item_name varchar(255) NOT NULL,
  user_id int(12) NOT NULL,
  is_valid boolean NOT NULL,
  PRIMARY KEY (item_id),
  CONSTRAINT FK_user_item FOREIGN KEY (user_id)
      REFERENCES user(user_id)
);

# --- !Downs

DROP TABLE user;