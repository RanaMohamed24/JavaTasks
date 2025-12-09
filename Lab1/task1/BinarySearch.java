import java.util.Arrays;

public class BinarySearch {

    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
             if (array[mid] == target) {
                return mid;
            }
            if (array[mid] < target) {
                left = mid +1;
            }
            else {
                right = mid - 1;
            }
        }
         return -1;
    }
    public static void main(String[] args) {
    
        int[] array = new int[1000];
        
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000);
        }
        
        long startTime = System.currentTimeMillis();
        
        Arrays.sort(array);
        
        int targetIndex = 500;
        int target = array[targetIndex];
        int index = binarySearch(array, target);
       
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("target is :" +target);
        System.out.println("target found in index :" +index);
        
        System.out.println("Time: " + duration + " milliseconds");
    }
}
