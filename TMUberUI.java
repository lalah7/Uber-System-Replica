import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service
//Lala Hussein 501106657


public class TMUberUI
{
  private static String fileName;
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 
    
    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      try {
        String action = scanner.nextLine();
        if (action == null || action.equals("")) 
        {
         System.out.print("\n>");
          continue;
       }
        // Quit the App
        else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
          return;
        // Print all the registered drivers
        else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
        {
         tmuber.listAllDrivers(); 
        }
        // Print all the registered users
        else if (action.equalsIgnoreCase("USERS"))  // List all users
        {
          tmuber.listAllUsers(); 
        }
        // Print all current ride requests or delivery requests
        else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
        {
          tmuber.listAllServiceRequests(); 
        }
        // Register a new driver
        else if (action.equalsIgnoreCase("REGDRIVER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String carModel = "";
          System.out.print("Car Model: ");
          if (scanner.hasNextLine())
          {
            carModel = scanner.nextLine();
          }
          String license = "";
          System.out.print("Car License: ");
          if (scanner.hasNextLine())
          {
            license = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }

          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-15s", name, carModel, license, address);
        }

        // Register a new user
        else if (action.equalsIgnoreCase("REGUSER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          double wallet = 0.0;
          System.out.print("Wallet: ");
          if (scanner.hasNextDouble())
          {
            wallet = scanner.nextDouble();
            scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
          }
          tmuber.registerNewUser(name, address, wallet);
           
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        }
        // Request a ride
        else if (action.equalsIgnoreCase("REQRIDE")) 
        {
          // Get the following information from the user (on separate lines)
          // Then use the TMUberSystemManager requestRide() method properly to make a ride request
          // "User Account Id: "      (string)
          // "From Address: "         (string)
          // "To Address: "           (string)
          String accountID = "";
          System.out.println("User Account Id: ");
          if (scanner.hasNextLine()){
           accountID = scanner.nextLine(); //get user account id
          }
          String fromAdd = "";
          System.out.println("From Address: ");
          if (scanner.hasNextLine()){
            fromAdd = scanner.nextLine(); //get address from
          }
          String toAdd = "";
          System.out.println("To Address: ");
          if (scanner.hasNextLine()){
            toAdd = scanner.nextLine();//get address to
          }
          tmuber.requestRide(accountID, fromAdd, toAdd);
           //check if valid inputs
          
          System.out.printf("RIDE for: %-15s From: %-15s To: %-15s",tmuber.getUser(accountID).getName(), fromAdd, toAdd);//get user's name from the given account id
        
        

        }
        // Request a food delivery
        else if (action.equalsIgnoreCase("REQDLVY")) 
        {
          // Get the following information from the user (on separate lines)
          // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
          // "User Account Id: "      (string)
          // "From Address: "         (string)
          // "To Address: "           (string)
          // "Restaurant: "           (string)
          // "Food Order #: "         (string)
          String accountID = "";
          System.out.println("User Account Id: ");
          if (scanner.hasNextLine()){
            accountID = scanner.nextLine();//get user account id
          }
          String from = "";
          System.out.println("From Address: ");
          if (scanner.hasNextLine()){
            from = scanner.nextLine(); //get from address
          }
          String toAdd = "";
          System.out.println("To Address: ");
          if (scanner.hasNextLine()){
            toAdd = scanner.nextLine();//get to address
          }
          String restaurant = "";
          System.out.println("Restaurant: ");
          if (scanner.hasNextLine()){
            restaurant = scanner.nextLine();//get restaurant name
          }
          String order = "";
          System.out.println("Food Order #: ");
          if (scanner.hasNextLine()){
            order = scanner.nextLine(); //get food order id
          }
          tmuber.requestDelivery(accountID,from,toAdd,restaurant,order);
             //check if user inputs are valid
          
          System.out.printf("DELIVERY for: %-15s From: %-15s To: %-15s", tmuber.getUser(accountID).getName(), from, toAdd);
      
        
        }
      // Sort users by name
        else if (action.equalsIgnoreCase("SORTBYNAME")) 
        {
          tmuber.sortByUserName();
        }
        // Sort users by number of ride they have had
        else if (action.equalsIgnoreCase("SORTBYWALLET")) 
        {
          tmuber.sortByWallet();
        }
      // Sort current service requests (ride or delivery) by distance
        else if (action.equalsIgnoreCase("SORTBYDIST")) 
        {
          tmuber.sortByDistance();
        }
        // Cancel a current service (ride or delivery) request
        else if (action.equalsIgnoreCase("CANCELREQ")) 
        {
          int request = -1;
          System.out.print("Request #: ");
          if (scanner.hasNextInt())
          {
            request = scanner.nextInt();
            scanner.nextLine(); // consume nl character
          }
          int zone = -1;
          System.out.print("Zone #: ");
          if (scanner.hasNextInt())
          {
            zone = scanner.nextInt();
          }
          tmuber.cancelServiceRequest(request,zone);
          System.out.println("Service request #" + request + " in Zone " + zone + " Cancelled");
        }
        // Drop-off the user or the food delivery to the destination address
        else if (action.equalsIgnoreCase("DROPOFF")) 
        {
          String driverID = "";
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine())
          {
            driverID = scanner.nextLine();
            //scanner.nextLine(); // consume nl
          }
          tmuber.dropOff(driverID);
          System.out.println("Driver " + driverID + " Dropping Off");
        }
        // Get the Current Total Revenues
        else if (action.equalsIgnoreCase("REVENUES")) 
        {
          System.out.println("Total Revenue: " + tmuber.totalRevenue);
        }
        // Unit Test of Valid City Address 
        else if (action.equalsIgnoreCase("ADDR")) 
        {
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          System.out.print(address);
          if (CityMap.validAddress(address))
            System.out.println("\nValid Address"); 
          else
            System.out.println("\nBad Address"); 
        }
        // Unit Test of CityMap Distance Method
        else if (action.equalsIgnoreCase("DIST")) 
        {
          String from = "";
          System.out.print("From: ");
          if (scanner.hasNextLine())
          {
            from = scanner.nextLine();
          }
          String to = "";
          System.out.print("To: ");
          if (scanner.hasNextLine())
          {
            to = scanner.nextLine();
          }
          System.out.print("\nFrom: " + from + " To: " + to);
          System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
        }

        else if (action.equalsIgnoreCase("PICKUP"))
        {
          String driverID = "";
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine()){
            driverID = scanner.nextLine();
          }
          int zone = tmuber.getDriver(driverID).getZone();
          tmuber.pickup(driverID);

          System.out.println("Driver " + driverID + " Picking Up in Zone " + zone);
        }

        else if (action.equalsIgnoreCase("LOADUSERS"))
        {
          //String fileName = "";
          System.out.print("Users File: ");
          if (scanner.hasNextLine())
          {
            fileName = scanner.nextLine();
          }

          ArrayList<User> usersList = TMUberRegistered.loadPreregisteredUsers(fileName);
          tmuber.setUsers(usersList);
          System.out.println("Users Loaded");
  
        }

        else if (action.equalsIgnoreCase("LOADDRIVERS"))
        {
          //String fileName = "";
          System.out.print("Drivers File: ");
          if (scanner.hasNextLine())
          {
            fileName = scanner.nextLine();
          }
          ArrayList<Driver> driverList = TMUberRegistered.loadPreregisteredDrivers(fileName);
          tmuber.setDrivers(driverList);
          System.out.println("Drivers Loaded");
        }

        else if (action.equalsIgnoreCase("DRIVETO"))
        {
          String driverID = "";
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine())
          {
            driverID = scanner.nextLine();
          }

          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          int zone = CityMap.getCityZone(address);
          tmuber.driveTo(driverID, address);
          System.out.println("Driver " + driverID + " Now in Zone " + zone);
        }
      
        
      } 
      catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
      catch (FileNotFoundException e){
        System.out.println(fileName + " Not Found");
      }
      catch (IOException e){
        return;
      }
      catch (InvalidAddressException e){
        System.out.println(e.getMessage());
      }
      catch (UserExistsException e){
        System.out.println(e.getMessage());
      }
      catch (DriverNotFoundException e){
        System.out.println(e.getMessage());
      }
      catch (ServiceNotFoundException e){
        System.out.println(e.getMessage());
      }
      catch (DriverExistsException e){
        System.out.println(e.getMessage());
      }
      catch (InsufficientTravelException e){
        System.out.println(e.getMessage());
      }
      catch (ExistingRequestException e){
        System.out.println(e.getMessage());
      }
      catch (EmptyZoneException e){
        System.out.println(e.getMessage());
      }

      System.out.print("\n>");
      
    }
  }
}


