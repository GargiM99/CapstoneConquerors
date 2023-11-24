CREATE TABLE person
  (
     id         INT IDENTITY NOT NULL PRIMARY KEY,
     firstname  VARCHAR(255),
     lastname   VARCHAR(255)
  )

CREATE TABLE _user
  (
     id        INT IDENTITY NOT NULL PRIMARY KEY,
     password  VARCHAR(255),
     role      VARCHAR(255) NOT NULL,
     username  VARCHAR(255) NOT NULL UNIQUE,
     person_id INT,
     agent_id  INT,
	CONSTRAINT fk_user_person FOREIGN KEY (person_id) REFERENCES person(id),
        CONSTRAINT fk_agentId_user_id FOREIGN KEY (agent_id) REFERENCES _user(id)
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

CREATE TABLE trip
 (
     id INT IDENTITY NOT NULL PRIMARY KEY,
     trip_name VARCHAR(255) NOT NULL,
     trip_start_date DATE NOT NULL,
     trip_end_date DATE NOT NULL,
	 trip_type VARCHAR(255) NULL,
     status VARCHAR(255) NOT NULL,
     user_id INT,
     CONSTRAINT fk_trip_user FOREIGN KEY (user_id) REFERENCES _user(id)
 )

CREATE TABLE _event
 (
     id INT IDENTITY NOT NULL PRIMARY KEY,
     event_name VARCHAR(255) NOT NULL,
     event_date DATE NOT NULL,
     status VARCHAR(255) NOT NULL,
	 event_description VARCHAR(1024) NULL,
     trip_id INT,
     CONSTRAINT fk_event_trip FOREIGN KEY (trip_id) REFERENCES trip(id)
 )

 CREATE TABLE client_note
 (
	id INT IDENTITY NOT NULL PRIMARY KEY,
	note_title VARCHAR(255) NOT NULL,
	note_body TEXT NOT NULL,
	client_id INT NULL,
	trip_id INT NULL,
	CONSTRAINT fk_note_user FOREIGN KEY (client_id) REFERENCES _user(id),
	CONSTRAINT fk_note_trip FOREIGN KEY (trip_id) REFERENCES trip(id)
 )



