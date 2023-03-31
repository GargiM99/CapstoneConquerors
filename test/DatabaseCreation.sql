CREATE TABLE person
  (
     id         INT IDENTITY NOT NULL PRIMARY KEY,
     birth_date DATE,
     firstname  VARCHAR(255),
     lastname   VARCHAR(255)
  )

CREATE TABLE _address
  (
     id           INT IDENTITY NOT NULL PRIMARY KEY,
     address_line VARCHAR(255),
     city         VARCHAR(255),
     country      VARCHAR(255),
     postal_code  VARCHAR(255),
     province     VARCHAR(255),
  )

CREATE TABLE _user
  (
     id        INT IDENTITY NOT NULL PRIMARY KEY,
     password  VARCHAR(255) NOT NULL UNIQUE,
     role      VARCHAR(255) NOT NULL,
     username  VARCHAR(255) NOT NULL UNIQUE,
     person_id INT,
	 CONSTRAINT fk_user_person FOREIGN KEY (person_id) REFERENCES person(id)
  )  

CREATE TABLE contact
  (
     id                     INT IDENTITY NOT NULL PRIMARY KEY,
     email                  VARCHAR(255),
     primary_phone_number   VARCHAR(255),
     secondary_phone_number VARCHAR(255),
     person_id              INT,
	 CONSTRAINT fk_contact_person FOREIGN KEY (person_id) REFERENCES person(id)
  ) 

CREATE TABLE token
  (
     id         INT IDENTITY NOT NULL PRIMARY KEY,
     expired    BIT NOT NULL,
     revoked    BIT NOT NULL,
     token      VARCHAR(255),
     token_type VARCHAR(255),
     user_id    INT,
     CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES _user(id)
  ) 

CREATE TABLE address_person
  (
     address_id INT NOT NULL,
     person_id  INT NOT NULL,
     CONSTRAINT fk_ap_address FOREIGN KEY (address_id) REFERENCES _address(id),
     CONSTRAINT fk_ap_person FOREIGN KEY (person_id) REFERENCES person(id)
  ) 

