import java.util.ArrayList;
import java.util.List;


abstract class Shape {
    abstract void draw();
}


class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    void draw() {
        System.out.println("Drawing Rectangle with width: " + width + ", height: " + height);
    }
}


class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    void draw() {
        System.out.println("Drawing Circle with radius: " + radius);
    }
}


class ShapeTest {
   
    public <T extends Shape> void processShapes(List<T> shapes) {
        System.out.println("Processing shapes...");
        for (T shape : shapes) {
            shape.draw();
        }
    }
}


class TestDraw {
    public static void main(String[] args) {
        ShapeTest test = new ShapeTest();
        
        
        ArrayList<Rectangle> rectangleList = new ArrayList<>();
        rectangleList.add(new Rectangle(5, 10));
        rectangleList.add(new Rectangle(3, 7));
        
       
        test.processShapes(rectangleList);
        
    
        ArrayList<Circle> circleList = new ArrayList<>();
        circleList.add(new Circle(4));
        circleList.add(new Circle(6));
        
   
        test.processShapes(circleList);
    }
}
