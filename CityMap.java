import java.util.Arrays;
import java.util.Scanner;

//Lala Hussein 501106657
// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3

    // get parts of address string
    String[] parts = getParts(address);

    // check if address is 3 parts
    if (parts.length != 3){
      return false;
    }

    //check if 1st part of address is two digits
    if ((!allDigits(parts[0])) || parts[0].length() != 2){
      return false;
    }

    //check if 2nd part of address is 3 characters long (1st,2nd,3rd...)
    if (parts[1].length() != 3){
      return false;
    }

    // initialize the number variable
    int num;

    //get 1st character of part 2 (the number)
    char firstNum = parts[1].charAt(0);

    //check if the first character in part is a number
    if (!Character.isDigit(firstNum)){
      return false;
    }
    
    //set number variable to first character variable if it is a digit
    num = Character.getNumericValue(firstNum);
    //check if number variable is between 1 and 9
    if (num < 1 || num > 9){
      return false;
    }

    //check if 3rd part of address is either Street or Avenue
    if (!(parts[2].equalsIgnoreCase("Street")) && !(parts[2].equalsIgnoreCase("Avenue"))){
      return false;
    }

    return true;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    //initialize the block coordinates
    int[] block = {-1, -1};
    // get parts of address
    String[] parts = getParts(address);
  
    //check if address valid
    if (validAddress(address)){
      //depending on if 3rd part of the address is street or avenue, add the avenue to index 0 and street to index 1 of block
      if (parts[2].equalsIgnoreCase("Avenue")){
        block[0] = Integer.parseInt(parts[1].substring(0, 1));
        block[1] = Integer.parseInt(parts[0].substring(0, 1));
      }else{
        block[0] = Integer.parseInt(parts[0].substring(0, 1));
        block[1] = Integer.parseInt(parts[1].substring(0, 1));
      }
    }

    // Fill in the code
    return block;
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  // Hint: be careful not to generate negative distances
  
  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to)
  {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part

    //get the cityblock from both addresses given
    int[] fromAdd = getCityBlock(from);
    int[] toAdd = getCityBlock(to);

    //calculate the distance between avenues
    int aveDist = Math.abs(fromAdd[0] - toAdd[0]);

    //calculate distance between streets
    int stDist = Math.abs(fromAdd[1] - toAdd[1]);

    // add both distance together to get total distance
    return aveDist + stDist;
  }

  public static int getCityZone(String address)
  {
    if (!validAddress(address)){
      return -1;
    }
    int[] addBlock = getCityBlock(address);
    int avenue = addBlock[0];
    int street = addBlock[1];

    if ((avenue >= 1 && avenue <= 5) && (street >= 6 && street <= 9)){
      return 0;
    }else if ((avenue >= 6 && avenue <= 9)&&(street >= 6 && street <= 9)){
      return 1;
    }else if ((avenue >= 6 && avenue <= 9)&&(street >= 1 && street <= 5)){
      return 2;
    }else if((avenue >= 1 && avenue <= 5)&&(street >= 1 && street <= 5)){
      return 3;
    }else{
      return -1;
    }



  }
}
