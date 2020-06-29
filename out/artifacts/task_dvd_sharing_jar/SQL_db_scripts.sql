DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id                    BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
  username              varchar (50) NOT NULL UNIQUE,
  password              VARCHAR(80),
  first_name            VARCHAR(50),
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id                    BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
  name                  VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
  id                    BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
  user_id               BIGINT NOT NULL,
  role_id               BIGINT NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

INSERT INTO users (username, password, first_name)
VALUES
('admin','$2a$10$93.GvO4rU88tUMM8DYqi9OIlStKWPyByqx9mQ9AZWCAmRilrC6X5i','Admin'),
-- password=12345
('user','$2a$10$C2yYw2Q5t1DSE48O9KG3wOFm8ermU6zIULjAMANWgwi5xZQgINK.u','UsEr'),
-- password=11111
('user2','$2a$10$C2yYw2Q5t1DSE48O9KG3wOFm8ermU6zIULjAMANWgwi5xZQgINK.u','UsEr2'),
-- password=11111
('user3','$2a$10$C2yYw2Q5t1DSE48O9KG3wOFm8ermU6zIULjAMANWgwi5xZQgINK.u','UsEr3'),
-- password=11111
('user4','$2a$10$C2yYw2Q5t1DSE48O9KG3wOFm8ermU6zIULjAMANWgwi5xZQgINK.u','UsEr4'),
-- password=11111
('user5','$2a$10$C2yYw2Q5t1DSE48O9KG3wOFm8ermU6zIULjAMANWgwi5xZQgINK.u','UsEr5');
-- password=11111

INSERT INTO roles (name)
VALUES
('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 2);

DROP TABLE IF EXISTS disks;
CREATE TABLE disks (
    id                    BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    created               TIMESTAMP NULL,
    title                 VARCHAR(255) NULL,
    last_time_taken_date  TIMESTAMP NULL,
    user_id               BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id)
    REFERENCES users (id)
);
INSERT INTO  disks(created, title, last_time_taken_date, user_id)
VALUES
(current_date-100,'Title 4', current_date-97, 2),
(current_date-99,'Title 3', current_date-98, 3),
(current_date-98,'Title 2', current_date-99, 4),
(current_date-97,'Title 1', current_date-100, 5),
(current_date-100,'Title 4', current_date-97, 6),
(current_date-99,'Title 3', current_date-98, 2),
(current_date-98,'Title 2', current_date-99, 3),
(current_date-97,'Title 1', current_date-100, 4),
(current_date-100,'Title 4', current_date-97, 5),
(current_date-99,'Title 3', current_date-98, 6),
(current_date-98,'Title 2', current_date-99, 2),
(current_date-97,'Title 1', current_date-100, 3),
(current_date-100,'Title 4', current_date-97, 4),
(current_date-99,'Title 3', current_date-98, 5),
(current_date-98,'Title 2', current_date-99, 6),
(current_date-97,'Title 1', current_date-100, 2),
(current_date-100,'Title 4', current_date-97, 3);

DROP TABLE IF EXISTS taken_item;
CREATE TABLE taken_item (
  id                    bigint NOT NULL UNIQUE AUTO_INCREMENT,
  user_id               bigint NOT NULL,
  disk_id               bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (disk_id )
  REFERENCES disks (id)
);

INSERT INTO  taken_item(user_id,  disk_id)
VALUES
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(6, 7),
(2, 8),
(3, 9),
(4,10),
(5,11),
(6,12);