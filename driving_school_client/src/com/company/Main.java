package com.company;

import java.util.Scanner;


//Print actions and receiving an action type from the user and switched to "Action".
public class Main {

    public static void main(String[] args) {
        boolean flag = true;
        while (flag){
            int action = readIntegerFromConsole();
            switch (action){
                case 1: //get teacher by id
                    Action.getTeacherById();
                    break;
                case 2: //get student by id
                    Action.getStudentById();
                    break;
                case 3: //get car by car number
                    Action.getCarByCarNumber();
                    break;
                case 4: // delete teacher
                    Action.delTeacher();
                    break;
                case 5: //delete student
                    Action.delStudent();
                    break;
                case 6: // delete car
                    Action.delCar();
                    break;
                case 7: // add teacher
                    Action.addTeacher();
                    break;
                case 8: // add student
                    Action.addStudent();
                    break;
                case 9: //add car
                    Action.addCar();
                    break;
                case 10://change teacher
                    Action.changeTeacher();
                    break;
                case 11: // change student
                    Action.changeStudent();
                    break;
                case 12: // change green form
                    Action.changeGreenForm();
                    break;
                case 13: // Exit
                    System.out.println("Bye bye...");
                    flag = false;
                    break;

            }
        }

    }
    public static void printMenuPart1(){
        System.out.print("please choose:   ");
        System.out.println("1.Get Object   2.Delete Object   3.Add object   4.Change object   5.Exit");
    }
    public static void printMenuPart2(int action){
        switch (action){
            case 1:
                System.out.print("1.Get teacher   2.Get student   3.Get car");
                break;
            case 2:
                System.out.print("1.Delete teacher   2.Delete student   3.Delete car");
                break;
            case 3:
                System.out.print("1.Add teacher   2.Add student   3.Add car");
                break;
            case 4:
                System.out.print("1.Change teacher   2.Change student   3.Change green form");
                break;
        }
            System.out.println("   4. Exit");
    }


    public static int readIntegerFromConsole(){
        printMenuPart1();
        Scanner s = new Scanner(System.in);
        int action = -1;
        try {
            action = Integer.valueOf(s.nextLine());
        }catch (Exception ex){}
        if (action < 1 || action > 5){
            System.out.println("wrong choose");
            return readIntegerFromConsole();
        }
        if (action == 5)
            return 13;
        int action2 = 4;
        while (true){
            printMenuPart2(action);
            action2 = -1;
            try {
                action2 = Integer.valueOf(s.nextLine());
            }catch (Exception ex){}
            if (action2 < 1 || action2 > 4)
                System.out.println("wrong choose");
            else
                break;

        }
        if (action2 == 4)
            return 13;
        return (action - 1) * 3 + action2;
    }
}
