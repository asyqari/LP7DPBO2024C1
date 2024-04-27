import javax.swing.*;
//Saya Asyqari NIM 2102204 mengerjakan LP7 dalam mata kuliah DPBO untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
public class App {
    public static void main(String[] args){
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360,640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

//        buat objek JPanel(flappybird)
        FlappyBird flappyBird = new FlappyBird(frame);
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();



        //tampilin semua dengan set visible
        frame.setVisible(true);


    }
}
