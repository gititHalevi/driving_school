package com.company;

import java.util.Scanner;


// Action is a class that input and output information to the console, using class "Server".
public class Action {

    // "enum Type" used to transfer data with information about the type of data between functions
    enum Type{
        TEACHER, CAR, STUDENT, GREEN_FORM
    }

    // Read id of teacher from console, Get the teacher by class "Server" and print it to the console
    public static void getTeacherById(){
        String id = readStringFromConsole("Enter id of teacher");
        Server.getTeacher(id, new Server.ObjectAnswerListener<Teacher>() {
            @Override
            public void onAnswerReady(Teacher teacher) {
                if (teacher != null)
                    System.out.println(teacher);
                else
                    System.out.println("Not exist teacher");
            }
        });
    }

    // Read id of student from console, Get the student by class "Server" and print it to the console
    public static void getStudentById(){
        String id = readStringFromConsole("Enter id of student");
        Server.getStudent(id, new Server.ObjectAnswerListener<Student>() {
            @Override
            public void onAnswerReady(Student student) {
                if (student != null)
                    System.out.println(student);
                else
                    System.out.println("Not exist student");


            }
        });
    }

    // Read number of car from console, Get the car by class "Server" and print it to the console
    public static void getCarByCarNumber(){
        String carNumber = readStringFromConsole("Enter number of car");
        Server.getCar(carNumber, new Server.ObjectAnswerListener<Car>() {
            @Override
            public void onAnswerReady(Car car) {
                if (car != null)
                    System.out.println(car);
                else
                    System.out.println("Not exist car");
            }
        });
    }

    // Read id of teacher from console, Send it to be deleted using the class "Server",
    // and print an appropriate answer to the console.
    public static void delTeacher(){
        String id = readStringFromConsole("Enter id of teacher to delete");
        Server.delTeacher(id, new Server.ObjectAnswerListener<String>() {
            @Override
            public void onAnswerReady(String teacherName) {
                if (!teacherName.equals(""))
                    System.out.println(teacherName + ", " + id + " is delete");
                else
                    System.out.println(id + " is not exist teacher");
            }
        });
    }

    // Read id of student from console, Send it to be deleted using the class "Server",
    // and print an appropriate answer to the console.
    public static void delStudent(){
        String id = readStringFromConsole("Enter id of student to delete");
        Server.delStudent(id, new Server.ObjectAnswerListener<String>() {
            @Override
            public void onAnswerReady(String studentName) {
                if (!studentName.equals(""))
                    System.out.println(studentName + ", " + id + " is delete");
                else
                    System.out.println(id + " is not exist student");
            }
        });
    }

    // Read number of car from console, Send it to be deleted using the class "Server",
    // and print an appropriate answer to the console.
    public static void delCar(){
        String carNumber = readStringFromConsole("Enter number of car to delete");
        Server.delCar(carNumber, new Server.ObjectAnswerListener<String>() {
            @Override
            public void onAnswerReady(String delCar) {
                if (!delCar.equals(""))
                    System.out.println(delCar + ", " + carNumber + " is delete");
                else
                    System.out.println(carNumber + " is not exist car");
            }
        });
    }

    // create a new teacher using the function "createObject", add it by class "Server",
    // and print an appropriate answer to the console.
    public static void addTeacher(){
        Object object = createObject(Type.TEACHER, null);
        Teacher teacher = (Teacher) object;
        Server.addTeacher(teacher, new Server.ObjectAnswerListener<Boolean>() {
            @Override
            public void onAnswerReady(Boolean isAdd) {
                if (isAdd)
                    System.out.println("The teacher: "+teacher.getFName()+" "+teacher.getLName()+" added to the system!");
                else
                    System.out.println("The teacher: "+teacher.getFName()+" "+teacher.getLName()+" is already exist in the system!");
            }
        });
    }

    // add new student: read from console number of car to the new student.
    // if the car exist, create a new student using the function "createObject", add it by class "Server",
    // and print an appropriate answer to the console.
    public static void addStudent(){
        String carNumber = readStringFromConsole("Enter number of car to the new student");
        Server.getCar(carNumber, new Server.ObjectAnswerListener<Car>() {
            @Override
            public void onAnswerReady(Car car) {
                if (car != null){
                    Object object = createObject(Type.STUDENT,(Object) car);
                    Student student = (Student) object;
                    Server.addStudent(student, new Server.ObjectAnswerListener<Integer>() {
                        @Override
                        public void onAnswerReady(Integer isAdd) {
                            if (isAdd == 1)
                                System.out.println("The student: " + student.getFName() + " " + student.getLName() + " added to the system!");
                            else
                            if (isAdd == 0)
                                System.out.println("The student: " + student.getFName() + " " + student.getLName() + " is already exist in the system!");
                            else
                                System.out.println("The car of student not exist in the system!");
                        }

                    });
                } else
                    System.out.println("Not exist car");
            }
        });
    }



    // add new car: read from console id of teacher to the new car.
    // if the teacher exist, create a new car using the function "createObject", add it by class "Server",
    // and print an appropriate answer to the console.
    public static void addCar(){
        String id = readStringFromConsole("Enter id of teacher to the new car");
        Server.getTeacher(id, new Server.ObjectAnswerListener<Teacher>() {
            @Override
            public void onAnswerReady(Teacher teacher) {
                if (teacher != null) {
                    Object object = createObject(Type.CAR, (Object) teacher);
                    Car car = (Car) object;
                    Server.addCar(car, new Server.ObjectAnswerListener<Integer>() {
                        @Override
                        public void onAnswerReady(Integer isAdd) {
                            if (isAdd == 1)
                                System.out.println("The car: " + car.getCompany() + " " + car.getModel() + " added to the system!");
                            else if (isAdd == 0)
                                System.out.println("The student: " + car.getCompany() + car.getModel() + " is already exist in the system!");
                            else
                                System.out.println("The teacher of car not exist in the system!");
                        }

                    });
                } else
                    System.out.println("Not exist teacher");
            }
        });
    }

    //change teacher:
    // Read id of teacher from console, Get the teacher by class "Server",
    // print it to the console with an appropriate menu using function "printMenuForChange",
    // chane the teacher and save it using class "Server"
    // print an appropriate answer to the console.
    public static void changeTeacher(){
        String id = readStringFromConsole("Enter id of teacher");
        Server.getTeacher(id, new Server.ObjectAnswerListener<Teacher>() {
            @Override
            public void onAnswerReady(Teacher teacher) {
                if (teacher != null) {
                    System.out.println(teacher);
                    int change = printMenuForChange(Type.TEACHER);
                    switch (change){
                        case 1://change fName
                            teacher.setFName(readStringFromConsole("Enter new first name: "));
                            break;
                        case 2://change lName
                            teacher.setLName(readStringFromConsole("Enter new last name: "));
                            break;
                        case 3://change phone number
                            teacher.setPhoneNumber(readStringFromConsole("Enter new phone number: "));
                            break;
                        case 4://change address
                            teacher.setAddress(readStringFromConsole("Enter new address: "));
                            break;
                        case 5://change email
                            teacher.setEmail(readStringFromConsole("Enter new email: "));
                            break;
                    }
                    Server.changeTeacher(teacher, new Server.ObjectAnswerListener<Integer>() {
                        @Override
                        public void onAnswerReady(Integer integer) {
                            if (integer == 1){
                                System.out.println("Teacher changed!");
                                System.out.println(teacher);
                            }else
                                System.out.println("The teacher doesn't exist...");
                        }
                    });
                }
                else
                    System.out.println("Not exist teacher");
            }
        });
    }

    //change student:
    // Read id of student from console, Get the student by class "Server",
    // print it to the console with an appropriate menu using function "printMenuForChange",
    // chane the student and save it using class "Server"
    // print an appropriate answer to the console.
    public static void changeStudent(){
        String id = readStringFromConsole("Enter id of student");
        Server.getStudent(id, new Server.ObjectAnswerListener<Student>() {
            @Override
            public void onAnswerReady(Student student) {
                if (student != null) {
                    System.out.println(student);
                    int change = printMenuForChange(Type.TEACHER);
                    switch (change){
                        case 1://change fName
                            student.setFName(readStringFromConsole("Enter new first name: "));
                            break;
                        case 2://change lName
                            student.setLName(readStringFromConsole("Enter new last name: "));
                            break;
                        case 3://change phone number
                            student.setPhoneNumber(readStringFromConsole("Enter new phone number: "));
                            break;
                        case 4://change address
                            student.setAddress(readStringFromConsole("Enter new address: "));
                            break;
                        case 5://change email
                            student.setEmail(readStringFromConsole("Enter new email: "));
                            break;
                    }
                    Server.changeStudent(student, new Server.ObjectAnswerListener<Integer>() {
                        @Override
                        public void onAnswerReady(Integer integer) {
                            if (integer == 1){
                                System.out.println("Student changed!");
                                System.out.println(student);
                            }else
                                System.out.println("The student doesn't exist...");
                        }
                    });
                }
                else
                    System.out.println("Not exist student");
            }
        });
    }


    // Change green form:
    // Read id of student from console, Get the student by class "Server",
    // print student's green form to the console with an appropriate menu using function "printMenuForChange",
    // chane the green form and save it using class "Server"
    // print an appropriate answer to the console.
    public static void changeGreenForm(){
        String id = readStringFromConsole("Enter id of student");
        Server.getStudent(id, new Server.ObjectAnswerListener<Student>() {
            @Override
            public void onAnswerReady(Student student) {
                if (student != null) {
                    GreenForm greenForm = student.getGreenForm();
                    int change = printMenuForChange(Type.GREEN_FORM);
                    switch (change){
                        case 1://change eye test date
                            greenForm.setEyeDate(getDateFromConsole("eye test date"));
                            break;
                        case 2://change medical certificate date
                            greenForm.setMedicalDate(getDateFromConsole("medical certificate date"));
                            break;
                        case 3://change theory date
                            greenForm.setTheoryDate(getDateFromConsole("theory date"));
                            break;

                    }
                    student.setGreenForm(greenForm);
                    Server.changeGreenForm(student.getId(), greenForm, new Server.ObjectAnswerListener<Integer>() {
                        @Override
                        public void onAnswerReady(Integer integer) {
                            if (integer == 1){
                                System.out.println("Green form changed!");
                                System.out.println(greenForm);
                            }else
                                System.out.println("The student doesn't exist...");
                        }
                    });
                }
                else
                    System.out.println("Not exist student");
            }
        });
    }


    // read string from console and return it. it have to be at least 2 characters.
    public static String readStringFromConsole(String string){
        Scanner s = new Scanner(System.in);
        System.out.println(string);
        String userString = s.nextLine();
        if (userString.length() < 2){
            System.out.println("Please write at least two characters");
            return readStringFromConsole(string);
        }
        return userString;
    }

    // create a new teacher, student or car and return it as "Object"
    public static Object createObject(Type type, Object object){
        switch (type){
            case TEACHER:
                return new Teacher(readStringFromConsole("Enter id: "), readStringFromConsole("Enter first name: "),
                        readStringFromConsole("Enter last name: "),getGenderFromConsole(), readStringFromConsole("Enter phone number: "),
                        readStringFromConsole("Enter address: "), readStringFromConsole("Enter Email: "),
                        readStringFromConsole("Enter license number: "));
            case STUDENT:
                if (object instanceof Car) {
                    Car car = (Car) object;
                    return new Student(readStringFromConsole("Enter id: "), readStringFromConsole("Enter first name: "),
                            readStringFromConsole("Enter last name: "), getGenderFromConsole(), readStringFromConsole("Enter phone number: "),
                            readStringFromConsole("Enter address: "), readStringFromConsole("Enter Email: "),
                            car, getGreenFormFromConsole());
                }
                break;
            case CAR:
                if (object instanceof Teacher) {
                    Teacher teacher = (Teacher) object;
                    return new Car(readStringFromConsole("Enter car number: "), readStringFromConsole("Enter car type: "),
                            readStringFromConsole("Enter company: "), readStringFromConsole("Enter model: "),
                            teacher);
                }
                break;
        }
        return null;

    }

    // read gender from console and return it as "Gender"
    public static Gender getGenderFromConsole(){
        System.out.println("Enter 1 for male, and 2 for female: ");
        Scanner s = new Scanner(System.in);
        int gender = -1;
        try {
            gender = Integer.valueOf(s.nextLine());
        }catch (Exception ex){}
        if (gender != 1 && gender != 2) {
            System.out.println("wrong number,");
            return getGenderFromConsole();
        }
        if (gender == 1)
            return Gender.MALE;
        return Gender.FEMALE;
    }

    // read date from console and return it as "Date"
    public static Date getDateFromConsole(String dateOf){
        Scanner s = new Scanner(System.in);
        int day = 0;
        while (day < 1 || day > 32){
            System.out.println("Enter a day of " + dateOf);
            try {
                day = Integer.valueOf(s.nextLine());
            }catch (Exception ex){}
            if (day < 1 || day > 32)
                System.out.println("it have to be between 1 and 32...");
        }
        int month = 0;
        while (month < 1 || month > 12){
            System.out.println("Enter a month of " + dateOf);
            try {
                month = Integer.valueOf(s.nextLine());
            }catch (Exception ex){}
            if (month < 1 || month > 12)
                System.out.println("it have to be between 1 and 12...");
        }
        int year = 0;
        while (year < 1900 || year > 2099){
            System.out.println("Enter a year of " + dateOf);
            try {
                year = Integer.valueOf(s.nextLine());
            }catch (Exception ex){}
            if (year < 1900 || year > 2099)
                System.out.println("it have to be between 1900 and 2099...");
        }
        return new Date(day,month,year);
    }

    // create a new green form from console and return it as "GreenForm"
    public static GreenForm getGreenFormFromConsole(){
        Date eye = getDateFromConsole("eye test date");
        Date medical = getDateFromConsole("medical certificate date");
        Date theory = getDateFromConsole("theory date");
        return new GreenForm(eye,medical,theory);
    }

    //print to the console menu for change teacher, student or green form, and return number as a symbol.
    public static int printMenuForChange(Type type){
        Scanner s = new Scanner(System.in);
        int change = -1;
        switch (type){
            case TEACHER:
                System.out.println("Enter change: 1.first name 2.last name 3.phone number 4.address 5.email");
                try {
                    change = Integer.valueOf(s.nextLine());
                }catch (Exception ex){}
                if (change < 1 || change > 5){
                    System.out.println("wrong choose...");
                    return printMenuForChange(Type.TEACHER);
                }
                break;
            case STUDENT:
                System.out.println("Enter change: 1.first name 2.last name 3.phone number 4.address 5.email");
                try {
                    change = Integer.valueOf(s.nextLine());
                }catch (Exception ex){}
                if (change < 1 || change > 5){
                    System.out.println("wrong choose...");
                    return printMenuForChange(Type.STUDENT);
                }
                break;
            case GREEN_FORM:
                System.out.println("1.eye test date 2.medical certificate date 3.theory date");
                try {
                    change = Integer.valueOf(s.nextLine());
                }catch (Exception ex){}
                if (change < 1 || change > 3){
                    System.out.println("wrong choose...");
                    return printMenuForChange(Type.GREEN_FORM);
                }
                break;
        }
        return change;
    }

}
