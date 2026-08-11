/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.utility;

/**
 *
 * @author phoon
 */
public class Validation {
    
    // Read int with validation and if 0 will return
    public static int getIntOrReturn(String message, int min, int max){
           
        while(true){
         System.out.print(message);
         
         String input = ScannerUtility.scanner.nextLine().trim();
        
        // Check if it empty
        if(input.equals("0")){
            return 0;
        }
         
         // Check empty input
         if(input.isEmpty()){
             System.out.println("Input cannot be empty");
             continue;
         }
         
         // Check Interer only
         if(!input.matches("\\d+")){
             System.out.println("Invalid input! Please enter a whole number");
             continue;
         }
         
         int value = Integer.parseInt(input);
         
         // Check range
         if(value < min || value > max){
         
             System.out.println("Please enter a number between " + min + " and " + max + ".");
             continue;
         }
         return value;
        }
    }
    
    // Read double with validation
    public static double getDouble(String message, double min){
    
        while (true){
         System.out.println(message);
         
         String input = ScannerUtility.scanner.nextLine().trim();
         
         // Check empty input
         if(input.isEmpty()){
             System.out.println("Input cannot be empty");
             continue;
         }
         
         try{
             double value = Double.parseDouble(input);
             
             // Check range
             if(value < min){
                 System.out.println("Value cannot be less than " + min);
                 continue;
             }
             return value;
         } catch (NumberFormatException e){
             System.out.println("Invalid decimal number");
        }
        }
    }
    
    // Read string with validation and if 0 will return
    public static String getStringOrReturn(String message) {

        while (true) {

            System.out.print(message);

            String input = ScannerUtility.scanner.nextLine().trim();

            // Check if equal 0
            if (input.equals("0")) {
                return "0";
            }
            
            // Check if is empty
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }

            return input;
        }
    }
    
    // Get guest name with validation (no numbers, 2-100 chars)
    public static String getNameOrReturn(String message) {
        while (true) {
            System.out.print(message);
            String input = ScannerUtility.scanner.nextLine().trim();
            
            if (input.equals("0")) {
                return "0";
            }
            
            if (input.isEmpty()) {
                System.out.println("Name cannot be empty.");
                continue;
            }
            
            if (input.length() < 2 || input.length() > 100) {
                System.out.println("Name must be between 2 and 100 characters.");
                continue;
            }
            
            // Check if name contains numbers
            if (input.matches(".*\\d.*")) {
                System.out.println("Name cannot contain numbers.");
                continue;
            }
            
            return input;
        }
    }
    
    // YesNo confirmation 
    public static boolean confirmYesNo(String message){
    
        while(true){
            System.out.println(message + " (Y/N): ");
            
            String input = ScannerUtility.scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("Y")){
                return true;
            }
            
            if(input.equalsIgnoreCase("N")){
                return false;
            }
            
            System.out.println("Please enter Y or N");
        }
    }
    
    // Pause Screen 
    public static void pressEnterToContinue() {
        System.out.println("\nPress ENTER to return back...");
        ScannerUtility.scanner.nextLine();
    }
    
    // Validate phone number (7-15 digits)
    public static String getPhoneOrReturn(String message) {
        while (true) {
            System.out.print(message);
            String input = ScannerUtility.scanner.nextLine().trim();
            
            if (input.equals("0")) {
                return "0";
            }
            
            if (input.isEmpty()) {
                System.out.println("Phone number cannot be empty.");
                continue;
            }
            
            if (!input.matches("\\d{7,15}")) {
                System.out.println("Invalid phone number. Use 7-15 digits.");
                continue;
            }
            
            return input;
        }
    }
    
    // Validate email format
    public static String getEmailOrReturn(String message) {
        while (true) {
            System.out.print(message);
            String input = ScannerUtility.scanner.nextLine().trim();
            
            if (input.equals("0")) {
                return "0";
            }
            
            if (input.isEmpty()) {
                System.out.println("Email cannot be empty.");
                continue;
            }
            
            if (!input.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                System.out.println("Invalid email format.");
                continue;
            }
            
            return input;
        }
    }
    
}
