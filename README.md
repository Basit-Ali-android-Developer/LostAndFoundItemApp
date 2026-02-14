# Lost & Found Item App

An Android application to report, manage, and claim lost and found items within an institute.

## Overview
This project is built in **Java** for Android, with a backend in **.NET C#** using **SQL Server**.  
The system allows users to report lost or found items, matches items automatically, notifies users and admin, and includes an auction system for unclaimed items.

---

## Features

### User Features
- **User Registration & Login**: Users create accounts to report and track items.
- **Report Lost Items**: Add lost item details (name, category, description, contact info).
- **Report Found Items**: Add found items to the system.
- **Automated Matching**: System matches lost items to found items using similarity; notifications are sent if match > 70%.
- **Claim Items**: Users can claim matched found items.
- **Live Auctions**: If found items remain unclaimed for 6 months, the admin sets up auctions; users can bid on items.
- **Compensation Option**: Admin can directly compensate users with old found items that match their lost items.

### Admin Features
- **Verify Found Items**: Admin verifies submitted found items before adding to the system.
- **Notifications**: Receives notifications about matched items, auctions, and claims.
- **Deliver Items**: Approves claims and delivers items to rightful owners.
- **Auction Management**: Sets date and time for live auctions for unclaimed items.

---

## Tech Stack
- **Android (Frontend)**: Java, Android Studio
- **Backend**: .NET C#
- **Database**: SQL Server
- **Networking**: REST API integration
- **Architecture**: MVVM / Standard Android structure
- **Notifications**: Push notifications for item matches and auctions

---

## How It Works
1. Users register and login.
2. Users can submit **lost** or **found** item reports.
3. Admin verifies found items before adding them to the system.
4. The system automatically **matches lost and found items**:
    - If a match is >70%, **notifications** are sent to the user and admin.
5. Users can **claim matched items**, and admin approves delivery.
6. Unclaimed found items for 6 months → **admin can initiate auctions**.
7. Users participate in auctions → **highest bidder gets the item**.
8. Admin can use **compensation** to return older items to rightful owners.

---

## Installation
1. Clone the repository:
```bash
git clone https://github.com/Basit-Ali-android-Developer/LostAndFoundItemApp.git
