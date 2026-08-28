/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phoon
 */

package mhotelreservationsystem.boundary;

import mhotelreservationsystem.adt.LinkedStack;
import mhotelreservationsystem.utility.Validation;

public class Navigator {

    private LinkedStack<Navigable> navStack;

    public Navigator() {
        navStack = new LinkedStack<>();
    }

    // // Navigate to a new page
    public void navigateTo(Navigable page) {
        navStack.push(page);
        page.display();
    }

    // Return to the previous page
    public void goBack() {
        navStack.pop();
        if (!navStack.isEmpty()) {
            navStack.peek().display();
        }
    }
    
    // Current Page
    public void run() {
        while (!navStack.isEmpty()) {
            Navigable currentPage = navStack.peek();
            int maxChoice = currentPage.getMaxChoice();
            int choice = Validation.getIntOrReturn("Enter your choice: ", 0, maxChoice);

            if (choice == 0) {
                goBack();
                continue;
            }
            
            // // Handle user selection
            Navigable nextPage = currentPage.handleChoice(choice);
            if (nextPage != null && nextPage != currentPage) {
                navigateTo(nextPage);
            } else {
                currentPage.display();
            }
        }
    }
}
