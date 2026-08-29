# M Hotel Reservation System
A console-based hotel management system for M Hotel, implementing Abstract Data Types (ADTs) for room booking, VIP allocation, housekeeping, and front-desk operations.

## How to Run
1. Open project in **NetBeans IDE**
2. Press `F6` or click **Run > Run Project**
3. Main menu will appear with 4 modules

**Java Version:** JDK 21 or higher

## Data Files
All data files are located in the `data/` folder. Each line uses `|` as delimiter.

### Room.txt
```
RoomNumber|RoomType|Floor|Capacity|Rate|Status
```
**Example:**
```
101|Single|1|2|350.0|Available
201|Double|2|2|420.0|Occupied
403|Suite|5|4|1200.0|Reserved
```
**Room Types:** Single, Double, Deluxe, Family, Suite, VIP
**Status:** Available, Reserved, Occupied, Cleaning, Maintenance

### Guest.txt
```
ConfirmationNumber|GuestName|Phone|Email|BookingID|RoomNumber|CheckIn|CheckOut|Status
```
**Example:**
```
10000001|Carson Phoon|0123456789|carson@gmail.com|BK0001|101|2026-07-01|2026-07-03|Checked In
```
**Status:** Reserved, Checked In, Checked Out

### Booking.txt
```
BookingID|ConfirmationNumber|RoomNumber|RoomType|Guests|BookingDate|CheckIn|CheckOut|Amount|Status
```
**Example:**
```
BK0001|10000001|101|Single|2|2026-06-28|2026-07-01|2026-07-03|700.0|Checked In
```
**Status:** Pending, Confirmed, Checked In, Checked Out

### Member.txt
```
MemberID|ConfirmationNumber|Level|Points|JoinDate|Status
```
**Example:**
```
MB0001|10000001|Platinum|15000|2025-06-15|Active
```
**Levels:** Bronze, Silver, Gold, Elite, Diamond, Platinum

### Comment.txt
```
CommentID|ConfirmationNumber|RoomNumber|Type|Description|Status|Date
```
**Example:**
```
CM0001|10000001|101|Complaint|Air conditioner not working|Pending|2026-07-01
```
**Types:** Comment, Complaint
**Status:** Pending, Resolved, Ignored

## Modules

| Module | Menu | Description |
|--------|------|-------------|
| Walk-In Registration | 1 | Register walk-in guests, manage pending bookings |
| VIP Room Allocation | 2 | Priority queue for loyalty members |
| Housekeeping & Task Log | 3 | Room cleaning status with rollback function |
| Front Desk Service | 4 | Search guests, view reports, manage comments |

## ADTs Used

- **ArrayListADT** - Linear list for bookings and room assignments
- **LinkedStack** - Stack for housekeeping rollback history
- **GuestBST** - Binary Search Tree for guest search by confirmation number
- **VipBST** - Priority BST for VIP room allocation by membership tier