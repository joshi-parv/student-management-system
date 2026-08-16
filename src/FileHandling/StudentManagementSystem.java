package FileHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentManagementSystem {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        File file = new File("students.txt");
        ArrayList<Student> list = new ArrayList<>();

        try {
            if (!file.exists() && !file.createNewFile()) {
                System.out.println("could not create students file");
                return;
            }
            loadfile(list, file);
        } catch (IOException e) {
            System.out.println("could not load students file: " + e.getMessage());
            return;
        }

        boolean T = true;
        while (T) {
            try {
                System.out.println(
                        "press 1 for add student\npress 2 for viewing student\npress 3 for searching student\npress 4 for update student\npress 5 for delete student\npress 6 for exit");
                int i = sc.nextInt();
                sc.nextLine();
                switch (i) {
                    case 1:
                        addstudent(list);
                        saveStudents(list, file);
                        break;
                    case 2:
                        view(list);
                        break;
                    case 3:
                        search(list);
                        break;
                    case 4:
                        update(list);
                        saveStudents(list, file);
                        break;
                    case 5:
                        delete(list);
                        saveStudents(list, file);
                        break;
                    case 6:
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
            }
        }
    }

    static void loadfile(ArrayList<Student> list, File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String c;
            while ((c = br.readLine()) != null) {
                String[] split = c.split(",", -1);
                if (split.length != 3) {
                    System.out.println("invalid data skipped: " + c);
                    continue;
                }

                try {
                    String name = split[0].trim();
                    int roll = Integer.parseInt(split[1].trim());
                    int mark = Integer.parseInt(split[2].trim());

                    if (name.isEmpty() || roll <= 0 || mark < 0 || mark > 100) {
                        System.out.println("invalid data skipped: " + c);
                        continue;
                    }

                    boolean duplicateRollNo = false;
                    for (Student student : list) {
                        if (student.getrollno() == roll) {
                            duplicateRollNo = true;
                            break;
                        }
                    }
                    if (duplicateRollNo) {
                        System.out.println("duplicate roll number skipped: " + c);
                        continue;
                    }

                    Student s1 = new Student(name, roll, mark);
                    list.add(s1);
                } catch (NumberFormatException e) {
                    System.out.println("invalid data skipped: " + c);
                }
            }
        }
    }

    static void addstudent(ArrayList<Student> list) {
        System.out.println("Enter Name of student: ");
        String name;
        while (true) {
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("name cannot be empty, enter again");
                continue;
            }
            if (name.contains(",")) {
                System.out.println("name cannot contain a comma, enter again");
                continue;
            }
            break;
        }
        int rollno = -1;
        while (true) {
            try {
                System.out.println("Enter Roll Number of student");
                rollno = sc.nextInt();
                sc.nextLine();
                if (rollno <= 0) {
                    System.out.println("enter roll number greater than 0");
                    continue;
                }
                boolean f = false;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getrollno() == rollno) {
                        f = true;
                        break;
                    }
                }
                if (f) {
                    System.out.println("roll number already exists");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("roll number must be number");
                sc.nextLine();
            }
        }

        int marks = -1;
        while (true) {
            try {
                System.out.println("Enter Marks of student");
                marks = sc.nextInt();
                sc.nextLine();
                if (marks > 100 || marks < 0) {
                    System.out.println("marks should be between 0 and 100");
                    continue;
                }
                break;

            } catch (InputMismatchException e) {
                System.out.println("marks should be number. type marks again");
                sc.nextLine();
            }
        }
        Student s1 = new Student(name, rollno, marks);
        list.add(s1);
        System.out.println("student added");
    }

    static void search(ArrayList<Student> list) {
        if (list.isEmpty()) {
            System.out.println("no student present, nothing to search");
            return;
        }
        while (true) {
            try {
                System.out.println("press 1 to search by name and 2 to search by roll number");
                int s = sc.nextInt();
                sc.nextLine();
                if (s == 1) {
                    boolean f = false;
                    System.out.println("enter name to search");
                    String name = sc.nextLine().trim();
                    for (Student st : list) {
                        if (st.getname().equals(name)) {
                            System.out.println("Student Present");
                            st.getdetails();
                            f = true;
                            break;
                        }
                    }
                    if (!f) {
                        System.out.println("student not found");
                    }
                    break;
                } else if (s == 2) {
                    boolean f = false;
                    int rn;
                    while (true) {
                        System.out.println("enter roll number to search");
                        rn = sc.nextInt();
                        sc.nextLine();
                        if (rn <= 0) {
                            System.out.println("roll number should be greater than 0");
                            continue;
                        }
                        break;
                    }
                    for (Student st : list) {
                        if (st.getrollno() == rn) {
                            System.out.println("Student Present");
                            st.getdetails();
                            f = true;
                            break;
                        }
                    }
                    if (!f) {
                        System.out.println("student not found");
                    }
                    break;
                } else {
                    System.out.println("choose correct option");
                }
            } catch (InputMismatchException e) {
                System.out.println("choose correct option");
                sc.nextLine();
            }
        }
    }

    static void delete(ArrayList<Student> list) {
        if (list.isEmpty()) {
            System.out.println("no student present to delete");
            return;
        }
        while (true) {
            try {
                System.out.println("press 1 to delete by name and 2 to delete by roll number");
                int s = sc.nextInt();
                sc.nextLine();
                if (s == 1) {
                    boolean f = false;
                    System.out.println("Name of student to be deleted");
                    String name = sc.nextLine().trim();

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getname().equals(name)) {
                            list.remove(i);
                            f = true;
                            System.out.println("Student Deleted");
                            break;
                        }
                    }

                    if (!f) {
                        System.out.println("student not found");
                    }
                    break;
                } else if (s == 2) {
                    boolean f = false;
                    int rn;
                    while (true) {
                        System.out.println("enter roll number to delete");
                        rn = sc.nextInt();
                        sc.nextLine();
                        if (rn <= 0) {
                            System.out.println("roll number should be greater than 0");
                            continue;
                        }
                        break;
                    }

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getrollno() == rn) {
                            list.remove(i);
                            f = true;
                            System.out.println("Student Deleted");
                            break;
                        }
                    }

                    if (!f) {
                        System.out.println("student not found");
                    }
                    break;
                } else {
                    System.out.println("choose correct option");
                }
            } catch (InputMismatchException e) {
                System.out.println("type correctly");
                sc.nextLine();
            }
        }

    }

    static void update(ArrayList<Student> list) {
        if (list.isEmpty()) {
            System.out.println("no student, cannot update");
            return;
        }
        System.out.println("Name and roll number of student  to be updated");
        System.out.println("name:");

        String name;
        while (true) {
            name = sc.nextLine().trim();
            if (name.trim().isEmpty()) {
                System.out.println("name cannot be empty, enter again");
                continue;
            }
            if (name.contains(",")) {
                System.out.println("name cannot contain a comma, enter again");
                continue;
            }
            break;
        }
        int rn;
        while (true) {
            try {
                System.out.println("enter roll number");
                rn = sc.nextInt();
                sc.nextLine();
                if (rn <= 0) {
                    System.out.println("roll number should be greater than 0");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("roll number must be a number");
                sc.nextLine();
            }
        }
        boolean f = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getname().equals(name) && list.get(i).getrollno() == rn) {
                f = true;
                while (true) {
                    try {
                        System.out.println(
                                "enter 1 to update name\n 2 for update roll number \n3 for update marks and 4 for completion of process");
                        int ch = sc.nextInt();
                        sc.nextLine();
                        if (ch == 1) {
                            String newname;
                            while (true) {
                                System.out.println("Enter Name of student: ");
                                newname = sc.nextLine().trim();
                                if (newname.isEmpty()) {
                                    System.out.println("name cannot be empty");
                                    continue;
                                }
                                if (newname.contains(",")) {
                                    System.out.println("name cannot contain a comma");
                                    continue;
                                }
                                break;
                            }
                            list.get(i).setname(newname);
                        } else if (ch == 2) {
                            int rollno = -1;
                            while (true) {
                                try {
                                    System.out.println("Enter Roll Number of student");
                                    rollno = sc.nextInt();
                                    sc.nextLine();
                                    if (rollno <= 0) {
                                        System.out.println("enter roll number greater than 0");
                                        continue;
                                    }
                                    boolean rf = false;
                                    for (int j = 0; j < list.size(); j++) {
                                        if (j != i && list.get(j).getrollno() == rollno) {
                                            rf = true;
                                            break;
                                        }
                                    }
                                    if (rf) {
                                        System.out.println("roll number already exists");
                                        continue;
                                    }
                                    list.get(i).setrollno(rollno);
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("roll number must be number");
                                    sc.nextLine();
                                }
                            }
                        } else if (ch == 3) {
                            int marks = -1;
                            while (true) {
                                try {
                                    System.out.println("Enter Marks of student");
                                    marks = sc.nextInt();
                                    sc.nextLine();
                                    if (marks > 100 || marks < 0) {
                                        System.out.println("marks should be between 0 and 100");
                                        continue;
                                    }
                                    list.get(i).setmarks(marks);
                                    break;

                                } catch (InputMismatchException e) {
                                    System.out.println("marks should be number. type marks again");
                                    sc.nextLine();
                                }
                            }

                        }

                        else if (ch == 4) {
                            System.out.println("student updated successfully ");
                            return;
                        } else {
                            System.out.println("choose correct option");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("choose correct option");
                        sc.nextLine();
                    }
                }
            }
        }
        if (!f) {
            System.out.println("student not found");
        }
    }

    static void view(ArrayList<Student> list) {
        if (list.isEmpty()) {
            System.out.println("no student present, nothing to view");
            return;
        }
        for (Student s : list) {
            s.getdetails();
        }
    }

    static void studentaddfile(ArrayList<Student> list, File file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Student s : list) {
                bw.write(s.getname().trim() + "," + s.getrollno() + "," + s.getmarks());
                bw.newLine();
            }
        }
    }

    static void saveStudents(ArrayList<Student> list, File file) {
        try {
            studentaddfile(list, file);
        } catch (IOException e) {
            System.out.println("could not save students file: " + e.getMessage());
        }
    }
}

class Student {
    private String name;
    private int rollno;
    private int marks;

    Student(String a, int b, int c) {
        this.name = a;
        this.rollno = b;
        this.marks = c;
    }

    void getdetails() {
        System.out.println("Name: " + name + ", Roll Number: " + rollno + ", Marks: " + marks);
    }

    void setname(String a) {
        this.name = a;
    }

    void setrollno(int a) {
        this.rollno = a;
    }

    void setmarks(int a) {
        this.marks = a;
    }

    String getname() {
        return name;
    }

    int getrollno() {
        return rollno;
    }

    int getmarks() {
        return marks;
    }
}
