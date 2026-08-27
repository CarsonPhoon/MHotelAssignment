/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.Room;
import mhotelreservationsystem.entity.RoomStatus;
import mhotelreservationsystem.entity.RoomType;
import mhotelreservationsystem.utility.FilePath;
import mhotelreservationsystem.utility.FileUtility;
/**
 *
 * @author phoon
 */
public class RoomRepository{

    // Use an ArrayList to store room data
    private ArrayListADT<Room> rooms;
    
    public RoomRepository() {
        
        rooms = new ArrayListADT<Room>();
        
        if(FileUtility.fileExists(FilePath.ROOM_FILE)){
            loadFromFile();
        }
       }

        // RU operation
        public Room getRoom(int index){
            return rooms.get(index);
        }
        
        public int getTotalRoom(){
            return rooms.getNumberOfEntries();
        }
        
        // Search for a room – using linear search
        public Room searchRoom(int roomNumber){
            for(int i = 0; i < rooms.getNumberOfEntries(); i++){
                Room r = rooms.get(i);
                if(r.getRoomNumber() == roomNumber){
                    return r;
                }
            }
            return null;
        }
        
        public boolean updateRoom(Room room){
            for(int i = 0; i < rooms.getNumberOfEntries(); i++){
                Room r = rooms.get(i);
                if(r.getRoomNumber() == room.getRoomNumber()){
                    rooms.replace(i, room);
                    saveToFile();
                    return true;
                }
            }
            return false;
        }
        
        // Load data from txt file
        private void loadFromFile(){
            
            try{
                BufferedReader reader = FileUtility.openReader(FilePath.ROOM_FILE);
                String line;
                
                while((line = reader.readLine()) != null){
                    if(line.trim().isEmpty()){
                        continue;
                    }
                    
                    Room room = convertToRoom(line);
                    
                    if(room != null) {
                        rooms.add(room);
                    }
                }
                reader.close();
            } catch(IOException e){
                System.out.println("Error loading Room.txt");
            }
        }
        
         // Save data to txt file
        private void saveToFile(){
            
            try{
                BufferedWriter writer = FileUtility.openWriter(FilePath.ROOM_FILE);
                
                for(int i = 0; i < rooms.getNumberOfEntries(); i++){
                    writer.write(convertToString(rooms.get(i)));
                    
                    writer.newLine();
                }
                
                writer.close();
            }catch(IOException e){
                System.out.println("Error saving ROOM.txt");
            }
        }
        
        private Room convertToRoom(String line){
            String[] data = line.split("\\|");

            if(data.length != 6){
                return null;
            }

            return new Room(

                    Integer.parseInt(data[0]),             // Room No
                    RoomType.fromDisplayName(data[1]),     // Room Type
                    Integer.parseInt(data[2]),             // Floor
                    Integer.parseInt(data[3]),             // Capacity
                    Double.parseDouble(data[4]),           // Room Price
                    RoomStatus.fromDisplayName(data[5])    // Room Status
            );
        }
        
        private String convertToString(Room room){
            return room.getRoomNumber() + "|" +
                   room.getRoomType() + "|" +
                   room.getFloor() + "|" +
                   room.getCapacity() + "|" +
                   room.getRoomRate() + "|" +
                   room.getStatus();
        }

    public String updateStatus(String roomNumber, mhotelreservationsystem.entity.RoomStatus newStatus) {
        for (int i = 0; i < rooms.getNumberOfEntries(); i++) {
            mhotelreservationsystem.entity.Room room = rooms.get(i);
            if (String.valueOf(room.getRoomNumber()).equals(roomNumber)) {
                
                if (!room.getStatus().name().equalsIgnoreCase("AVAILABLE")) {
                    return "NOT_AVAILABLE";
                }
                
                room.setStatus(newStatus);
                rooms.replace(i + 1, room);
                saveToFile();
                return "SUCCESS";
            }
        }
        return "NOT_FOUND";
    }
}
