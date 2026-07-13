/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mhotelreservationsystem.adt;

import mhotelreservationsystem.entity.Guest;
/**
 *
 * @author phoon
 */
public interface GuestBSTInterface {
    
    boolean insert(Guest data);
    
    Guest search(String key);
    
    boolean remove(String key);
    
    boolean isEmpty();
    
    void clear();
    
    void inorderTraversal();
    
    int getSize();
}
