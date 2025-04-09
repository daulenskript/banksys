package services;

import database.ClientRepository;
import model.Client;

public class TransferByPhoneNumber implements MoneyTransfer {
    private ClientRepository clientRepository;

    public TransferByPhoneNumber(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void pay(String sender, String recipient, int amount) {
        Client senderClient = findClientByPhoneNumber(sender);
        Client recipientClient = findClientByPhoneNumber(recipient);

        if (senderClient == null || recipientClient == null) {
            System.out.println("Ошибка: один из клиентов не найден.");
            return;
        }

        if (senderClient.getBalance() < amount) {
            System.out.println("Ошибка: недостаточно средств у отправителя.");
            return;
        }

        senderClient.setBalance(senderClient.getBalance() - amount);
        recipientClient.setBalance(recipientClient.getBalance() + amount);
        
        System.out.println("Перевод выполнен: " + amount + " от " + sender + " к " + recipient);
        
        clientRepository.saveClientsInfo();
    }

    private Client findClientByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber);  // Поиск через репозиторий
    }
}
