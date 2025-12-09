public interface RegistrationAction {
    void register(Student student, Course course, Double grade);
    String generateReport(Student student);
}