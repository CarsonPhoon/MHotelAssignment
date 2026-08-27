/*
 * GuestBSTInterface - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import mhotelreservationsystem.entity.Guest;
import mhotelreservationsystem.entity.GuestStatus;
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
    
    void saveToFile(BufferedWriter writer) throws IOException;
    
    int displayGuestByStatus(GuestStatus status);
    
    Iterator<Guest> getIterator();
}