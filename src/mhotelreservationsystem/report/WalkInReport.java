/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.*;
import mhotelreservationsystem.repository.*;

/**
 * Walk-In Reports: Daily Summary and Revenue Analysis
 */
public class WalkInReport {
    
    private GuestRepository guestRepository;
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private ArrayListADT<Booking> pendingBookings;
    
    public WalkInReport(GuestRepository guestRepository,
                        BookingRepository bookingRepository,
                        RoomRepository roomRepository,
                        ArrayListADT<Booking> pendingBookings){
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.pendingBookings = pendingBookings;
    }
    
    // REPORT 1: Daily Walk-In Registration Report
    public void generateDailyWalkInReport(){
        
        LocalDate today = LocalDate.now();
        int totalRegistrations = 0;
        double totalRevenue = 0;
        int avgGuests = 0;
        
        // Collect all bookings made today
        int count = 0;
        for(int i = 0; i < bookingRepository.getTotalBooking(); i++){
            Booking b = bookingRepository.getBooking(i);
            
            // Assume walk-in bookings have status CHECKED_IN (made today)
            if(b.getBookingDate().equals(today) && 
               b.getBookingStatus() == BookingStatus.CHECKED_IN){
                totalRegistrations++;
                totalRevenue += b.getTotalAmount();
                avgGuests += b.getNumberOfGuests();
                count++;
            }
        }
        
        System.out.println();
        System.out.println("========================================================");
        System.out.println("           DAILY WALK-IN REGISTRATION REPORT");
        System.out.println("  Date: " + today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("========================================================");
        
        System.out.println();
        System.out.println("Summary:");
        System.out.println("  Total Walk-In Registrations : " + totalRegistrations);
        System.out.println("  Total Revenue              : RM " + String.format("%.2f", totalRevenue));
        
        if(totalRegistrations > 0){
            System.out.println("  Average Guests per Booking : " + (avgGuests / totalRegistrations));
            System.out.println("  Average Revenue per Booking: RM " + String.format("%.2f", totalRevenue / totalRegistrations));
        }
        
        System.out.println("  Pending Confirmations      : " + pendingBookings.getNumberOfEntries());
        
        System.out.println();
        System.out.println("Booking Details:");
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-8s %-10s %-5s %-6s %-10s %-10s%n",
                "Book ID", "Confirm", "Room", "Guest", "Check-In", "Amount");
        System.out.println("-----------------------------------------------------");
        
        for(int i = 0; i < bookingRepository.getTotalBooking(); i++){
            Booking b = bookingRepository.getBooking(i);
            
            if(b.getBookingDate().equals(today) && 
               b.getBookingStatus() == BookingStatus.CHECKED_IN){
                System.out.printf("%-8s %-10s %-5d %-6d %-10s RM%-9.2f%n",
                        b.getBookingID(),
                        b.getConfirmationNumber(),
                        b.getRoomNumber(),
                        b.getNumberOfGuests(),
                        b.getCheckInDate(),
                        b.getTotalAmount());
            }
        }
        
        System.out.println("========================================================");
    }
    
    // REPORT 2: Walk-In Revenue Analysis by Room Type
    public void generateWalkInRevenueAnalysis(){
        
        LocalDate today = LocalDate.now();
        
        // Collect revenue by room type
        java.util.Map<RoomType, Double> revenueByType = new java.util.HashMap<>();
        java.util.Map<RoomType, Integer> countByType = new java.util.HashMap<>();
        
        for(int i = 0; i < bookingRepository.getTotalBooking(); i++){
            Booking b = bookingRepository.getBooking(i);
            
            if(b.getBookingDate().equals(today) && 
               b.getBookingStatus() == BookingStatus.CHECKED_IN){
                
                RoomType type = b.getRoomType();
                revenueByType.put(type, revenueByType.getOrDefault(type, 0.0) + b.getTotalAmount());
                countByType.put(type, countByType.getOrDefault(type, 0) + 1);
            }
        }
        
        System.out.println();
        System.out.println("========================================================");
        System.out.println("      WALK-IN REVENUE ANALYSIS BY ROOM TYPE");
        System.out.println("  Date: " + today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("========================================================");
        
        if(revenueByType.isEmpty()){
            System.out.println("No walk-in bookings recorded for today.");
        } else {
            
            System.out.println();
            System.out.printf("%-10s %-8s %-12s %-15s%n",
                    "Room Type", "Count", "Total Revenue", "Avg per Booking");
            System.out.println("-----------------------------------------------------");
            
            double totalRevenue = 0;
            int totalCount = 0;
            
            for(RoomType type : revenueByType.keySet()){
                double revenue = revenueByType.get(type);
                int count = countByType.get(type);
                double avgPerBooking = revenue / count;
                
                System.out.printf("%-10s %-8d RM%-11.2f RM%-14.2f%n",
                        type,
                        count,
                        revenue,
                        avgPerBooking);
                
                totalRevenue += revenue;
                totalCount += count;
            }
            
            System.out.println("-----------------------------------------------------");
            System.out.printf("%-10s %-8d RM%-11.2f RM%-14.2f%n",
                    "TOTAL",
                    totalCount,
                    totalRevenue,
                    totalRevenue / totalCount);
        }
        
        System.out.println("========================================================");
    }
    
}
