
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code (i.e. check your output with the
    // sample output provided). 
    public static ArrayList<User> loadPreregisteredUsers(String fileName) throws IOException
    {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()){
            String userID = generateUserAccountId(users);
            String name = scan.nextLine();
            String address = scan.nextLine();
            double wallet = Double.parseDouble(scan.nextLine());

            User user = new User(userID, name, address, wallet);
            users.add(user);

        }
        scan.close();
        return users;


    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    public static ArrayList<Driver> loadPreregisteredDrivers(String fileName) throws IOException
    {
        ArrayList<Driver> drivers = new ArrayList<>();
        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()){
            String driverID = generateDriverId(drivers);
            String name = scan.nextLine();
            String carModel = scan.nextLine();
            String carLicense = scan.nextLine();
            String address = scan.nextLine();
            
            int zone = CityMap.getCityZone(address);

            Driver driver = new Driver(driverID, name, carModel, carLicense, address, zone);
            drivers.add(driver);
        }
        scan.close();
        return drivers;
    }
}


