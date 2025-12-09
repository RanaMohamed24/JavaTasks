public class MinMaxElement{
public static void main(String[] args){
   int[] array =new int[1000];
   for(int i=0; i<array.length ; i++){
       array[i] = (int) (Math.random() *1000);
   }
   
   long startTime = System.currentTimeMillis();
   
   int min= array[0];
   int max= array[0];
   
   for(int i=1;i<array.length ;i++){
      if(array[i]<min){
         min=array[i];
      }
      if(array[i]>max){
      max=array[i];
      }
   }
     long endTime = System.currentTimeMillis();
     long duration = endTime - startTime;
    
       System.out.println("Search :");
        System.out.println("Minimum: " + min);
        System.out.println("Maximum: " + max);
        
        System.out.println("Time: " + duration + " milliseconds");
   

 }
}
