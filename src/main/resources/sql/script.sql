DROP TABLE users CASCADE;
DROP TABLE authorities CASCADE;
CREATE TABLE users (
  username VARCHAR(50) NOT NULL PRIMARY KEY,
  password VARCHAR(50) NOT NULL,
  enabled  BOOLEAN     NOT NULL
);
CREATE TABLE authorities (
  username  VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
CREATE UNIQUE INDEX ix_auth_username
  ON authorities (username, authority);

INSERT INTO users (username, password, enabled)
VALUES ('admin', 'admin', TRUE);
INSERT INTO authorities (username, authority)
VALUES ('admin', 'admin');
INSERT INTO authorities (username, authority)
VALUES ('admin', 'user');
INSERT INTO users (username, password, enabled)
VALUES ('user', 'user', TRUE);
INSERT INTO authorities (username, authority)
VALUES ('user', 'user');