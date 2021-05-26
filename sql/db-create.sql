-- -----------------------------------------------------
-- Schema timedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS timedb DEFAULT CHARACTER SET utf8 ;
USE timedb ;

DROP TABLE IF EXISTS participant_activity;
DROP TABLE IF EXISTS participant;
DROP TABLE IF EXISTS activity;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS category;

-- -----------------------------------------------------
-- Table role
-- -----------------------------------------------------

CREATE TABLE role (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(10) NULL UNIQUE,
  PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table participant
-- -----------------------------------------------------

CREATE TABLE participant (
  id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20) NOT NULL,
  login VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(10) NOT NULL,
  -- locale_name VARCHAR(20),
  role_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_participant_role1_idx (role_id ASC) VISIBLE,
  CONSTRAINT fk_participant_role1
    FOREIGN KEY (role_id)
    REFERENCES role (id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT);


-- -----------------------------------------------------
-- Table category
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS category (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table activity
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS activity (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  category_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_activity_category1_idx (category_id ASC) VISIBLE,
  CONSTRAINT fk_activity_category1
    FOREIGN KEY (category_id)
    REFERENCES category (id)
	ON DELETE CASCADE
    ON UPDATE RESTRICT);


-- -----------------------------------------------------
-- Table participant_activity
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS participant_activity (
  participant_id INT NOT NULL,
  activity_id INT NOT NULL,
  duration INT UNSIGNED NULL,
  PRIMARY KEY (participant_id, activity_id),
  INDEX fk_participant_has_activity_activity1_idx (activity_id ASC) VISIBLE,
  INDEX fk_participant_has_activity_participant_idx (participant_id ASC) VISIBLE,
  CONSTRAINT fk_participant_has_activity_participant
    FOREIGN KEY (participant_id)
    REFERENCES participant (id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT fk_participant_has_activity_activity1
    FOREIGN KEY (activity_id)
    REFERENCES activity (id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT);

-- -----------------------------------------------------
-- Inserts
-- -----------------------------------------------------

-- role
INSERT INTO role (id, name) VALUES(DEFAULT, "admin");
INSERT INTO role (id, name) VALUES(DEFAULT, "user");

-- participant
SET @text = "admin";
INSERT INTO participant (id, first_name, last_name, login, password, role_id) VALUES(DEFAULT, @text, @text, @text, @text, (SELECT id FROM role WHERE name = @text));
SET @text = "user";
INSERT INTO participant (id, first_name, last_name, login, password, role_id) VALUES(DEFAULT, @text, @text, @text, @text, (SELECT id FROM role WHERE name = @text));

-- category
INSERT INTO category (id, name) VALUES(DEFAULT, "physical");
INSERT INTO category (id, name) VALUES(DEFAULT, "mental");

-- activity
SET @text = "physical";
INSERT INTO activity (id, name, category_id) VALUES(DEFAULT, "running", (SELECT id FROM category WHERE name = @text));
INSERT INTO activity (id, name, category_id) VALUES(DEFAULT, "walking", (SELECT id FROM category WHERE name = @text));
INSERT INTO activity (id, name, category_id) VALUES(DEFAULT, "swimming", (SELECT id FROM category WHERE name = @text));
SET @text = "mental";
INSERT INTO activity (id, name, category_id) VALUES(DEFAULT, "reading", (SELECT id FROM category WHERE name = @text));
INSERT INTO activity (id, name, category_id) VALUES(DEFAULT, "writing", (SELECT id FROM category WHERE name = @text));
INSERT INTO activity (id, name, category_id) VALUES(DEFAULT, "watching", (SELECT id FROM category WHERE name = @text));

-- participant_activity
INSERT INTO participant_activity (participant_id, activity_id, duration) VALUES((SELECT id FROM participant WHERE login = "user"), (SELECT id FROM activity WHERE name = "swimming"), 30);
INSERT INTO participant_activity (participant_id, activity_id, duration) VALUES((SELECT id FROM participant WHERE login = "user"), (SELECT id FROM activity WHERE name = "writing"), 120);
INSERT INTO participant_activity (participant_id, activity_id, duration) VALUES((SELECT id FROM participant WHERE login = "user"), (SELECT id FROM activity WHERE name = "running"), 150);
INSERT INTO participant_activity (participant_id, activity_id, duration) VALUES((SELECT id FROM participant WHERE login = "user"), (SELECT id FROM activity WHERE name = "watching"), 180);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
