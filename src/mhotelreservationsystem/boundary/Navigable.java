package mhotelreservationsystem.boundary;

public interface Navigable {
    void display();
    Navigable handleChoice(int choice);
    int getMaxChoice();
}
