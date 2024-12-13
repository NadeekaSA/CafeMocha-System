package com.mycompany.cafemochasystem;

import java.io.*;
import java.util.Scanner;

public class CafeMochaSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Main application loop
        while (true) {
            // User must log in before accessing the menu options
            boolean isLoggedIn = false;
            while (!isLoggedIn) {
                System.out.println("   ******        ****         *******      *******");
                System.out.println("  *            *    *        *            *");
                System.out.println(" *           *******        *******      *******");
                System.out.println("*          *       *       *            *");
                System.out.println("******    *         *      *            *******");
                System.out.println();
                System.out.println("     *        *    *******   ******    *    *      *");
                System.out.println("    * *     **    *     *   *         *    *     * *");
                System.out.println("   *  *   * *    *     *   *         ******    ****");
                System.out.println("  *   * *  *    *     *   *         *    *   *    *");
                System.out.println(" *    *   *    *******   ******    *    *  *      *");
                System.out.println();
                System.out.println("|-----------------------| Welcome To Cafe Mocha  |-----------------------|");
                System.out.println(" *                          1. Login                                    *  ");
                System.out.println("   *                        2. Exit                                  *   ");
                System.out.println("     ** |---------------------------------------------------------| **      ");
                System.out.print("Choose an option: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.next();  // Clear the invalid input
                    continue;
                }

                switch (choice) {
                    case 1:
                        isLoggedIn = login();
                        break;
                    case 2:
                        System.out.println("Exiting the system...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }

            // Main menu after login
            while (isLoggedIn) {
                System.out.println("    +=+=+=+=+=   <|Cafe Mocha System Menu|>   =+=+=+=+=+        ");
                System.out.println();
                System.out.println(" **                   1. Add Customer Order             ** ------/   ");
                System.out.println();
                System.out.println("   **                 2. View Order Details           **      /");
                System.out.println();
                System.out.println("      **              3. Calculate and Print Bill   **      / ");
                System.out.println();
                System.out.println("        **            4. Add Items to Menu        **------/               ");
                System.out.println();
                System.out.println("           **         5. User Manual           **                     ");
                System.out.println();
                System.out.println("              **      6. Logout            **                         ");
                System.out.println();
                System.out.println("                **    7. Exit           **                            ");
                System.out.println();
                System.out.println("     --          +=+=+=+=+=-----=+=+=+=+=               --     ");
                System.out.print("Choose an option: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.next();  // Clear the invalid input
                    continue;
                }

                switch (choice) {
                    case 1:
                        addOrder();
                        break;
                    case 2:
                        displayOrders();
                        break;
                    case 3:
                        calculateAndPrintBill();
                        break;
                    case 4:
                        addMenuItem();
                        break;
                    case 5:
                        userManual();
                        break;
                    case 6:
                        System.out.println("Logging out...");
                        isLoggedIn = false;  // Set isLoggedIn to false to exit the menu loop
                        break;
                    case 7:
                        System.out.println("Exiting the system...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        }
    }

    // Login function using users.txt with added validations
    public static boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty. Please try again.");
            return false;
        }

        File userFile = new File("users.txt");
        if (!userFile.exists()) {
            System.out.println("User file not found. Please contact the system administrator.");
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split("/");
                if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                    System.out.println("Login successful!");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }

        System.out.println("Login failed! Please check your username and password.");
        return false;
    }

    // Function to add a new order with input validation
    public static void addOrder() {
        Scanner scanner = new Scanner(System.in);
        
        // Order number input with validation
        System.out.print("Enter order number: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid order number.");
            scanner.next();  // Clear the invalid input
        }
        int orderNumber = scanner.nextInt();
        scanner.nextLine();  // Clear the newline character

        // Customer name input
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();

        // Address input
        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();

        // Phone number input with validation loop
        String phone;
        while (true) {
            System.out.print("Enter phone number (10 digits): ");
            phone = scanner.nextLine().trim();
            if (phone.matches("\\d{10}")) {
                break;  // Valid phone number, exit the loop
            } else {
                System.out.println("Invalid phone number! Please enter a 10-digit phone number.");
            }
        }

        // Order details input
        System.out.print("Enter order details (item:quantity;item:quantity): ");
        String orderDetails = scanner.nextLine().trim();
        if (orderDetails.isEmpty()) {
            System.out.println("Order details cannot be empty!");
            return;
        }

        // Write order details to file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("orders.txt", true))) {
            bw.write(orderNumber + "/" + customerName + "/" + address + "/" + phone + "/" + orderDetails);
            bw.newLine();
            System.out.println("Order added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to orders file: " + e.getMessage());
        }
    }

    // Function to display all orders
 public static void displayOrders() {
    boolean hasOrders = false;

    try (BufferedReader br = new BufferedReader(new FileReader("orders.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            hasOrders = true;
            String[] order = line.split("/");
            System.out.println("----------------------------");
            System.out.println("Order Number: " + order[0]);
            System.out.println("Customer Name: " + order[1]);
            System.out.println("Address: " + order[2]);
            System.out.println("Phone: " + order[3]);
            System.out.println("Order Details(item+quantity): " + order[4]);
            System.out.println("----------------------------");
        }
    } catch (IOException e) {
        System.out.println("Error reading orders file: " + e.getMessage());
    }

    if (!hasOrders) {
        System.out.println("There are no orders available!.");
    }
}

    // Function to calculate and print a bill with a discount if applicable, and save it to bill.txt
  public static void calculateAndPrintBill() {
    Scanner scanner = new Scanner(System.in);
    int orderNumber = -1;

    // Input validation for order number
    while (true) {
        System.out.print("Enter order number to calculate bill: ");
        if (scanner.hasNextInt()) {
            orderNumber = scanner.nextInt();
            break; // Exit the loop if the input is a valid integer
        } else {
            System.out.println("Invalid input! Please enter a valid order number (numeric value).");
            scanner.next(); // Clear the invalid input
        }
    }

    double totalAmount = 0.0;
    boolean discountApplied = false;

    try (BufferedReader br = new BufferedReader(new FileReader("orders.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] order = line.split("/");
            if (Integer.parseInt(order[0]) == orderNumber) {
                String[] items = order[4].split(";");
                for (String item : items) {
                    String[] itemDetails = item.split(":");
                    double price = getItemPrice(itemDetails[0]);
                    int quantity = Integer.parseInt(itemDetails[1]);
                    totalAmount += price * quantity;

                    if (quantity > 3) {
                        discountApplied = true;
                    }
                }

                if (discountApplied) {
                    totalAmount *= (1 - 10.0 / 100); // Apply 10% discount
                    System.out.println("A 10% discount has been applied.");
                }

                System.out.println("Total Bill: Rs:" + totalAmount);

                // Write order number and total amount to bill.txt
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("bills.txt", true))) {
                    bw.write("Order Number: " + orderNumber + " - Total Amount: Rs:" + totalAmount);
                    bw.newLine();
                    System.out.println("Bill Printed Successfully");
                } catch (IOException e) {
                    System.out.println("Error writing to bill file: " + e.getMessage());
                }

                return; // Exit the method after processing the order
            }
        }
        System.out.println("Order number not found.");
    } catch (IOException e) {
        System.out.println("Error reading orders file: " + e.getMessage());
    }
}
    // Helper function to get item price from menu
    public static double getItemPrice(String itemName) {
        try (BufferedReader br = new BufferedReader(new FileReader("menu.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] menuItem = line.split("/");
                if (menuItem[0].equals(itemName)) {
                    return Double.parseDouble(menuItem[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading menu file: " + e.getMessage());
        }
        return 0.0;
    }

    // Function to add a new menu item
    public static void addMenuItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("menu.txt", true))) {
            bw.write(itemName + "/" + description + "/" + price + "/" + category);
            bw.newLine();
            System.out.println("Menu item added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to menu file: " + e.getMessage());
        }
    }

    // Function to display help
    public static void userManual() {
    System.out.println("========================== Welcome to the Cafe Management System User Manual ==========================");
    System.out.println();
    System.out.println("Below are the options available to you:");
    System.out.println("1. Login:");
    System.out.println("   - Description: Log into the system using your credentials.");
    System.out.println("   - Instructions: Enter your username and password when prompted.");
    System.out.println("   - Example: If your username is 'john_doe' & password is '1234', simply type it in and press Enter.");
    System.out.println();
    System.out.println("2. Add New Customer Order:");
    System.out.println("   - Description: Record the details of a customer's order.");
    System.out.println("   - Instructions: Enter the customer's name, address, and specific order details.");
    System.out.println("   - Note: Ensure all mandatory fields are filled out before submitting.");
    System.out.println();
    System.out.println("3. Display Order Details:");
    System.out.println("   - Description: View all orders that have been recorded in the system.");
    System.out.println("   - Instructions: Simply select this option to list all current orders.");
    System.out.println("   - Note: Orders are displayed in the order they were entered.");
    System.out.println();
    System.out.println("4. Calculate and Print Bill:");
    System.out.println("   - Description: Calculate the total bill for a specific order.");
    System.out.println("   - Instructions: Enter the order ID to retrieve the bill.");
    System.out.println("   - Note: A 10% discount is applied automatically if any item in the order has a quantity greater than 3.");
    System.out.println("   - Example: If order ID 102 has 4 coffees, the discount is applied.");
    System.out.println();
    System.out.println("5. Add New Items to Menu:");
    System.out.println("   - Description: Update the café menu with new items.");
    System.out.println("   - Instructions: Enter the item name, category, and price.");
    System.out.println("   - Note: Ensure no duplicate items are entered.");
    System.out.println();
    System.out.println("6. Logout:");
    System.out.println("   - Description: Log out of your current session.");
    System.out.println("   - Instructions: Select this option to safely log out and return to the login screen.");
    System.out.println("   - Note: Any unsaved changes will be lost if you log out without saving.");
    System.out.println();
    System.out.println("7. Exit:");
    System.out.println("   - Description: Safely exit the system.");
    System.out.println("   - Instructions: Confirm your intent to exit when prompted.");
    System.out.println();
    System.out.println("For more information on each option, please refer to the user manual or contact support.");
    System.out.println("==================================================================================================================");
}

}
