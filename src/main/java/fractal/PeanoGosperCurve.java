package fractal;


import util.CyclicIterator;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

import static java.awt.Color.*;
import static java.lang.Math.*;


public class PeanoGosperCurve extends JPanel {
    private static final double ANGLE = 60, START_X = 100, START_Y = 200, START_ANGLE = 0, START_LENGTH = 600;
    private static final Color[] COLORS = {BLACK, BLUE, GREEN, MAGENTA, CYAN, PINK, RED};
    private static final Iterator<Color> COLOR_ITERATOR = new CyclicIterator<>(COLORS);
    private int iter;
    private double x, y, angle;
    
    
    @Override
    public void paint(Graphics g) {
        x = START_X;
        y = START_Y;
        angle = START_ANGLE - (iter * toDegrees(atan(sqrt(3) / 5)));
        curve(iter, 'a', START_LENGTH, (Graphics2D) g);
    }
    
    
    public void curve(int currIter, char type, double length, Graphics2D g) {
        if (currIter > 0) {
            length /= sqrt(7);
            currIter--;
            
            if (type == 'a') {
                curve(currIter, 'a', length, g);
                angle += ANGLE;
                curve(currIter, 'b', length, g);
                angle += 2 * ANGLE;
                curve(currIter, 'b', length, g);
                angle -= ANGLE;
                curve(currIter, 'a', length, g);
                angle -= 2 * ANGLE;
                curve(currIter, 'a', length, g);
                curve(currIter, 'a', length, g);
                angle -= ANGLE;
                curve(currIter, 'b', length, g);
                angle += ANGLE;
            }
            
            else {
                angle -= ANGLE;
                curve(currIter, 'a', length, g);
                angle += ANGLE;
                curve(currIter, 'b', length, g);
                curve(currIter, 'b', length, g);
                angle += 2 * ANGLE;
                curve(currIter, 'b', length, g);
                angle += ANGLE;
                curve(currIter, 'a', length, g);
                angle -= 2 * ANGLE;
                curve(currIter, 'a', length, g);
                angle -= ANGLE;
                curve(currIter, 'b', length, g);
            }
        }
        
        else {
            g.setColor(COLOR_ITERATOR.next());
            g.drawLine(
                    (int) round(x),
                    (int) round(y),
                    (int) round(x + length * cos(toRadians(angle))),
                    (int) round(y + length * sin(toRadians(angle)))
            );
            
            x += length * cos(toRadians(angle));
            y += length * sin(toRadians(angle));
        }
    }
    
    
    private void updateCurve(int newIter, JTextField iterField, PeanoGosperCurve curve) {
        if (newIter < 0)
            newIter = 0;
        
        if (iter != newIter) {
            iter = newIter;
            iterField.setText(Integer.toString(newIter));
            curve.updateUI();
        }
    }
    
    
    public static void main(String[] args) {
        PeanoGosperCurve curve = new PeanoGosperCurve();
        curve.setPreferredSize(new Dimension((int) (2 * START_X + START_LENGTH), (int) (START_Y + START_LENGTH)));
        curve.setBackground(Color.WHITE);
        
        JTextField iterField = new JTextField(Integer.toString(curve.iter), 3);
        iterField.addActionListener(
                actionEvent -> curve.updateCurve(Integer.parseInt(iterField.getText()), iterField, curve)
        );
        
        JButton plus = new JButton("+");
        plus.addActionListener(
                actionEvent -> curve.updateCurve(Integer.parseInt(iterField.getText()) + 1, iterField, curve)
        );
        
        JButton minus = new JButton("-");
        minus.addActionListener(
                actionEvent -> curve.updateCurve(Integer.parseInt(iterField.getText()) - 1, iterField, curve)
        );
        
        JPanel iterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        iterPanel.add(new Label("Iterations:"));
        iterPanel.add(iterField);
        iterPanel.add(plus);
        iterPanel.add(minus);
        
        JScrollPane scrollPane = new JScrollPane(curve);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(iterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JFrame frame = new JFrame("Peano—Gosper curve");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}