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
    
    // Read int with validation
    public static int getInt(String message, int min, int max){
           
        while(true){
         System.out.print(message);
         
         String input = ScannerUtility.scanner.nextLine().trim();
         
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
    
    // Read string with validation
    public static String getString(String message){
    
        while(true){
            System.out.println(message);
            
            String input = ScannerUtility.scanner.nextLine().trim();
            
            // Check empty input
            if (input.isEmpty()){
                System.out.println("Input cannot be empty");
                continue;
            }
            return input;
        }
    }
    
    // YesNo confirmation 
    public static boolean comfirmYesNo(String message){
    
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
        System.out.println("\nPress ENTER to continue...");
        ScannerUtility.scanner.nextLine();
    }
    
}
