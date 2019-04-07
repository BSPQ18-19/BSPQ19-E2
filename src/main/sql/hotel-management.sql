<<<<<<< HEAD
/* DELETE 'nucleus' database*/
DROP SCHEMA IF EXISTS hotelManagementDB;
/* DELETE USER 'hm' AT LOCAL SERVER*/
DROP USER IF EXISTS 'hm'@'%';

/* CREATE 'nucleus' DATABASE */
CREATE SCHEMA hotelManagementDB;
/* CREATE THE USER 'hm' AT LOCAL SERVER WITH PASSWORD 'hm' */
CREATE USER IF NOT EXISTS 'hm'@'%' IDENTIFIED BY 'hm';

=======
/* DELETE 'nucleus' database*/
DROP SCHEMA IF EXISTS hotelManagementDB;
/* DELETE USER 'hm' AT LOCAL SERVER*/
DROP USER IF EXISTS 'hm'@'%';

/* CREATE 'nucleus' DATABASE */
CREATE SCHEMA hotelManagementDB;
/* CREATE THE USER 'hm' AT LOCAL SERVER WITH PASSWORD 'hm' */
CREATE USER IF NOT EXISTS 'hm'@'%' IDENTIFIED BY 'hm';

>>>>>>> master
GRANT ALL ON hotelManagementDB.* TO 'hm'@'%';