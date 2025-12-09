public class Course{
    private Integer courseId;
    private String courseName;
    private Integer creditHours;

    public Course(Integer courseId, String courseName, Integer creditHours) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creditHours = creditHours;
    }
    public Integer getCourseId() {
        return courseId;   }
    public String getCourseName() {
        return courseName;   }
    public Integer getCreditHours() {
        return creditHours;   }
             




}