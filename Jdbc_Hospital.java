// Import required packages

import java.sql.*;
import java.util.Scanner;

public class Jdbc_Hospital {

//Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/Hospital?allowPublicKeyRetrieval=true&useSSL=false";
//  Database credentials
   static final String USER = "root";// add your user 
   static final String PASS = "admin";// add password
   

   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;


//Connecting to the Database
   try{
      Class.forName(JDBC_DRIVER);
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      System.out.println("Creating statement...");
      stmt = conn.createStatement();

      Scanner scan = new Scanner(System.in);

      intro(stmt,scan);

//Clean-up environment
      scan.close();
      stmt.close();
      conn.close();
	}catch(SQLException se){    	 //Handle errors for JDBC
      	se.printStackTrace();
   	}catch(Exception e){        	//Handle errors for Class.forName
      e.printStackTrace();
   }finally{				//finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }					//end finally try
   }					//end try
}					

   static void intro(Statement stmt,Scanner scan){
	   System.out.println("==================================================================");
      System.out.println("Welcome to the City Central Hospital");
      System.out.println("Please identify yourself as one of the below: ");
      System.out.println("1.Admin");
      System.out.println("2.Staff");
      System.out.println("3.Patient");
      System.out.println("4.Exit");
      
      try{
      int choice  = Integer.parseInt(scan.nextLine());
      System.out.println("\033[H\033[J");
      System.out.flush();
      switch (choice) {
         case 1:
            adm(stmt,scan);
            break;

         case 2:
          staff(stmt,scan);
            break;

         case 3:
            patient(stmt,scan);
            break;
         
         case 4:
            System.out.println("Thank you");
            System.exit(0);
      
         default:
            System.out.println("Invalid input");
            break;
      }
      } catch(Exception e){
      		e.printStackTrace();
      		}
      //System.out.println("\033[H\033[J");
      // System.out.flush();
      intro(stmt, scan);
   }

   static void patient(Statement stmt,Scanner scan){
      System.out.println("Please enter your patient ID: ");
      int id = Integer.parseInt(scan.nextLine());

      try {
         
         String cmd = String.format("SELECT * FROM Patient WHERE pnt_id = '%d';",id);
         ResultSet rs = execute(stmt,cmd);

         rs.next();

         int pid = rs.getInt("pnt_presid");
         System.out.println("Patient name: "+rs.getString("pnt_name"));
         System.out.println("Prescription id: "+pid);
         
         String PID = String.format("SELECT med1_id,med2_id,med3_id from Prescription WHERE pres_id='%d'",pid);
       
         rs = execute(stmt,PID);
         rs.next();
         
         int one = rs.getInt("med1_id");
         int two = rs.getInt("med2_id");
         int three = rs.getInt("med3_id");

         System.out.println("Patient "+id+" needs the medicines "+one+" "+two+" "+three);


      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   } 

   static boolean auth(Statement stmt,Scanner scan,boolean isAdmin){
      boolean result = false;

      if(isAdmin){
         System.out.println("Enter the Admin Username:");
         String username = scan.nextLine();
         System.out.println("Enter the password:");
         String password = scan.nextLine();

         String cmd = "SELECT * from Super_admin";
         ResultSet rs = execute(stmt, cmd);
         
         try {
            while (rs.next()) {
               String pid = rs.getString("admin_username");
               String ppass = rs.getString("admin_password");

               if (pid.equals(username) && ppass.equals(password)) {
                  result = true;
                  break;
               }
            }
         } catch (SQLException sq) {
            sq.printStackTrace();
         } catch (Exception e) {
            e.printStackTrace();
         }
         return result;
      }
      else{
         System.out.println("Enter the Staff Username:");
         String username = scan.nextLine();
         System.out.println("Enter the Staff Password:");
         String password = scan.nextLine();

         String cmd = "SELECT * from Staff";
         ResultSet rs = execute(stmt,cmd);

         try {
            while (rs.next()) {
               String pid = rs.getString("staff_username");
               String ppass = rs.getString("staff_password");

               if (pid.equals(username) && ppass.equals(password)) {
                  result = true;
                  break;
               }
            }
         } catch (SQLException sq) {
            sq.printStackTrace();
         } catch (Exception e) {
            e.printStackTrace();
         }
         return result;
      }
   }

   static void adm(Statement stmt,Scanner scan){
      boolean sa = auth(stmt,scan,true);
      try{
      if (!sa) {
         System.out.println("Incorrect credentials");
         return;
      }
      else{
         System.out.println("\n\n\nChoose one of the below operations");
         System.out.println("1. List all Staff records");
         System.out.println("2. List all Medicine records");
         System.out.println("3. List all Prescription records");
         System.out.println("4. Add a new staff record");
         System.out.println("5. Delete a staff record");
         System.out.println("6. Add a new medicine record");
         System.out.println("7. Delete a medicine record");
         System.out.println("8. Change staff's password");
         System.out.println("9. Exit");
      }

      boolean loop = true;
      while(loop){
         System.out.println("\nEnter choice: ");
      int choice = Integer.parseInt(scan.nextLine());
      switch (choice) {
         case 1:
            String cmd = "SELECT * FROM Staff";
            ResultSet rs = execute(stmt,cmd);
            while(rs.next())
            System.out.println("Staff ID: " + rs.getInt("staff_id")+" Staff Username: " + rs.getString("staff_username") + " Staff Password: "+rs.getString("staff_password"));
            
            break;
         
         case 2:
             cmd = "SELECT * FROM Medicine";
             rs = execute(stmt,cmd);
            while(rs.next())            
             System.out.println("Medicine ID: " + rs.getInt("medicine_id")+" Medicine Name: " + rs.getString("medicine_name") + " Medicine Cost: "+rs.getInt("med_cost")+ " Cure For: "+rs.getString("med_disease"));
            
            break;
         case 3:
            cmd = "SELECT * FROM Prescription";
             rs = execute(stmt,cmd);
            while(rs.next())            
             System.out.println("Prescription ID: " + rs.getInt("pres_id")+" Med1 ID: "+rs.getInt("med1_id")+" Med2 ID: "+rs.getInt("med2_id")+" Med3 ID: "+rs.getInt("med3_id"));
            
            break;

         case 4:
            add(stmt,scan,1);
            break;

         case 5:
            delete(stmt,scan,1);
            break;

         case 6:
            add(stmt,scan,2);
            break;

         case 7:
            delete(stmt,scan,2);
            break;
         
         case 8:
            System.out.println("Enter staff username: ");
               String username = scan.nextLine();
               System.out.println("Enter new staff password: ");
               String password = scan.nextLine();

               cmd = String.format("UPDATE Staff SET staff_password = '%s' WHERE staff_username = '%s'",password,username);
               int r = update(stmt,cmd);
               if (r!=0) {
                  System.out.println("Success!");
               }
               else {
                  System.out.println("Failed!");
               }
               break;   
         
         case 9:
            loop = false;
            break;
            
         default:
            System.out.println("Invalid choice. Enter choice again.");
            break;
      }}
   } catch(SQLException sq){
      sq.printStackTrace();
   } catch(Exception e){
      e.printStackTrace();
   }
   }

   static void staff(Statement stmt,Scanner scan){
      boolean sf = auth(stmt,scan,false);
      try{
      if (!sf) {
         System.out.println("Incorrect credentials");
         return;
      }
      else{
         System.out.println("\n\n\nChoose one of the below operations");
         System.out.println("1. List all Patient records");
         System.out.println("2. List all Medicine records");
         System.out.println("3. List all Prescription records");
         System.out.println("4. Add a new prescription record");
         System.out.println("5. Delete a prescription record");
         System.out.println("6. Add a new patient record");
         System.out.println("7. Delete a patient record");
         System.out.println("8. Exit");
      }

      boolean loop = true;
      while (loop) {
         System.out.println("\nEnter choice: ");
      int choice = Integer.parseInt(scan.nextLine());
      switch (choice) {
         case 1:
            String cmd = "SELECT * FROM Patient";
            ResultSet rs = execute(stmt,cmd);
            while(rs.next())
            System.out.println("Patient ID: " + rs.getInt("pnt_id")+" Patient Name: " + rs.getString("pnt_name") + " Prescription ID: "+rs.getInt("pnt_presid"));
            
            break;
         
         case 2:
             cmd = "SELECT * FROM Medicine";
             rs = execute(stmt,cmd);
            while(rs.next())
            System.out.println("Medcine ID: " + rs.getInt("medicine_id")+" Medicine Name: " + rs.getString("medicine_name") + " Medicine Cost: "+rs.getInt("med_cost")+ " Cure For: "+rs.getString("med_disease"));
            
            break;
         
         case 3:
            cmd = "SELECT * FROM Prescription";
            rs = execute(stmt,cmd);
            while(rs.next())            
            System.out.println("Prescription ID: " + rs.getInt("pres_id")+" Med1 ID: "+rs.getInt("med1_id")+" Med2 ID: "+rs.getInt("med2_id")+" Med3 ID: "+rs.getInt("med3_id"));
        
            break;

         case 4:
            add(stmt,scan,3);
            break;
         
         case 5:
            delete(stmt,scan,3);
            break;

         case 6:
            add(stmt,scan,4);
            break;
         
         case 7:
            delete(stmt,scan,4);
            break;
         
         case 8:
            loop = false;
            break;

         default:
            System.out.println("Invalid choice. Enter choice again.");
            break;
      }}
   } catch (SQLException sq) {
      sq.printStackTrace();
   } catch (Exception e) {
      e.printStackTrace();
   }
   }

   static void add(Statement stmt,Scanner scan,int choice){
      try {
         switch (choice) {
            case 1:
               System.out.println("Enter staff username: ");
               String username = scan.nextLine();
               System.out.println("Enter staff password: ");
               String password = scan.nextLine();

               String cmd = String.format("INSERT INTO Staff(staff_username,staff_password) VALUES ('%s','%s');",username,password);
               int r = update(stmt,cmd);
               if (r!=0) {
                  System.out.println("Success!");
               }
               else {
                  System.out.println("Failed!");
               }
               break;
            
            case 2:
               System.out.println("Enter Medicine name: ");
               String name = scan.nextLine();
               System.out.println("Enter Medicine cost: ");
               int cost = Integer.parseInt(scan.nextLine());
               System.out.println("Enter disease: ");
               String disease = scan.nextLine();

                cmd = String.format("INSERT INTO Medicine(medicine_name,med_cost,med_disease) VALUES ('%s','%d','%s');",name,cost,disease);
                r = update(stmt,cmd);
               if (r!=0) {
                  System.out.println("Success!");
               }
               else {
                  System.out.println("Failed!");
               }
               break;
            
            case 3:
               System.out.println("Please make sure that the related medicines are registered");
               System.out.println("Enter Medicine_1 ID: ");
               int one  = Integer.parseInt(scan.nextLine());
               System.out.println("Enter Medicine_2 ID: ");
               int two = Integer.parseInt(scan.nextLine());
               System.out.println("Enter Medicine_3 ID: ");
               int three  = Integer.parseInt(scan.nextLine());

                cmd = String.format("INSERT INTO Prescription(med1_id,med2_id,med3_id) VALUES ('%d','%d','%d');",one,two,three);
                r = update(stmt,cmd);
               if (r!=0) {
                  System.out.println("Success!");
               }
               else {
                  System.out.println("Failed!");
               }
               break;

            case 4:
               System.out.println("Please make sure that the related presciptions are registered");
               System.out.println("Enter Patient name: ");
                name = scan.nextLine();
               System.out.println("Enter Prescription ID: ");
               int id = Integer.parseInt(scan.nextLine());


                cmd = String.format("INSERT INTO Patient(pnt_name,pnt_presid) VALUES ('%s','%d');",name,id);
                r = update(stmt,cmd);
               if (r!=0) {
                  System.out.println("Success!");
               }
               else {
                  System.out.println("Failed!");
               }
               break;

            default:
               break;
         }
      
      } catch(Exception e) {
         e.printStackTrace();
      }
   }

   static void delete(Statement stmt,Scanner scan,int choice){
      try {
         switch (choice) {
         case 1:
            System.out.println("Enter staff id: ");
            int id = Integer.parseInt(scan.nextLine());

            String cmd = String.format("DELETE FROM Staff where staff_id = '%d'",id);
            int r = update(stmt,cmd);
            if (r!=0) {
               System.out.println("Success!");
            }
            else {
               System.out.println("Failed!");
            }
            
            break;
         case 2:
            System.out.println("Enter medicine id: ");
            id = Integer.parseInt(scan.nextLine());

            cmd = String.format("DELETE FROM Medicine where medicine_id = '%d'",id);
            r = update(stmt,cmd);
            if (r!=0) {
               System.out.println("Success!");
            }
            else {
               System.out.println("Failed!");
            }
            
            break;
         
         case 3:
            System.out.println("Enter prescription id: ");
            id = Integer.parseInt(scan.nextLine());

            cmd = String.format("DELETE FROM Prescription where pres_id='%d'",id);
            r = update(stmt,cmd);
            if (r!=0) {
               System.out.println("Success!");
            }
            else {
               System.out.println("Failed!");
            }
            
            break;

         case 4:
            System.out.println("Enter patient id: ");
            id = Integer.parseInt(scan.nextLine());

            cmd = String.format("DELETE FROM Patient where pnt_id = '%d'",id);
            r = update(stmt,cmd);
            if (r!=0) {
               System.out.println("Success!");
            }
            else {
               System.out.println("Failed!");
            }
            
            break;
         
         default:
            break;
         }
     
      } catch(Exception e) {
         e.printStackTrace();
      }
   }

   

   static ResultSet execute(Statement stmt, String cmd){
      
      try {
         ResultSet rs = stmt.executeQuery(cmd);
         return rs;
      }catch(SQLException sq){
         sq.printStackTrace();
      }catch(Exception e){
         e.printStackTrace();
      }
      
      return null;
   }

   static int update(Statement stmt,String cmd){
      try {
         int rs = stmt.executeUpdate(cmd);
         return rs;
      } catch (SQLException sq) {
         //  sq.printStackTrace();
      } catch (Exception e) {
         //  e.printStackTrace();
      }
      return 0;
   }
}					


