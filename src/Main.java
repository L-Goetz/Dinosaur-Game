import  javax.swing.*;

public class Main {
    public static void main(String[] args){
        int boardWidth = 750;
        int boardHeight = 250;

        JFrame frame = new JFrame("Game");
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dinosaur dino = new Dinosaur();
        frame.add(dino);
        frame.pack();
        dino.requestFocus();
        frame.setVisible(true);
        }
    }