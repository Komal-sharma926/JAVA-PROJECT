import java.sql.*;
import java.util.Scanner;

public class CollegeAdmission {
    static Connection con;

    public static void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CollegeMini", "root", "");
        } catch (Exception e) {
            System.out.println("Connection failed!");
        }
    }

    public static void registerStudent() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter marks: ");
            int marks = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter course: ");
            String course = sc.nextLine();

            String status = (marks >= 60) ? "Approved" : "Rejected";

            PreparedStatement pst = con.prepareStatement("INSERT INTO Students (name, marks, course, status) VALUES (?, ?, ?, ?)");
            pst.setString(1, name);
            pst.setInt(2, marks);
            pst.setString(3, course);
            pst.setString(4, status);
            pst.executeUpdate();

            System.out.println("Student Registered. Status: " + status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewAllStudents() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Students");

            System.out.println("ID | Name | Marks | Course | Status");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("marks") + " | " +
                        rs.getString("course") + " | " +
                        rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewApprovedStudents() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name, course FROM Students WHERE status = 'Approved'");

            System.out.println("Approved Students:");
            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getString("course"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connect();

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== College Admission Menu ===");
            System.out.println("1. Register Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Approved Students");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: registerStudent(); break;
                case 2: viewAllStudents(); break;
                case 3: viewApprovedStudents(); break;
                case 4: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }
}
