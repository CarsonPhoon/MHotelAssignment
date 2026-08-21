package mhotelreservationsystem.boundary.frontdeskmodule;

import mhotelreservationsystem.boundary.Navigable;
import mhotelreservationsystem.control.FrontDeskControl;
import mhotelreservationsystem.report.FrontDeskReport;
import mhotelreservationsystem.utility.ScannerUtility;

public class ReportManagementUI implements Navigable {

    private static final String Y = "\033[33m";
    private static final String R = "\033[0m";

    private FrontDeskControl control;
    private FrontDeskReport report;

    public ReportManagementUI() {
        control = new FrontDeskControl();
        report = new FrontDeskReport(
            control.getGuestRepository(),
            control.getBookingRepository(),
            control.getRoomRepository(),
            control.getMemberRepository()
        );
    }

    @Override
    public void display() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         " + Y + "REPORT MANAGEMENT" + R);
        System.out.println("========================================");
        System.out.println("1. Daily Room Occupancy Report");
        System.out.println("2. Daily Guest Check-In/Check-Out Report");
        System.out.println("0. Back");
        System.out.println("========================================");
    }

    @Override
    public Navigable handleChoice(int choice) {
        switch (choice) {
            case 1: generateRoomOccupancyReport(); break;
            case 2: generateGuestCheckInOutReport(); break;
        }
        return null;
    }

    @Override
    public int getMaxChoice() {
        return 2;
    }

    private void generateRoomOccupancyReport() {
        report.generateRoomOccupancyReport();
        System.out.print("\nPress ENTER to return to menu... ");
        ScannerUtility.scanner.nextLine();
    }

    private void generateGuestCheckInOutReport() {
        report.generateGuestCheckInOutReport();
        System.out.print("\nPress ENTER to return to menu... ");
        ScannerUtility.scanner.nextLine();
    }
}
