/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;



/**
 *
 * @author phoon
 */
public class HousekeepingUI {
    

    public HousekeepingUI(){

    }

    public void startModule(){
        menu();


    }

    public void menu(){
        System.out.println(" ---------------------------- ");
        System.out.println("|                            |");
        System.out.println("|        HOUSEKEEPING        |");
        System.out.println("|                            |");
        System.out.println(" ---------------------------- ");
        System.out.println("\n1. Update Cleaning Status");
        System.out.println("2. View Room Status");
        System.out.println("3. View Cleaning Task Log");
        System.out.println("The options choosed: ");

    }
   
}
