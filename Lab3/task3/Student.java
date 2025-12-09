import java.util.ArrayList;

public class Student {
    private Integer studentId;
    private String studentName;
    private ArrayList<CourseRegistration> registrations;

    public class CourseRegistration {
        private Course course;
        private Double grade;
        
        public CourseRegistration(Course course, Double grade) {
            this.course = course;
            this.grade = grade;
        }
        
        public Course getCourse() { 
            return course; 
        }
        public Double getGrade() { 
            return grade; 
        }
    }

    public Student(Integer studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.registrations = new ArrayList<>();
    }

    public void registerCourse(Course course, Double grade) {
        registrations.add(new CourseRegistration(course, grade));
    }

    public String printReport() {
        StringBuilder report = new StringBuilder();
        report.append("\nStudent: ").append(studentName).append(" (ID: ").append(studentId).append(")\n");
        report.append("---Registered Courses---\n");
        
        if (registrations.isEmpty()) {
            report.append("No courses registered yet.\n");
        } else {
            for (CourseRegistration reg : registrations) {
                report.append("- ").append(reg.getCourse().getCourseName())
                      .append(" | Grade: ").append(reg.getGrade()).append("\n");
            }
        }
        
        return report.toString();
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getName() {
        return studentName;
    }

    public ArrayList<CourseRegistration> getRegistrations() {
        return registrations;
    }
}