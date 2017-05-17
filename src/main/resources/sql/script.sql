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

CREATE OR REPLACE FUNCTION product_room() RETURNS
  TRIGGER AS $$
DECLARE id_room BIGINT;
DECLARE idid BIGINT;
BEGIN
  SELECT nextval
  ('hibernate_sequence') INTO idid;
  SELECT
    room.id FROM room WHERE room_type='Store' INTO id_room;
  INSERT INTO
    room_product(id, amount, measure,
    name_type, price, room_id) VALUES(idid, new.amount, new.measure,
  new.name_type, new.price, id_room);
  RETURN new;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER insert_prod_room
AFTER INSERT OR UPDATE ON product
FOR EACH ROW
EXECUTE PROCEDURE product_room();

CREATE OR REPLACE FUNCTION move_product(
sourceRoom VARCHAR(255),
targetRoom VARCHAR(255),
  product_name VARCHAR(255),
  amounts DOUBLE PRECISION
) RETURNS
  VOID AS $$
DECLARE
  _amount DOUBLE PRECISION;
  DECLARE idid BIGINT;
  DECLARE source_id BIGINT;
  DECLARE target_id BIGINT;
BEGIN
  SELECT id FROM room WHERE room_type = sourceRoom INTO source_id;
  IF (source_id) IS NULL THEN
    RAISE EXCEPTION 'Source Room doesnt exist' ;
  END IF;
SELECT id FROM room WHERE room_type = targetRoom INTO target_id;
  IF (target_id) IS NULL THEN
    RAISE EXCEPTION 'Target Room doesnt exist' ;
  END IF;
  IF (SELECT id FROM room_product WHERE name_type = product_name
     AND room_id=source_id) IS NULL THEN
    RAISE EXCEPTION 'Product doesnt exist' ;
  END IF;
  SELECT nextval
  ('hibernate_sequence') INTO idid;
  SELECT amount FROM room_product WHERE room_id=source_id INTO _amount;
  IF _amount-amounts<0 THEN
    RAISE EXCEPTION 'No so much product in room';
  END IF;
  IF _amount-amounts=0 THEN
   /* UPDATE room_product SET room_id=target_id WHERE name_type=product_name
    AND room_id=source_id;*/
    IF (SELECT id FROM room_product WHERE room_id=target_id
                                          AND name_type IN (product_name))
       IS NULL THEN
      INSERT INTO room_product (id, amount, name_type, room_id)
      VALUES (idid, amounts, product_name, target_id);
    ELSE
      UPDATE room_product SET amount=amount+amounts WHERE room_id=target_id
                                                          AND name_type=product_name;
    END IF;
    DELETE FROM room_product WHERE room_id=source_id AND name_type=product_name;
  END IF;
  IF _amount-amounts>0 THEN
  UPDATE room_product SET amount=(_amount-amounts) WHERE
    room_id=source_id AND name_type=product_name;
    IF (SELECT id FROM room_product WHERE room_id=target_id
                                          AND name_type IN (product_name))
    IS NULL THEN
      INSERT INTO room_product (id, amount, name_type, room_id)
        VALUES (idid, amounts, product_name, target_id);
      ELSE
        UPDATE room_product SET amount=amount+amounts WHERE room_id=target_id
    AND name_type=product_name;
         END IF;
    END IF;
 END;
$$
LANGUAGE plpgsql;