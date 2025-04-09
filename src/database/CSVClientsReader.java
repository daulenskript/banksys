package database;

import model.Client;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVClientsReader {

    public static List<Client> read(String filename) {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length < 7) continue;

                String firstName = values[0].trim();
                String secondName = values[1].trim();
                String dateOfBirth = values[2].trim();
                String inn = values[3].trim();
                String phoneNumber = values[4].trim();
                String bankAccount = values[5].trim();
                double balance = Double.parseDouble(values[6].trim());

                Client client = new Client(firstName, secondName, dateOfBirth, inn, phoneNumber, bankAccount, balance);
                clients.add(client);
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении CSV файла: " + e.getMessage());
        }

        return clients;
    }
}
