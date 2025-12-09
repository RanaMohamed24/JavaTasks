import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RegistrationSystem implements RegistrationAction {
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    
    public RegistrationSystem() {
        courses.add(new Course(101, "OOP", 3));
        courses.add(new Course(102, "Databases", 4));
        courses.add(new Course(103, "Web Dev", 3));
    }
    
    @Override
    public void register(Student student, Course course, Double grade) {
        student.registerCourse(course, grade);
        System.out.println("✓ Registered: " + student.getName() + " for " + course.getCourseName());
    }
    
    @Override
    public String generateReport(Student student) {
        return student.printReport();
    }
    
    public void run() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n--- Registration System ---");
            System.out.println("1. Add Student\n2. Register for Course\n3. View Report\n4. Exit");
            System.out.print("Choice: ");
            
            switch (sc.nextInt()) {
                case 1:
                    sc.nextLine(); 
                    System.out.print("Student ID: ");
                    Integer id = sc.nextInt();
                    sc.nextLine(); 
                    
                    
                    if (findStudent(id) != null) {
                        System.out.println(" Student ID already exists!");
                        break;
                    }
                    
                    System.out.print("Name: ");
                    students.add(new Student(id, sc.nextLine()));
                    System.out.println("✓ Student added!");
                    break;
                
                case 2:
                    sc.nextLine();
                    System.out.print("Student ID: ");
                    Integer studentId = sc.nextInt();
                    sc.nextLine(); 
                    Student student = findStudent(studentId);
                    
                    if (student != null) {
                        System.out.println("\nCourses: ");
                        courses.forEach(c -> System.out.println(c.getCourseId() + ". " + c.getCourseName()));
                        System.out.print("Enter Course IDs (comma-separated): ");
                        String courseInput = sc.nextLine();
                        
                        if (courseInput.trim().isEmpty()) {
                            System.out.println("No courses entered!");
                            break;
                        }
                        
                        StringTokenizer tokenizer = new StringTokenizer(courseInput, ",");
                        
                        while (tokenizer.hasMoreTokens()) {
                            try {
                                Integer courseId = Integer.parseInt(tokenizer.nextToken().trim());
                                Course selectedCourse = findCourse(courseId);
                                if (selectedCourse != null) {
                                    Double grade;
                                    while (true) {
                                        System.out.print("Enter grade for " + selectedCourse.getCourseName() + " (0-100): ");
                                        grade = sc.nextDouble();
                                        
                                       
                                        if (grade >= 0 && grade <= 100) {
                                            break;
                                        } else {
                                            System.out.println(" Grade must be between 0 and 100!");
                                        }
                                    }
                                    sc.nextLine(); 
                                    register(student, selectedCourse, grade);
                                } else {
                                    System.out.println("Course " + courseId + " not found!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid course ID!");
                            }
                        }
                    } else {
                        System.out.println("Student not found!");
                    }
                    break;
                
                case 3:
                    sc.nextLine(); 
                    System.out.print("Student ID: ");
                    Integer reportId = sc.nextInt();
                    Student s = findStudent(reportId);
                    if (s != null) {
                        System.out.println(generateReport(s));
                    } else {
                        System.out.println("Student not found!");
                    }
                    break;
                
                case 4:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                
                default:
                    System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }
    
    private Student findStudent(Integer id) {
        return students.stream().filter(s -> s.getStudentId().equals(id)).findFirst().orElse(null);
    }
    
    private Course findCourse(Integer id) {
        return courses.stream().filter(c -> c.getCourseId().equals(id)).findFirst().orElse(null);
    }
    
    public static void main(String[] args) {
        new RegistrationSystem().run();
    }
}