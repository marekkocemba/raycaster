package mk.raycaster;

import mk.raycaster.engine.Engine;

import javax.swing.*;
import java.awt.*;
import  mk.raycaster.Configuration.*;

public class Main extends JFrame {

    public Main() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setSize(Configuration.WIDTH, Configuration.HEIGHT);

        setTitle("Raycaster");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private Engine engine = new Engine();

    public static void main(String ... args){
        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}
