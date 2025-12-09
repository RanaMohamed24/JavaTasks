
@FunctionalInterface
interface StringCompare {
    boolean compare(String s1, String s2);
}


class StringUtils {
  
    public static String betterString(String string1, String string2, StringCompare comparator) {
        if (comparator.compare(string1, string2)) {
            return string1;
        } else {
            return string2;
        }
    }
}


class TestCompare {
    public static void main(String[] args) {
        String string1 = "Hello";
        String string2 = "World";
        
      
        String longer = StringUtils.betterString(string1, string2, 
            (s1, s2) -> s1.length() > s2.length());
        
        System.out.println("String 1: " + string1);
        System.out.println("String 2: " + string2);
        System.out.println("Longer string: " + longer);
        System.out.println();
        
    
        String first = StringUtils.betterString(string1, string2, 
            (s1, s2) -> true);
        
        System.out.println("First string is better: " + first);
        System.out.println();
        
     
        String alphabetical = StringUtils.betterString(string1, string2, 
            (s1, s2) -> s1.compareTo(s2) < 0);
        
        System.out.println("Alphabetically first: " + alphabetical);
    }
}
