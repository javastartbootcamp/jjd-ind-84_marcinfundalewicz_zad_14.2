package pl.javastart.task;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        StationUtils stationUtils = new StationUtils();
        String fileName = "vehicles.txt";
        try {
            stationUtils.run(fileName);
        } catch (IOException e) {
            System.out.println("Coś poszło nie tak");
        }
    }
}

