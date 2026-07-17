/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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
    private static final int MAX_ROOM = 100;
    private Room[] rooms;
    private int size;
    
    public RoomRepository() {
        
        rooms = new Room[MAX_ROOM];
        size = 0;
        
        if(FileUtility.fileExists(FilePath.ROOM_FILE)){
            loadFromFile();
        }
       }

        
        // CRUD Operation
        public boolean addRoom(Room room){
            if(size >= MAX_ROOM){
                return false;
            }
            
            rooms[size++] = room;
            
            saveToFile();
            
            return true;
        }
        
        public Room getRoom(int index){
            if(index < 0 || index >= size){
                return null;
            }
            
            return rooms[index];
        }
        
        public int getTotalRoom(){
            return size;
        }
        
        public Room searchRoom(int roomNumber){
            for(int i = 0; i< size; i++){
                if(rooms[i].getRoomNumber() == roomNumber){
                    return rooms[i];
                }
            }
            return null;
        }
        
        public boolean updateRoom(Room room){
            for(int i =0; i < size; i++){
                if(rooms[i].getRoomNumber() == room.getRoomNumber()){
                    rooms[i] = room;
                    saveToFile();
                    return true;
                }
            }
            return false;
        }
        
        public boolean removeRoom(int roomNumber){
            for (int i = 0; i < size; i++){
                if(rooms[i].getRoomNumber() == roomNumber){
                    for(int j = i; j < size - 1; j++){
                        rooms[j] = rooms[j+1];
                    }
                    
                    rooms[size - 1 ] = null;
                    size--;
                    saveToFile();
                    return true;
                }
            }
            return false;
        }
        
        public void displayAllRooms(){

            System.out.println();
            System.out.println("============================================================================");
            System.out.printf("%-8s %-10s %-6s %-8s %-10s %-15s%n",
                    "Room",
                    "Type",
                    "Floor",
                    "Capacity",
                    "Rate",
                    "Status");
            System.out.println("============================================================================");

            for(int i = 0; i < size; i++){

                System.out.println(rooms[i]);

            }

            System.out.println("============================================================================");
            System.out.println("Total Rooms : " + size);
        }
        
        private void loadFromFile(){
            size = 0;
            
            try{
                BufferedReader reader = FileUtility.openReader(FilePath.ROOM_FILE);
                
                String line;
                
                while((line = reader.readLine()) != null){
                    if(line.trim().isEmpty()){
                        continue;
                    }
                    
                    Room room = convertToRoom(line);
                    
                    if(room != null) {
                        rooms[size++] = room;
                    }
                }
            
                reader.close();
            } catch(IOException e){
                System.out.println("Error loading Room.txt");
            }
        }
        
        private void saveToFile(){
            try{
                BufferedWriter writer = FileUtility.openWriter(FilePath.ROOM_FILE);
                
                for(int i = 0; i < size; i++){
                    writer.write(convertToString(rooms[i]));
                    
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

                    Integer.parseInt(data[0]),
                    RoomType.valueOf(data[1]),
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]),
                    Double.parseDouble(data[4]),
                    RoomStatus.valueOf(data[5])
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
}