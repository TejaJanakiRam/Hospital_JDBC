CREATE database Hospital;
USE Hospital;

-- GRANT ALL privileges on Hospital.* TO 'root'@'localhost';

CREATE table Patient(
    pnt_id int NOT NULL AUTO_INCREMENT,
    pnt_name varchar(30) NOT NULL,
    pnt_presid int NOT NULL,
    CONSTRAINT pk_patient PRIMARY KEY (pnt_id)
);

CREATE table Prescription(
    pres_id int NOT NULL AUTO_INCREMENT,
    med1_id int NOT NULL,
    med2_id int,
    med3_id int,
    CONSTRAINT pk_Prescription PRIMARY KEY (pres_id)
);

CREATE table Medicine (
    medicine_id int NOT NULL AUTO_INCREMENT,
    medicine_name varchar(30) NOT NULL,
    med_cost float NOT NULL,
    med_disease varchar(100) NOT NULL,
    CONSTRAINT pk_med PRIMARY KEY (medicine_id)
);

CREATE table Staff(
    staff_id int NOT NULL AUTO_INCREMENT,
    staff_username varchar(30) NOT NULL,
    staff_password varchar(30) NOT NULL,
    CONSTRAINT pk_staff PRIMARY KEY (staff_id)
);

CREATE table Super_admin(
    admin_id int NOT NULL,
    admin_username varchar(30) NOT NULL,
    admin_password varchar(30) NOT NULL,
    CONSTRAINT pk_admin PRIMARY KEY (admin_id)
);