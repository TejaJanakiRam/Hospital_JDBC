INSERT INTO Medicine(medicine_name,med_cost,med_disease) VALUES ('Dolo',20.00,'Fever');
INSERT INTO Medicine(medicine_name,med_cost,med_disease) VALUES ('Aspirin',30.00,'Stomach Pain');
INSERT INTO Medicine(medicine_name,med_cost,med_disease) VALUES ('Zofran',40.00,'Nausea');
INSERT INTO Medicine(medicine_name,med_cost,med_disease) VALUES ('Sinarest',10.00,'Cold');
INSERT INTO Medicine(medicine_name,med_cost,med_disease) VALUES ('Ibuprofen',20.00,'Headache');

INSERT INTO Prescription(med1_id,med2_id,med3_id) values (1,4,5);
INSERT INTO Prescription(med1_id,med2_id,med3_id) values (2,3,NULL);
INSERT INTO Prescription(med1_id,med2_id,med3_id) VALUES (1,NULL,NULL);

INSERT INTO Patient(pnt_name, pnt_presid) values ('John',1);
INSERT INTO Patient(pnt_name, pnt_presid) values ('Alex',2);
INSERT INTO Patient(pnt_name, pnt_presid) values ('Jesse',3);

INSERT INTO Staff(staff_username,staff_password) VALUES ('Jack','Jack123');
INSERT INTO Staff(staff_username,staff_password) VALUES ('Emilia','Cats4lyf');
INSERT INTO Staff(staff_username,staff_password) VALUES ('Julie','DogsRbetter');

INSERT INTO Super_admin(admin_id,admin_username,admin_password) VALUES (23,'Admin','DifficultPassword');
