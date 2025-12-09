public class TwoArguments {
    public static void main(String[] args) {
      
        if (args.length < 2) {
            return;
        }
        
    
        int number = Integer.parseInt(args[0]);
        String text = args[1];
        
    
        for (int i = 0; i < number; i++) {
            System.out.println(text);
        }
    }
}
