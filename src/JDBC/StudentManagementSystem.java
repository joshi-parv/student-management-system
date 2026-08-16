package JDBC;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentManagementSystem {
    static Scanner sc = new Scanner(System.in);

    static void addstudent(Connection con) throws SQLException {
        String name;
        while (true) {
            System.out.println("Enter Name of student");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                continue;
            }
            break;
        }
        int roll_no;
        while (true) {
            try {
                System.out.println("Enter Roll number of student");
                roll_no = sc.nextInt();
                sc.nextLine();
                if (roll_no <= 0) {
                    System.out.println("Roll Number must be greater than 0");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Roll Number should be a number");
                sc.nextLine();
            }
        }
        double marks;
        while (true) {
            try {
                System.out.println("Enter marks of student");
                marks = sc.nextDouble();
                sc.nextLine();
                if (!Double.isFinite(marks) || marks > 100 || marks < 0) {
                    System.out.println("Enter correct marks again");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Marks should be a number");
                sc.nextLine();
            }
        }
        String query = "insert into student(roll_no,name,marks) values(?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, roll_no);
            ps.setString(2, name);
            ps.setDouble(3, marks);
            int ra = ps.executeUpdate();
            if (ra > 0) System.out.println("Student Added");
            else System.out.println("Cannot Add Student");
        }
    }

    static void viewstudent(Connection con) throws SQLException {
        String query = "Select * from student";
        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            boolean found = false;
            while (rs.next()) {
                found = true;
                String name = rs.getString("name");
                int roll_no = rs.getInt("roll_no");
                double marks = rs.getDouble("marks");
                System.out.println("Roll Number : " + roll_no + "  ||  Name : " + name + "  ||  Marks : " + marks);
                System.out.println("=======================================================================");
            }
            if (!found) {
                System.out.println("no student present");
            }
        }
    }

    static void update(Connection con) throws SQLException {
        int roll_no;
        while (true) {
            try {
                System.out.println("Enter Roll number of student");
                roll_no = sc.nextInt();
                sc.nextLine();
                if (roll_no <= 0) {
                    System.out.println("Roll Number must be greater than 0");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Roll Number should be a number");
                sc.nextLine();
            }
        }

        try (PreparedStatement findStudent = con.prepareStatement("select roll_no from student where roll_no = ?")) {
            findStudent.setInt(1, roll_no);
            try (ResultSet rs = findStudent.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Student not found");
                    return;
                }
            }
        }

        int new_roll_no;
        while (true) {
            try {
                System.out.println("Enter new Roll number of student");
                new_roll_no = sc.nextInt();
                sc.nextLine();
                if (new_roll_no <= 0) {
                    System.out.println("Roll Number must be greater than 0");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Roll Number should be a number");
                sc.nextLine();
            }
        }

        String name;
        while (true) {
            System.out.println("Enter Name of student");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                continue;
            }
            break;
        }
        double marks;
        while (true) {
            try {
                System.out.println("Enter marks of student");
                marks = sc.nextDouble();
                sc.nextLine();
                if (!Double.isFinite(marks) || marks > 100 || marks < 0) {
                    System.out.println("Enter correct marks again");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Marks should be a number");
                sc.nextLine();
            }
        }

        String query = "update student set name = ?, roll_no = ?, marks = ? where roll_no = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, new_roll_no);
            ps.setDouble(3, marks);
            ps.setInt(4, roll_no);
            int ra = ps.executeUpdate();
            if (ra > 0) {
                System.out.println("Student updated");
            } else {
                System.out.println("Updation failed");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Roll number already exists");
        }
    }

    static void delete(Connection con) throws SQLException {
        int roll_no;
        while (true) {
            try {
                System.out.println("Enter Roll number of student to delete");
                roll_no = sc.nextInt();
                sc.nextLine();
                if (roll_no <= 0) {
                    System.out.println("Roll Number must be greater than 0");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Roll Number should be a number");
                sc.nextLine();
            }
        }
        try (PreparedStatement ps = con.prepareStatement("delete from student where roll_no = ?")) {
            ps.setInt(1, roll_no);
            int ra = ps.executeUpdate();
            if (ra > 0) System.out.println("Student deleted");
            else System.out.println("Student not found");
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc_practice";
        String username = "root";
        String pass = "Schoolap69";
        try (Connection con = DriverManager.getConnection(url, username, pass)) {
            boolean T = true;
            while (T) {
                try {
                    System.out.println(
                            "press 1 for add student\npress 2 for viewing student\npress 3 for update student\npress 4 for delete student\npress 5 for exit");
                    int i = sc.nextInt();
                    sc.nextLine();
                    switch (i) {
                        case 1:
                            addstudent(con);
                            break;
                        case 2:
                            viewstudent(con);
                            break;
                        case 3:
                            update(con);
                            break;
                        case 4:
                            delete(con);
                            break;
                        case 5:
                            System.out.println("program finished");
                            T = false;
                            break;
                        default:
                            System.out.println("invalid choice");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("choose correct option");
                    sc.nextLine();
                } catch (SQLException e) {
                    System.out.println("Database operation failed: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
