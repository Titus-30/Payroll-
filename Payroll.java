import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

interface Payroll {
    double calculatePermanent(double salary);
    double calculateCasual(double salary);
    double calculateContractual(double salary);
}

class PermanentEmployee1 implements Payroll {
    private static final double PENSION_DEDUCTION = 0.02;
    private static final double CBHI_DEDUCTION = 0.05;
    private static final double MATERNITY_DEDUCTION = 0.026;

    @Override
    public double calculatePermanent(double salary) {
        double deductions = (PENSION_DEDUCTION + CBHI_DEDUCTION + MATERNITY_DEDUCTION) * salary;
        return salary - deductions;
    }

    @Override
    public double calculateCasual(double salary) {
        throw new UnsupportedOperationException("Not implemented for this type");
    }

    @Override
    public double calculateContractual(double salary) {
        throw new UnsupportedOperationException("Not implemented for this type");
    }
}

class CasualEmployee implements Payroll {
    private static final double PENSION_DEDUCTION = 0.02;
    private static final double MATERNITY_DEDUCTION = 0.026;

    @Override
    public double calculatePermanent(double salary) {
        throw new UnsupportedOperationException("Not implemented for this type");
    }

    @Override
    public double calculateCasual(double salary) {
        double deductions = (PENSION_DEDUCTION + MATERNITY_DEDUCTION) * salary;
        return salary - deductions;
    }

    @Override
    public double calculateContractual(double salary) {
        throw new UnsupportedOperationException("Not implemented for this type");
    }
}

class ContractualEmployee implements Payroll {
    private static final double PENSION_DEDUCTION = 0.026;
    private static final double MATERNITY_DEDUCTION = 0.026;

    @Override
    public double calculatePermanent(double salary) {
        throw new UnsupportedOperationException("Not implemented for this type");
    }

    @Override
    public double calculateCasual(double salary) {
        throw new UnsupportedOperationException("Not implemented for this type");
    }

    @Override
    public double calculateContractual(double salary) {
        double deductions = (PENSION_DEDUCTION + MATERNITY_DEDUCTION) * salary;
        return salary - deductions;
    }
}

class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private int age;
    private String dateOfBirth;
    private String department;
    private int yearsOfExperience;
    private double salary;
    private Payroll payroll;

    // Constructor to initialize Employee details
    public Employee(int employeeId, String firstName, String lastName, int age, String dateOfBirth,
                    String department, int yearsOfExperience, double salary, Payroll payroll) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.department = department;
        this.yearsOfExperience = yearsOfExperience;
        this.salary = salary;
        this.payroll = payroll;
    }

    // Method to display employee details and salary after deductions
    public void displayDetailsAndSalary() {
        double finalSalary = 0;
        if (payroll instanceof PermanentEmployee1) {
            finalSalary = payroll.calculatePermanent(salary);
        } else if (payroll instanceof CasualEmployee) {
            finalSalary = payroll.calculateCasual(salary);
        } else if (payroll instanceof ContractualEmployee) {
            finalSalary = payroll.calculateContractual(salary);
        }
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Department: " + department);
        System.out.println("Years of Experience: " + yearsOfExperience);
        System.out.println("Original Salary: " + salary);
        System.out.println("Final Salary After Deductions: " + finalSalary);
        System.out.println("==========================================================\n");
    }
}

 class PayrollSystem {
     private static Scanner scanner = new Scanner(System.in);

     // Method to validate integer input
     public static int validateInteger(String prompt) {
         int input = -1;
         while (true) {
             System.out.print(prompt);
             try {
                 input = Integer.parseInt(scanner.nextLine());
                 if (input <= 0) {
                     throw new NumberFormatException("Input should be a positive integer.");
                 }
                 break;
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input. " + e.getMessage() + " Try again.");
             }
         }
         return input;
     }

     // Method to validate double input for salary
     public static double validateDouble(String prompt) {
         double input = -1;
         while (true) {
             System.out.print(prompt);
             try {
                 input = Double.parseDouble(scanner.nextLine());
                 if (input <= 0) {
                     throw new NumberFormatException("Salary should be a positive value.");
                 }
                 break;
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input. " + e.getMessage() + " Try again.");
             }
         }
         return input;
     }

     // Method to validate string input (non-empty and without numbers)
     public static String validateString(String prompt) {
         String input = "";
         while (true) {
             System.out.print(prompt);
             input = scanner.nextLine();
             if (input.matches("^[a-zA-Z]+$")) { // Ensures the input contains only letters
                 break;
             } else {
                 System.out.println("Invalid input. Please enter a valid name (letters only).");
             }
         }
         return input;
     }

     // Method to validate string input for department
     public static String validateDepartment(String prompt) {
         String input = "";
         String[] validDepartments = {"accounting", "planning", "strategy", "human resources"};
         while (true) {
             System.out.print(prompt);
             input = scanner.nextLine().toLowerCase();
             boolean valid = false;
             for (String department : validDepartments) {
                 if (input.equals(department)) {
                     valid = true;
                     break;
                 }
             }
             if (valid) {
                 break;
             } else {
                 System.out.println("Invalid department. Please enter a valid department.");
             }
         }
         return input;
     }

     // Method to validate age
     public static int validateAge() {
         int age = validateInteger("Enter age (20-65): ");
         while (age < 20 || age > 65) {
             System.out.println("Age must be between 20 and 65.");
             age = validateInteger("Enter age (20-65): ");
         }
         return age;
     }

     // Method to validate date format (yyyy-mm-dd)
     public static String validateDateOfBirth() {
         String dateOfBirth = "";
         while (true) {
             System.out.print("Enter date of birth (yyyy-mm-dd): ");
             dateOfBirth = scanner.nextLine();
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             try {
                 sdf.setLenient(false);
                 sdf.parse(dateOfBirth);
                 break;
             } catch (ParseException e) {
                 System.out.println("Invalid date format. Please use yyyy-mm-dd.");
             }
         }
         return dateOfBirth;
     }

     // Method to validate years of experience
     public static int validateYearsOfExperience() {
         int years = validateInteger("Enter years of experience (minimum 5): ");
         while (years < 5) {
             System.out.println("Years of experience must be at least 5.");
             years = validateInteger("Enter years of experience (minimum 5): ");
         }
         return years;
     }

     // Main method to run the payroll system
     public static void main(String[] args) {
         System.out.println("Welcome to the Payroll System!");

         for (int i = 1; i <= 3; i++) {
             System.out.println("\nEnter details for Employee " + i + ":");

             int employeeId = validateInteger("Enter employee ID: ");
             String firstName = validateString("Enter first name: ");
             String lastName = validateString("Enter last name: ");
             int age = validateAge();
             String dateOfBirth = validateDateOfBirth();
             String department = validateDepartment("Enter department (accounting, planning, strategy, human resources): ");
             int yearsOfExperience = validateYearsOfExperience();

             Payroll payroll = null;
             double salary = 0;

             // Loop to validate salary range and assign the correct payroll type
             while (true) {
                 salary = validateDouble("Enter salary: ");
                 if (salary >= 1000000 && salary <= 1500000) {
                     payroll = new PermanentEmployee1();
                     break;
                 } else if (salary >= 700000 && salary <= 850000) {
                     payroll = new CasualEmployee();
                     break;
                 } else if (salary >= 200000 && salary <= 400000) {
                     payroll = new ContractualEmployee();
                     break;
                 } else {
                     System.out.println("Salary is not in the valid range for any employee type. Please try again.");
                 }
             }

             Employee employee = new Employee(employeeId, firstName, lastName, age, dateOfBirth, department, yearsOfExperience, salary, payroll);
             employee.displayDetailsAndSalary();
         }
     }
 }