package mk.raycaster;


import mk.raycaster.engine.BufferSingleton;
import mk.raycaster.engine.Engine;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    private Engine engine;
    private BufferSingleton buffer;
    private Timer timer;
    private final int DELAY = 5;

    public Board() {
        engine = new Engine();
        buffer = BufferSingleton.getInstance();
        initTimer();
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        engine.updatePosition();
        engine.calculate();
        super.paintComponent(g);

        draw(g);
    }

    private void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

//        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//
//        g2d.setRenderingHints(rh);

        Dimension size = getSize();
        double w = size.getWidth();
        double h = size.getHeight();

        g2d.setStroke(new BasicStroke(1));


        for (int x = 0; x < buffer.matrix.length; x++) {
            for (int y = 0; y < buffer.matrix[x].length; y++) {
                if(buffer.matrix[x][y] != 0){
                    g2d.setColor(new Color(buffer.matrix[x][y]));
                    g2d.drawLine(x, y, x, y);
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    engine.setKeyPressed("UP");
                    break;
                case KeyEvent.VK_DOWN:
                    engine.setKeyPressed("DOWN");
                    break;
                case KeyEvent.VK_LEFT:
                    engine.setKeyPressed("LEFT");
                    break;
                case KeyEvent.VK_RIGHT:
                    engine.setKeyPressed("RIGHT");
                    break;
                case KeyEvent.VK_Q:
                    engine.floorCasting = !engine.floorCasting;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            engine.setKeyPressed(null);
        }
    }
}