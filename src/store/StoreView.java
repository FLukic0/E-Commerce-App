package store;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Manages User Interface for the system
 * Text UI for this version
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.0
 */
public class StoreView {
    private StoreManager myStoreManager;
    private int cartID;

    public StoreView(StoreManager myStoreManager, int cartID) {
        this.myStoreManager = myStoreManager;
        this.cartID = cartID;
    }

    /**
     * Available commands for user to input when connected to a store
     */
    private static final String[] COMMANDS = {"browse", "viewCart", "add", "remove", "checkout", "help"};

    /**
     * Override= default to string method to return cart id
     * @return String, cartID
     */
    public String toString() {
        return String.valueOf(cartID);
    }

    /**
     * Display's the Store's inventory
     */
    public void browse() {
        myStoreManager.printInventory();
    }

    /**
     * Display's the User's cart
     */
    public void viewCart() {
        myStoreManager.getUserCarts().get(cartID).printCartInventory();
    }

    /**
     * Add a store.Product to the User's cart
     *
     * @param id int, store.Product id
     * @param amount int, amount to add to user
     */
    public void addToUser(int id, int amount) {
        if (!myStoreManager.addToCart(cartID, id, amount)) {
            System.out.println("Error: Please enter a valid amount of stock to add to your cart!");
        }
    }

    /**
     * Remove a store.Product from the user'sCart
     *
     * @param id int, store.Product id
     * @param amount int, amount to remove from user
     */
    public void removeFromUser(int id, int amount) {
        if (!myStoreManager.removeFromCart(cartID, id, amount)) {
            System.out.println("Error: Please enter a valid amount of stock to remove from your cart!");
        }
    }

    /**
     * Checkout User's cart
     *
     * @return boolean, false if user aborts checkout
     */
    public boolean checkout() {
        return myStoreManager.processTransaction(cartID);
    }

    /**
     * Print available commands for UI
     */
    public static void help() {
        System.out.print("Valid Commands: ");
        for (int i = 0; i < COMMANDS.length - 1; i++) {
            System.out.print(COMMANDS[i] + ", ");
        }
        System.out.println(COMMANDS[COMMANDS.length - 1]);
    }

    /**
     * Main Method for program execution
     * @param args Unused: no parameters utilized in this version
     */
    public static void main(String[] args) {
        StoreManager sm = new StoreManager();
        Scanner sc = new Scanner(System.in);
        ArrayList<StoreView> storeViews = new ArrayList<>();
        boolean exit = false;
        boolean exitCart;
        int i, id, amount;
        String s;


        storeViews.add(new StoreView(sm, sm.newShoppingCart())); //add one existing store.StoreView

        while (!exit) {
            System.out.println("Please select a command:");
            System.out.println("1: Select an existing StoreView");
            System.out.println("2: Create a new StoreView");
            System.out.println("-1: Exit \n");


            i = UserInput.getIntInput(-1, 2);

            if (i == 1) { //choose existing
                System.out.println("Please select a StoreView or choose -1 to exit.");
                System.out.println("Existing Store Views:");
                UserInput.printArray(storeViews);
                System.out.print("\n");
                i = UserInput.getIntInput(-1, storeViews.size() - 1);

                if (i == -1) {
                    break;
                } else {  //the juice
                    exitCart = false;
                    while (!exitCart) {
                        System.out.println("Enter a command or -1 to exit");
                        System.out.print(">>> ");
                        s = sc.nextLine();
                        switch (s) {
                            case "browse" -> storeViews.get(i).browse();
                            case "viewCart" -> storeViews.get(i).viewCart();
                            case "add" -> {
                                storeViews.get(i).browse();
                                System.out.println("Please enter the id of the Product you wish to add to your cart.");
                                id = UserInput.getIntInput();
                                System.out.println("Please enter the amount of the Product you wish to add to your cart.");
                                amount = UserInput.getIntInput();
                                storeViews.get(i).addToUser(id, amount);
                            }
                            case "remove" -> {
                                storeViews.get(i).viewCart();
                                System.out.println("Please enter the id of the Product you wish to remove from your cart.");
                                id = UserInput.getIntInput();
                                System.out.println("Please enter the amount of the Product you wish to remove from your cart.");
                                amount = UserInput.getIntInput();
                                storeViews.get(i).removeFromUser(id, amount);
                            }
                            case "checkout" -> storeViews.get(i).checkout();
                            case "-1" -> exitCart = true;
                            default -> help();
                        }
                    }

                }

            } else if (i == 2) { //add new
                storeViews.add(new StoreView(sm, sm.newShoppingCart()));
                System.out.println("New StoreView Created. \n");
            } else {
                exit = true;
            }
        }

    }

}