/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.utility;


import java.io.*;
/**
 *
 * @author phoon
 */
public class FileUtility {
    
    // Open Reader
    public static BufferedReader openReader(String filePath)
            throws IOException {

        return new BufferedReader(
                new FileReader(filePath));

    } 
            
    // Open Writer
    public static BufferedWriter openWriter(String filePath)
            throws IOException {

        return new BufferedWriter(
                new FileWriter(filePath));

    }
            
    // Open Append Writter
    public static BufferedWriter openAppendWriter(String filePath)
            throws IOException {

        return new BufferedWriter(
                new FileWriter(filePath, true));

    }
            
    // Check file exists
    public static boolean fileExists(String filePath){

        File file = new File(filePath);

        return file.exists();

    }   
}