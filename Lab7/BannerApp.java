import javax.swing.*;

public class BannerApp extends JFrame {
    private JLabel bannerLabel;
    private int xPosition = 0;
    private int speed = 6;

    public BannerApp() {
        setTitle("App ");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        bannerLabel = new JLabel("Java World");
        bannerLabel.setFont(bannerLabel.getFont().deriveFont(24f));
        bannerLabel.setBounds(0, 180, 150, 40);
        add(bannerLabel);
        
        setVisible(true);
        startMoving();
    }

    private void startMoving() {
        new Thread(() -> {
            while (true) {
                xPosition += speed;
                
                if (xPosition > getWidth()) {
                    xPosition = -100;
                }
                
                bannerLabel.setLocation(xPosition, 180);
                
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new BannerApp();
    }
}