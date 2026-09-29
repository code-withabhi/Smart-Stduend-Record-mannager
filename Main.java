import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
public class Main {
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;

        do {
            System.out.println("\n===== STUDENT RECORD MANAGER =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Sort Student Records");
            System.out.println("6. Exit");
            
            choice = getValidInt("Enter your choice (1-6): ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    sortStudents();
                    break;
                case 6:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please choose an option between 1 and 6.");
            }
        } while (choice != 6);
    }

    private static void addStudent() {
        int id = getValidInt("Enter Student ID: ");

        for (Student s : studentList) {
            if (s.getId() == id) {
                System.out.println("Error: Student with ID " + id + " already exists!");
                return;
            }
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter Student Name: ");
            name = scanner.nextLine().trim();
        }

        double marks = getValidDouble("Enter Marks (0 - 100): ");

        studentList.add(new Student(id, name, marks));
        System.out.println("Student record added successfully!");
    }

    private static void displayStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        System.out.println("\n----------------------------------------------------");
        System.out.println("                  STUDENT RECORDS                   ");
        System.out.println("----------------------------------------------------");
        for (Student s : studentList) {
            System.out.println(s);
        }
        System.out.println("----------------------------------------------------");
    }

    private static void searchStudent() {
        if (studentList.isEmpty()) {
            System.out.println("List is empty. Nothing to search.");
            return;
        }

        System.out.println("\nSearch by:");
        System.out.println("1. Student ID");
        System.out.println("2. Student Name");
        int searchOption = getValidInt("Choose an option (1 or 2): ");

        if (searchOption == 1) {
            int id = getValidInt("Enter Student ID to search: ");
            boolean found = false;

            for (Student s : studentList) {
                if (s.getId() == id) {
                    System.out.println("Record Found -> " + s);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student with ID " + id + " not found.");
            }

        } else if (searchOption == 2) {
            System.out.print("Enter Student Name to search: ");
            String name = scanner.nextLine().trim();
            boolean found = false;

            for (Student s : studentList) {
                if (s.getName().equalsIgnoreCase(name)) {
                    System.out.println("Record Found -> " + s);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Student with name '" + name + "' not found.");
            }
        } else {
            System.out.println("Invalid search option.");
        }
    }

    private static void deleteStudent() {
        if (studentList.isEmpty()) {
            System.out.println("List is empty. Nothing to delete.");
            return;
        }

        int id = getValidInt("Enter Student ID to delete: ");

        Student toRemove = null;
        for (Student s : studentList) {
            if (s.getId() == id) {
                toRemove = s;
                break;
            }
        }

        if (toRemove != null) {
            studentList.remove(toRemove);
            System.out.println("Student with ID " + id + " deleted successfully!");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void sortStudents() {
        if (studentList.isEmpty()) {
            System.out.println("List is empty. Nothing to sort.");
            return;
        }

        System.out.println("\nSort records by:");
        System.out.println("1. Sort by ID (Ascending)");
        System.out.println("2. Sort by Name (Alphabetical)");
        System.out.println("3. Sort by Marks (Descending / Rank wise)");
        int sortOption = getValidInt("Choose sorting criteria (1-3): ");

        switch (sortOption) {
            case 1:
                studentList.sort(Comparator.comparingInt(Student::getId));
                System.out.println("Records sorted by ID successfully!");
                displayStudents();
                break;

            case 2:
                studentList.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
                System.out.println("Records sorted alphabetically by Name successfully!");
                displayStudents();
                break;

            case 3:
                studentList.sort(Comparator.comparingDouble(Student::getMarks).reversed());
                System.out.println("Records sorted by Marks (Rank-wise) successfully!");
                displayStudents();
                break;

            default:
                System.out.println("Invalid sorting option.");
        }
    }

    private static int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid whole number (no letters or symbols).");
            }
        }
    }

    private static double getValidDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double val = Double.parseDouble(input);
                if (val < 0 || val > 100) {
                    System.out.println("Marks should typically be between 0 and 100. Try again.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid decimal/number for marks.");
            }
        }
    }
}
