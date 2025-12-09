
class StringValidator {
    
   
    public static boolean isOnlyAlphabets(String str) {
      
        if (str == null || str.isEmpty()) {
            return false;
        }
        
       
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}


class TestString{
    public static void main(String[] args) {
        
    
        String test1 = "HelloWorld";
        System.out.println("String: \"" + test1 + "\"");
        System.out.println("Contains only alphabets: " + StringValidator.isOnlyAlphabets(test1));
        System.out.println();
        
      
        String test2 = "Hello123";
        System.out.println("String: \"" + test2 + "\"");
        System.out.println("Contains only alphabets: " + StringValidator.isOnlyAlphabets(test2));
        System.out.println();
        
       
        String test3 = "Hello@World";
        System.out.println("String: \"" + test3 + "\"");
        System.out.println("Contains only alphabets: " + StringValidator.isOnlyAlphabets(test3));
        System.out.println();
        
     
        String test4 = "Hello World";
        System.out.println("String: \"" + test4 + "\"");
        System.out.println("Contains only alphabets: " + StringValidator.isOnlyAlphabets(test4));
        System.out.println();
        
       
        String test5 = "java";
        System.out.println("String: \"" + test5 + "\"");
        System.out.println("Contains only alphabets: " + StringValidator.isOnlyAlphabets(test5));
    }
}
