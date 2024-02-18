package pl.javastart.task;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class StationUtils {
    private Queue<Vehicle> vehicles = new LinkedList<>();

    public void run(String fileName) throws IOException {
        if (!fileName.isEmpty()) {
            readQueueFromFile(fileName);
        }
        boolean canContinue = true;
        while (canContinue) {
            printUserChoice();
            int userChoice = readUserChoice();
            if (userChoice == 0) {
                canContinue = fileWritingAction(fileName);
            } else if (userChoice == 1) {
                vehicles.offer(createVehicle());
            } else if (userChoice == 2) {
                inspectionIfQueueNotEmpty(fileName);
            } else {
                System.out.println("Coś poszło nie tak");
            }
        }
    }

    public void performInspection(String fileName) throws IOException {
        if (!vehicles.isEmpty()) {
            Vehicle vehicleToInspection = vehicles.poll();
            System.out.println("Dokonano przeglądu pojazdu o numerze VIN: " + vehicleToInspection.getVin());
        } else {
            clearFile(fileName);
        }
    }

    private boolean fileWritingAction(String fileName) throws IOException {
        boolean canContinue;
        if (vehicles.isEmpty()) {
            canContinue = false;
            clearFile(fileName);
        } else {
            writeQueueToFile("vehicles.txt");
            canContinue = false;
        }
        return canContinue;
    }

    private void inspectionIfQueueNotEmpty(String fileName) throws IOException {
        if (!vehicles.isEmpty()) {
            performInspection(fileName);
        } else {
            System.out.println("Pojazdu nie ma w kolejce");
        }
    }

    public void writeQueueToFile(String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            for (Vehicle vehicle : vehicles) {
                bufferedWriter.write(vehicle.toCsv());
                bufferedWriter.newLine();
            }
        }
    }

    public void readQueueFromFile(String fileName) throws IOException {
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String fileLine = null;
            while ((fileLine = bufferedReader.readLine()) != null) {
                String[] lines = fileLine.split(",");
                String type = lines[0];
                String name = lines[1];
                String model = lines[2];
                int year = Integer.parseInt(lines[3]);
                int milleage = Integer.parseInt(lines[4]);
                String vin = lines[5];
                Vehicle vehicle = new Vehicle(type, name, model, year, milleage, vin);
                vehicles.offer(vehicle);
            }
        }
    }

    public void clearFile(String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            fileWriter.write("");
        }
    }

    public Vehicle createVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj typ pojazdu");
        String type = scanner.nextLine();
        System.out.println("Podaj nazwę pojazdu");
        String name = scanner.nextLine();
        System.out.println("Podaj model pojazdu");
        String model = scanner.nextLine();
        System.out.println("Podaj rocznik pojazdu");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj przebieg pojazdu");
        int milleage = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj numer VIN pojazdu");
        String vin = scanner.nextLine();
        return new Vehicle(type, name, model, year, milleage, vin);
    }

    private void printUserChoice() {
        System.out.println("Witaj w stacji kontroli pojazdów. Proszę wybrać jedną z poniższych opcji:");
        System.out.println("0 - zakończenie działania programu,");
        System.out.println("1 - dodanie pojazdu do przeglądu,");
        System.out.println("2 - wykonanie przeglądu przez naszych specjalistów.");
    }

    private int readUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();
        return userChoice;
    }
}
