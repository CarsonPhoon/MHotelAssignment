/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phoon
 */

package mhotelreservationsystem.boundary;

public interface Navigable {
    
    void display();
    
    Navigable handleChoice(int choice);
    
    int getMaxChoice();
    
}
