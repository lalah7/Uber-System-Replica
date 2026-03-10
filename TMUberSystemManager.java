import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
//Lala Hussein 501106657
public class TMUberSystemManager
{
  private Map<String, User>  users; //map of user accountID to users
  private ArrayList<Driver> drivers;

  //private ArrayList<TMUberService> serviceRequests; 
  @SuppressWarnings("unchecked")
  private Queue<TMUberService>[] serviceRequests = new Queue[4];

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  
  public TMUberSystemManager()
  {
    users   = new LinkedHashMap<>();
    drivers = new ArrayList<Driver>();
    //serviceRequests = new Queue[4]; 

    for(int i = 0; i < 4; i++){
      serviceRequests[i] = new LinkedList<>();
    }
    
    
    
    totalRevenue = 0;
  }

  // General string variable used to store an error message when something is invalid 
  // (e.g. user does not exist, invalid address etc.)  
  // The methods below will set this errMsg string and then return false
  
  
  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    // Fill in the code
    return users.get(accountId);
    //return null if not found
    
  }

  public Driver getDriver(String driverID)
  {
    for (Driver driver : drivers){
      if (driver.getId().equals(driverID)){
        return driver;
      }
    }
    return null;
  }

  public void setUsers(ArrayList<User> userList){
    //users.clear();
    for (User user : userList){
      users.put(user.getAccountId(), user);
    }
  }

  public void setDrivers(ArrayList<Driver> drivers){
    this.drivers = drivers;
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    // Fill in the code
    for (User user2 : users.values()){
      if (user2.equals(user)){
        return true;
      }
    }
    return false;
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   // Fill in the code
   
   //iterate through list of drivers
   for (Driver driver2 : drivers){
    //check if driver is in list
    if (driver2.equals(driver)){
      //true if found
      return true;
    }
   }
   return false;
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {
    // Fill in the code

    //iterate through list of requests
    for (Queue<TMUberService> queue : serviceRequests){
      if (queue.contains(req)){
        return true;
      }
    }
    return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    int index = 1;
    
    for (User user : users.values())
    {
      System.out.printf("%-2s. ", index++);
      user.printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    System.out.println();

    for (int i = 0; i < drivers.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println();
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    System.out.println();
    
    for (int zone = 0; zone < serviceRequests.length; zone++)
    {
      System.out.println("ZONE " + zone);
      System.out.println("======");
      

      int index = 1;
      Queue<TMUberService> queue = serviceRequests[zone];
      
      for (TMUberService service : queue)
      {
      System.out.printf("%-2s. " + "------------------------------------------------------------", index++);
      service.printInfo();
      System.out.println();
      }
      
    }
    
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {
    // Fill in the code. Before creating a new user, check paramters for validity
    // See the assignment document for list of possible erros that might apply
    // Write the code like (for example):
    // if (address is *not* valid)
    // {
    //    set errMsg string variable to "Invalid Address "
    //    return false
    // }
    // If all parameter checks pass then create and add new user to array list users
    // Make sure you check if this user doesn't already exist!

    //check if name is valid
    if (name == null || name.isEmpty()){
      throw new IllegalArgumentException("Invalid User Name");
    }

    //check if address is balanced
    if ((CityMap.validAddress(address) == false)){
      throw new InvalidAddressException("Invalid User Address");
    }

    //check if wallet is empty
    if (wallet < 0){
      throw new IllegalArgumentException("Invalid Money in Wallet");
    }

    ArrayList<User> usersList = new ArrayList<>(users.values());
    String accountID = TMUberRegistered.generateUserAccountId(usersList);
    //check if user already exists in list of users
    if (userExists(new User(accountID,name, address, wallet))){//check TMUberRegistered
      throw new UserExistsException("User Already Exists in System");
    }

    //add new user to Users list if no issues
    users.put(accountID, new User(accountID, name, address, wallet));
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    // Fill in the code - see the assignment document for error conditions
    // that might apply. See comments above in registerNewUser
    
    ////check if name is valid
    if (name == null || name.isEmpty()){
      throw new IllegalArgumentException("Invalid Driver Name");
    }

    //check if car model is valid
    if (carModel == null || carModel.isEmpty()){
       throw new IllegalArgumentException("Invalid Car Model");
    }

    //check if licence plate valid
    if (carLicencePlate == null || carLicencePlate.isEmpty()){
      throw new IllegalArgumentException("Invalid Car License Plate");
    }

    if (!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid address");
    }

    int zone = CityMap.getCityZone(address);

    //check if driver exists in list of drivers
    if (driverExists(new Driver(TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate,address, zone))){
      throw new DriverExistsException("Driver Already Exists in System");
    }
    

    //add new driver to list of drivers
    drivers.add(new Driver(TMUberRegistered.generateDriverId(drivers),name, carModel, carLicencePlate,address, zone));
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    // Use the account id to find the user object in the list of users
    User user = getUser(accountId);

    // Check for valid parameters
    if (user == null){
      throw new IllegalArgumentException("User Account Not Found");
    }

    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      throw new InvalidAddressException("Invalid Address");
    }

    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    int Distance = CityMap.getDistance(from, to);
    if (Distance <= 1){
      throw new InsufficientTravelException("Insufficient Travel Distance");
    }

    double cost = getRideCost(Distance);
    if (cost > user.getWallet()){
      throw new IllegalArgumentException("Insufficient Funds");
    }

    // Create the TMUberRide object and check if existing ride request for this user
    TMUberService newRide = new TMUberRide(from, to, user, Distance, cost);
    if (existingRequest(newRide)){
      throw new ExistingRequestException("User Already Has Ride Request");
    }
    
    int zone = CityMap.getCityZone(from);
    
    

    // Add the ride request to the list of requests
    serviceRequests[zone].add(newRide);

    // Increment the number of rides for this user
    user.addRide();
  }



  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    //See the comments above and use them as a guide
    // For deliveries, an existing delivery has the same user, restaurant and food order id
    // Increment the number of deliveries the user has had

    // Use the account id to find the user object in the list of users
    User user = getUser(accountId);

    // check for valid parameters
    if (user == null){
      throw new IllegalArgumentException("User Account Not Found");
    }

    if (CityMap.validAddress(from) == false || CityMap.validAddress(to) == false){
      throw new InvalidAddressException("Invalid Address");
    }

    int Distance = CityMap.getDistance(from, to);
    if (Distance <= 1){
      throw new InsufficientTravelException("Insufficient Travel Distance");
    }

    double cost = getDeliveryCost(Distance);

    if (cost > user.getWallet()){
      throw new IllegalArgumentException("Insufficient Funds");
    }

    

    // Create the TMUberDelivery object and check if existing delivery request for this user
    TMUberService newDelivery = new TMUberDelivery(from, to, user, Distance, cost, restaurant, foodOrderId);
    if (existingRequest(newDelivery)){
      throw new ExistingRequestException("User Already Has Delivery Request at Restaurant with this Food Order");
    }
    
    
    
    int zone = CityMap.getCityZone(from);
    //add delivery request to list of servicerequests
    serviceRequests[zone].add(newDelivery);

    //increment number of delivery for the user
    user.addDelivery();

  }





  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int request, int zone)
  {
    // Remove request from list
    // Also decrement number of rides or number of deliveries for this user
    // since this ride/delivery wasn't completed
    
    // Check if valid request #
    if (request <= 0 || request > serviceRequests[zone].size()){
      throw new IllegalArgumentException("Invalid Request #");
    }

    //get cancelled service request from list 
   Queue<TMUberService> zoneQueue = serviceRequests[zone];
   TMUberService service = zoneQueue.toArray(new TMUberService[0])[request - 1];
   User user = service.getUser();

    //decrement number of rides for user if service is a ride
    if (service instanceof TMUberRide){
      user.setRides(user.getRides() - 1);
    }

    //decrement number of delivery for user if service is a delivery
    if (service instanceof TMUberDelivery){
      user.setDeliveries(user.getDeliveries() - 1);
    }
    

    //remove request from list of requests
    LinkedList<TMUberService> userList = (LinkedList<TMUberService>) zoneQueue;
    userList.remove(request - 1);
    
  }



  //Pickup method
  public void pickup(String driverID)
  {
    Driver driver = null;
    for (Driver drive : drivers){
      if(drive.getId().equals(driverID)){
        driver = drive;
        break;
      }
    }

    if (driver == null){
      throw new DriverNotFoundException("Driver not found");
    }

    int driverZone = CityMap.getCityZone(driver.getAddress());
    Queue<TMUberService> zoneQueue = serviceRequests[driverZone];

    if (zoneQueue.isEmpty()){
      throw new EmptyZoneException("No service requests in Zone " + driverZone);
    }

    TMUberService service = zoneQueue.poll();
    driver.setService(service);
    driver.setStatus(Driver.Status.DRIVING);
    driver.setAddress(service.getFrom());


  }
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverID)
  {
    // See above method for guidance

    //check for valid parameters
    Driver driver = null;
    for (Driver drive : drivers){
      if (drive.getId().equals(driverID)){
        driver = drive;
        break;
      }
    }

    if (driver == null){
      throw new DriverNotFoundException("Driver with ID:  " + driverID + " not found in system");
    }

    if (driver.getStatus() != Driver.Status.DRIVING){
      throw new IllegalArgumentException("Driver is not available"); // add exception?
    }

    // get the service, driver, user and distance of request
    TMUberService service = driver.getService();
    
    User user = service.getUser();
    int distance = service.getDistance();

    //get the cost of the service
    double cost = service.getCost();

    //get cost based on distance depending on type of service
    if (service instanceof TMUberRide){
      cost += getRideCost(distance);
    }else{
      cost += getDeliveryCost(distance);
    }
    
    //add cost to total revenue
    totalRevenue += cost;

    // Pay the driver
    driver.pay(cost * PAYRATE);

    // Deduct driver fee from total revenues
    totalRevenue-= (cost * PAYRATE);

    // Change driver status
    driver.setStatus(Driver.Status.AVAILABLE);
    driver.setService(null);
    driver.setAddress(service.getTo());
    driver.setZone(CityMap.getCityZone(service.getTo()));

    // Deduct cost of service from user
    user.payForService(cost);

    //remove request from list
    serviceRequests[CityMap.getCityZone(service.getTo())].remove(service);

  }

  public void driveTo(String driverID, String address)
  {
    Driver driver = null;
    for (Driver drive : drivers){
      if (drive.getId().equals(driverID)){
        driver = drive;
        break;
      }
    }
    if (driver == null){
      throw new DriverNotFoundException("Driver not found");
    }

    if (driver.getStatus() != Driver.Status.AVAILABLE){
      throw new IllegalArgumentException("Driver not available");
    }

    if (!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid Address");
    }

    int zone = CityMap.getCityZone(address);
    driver.setAddress(address);
    driver.setZone(zone);


  }


  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    //sort users by name
    ArrayList<User> userList = new ArrayList<>(users.values());
    Collections.sort(userList, new NameComparator());
    users.clear();
    for (User user : userList){
      users.put(user.getAccountId(), user);
    }

    //list users after sorted
    listAllUsers();

  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User> 
  {
    //compare 2 users by their Names
    public int compare(User user1, User user2){
      return user1.getName().compareToIgnoreCase(user2.getName());
    }
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet()
  {
    //sort users by amount in wallet
    ArrayList<User> userList = new ArrayList<>(users.values());
    Collections.sort(userList, new UserWalletComparator());
    users.clear();
    for (User user : userList){
      users.put(user.getAccountId(), user);
    }

    //list users after sorted
    listAllUsers();
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    //compare two users by amount in wallet
    public int compare(User user1, User user2){
      return Double.compare(user1.getWallet(), user2.getWallet());
    }
  }

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance()
  {
    //sort requests by distance
    for (Queue<TMUberService> queue : serviceRequests){
      Collections.sort((LinkedList<TMUberService>) queue, new requestComparator());
    }
    
    //list requests after sorted
    listAllServiceRequests();
  }

  private class requestComparator implements Comparator<TMUberService>
  {
    //compare two services by distance
    public int compare(TMUberService service1, TMUberService service2){
      return Integer.compare(service2.getDistance(), service1.getDistance());
    }
  }

  
}
class DriverNotFoundException extends RuntimeException
  {
    public DriverNotFoundException(){}
    public DriverNotFoundException(String message){
      super(message);
    }
  }

  class ServiceNotFoundException extends RuntimeException
  {
    public ServiceNotFoundException(){}
    public ServiceNotFoundException(String message){
      super(message);
    }
  }
  
  class DriverExistsException extends RuntimeException
  {
    public DriverExistsException(){}
    public DriverExistsException(String message){
      super(message);
    }
  }

  class UserExistsException extends RuntimeException
  {
    public UserExistsException(){}
    public UserExistsException(String message){
      super(message);
    }
  }

  class InvalidAddressException extends RuntimeException
  {
    public InvalidAddressException(){}
    public InvalidAddressException(String message){
      super(message);
    }
  }

  class InsufficientTravelException extends RuntimeException
  {
    public InsufficientTravelException(){}
    public InsufficientTravelException(String message){
      super(message);
    }
  }

  class ExistingRequestException extends RuntimeException
  {
    public ExistingRequestException(){}
    public ExistingRequestException(String message){
      super(message);
    }
  }

  class EmptyZoneException extends RuntimeException
  {
    public EmptyZoneException(){}
    public EmptyZoneException(String message){
      super(message);
    }
  }