import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartForm extends JFrame {

    public StartForm() {
        setTitle("Flappy Bird - Start");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutup form StartForm
                dispose();

                // Buka JFrame game FlappyBird
                openFlappyBird();
            }
        });

        JPanel panel = new JPanel();
        panel.add(startButton);
        add(panel, BorderLayout.CENTER);
    }

    private void openFlappyBird() {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        FlappyBird flappyBird = new FlappyBird(frame);
        frame.add(flappyBird);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Buka GUI Form StartForm saat aplikasi dimulai
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartForm startForm = new StartForm();
                startForm.setVisible(true);
            }
        });
    }
}
