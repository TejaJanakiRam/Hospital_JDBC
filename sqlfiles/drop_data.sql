ALTER TABLE Prescription
    DROP FOREIGN KEY fk_prescription1;
ALTER TABLE Prescription
    DROP FOREIGN KEY fk_prescription2;
ALTER TABLE Prescription
    DROP FOREIGN KEY fk_prescription3;

ALTER TABLE Patient
    DROP FOREIGN KEY fk_patient;

DROP TABLE Patient;
DROP TABLE Prescription;
DROP TABLE Medicine;
DROP TABLE Staff;
DROP TABLE Super_admin;

DROP DATABASE Hospital;