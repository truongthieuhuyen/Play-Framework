CREATE TABLE users (
	user_id INT PRIMARY KEY,
	username varchar(20) NOT NULL,
	password varchar(200) NOT NULL
);

CREATE TABLE items (
	item_id INT PRIMARY KEY,
	user_id INT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
	text varchar(2000) NOT NULL
);