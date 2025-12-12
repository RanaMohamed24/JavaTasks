import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeApp extends JFrame {
    private JLabel timeLabel;

    public DateTimeApp() {
        setTitle("Time & Date ");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        timeLabel = new JLabel();
        timeLabel.setFont(timeLabel.getFont().deriveFont(20f));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(timeLabel);
        
        setVisible(true);
        updateTime();
    }

    private void updateTime() {
        new Thread(() -> {
            while (true) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                String time = sdf.format(new Date());
                timeLabel.setText(time);
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new DateTimeApp();
    }
}