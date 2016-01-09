package fr.kocal.graphstream;

import javax.swing.*;

/**
 * Created by Hugo Alliaume on 04/01/16.
 */
public class App {

    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        Window window = new Window();
        window.setVisible(true);
    }
}
