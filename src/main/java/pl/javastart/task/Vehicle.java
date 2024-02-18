package pl.javastart.task;

public class Vehicle {
    private String type;
    private String name;
    private String model;
    private int year;
    private int milleage;
    private String vin;

    public Vehicle(String type, String name, String model, int year, int milleage, String vin) {
        this.type = type;
        this.name = name;
        this.model = model;
        this.year = year;
        this.milleage = milleage;
        this.vin = vin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMilleage() {
        return milleage;
    }

    public void setMilleage(int milleage) {
        this.milleage = milleage;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String toCsv() {
        return type + "," + name + "," + model + "," + year + "," + milleage + "," + vin;
    }
}
