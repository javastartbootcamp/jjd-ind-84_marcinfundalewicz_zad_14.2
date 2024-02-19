package pl.javastart.task;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class StationUtils {
    private static final int EXIT = 0;
    private static final int ADD_VEHICLE = 1;
    private static final int PERFORM_INSPECTION = 2;
    private Queue<Vehicle> vehicles = new LinkedList<>();
    Scanner scanner = new Scanner(System.in);

    public void run(String fileName) throws IOException {
        if (!fileName.isEmpty()) {
            readQueueFromFile(fileName);
        }
        boolean canContinue = true;
        while (canContinue) {
            printUserChoice();
            int userChoice = readUserChoice();
            if (userChoice == EXIT) {
                fileWritingAction(fileName);
                canContinue = false;
            } else if (userChoice == ADD_VEHICLE) {
                vehicles.offer(createVehicle());
            } else if (userChoice == PERFORM_INSPECTION) {
                inspectionIfQueueIsNotEmpty(fileName);
            } else {
                System.out.println("Coś poszło nie tak");
            }
        }
    }

    private boolean fileWritingAction(String fileName) throws IOException {
        boolean canContinue;
        if (vehicles.isEmpty()) {
            canContinue = false;
            clearFile(fileName);
        } else {
            writeQueueToFile(fileName);
            canContinue = false;
        }
        return canContinue;
    }

    private void inspectionIfQueueIsNotEmpty(String fileName) throws IOException {
        if (!vehicles.isEmpty()) {
            Vehicle vehicleToInspection = vehicles.poll();
            System.out.println("Dokonano przeglądu pojazdu o numerze VIN: " + vehicleToInspection.getVin());
        } else {
            System.out.println("Pojazdu nie ma w kolejce");
        }
    }

    private void writeQueueToFile(String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            for (Vehicle vehicle : vehicles) {
                bufferedWriter.write(vehicle.toCsv());
                bufferedWriter.newLine();
            }
        }
    }

    private void readQueueFromFile(String fileName) throws IOException {
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String fileLine = null;
            while ((fileLine = bufferedReader.readLine()) != null) {
                String[] lines = fileLine.split(",");
                String type = lines[EXIT];
                String name = lines[ADD_VEHICLE];
                String model = lines[PERFORM_INSPECTION];
                int year = Integer.parseInt(lines[3]);
                int milleage = Integer.parseInt(lines[4]);
                String vin = lines[5];
                Vehicle vehicle = new Vehicle(type, name, model, year, milleage, vin);
                vehicles.offer(vehicle);
            }
        }
    }

    private void clearFile(String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            fileWriter.write("");
        }
    }

    private Vehicle createVehicle() {
        scanner.nextLine();
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
        int userChoice = scanner.nextInt();
        return userChoice;
    }
}
