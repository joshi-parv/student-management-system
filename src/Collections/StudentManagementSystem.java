package Collections;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private int rollno;
    private int marks;

    Student(String a, int b, int c) {
        this.name = a;
        this.rollno = b;
        this.marks = c;
    }

    void getdetails(){
        System.out.println("Name: "+name+", Roll Number: "+rollno+", Marks: "+marks);
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
}

public class StudentManagementSystem {

    static Scanner sc = new Scanner(System.in);

    static void addStudent(List<Student> list){
        String name;
        while (true) {
            System.out.println("Enter Name of student: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("name cannot be empty");
                continue;
            }
            break;
        }

        int rollno;
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

    static void search(List<Student> list) {
        if(list.isEmpty()){
            System.out.println("No student present to search");
            return ;
        }
        boolean f = false;
        System.out.println("enter roll number of student to search");
        int rollNo;
        while(true) {
            try {
                rollNo = sc.nextInt();
                sc.nextLine();
                if(rollNo<=0){
                    System.out.println("roll number should be greater than 0");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Roll number must be a number");
                sc.nextLine();
                continue;
            }
        }
        for (Student s : list) {
            if (s.getrollno()==rollNo) {
                System.out.println("Student Present");
                s.getdetails();
                f = true;
                break;
            }
        }
        if (!f) {
            System.out.println("student not found");

        }
    }

    static void update(List<Student> list) {
        if(list.isEmpty()){
            System.out.println("No student present to update");
            return;
        }

        System.out.println("enter roll number of student to update");
        int rollNo;

        while(true) {
            try {
                rollNo = sc.nextInt();
                sc.nextLine();

                if(rollNo <= 0){
                    System.out.println("roll number should be greater than 0");
                    continue;
                }
                break;

            } catch (InputMismatchException e) {
                System.out.println("Roll number must be a number");
                sc.nextLine();
            }
        }

        boolean f = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getrollno() == rollNo) {
                f = true;

                String newName;
                while (true) {
                    System.out.println("enter new name");
                    newName = sc.nextLine().trim();
                    if (newName.isEmpty()) {
                        System.out.println("name cannot be empty");
                        continue;
                    }
                    break;
                }

                int newRollNo;
                while(true) {
                    try {
                        System.out.println("enter new Roll number");
                        newRollNo = sc.nextInt();
                        sc.nextLine();

                        if(newRollNo <= 0) {
                            System.out.println("roll number should be greater than 0");
                            continue;
                        }

                        boolean duplicate = false;

                        for (int j = 0; j < list.size(); j++) {
                            if(j != i && list.get(j).getrollno() == newRollNo) {
                                duplicate = true;
                                break;
                            }
                        }

                        if(duplicate) {
                            System.out.println("roll number already exists");
                            continue;
                        }

                        break;

                    } catch (InputMismatchException e) {
                        System.out.println("Roll number must be a number");
                        sc.nextLine();
                    }
                }

                int newMarks;
                while(true) {
                    try {
                        System.out.println("enter new Marks");
                        newMarks = sc.nextInt();
                        sc.nextLine();

                        if(newMarks < 0 || newMarks > 100) {
                            System.out.println("marks should be between 0 and 100");
                            continue;
                        }

                        break;

                    } catch (InputMismatchException e) {
                        System.out.println("marks should be number. type marks again");
                        sc.nextLine();
                    }
                }

                Student student = list.get(i);
                student.setname(newName);
                student.setrollno(newRollNo);
                student.setmarks(newMarks);

                System.out.println("student updated");
                break;
            }
        }

        if (!f) {
            System.out.println("student not found");
        }
    }

    static void delete(List<Student> list) {
        if(list.isEmpty()){
            System.out.println("No Student present to delete");
            return;
        }
        boolean f = false;
        System.out.println("enter roll number of student to delete");
        int rollNo;
        while(true) {
            try {
                rollNo = sc.nextInt();
                sc.nextLine();
                if(rollNo<=0){
                    System.out.println("roll number should be greater than 0");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Roll number must be a number");
                sc.nextLine();
                continue;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getrollno()==rollNo){
                list.remove(i);
                f = true;
                System.out.println("Student Deleted");
                break;
            }
        }

        if (!f) {
            System.out.println("student not found");
        }

    }

    static void view(List<Student> list) {
        if(list.isEmpty()) {
            System.out.println("No Student Present");
            return ;
        }
        for (Student s : list) {
            s.getdetails();
        }
    }

    public static void main(String[] args) {

        ArrayList<Student> list = new ArrayList<>();
        boolean T = true;
        while (T) {
            try {
                System.out.println(
                        "press 1 for add student\npress 2 for viewing student\npress 3 for searching student\npress 4 for update student\npress 5 for delete student\npress 6 for exit");
                int i = sc.nextInt();
                sc.nextLine();
                switch (i) {
                    case 1:
                        addStudent(list);
                        break;
                    case 2:
                        view(list);
                        break;
                    case 3:
                        search(list);
                        break;
                    case 4:
                        update(list);
                        break;
                    case 5:
                        delete(list);
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
                System.out.println("Choose correctly");
                sc.nextLine();
            }
        }
        sc.close();
    }
}
