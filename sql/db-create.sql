-- -----------------------------------------------------
-- Schema timedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS timedb DEFAULT CHARACTER SET utf8 ;
USE timedb ;

DROP TABLE IF EXISTS participant_activity;
DROP TABLE IF EXISTS participant;
DROP TABLE IF EXISTS activity;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS category;

-- -----------------------------------------------------
-- Table role
-- -----------------------------------------------------

CREATE TABLE role (
  id INT NOT NULL,
  name VARCHAR(10) NOT NULL UNIQUE,
  PRIMARY KEY (id));
  
-- -----------------------------------------------------
-- Table status
-- -----------------------------------------------------

CREATE TABLE status (
  id INT NOT NULL,
  name VARCHAR(10) NOT NULL UNIQUE,
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
  locale_name VARCHAR(20),
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
  duration INT UNSIGNED NULL,
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
  status_id INT NOT NULL,
  PRIMARY KEY (participant_id, activity_id),
  INDEX fk_participant_activity_activity1_idx (activity_id ASC) VISIBLE,
  INDEX fk_participant_activity_participant_idx (participant_id ASC) VISIBLE,
  INDEX fk_participant_activity_status1_idx (status_id ASC) VISIBLE,
  CONSTRAINT fk_participant_activity_participant
    FOREIGN KEY (participant_id)
    REFERENCES participant (id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT fk_participant_activity_activity1
    FOREIGN KEY (activity_id)
    REFERENCES activity (id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT fk_participant_activity_status1
    FOREIGN KEY (status_id)
    REFERENCES status (id)
	ON DELETE CASCADE
    ON UPDATE RESTRICT);

-- -----------------------------------------------------
-- Inserts
-- -----------------------------------------------------

-- role
-- Role entity is ENUM, so the numeration must start from 0 
INSERT INTO role (id, name) VALUES(0, "admin");
INSERT INTO role (id, name) VALUES(1, "user");

-- status
-- Status entity is ENUM, so the numeration must start from 0 
INSERT INTO status (id, name) VALUES(0, "requested");
INSERT INTO status (id, name) VALUES(1, "approved");

-- participant
SET @text = "admin";
INSERT INTO participant VALUES(DEFAULT, @text, @text, @text, @text, NULL, (SELECT id FROM role WHERE name = @text));
SET @text = "user";
INSERT INTO participant VALUES(DEFAULT, @text, @text, @text, @text, NULL, (SELECT id FROM role WHERE name = @text));

-- category
INSERT INTO category (id, name) VALUES(DEFAULT, "physical");
INSERT INTO category (id, name) VALUES(DEFAULT, "mental");

-- activity
SET @text = "physical";
INSERT INTO activity (id, name, duration, category_id) VALUES(DEFAULT, "running", null, (SELECT id FROM category WHERE name = @text));
INSERT INTO activity (id, name, duration, category_id) VALUES(DEFAULT, "walking", null, (SELECT id FROM category WHERE name = @text));
INSERT INTO activity (id, name, duration, category_id) VALUES(DEFAULT, "swimming", null, (SELECT id FROM category WHERE name = @text));
SET @text = "mental";
INSERT INTO activity (id, name, duration, category_id) VALUES(DEFAULT, "reading", null, (SELECT id FROM category WHERE name = @text));
INSERT INTO activity (id, name, duration, category_id) VALUES(DEFAULT, "writing", null, (SELECT id FROM category WHERE name = @text));
INSERT INTO activity (id, name, duration, category_id) VALUES(DEFAULT, "watching", null, (SELECT id FROM category WHERE name = @text));

-- participant_activity
INSERT INTO participant_activity (participant_id, activity_id, status_id) VALUES((SELECT id FROM participant WHERE login = "user"), (SELECT id FROM activity WHERE name = "running"), 0);
INSERT INTO participant_activity (participant_id, activity_id, status_id) VALUES((SELECT id FROM participant WHERE login = "user"), (SELECT id FROM activity WHERE name = "walking"), 1);