
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}


class AgeValidator {
    public void validateAge(int age) throws InvalidAgeException{
        if (age < 0) {
            throw new InvalidAgeException("Age cannot be negative!");
        }
        System.out.println("Age " + age + " is valid.");
    }
    
    public void checkAdult(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Must be 18 or older!");
        }
        System.out.println("User is an adult.");
    }
    
    public void checkSenior(int age) throws InvalidAgeException {
        if (age < 60) {
            throw new InvalidAgeException("Not a senior citizen!");
        }
        System.out.println("User is a senior citizen.");
    }
}


class CheckException {
    public static void main(String[] args) {
        AgeValidator validator = new AgeValidator();
                    validator.validateAge(-5);
    

        
      
        try {
            validator.checkAdult(15);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
        } finally {
            System.out.println("Finally block 2 executed.\n");
        }
        
      
        try {
            validator.checkSenior(65);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
        } finally {
            System.out.println("Finally block 3 executed.\n");
        }
    }
}
