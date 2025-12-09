
class Complex<T extends Number> {
    private T real;
    private T imaginary;
    
    public Complex(T real, T imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }
    
    public T getReal() {
        return real;
    }
    
    public T getImaginary() {
        return imaginary;
    }
    
    @Override
    public String toString() {
        return real + " + " + imaginary + "i";
    }
}


class ComplexCalculator {
    
  
    public Complex<Double> add(Complex<Double> c1, Complex<Double> c2) {
        double realPart = c1.getReal() + c2.getReal();
        double imaginaryPart = c1.getImaginary() + c2.getImaginary();
        return new Complex<>(realPart, imaginaryPart);
    }
    
   
    public Complex<Double> subtract(Complex<Double> c1, Complex<Double> c2) {
        double realPart = c1.getReal() - c2.getReal();
        double imaginaryPart = c1.getImaginary() - c2.getImaginary();
        return new Complex<>(realPart, imaginaryPart);
    }
    
  
    public Complex<Double> multiply(Complex<Double> c1, Complex<Double> c2) {
        double realPart = (c1.getReal() * c2.getReal()) - (c1.getImaginary() * c2.getImaginary());
        double imaginaryPart = (c1.getReal() * c2.getImaginary()) + (c1.getImaginary() * c2.getReal());
        return new Complex<>(realPart, imaginaryPart);
    }
}


class TestComplex {
    public static void main(String[] args) {
        ComplexCalculator calc = new ComplexCalculator();
        
     
        Complex<Double> c1 = new Complex<>(3.0, 4.0);
        Complex<Double> c2 = new Complex<>(1.0, 2.0);
        
        System.out.println("Complex Number 1: " + c1);
        System.out.println("Complex Number 2: " + c2);
        System.out.println();
        
       
        Complex<Double> sum = calc.add(c1, c2);
        System.out.println("Addition: " + c1 + " + " + c2 + " = " + sum);
        
      
        Complex<Double> diff = calc.subtract(c1, c2);
        System.out.println("Subtraction: " + c1 + " - " + c2 + " = " + diff);
        
      
        Complex<Double> product = calc.multiply(c1, c2);
        System.out.println("Multiplication: " + c1 + " * " + c2 + " = " + product);
    }
}
