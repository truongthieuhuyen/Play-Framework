# --- !Ups

CREATE TABLE `task_list`.`user`  (
  user_id int(12) NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  is_admin boolean NOT NULL,
  PRIMARY KEY (user_id)
);

# --- !Downs

DROP TABLE user;