import java.util.*;
import java.io.*;


class Person {
    protected String name;

    public Person(String name) {
        this.name = name;
    }

    public String getInfo() {
        return "Person: " + name;
    }
}


class Customer extends Person {
    public Customer(String name) {
        super(name);
    }

    @Override
    public String getInfo() {
        return "Customer: " + name;
    }
}


class Room {
    private int number;
    private String type;
    private double price;
    private boolean isBooked;

    public Room(int number, String type, double price) {
        this.number = number;
        this.type = type;
        this.price = price;
        this.isBooked = false;
    }

    public int getNumber() { return number; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public boolean isBooked() { return isBooked; }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}


class Booking {
    private Room room;
    private Customer customer;

    public Booking(Room room, Customer customer) {
        this.room = room;
        this.customer = customer;
    }

    public Room getRoom() { return room; }
    public Customer getCustomer() { return customer; }
}


class HotelManager {
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public HotelManager() {
        rooms.add(new Room(1, "Single", 50));
        rooms.add(new Room(2, "Double", 80));
        rooms.add(new Room(3, "Luxury", 150));
    }

    public void showRooms() {
        for (Room r : rooms) {
            System.out.println("Room " + r.getNumber() +
                    " | " + r.getType() +
                    " | $" + r.getPrice() +
                    (r.isBooked() ? " (Booked)" : " (Free)"));
        }
    }

    public void bookRoom(int num, String name) {
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        for (Room r : rooms) {
            if (r.getNumber() == num && !r.isBooked()) {
                bookings.add(new Booking(r, new Customer(name)));
                r.setBooked(true);
                System.out.println("Booked successfully!");
                return;
            }
        }
        System.out.println("Room not available!");
    }

    public void updateBooking(int num, String newName) {
        for (Booking b : bookings) {
            if (b.getRoom().getNumber() == num) {
                b.getCustomer().name = newName;
                System.out.println("Updated!");
                return;
            }
        }
        System.out.println("Booking not found!");
    }

    public void cancelBooking(int num) {
        Iterator<Booking> it = bookings.iterator();

        while (it.hasNext()) {
            Booking b = it.next();
            if (b.getRoom().getNumber() == num) {
                b.getRoom().setBooked(false);
                it.remove();
                System.out.println("Cancelled!");
                return;
            }
        }
    }

    public void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"));

            for (Booking b : bookings) {
                bw.write(b.getRoom().getNumber() + "," + b.getCustomer().name);
                bw.newLine();
            }

            bw.close();
            System.out.println("Saved!");

        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    public void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int roomNum = Integer.parseInt(parts[0]);
                String name = parts[1];

                for (Room r : rooms) {
                    if (r.getNumber() == roomNum) {
                        r.setBooked(true);
                        bookings.add(new Booking(r, new Customer(name)));
                    }
                }
            }

            br.close();
            System.out.println("Loaded!");

        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    public void exportToCSV() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("export.csv"));

            for (Booking b : bookings) {
                bw.write(b.getRoom().getNumber() + "," + b.getCustomer().name);
                bw.newLine();
            }

            bw.close();
            System.out.println("Exported to CSV!");

        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    public void importFromCSV() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("export.csv"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int roomNum = Integer.parseInt(parts[0]);
                String name = parts[1];

                for (Room r : rooms) {
                    if (r.getNumber() == roomNum) {
                        r.setBooked(true);
                        bookings.add(new Booking(r, new Customer(name)));
                    }
                }
            }

            br.close();
            System.out.println("Imported!");

        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    public void countFreeRooms() {
        int count = 0;
        for (Room r : rooms) {
            if (!r.isBooked()) count++;
        }
        System.out.println("Free rooms: " + count);
    }
}

// -------- Main --------
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        HotelManager hm = new HotelManager();

        while (true) {
            System.out.println("==== HOTEL MENU ====");
            System.out.println("1.Show 2.Book 3.Update 4.Cancel");
            System.out.println("5.Save 6.Load 7.ExportCSV 8.ImportCSV");
            System.out.println("9.FreeRooms 0.Exit");

            try {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) hm.showRooms();

                else if (choice == 2) {
                    System.out.print("Room: ");
                    int r = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Name: ");
                    String n = sc.nextLine();
                    hm.bookRoom(r, n);
                }

                else if (choice == 3) {
                    System.out.print("Room: ");
                    int r = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New name: ");
                    String n = sc.nextLine();
                    hm.updateBooking(r, n);
                }

                else if (choice == 4) {
                    System.out.print("Room: ");
                    int r = sc.nextInt();
                    hm.cancelBooking(r);
                }

                else if (choice == 5) hm.saveToFile();
                else if (choice == 6) hm.loadFromFile();
                else if (choice == 7) hm.exportToCSV();
                else if (choice == 8) hm.importFromCSV();
                else if (choice == 9) hm.countFreeRooms();
                else break;

            } catch (Exception e) {
                System.out.println("Invalid input!");
                sc.nextLine();
            }
        }
    }
}
