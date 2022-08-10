ALTER TABLE Patient
    ADD CONSTRAINT fk_Patient FOREIGN KEY (pnt_presid) REFERENCES Prescription(pres_id);

ALTER TABLE Prescription
    ADD CONSTRAINT fk_Prescription1 FOREIGN KEY (med1_id) REFERENCES Medicine(medicine_id);
ALTER TABLE Prescription
    ADD CONSTRAINT fk_Prescription2 FOREIGN KEY (med2_id) REFERENCES Medicine(medicine_id);
ALTER TABLE Prescription
    ADD CONSTRAINT fk_Prescription3 FOREIGN KEY (med3_id) REFERENCES Medicine(medicine_id);

ALTER TABLE Prescription AUTO_INCREMENT = 1;
ALTER TABLE Medicine AUTO_INCREMENT = 1;
ALTER TABLE Patient AUTO_INCREMENT = 1;
ALTER TABLE Staff AUTO_INCREMENT = 1;
