package services;

public interface MoneyTransfer {
    void pay(String sender, String recipient, int amount);
}