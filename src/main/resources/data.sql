INSERT INTO authorities (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users (username, password, enabled) VALUES ('user', 'userpassword', TRUE), ('admin', 'adminpassword', TRUE);

INSERT INTO users_authorities (user_id, authority_id) VALUES (1, 1), (2, 2);
