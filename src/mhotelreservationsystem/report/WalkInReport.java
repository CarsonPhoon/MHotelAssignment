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
 * @author xb
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
            
            if(b.getBookingDate().equals(today) && 
               (b.getBookingStatus() == BookingStatus.CHECKED_IN ||
                b.getBookingStatus() == BookingStatus.CHECKED_OUT)){
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
                    (b.getBookingStatus() == BookingStatus.CHECKED_IN ||
                     b.getBookingStatus() == BookingStatus.CHECKED_OUT)){
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
        
        // Collect revenue by room type using parallel ADT lists (no Java Collections allowed)
        ArrayListADT<RoomType> roomTypes = new ArrayListADT<>();
        ArrayListADT<Double> revenueByType = new ArrayListADT<>();
        ArrayListADT<Integer> countByType = new ArrayListADT<>();
        
        for(int i = 0; i < bookingRepository.getTotalBooking(); i++){
            Booking b = bookingRepository.getBooking(i);
            
                if(b.getBookingDate().equals(today) && 
                    (b.getBookingStatus() == BookingStatus.CHECKED_IN ||
                     b.getBookingStatus() == BookingStatus.CHECKED_OUT)){
                
                RoomType type = b.getRoomType();

                int index = -1;
                for(int j = 0; j < roomTypes.getNumberOfEntries(); j++){
                    if(roomTypes.get(j) == type){
                        index = j;
                        break;
                    }
                }

                if(index == -1){
                    roomTypes.add(type);
                    revenueByType.add(b.getTotalAmount());
                    countByType.add(1);
                } else {
                    revenueByType.replace(index, revenueByType.get(index) + b.getTotalAmount());
                    countByType.replace(index, countByType.get(index) + 1);
                }
            }
        }
        
        System.out.println();
        System.out.println("========================================================");
        System.out.println("      WALK-IN REVENUE ANALYSIS BY ROOM TYPE");
        System.out.println("  Date: " + today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("========================================================");
        
        if(roomTypes.isEmpty()){
            System.out.println("No walk-in bookings recorded for today.");
        } else {
            
            System.out.println();
            System.out.printf("%-10s %-8s %-12s %-15s%n",
                    "Room Type", "Count", "Total Revenue", "Avg per Booking");
            System.out.println("-----------------------------------------------------");
            
            double totalRevenue = 0;
            int totalCount = 0;
            
            for(int i = 0; i < roomTypes.getNumberOfEntries(); i++){
                RoomType type = roomTypes.get(i);
                double revenue = revenueByType.get(i);
                int count = countByType.get(i);
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

    public void generateDailyCheckOutReport(){
        LocalDate today = LocalDate.now();
        int totalCheckOut = 0;
        double totalRevenue = 0;

        System.out.println();
        System.out.println("========================================================");
        System.out.println("              DAILY CHECK-OUT REPORT");
        System.out.println("  Date: " + today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("========================================================");

        System.out.println();
        System.out.println("Check-Out Details:");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-8s %-10s %-5s %-10s %-10s %-10s%n",
                "Book ID", "Confirm", "Room", "Check-In", "Check-Out", "Amount");
        System.out.println("----------------------------------------------------------------");

        for (int i = 0; i < bookingRepository.getTotalBooking(); i++) {
            Booking b = bookingRepository.getBooking(i);
            if (b.getBookingStatus() == BookingStatus.CHECKED_OUT && b.getCheckOutDate().equals(today)) {
                System.out.printf("%-8s %-10s %-5d %-10s %-10s RM%-9.2f%n",
                        b.getBookingID(),
                        b.getConfirmationNumber(),
                        b.getRoomNumber(),
                        b.getCheckInDate(),
                        b.getCheckOutDate(),
                        b.getTotalAmount());
                totalCheckOut++;
                totalRevenue += b.getTotalAmount();
            }
        }

        System.out.println("----------------------------------------------------------------");
        System.out.println("Total Check-Out Today : " + totalCheckOut);
        System.out.println("Total Realized Revenue: RM " + String.format("%.2f", totalRevenue));
        if (totalCheckOut > 0) {
            System.out.println("Average Revenue/Stay  : RM " + String.format("%.2f", totalRevenue / totalCheckOut));
        }
        System.out.println("========================================================");
    }
    
}
