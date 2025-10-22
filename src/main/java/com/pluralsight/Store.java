package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: id|name|price)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("==============================");
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    /**
     * Reads product data from a file and populates the inventory list.
     * File format (pipe-delimited):
     * id|name|price
     * <p>
     * Example line:
     * A17|Wireless Mouse|19.99
     */
    public static void loadInventory(String fileName, ArrayList<Product> inventory)   {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split("\\|");
                String sku = parts[0];
                String productName = parts[1];
                double price = Double.parseDouble(parts[2]);
                inventory.add(new Product(sku,productName,price));
            } br.close();
        } catch (Exception e) {
            System.out.println("Something went wrong while loading the inventory");
        }
    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        System.out.println("SKU|Product name|Price");
        for (Product product : inventory) {
            System.out.println(product);
        }
        int choice = -1;
        boolean running = true;
        while (running) {
            System.out.println("============================" +
                    "\n1.Add product to the cart." +
                    "\n2.Return to the home screen.");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1 or 2");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter SKU of the product: ");
                    String id = scanner.nextLine().trim();
                    int i = 0;
                    for (Product product : inventory) {
                        if (id.equalsIgnoreCase(product.getSku())) {
                            i ++;
                            cart.add(product);
                            System.out.printf("You added %s to the cart", product);
                            System.out.println("\n");
                        }
                    }
                    if (i == 0) {
                        System.out.println("We don't carry this product");
                    }
                }
                case 2 -> running = false;
                default -> System.out.println("Enter 1 or 2");
            }

        }
    }
    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        // TODO:
        //   • list each product in the cart
        //   • compute the total cost
        //   • ask the user whether to check out (C) or return (X)
        //   • if C, call checkOut(cart, totalAmount, scanner)
        for (Product product : cart) System.out.println(product);
        double totalAmount = 0;
        for (Product product : cart) {
            totalAmount += product.getPrice();
        }
        System.out.println("Your total is " + totalAmount);

        String choice ="";
        while (!choice.equalsIgnoreCase("x")) {
            System.out.println("\n============================");
            System.out.println("C - Check out");
            System.out.println("X - Return");
            System.out.print("Your choice: ");

            choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("c")) {
                checkOut(cart, totalAmount, scanner);
                break;
            } else if (choice.equalsIgnoreCase("x")) {
                break;
            } else {
                System.out.println("Please enter C or X");
            }
        }

    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        // TODO: implement steps listed above
        String confirm = "";
        while (!confirm.equalsIgnoreCase("x")) {
            //System.out.println("C - Confirm the check out");
            //System.out.println("X - Keep shopping");
            //confirm = scanner.nextLine();
            System.out.println("\n============================");
            System.out.println("C - Confirm the check out");
            System.out.println("X - Keep shopping");
            System.out.print("Your choice: ");

            confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("c")) {
                System.out.println("Enter the payment amount:");
                double payment = 0;



                if (!scanner.hasNextDouble()) {
                    System.out.println("Please enter the amount");
                    scanner.nextLine();                 // discard bad input
                    continue;
                }
                payment = scanner.nextDouble();
                scanner.nextLine();

                if (payment > totalAmount) {
                    double change = payment - totalAmount;
                    System.out.println("========================");
                    System.out.println("Your receipt");
                    for (Product product : cart) {
                        System.out.println(product);
                    }
                    System.out.println("Total amount is " + totalAmount);
                    System.out.printf("Your change is %.2f",change);
                    cart.clear();
                    break;
                }
                else {
                    System.out.println("Insufficient funds");
                }
            } else {
                System.out.println("Please enter C or X");
            }
        }
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        for (Product product : inventory) {
            
        }
        return null;
    }
}





