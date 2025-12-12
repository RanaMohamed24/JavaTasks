import javax.swing.*;
import java.awt.*;


public class Ball extends JFrame {
    public Ball() {
        setTitle("Moving Ball ");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        add(new BallPanel());
        setVisible(true);
    }

    public static void main(String[] args) {
        new Ball();
    }
}

class BallPanel extends JPanel {
    private double ballX = 100;
    private double ballY = 100;
    private int ballSize = 100;
    private double dirX = 4.5;
    private double dirY = 3.5;
  

    public BallPanel() {
        setBackground(Color.WHITE);
        startAnimation();
    }

    private void startAnimation() {
        new Thread(() -> {
            while (true) {
                ballX += dirX;
                ballY += dirY;

               
                if (ballX <= 0 || ballX >= getWidth() - ballSize) {
                    dirX = -dirX;
                    ballX = Math.max(0, Math.min(ballX, getWidth() - ballSize));
                }
             
                if (ballY <= 0 || ballY >= getHeight() - ballSize) {
                    dirY = -dirY;
                    ballY = Math.max(0, Math.min(ballY, getHeight() - ballSize));
                }

                repaint();

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(Color.pink);
        g2d.fillOval((int)ballX, (int)ballY, ballSize, ballSize);
    }
}