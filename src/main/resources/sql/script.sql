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
VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_USER');
INSERT INTO users (username, password, enabled)
VALUES ('user', 'user', TRUE);
INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER');

CREATE OR REPLACE FUNCTION move_product(
sourceRoom VARCHAR(255),
targetRoom VARCHAR(255),
  product_name VARCHAR(255),
  amounts DOUBLE PRECISION
) RETURNS
  VARCHAR AS $$
DECLARE
  _amount DOUBLE PRECISION;
  DECLARE idid BIGINT;
  DECLARE source_id BIGINT;
  DECLARE target_id BIGINT;
  DECLARE msg VARCHAR = 'OK';
BEGIN
  SELECT id FROM room WHERE "number" = sourceRoom INTO source_id;
  IF (source_id) IS NULL THEN
    RAISE EXCEPTION 'Source Room doesnt exist' ;
  END IF;
SELECT id FROM room WHERE "number" = targetRoom INTO target_id;
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
    RETURN msg;
 END;
$$
LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION product_room() RETURNS
  TRIGGER AS $$
DECLARE id_room BIGINT;
  DECLARE idid BIGINT;
BEGIN
  SELECT nextval
  ('hibernate_sequence') INTO idid;
  SELECT
    room.id FROM room WHERE room_type='Store' INTO id_room;
  IF (SELECT name_type FROM room_product WHERE
       name_type =new.name_type AND room_id=id_room)IS NULL THEN
  INSERT INTO
    room_product(id, amount,
                 name_type, room_id) VALUES(idid, new.amount,
                                                   new.name_type, id_room);
    ELSE
      UPDATE room_product SET amount = amount+new.amount WHERE room_id=id_room
        AND name_type=new.name_type;
     END IF;
  RETURN new;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER insert_prod_room
AFTER INSERT OR UPDATE ON product
FOR EACH ROW
EXECUTE PROCEDURE product_room();

CREATE OR REPLACE FUNCTION Juridical_check()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  IF (SELECT COUNT(*) FROM juridical_person WHERE
    edrpou = New.edrpou)
  THEN RAISE EXCEPTION 'Entity is already exists';
  END IF;
  Return New;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

CREATE TRIGGER CheckPJTrigger BEFORE INSERT ON juridical_person FOR EACH ROW
EXECUTE PROCEDURE Juridical_check();

CREATE OR REPLACE VIEW Product_in_room
AS SELECT
 room.id,  room.room_type,
                       room.number,
                        room_product.name_type,
                       room_product.amount
                        FROM room
                         LEFT JOIN room_product
                       ON room.id = room_product.room_id ORDER BY room.number;