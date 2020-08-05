package assignments.eventDrivenSimulation;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import java.awt.*;
import java.awt.event.*;

/**
 * This class provides a GUI for testing Collision System.
 */
public class CollisionSystemTest {

    private static final String TEST_FILES = ("The test Files are the following:\n\n"
            + "billiards2.txt    diagonal.txt      diffusion3.txt    p1000-.5K.txt     squeeze2.txt\n"
            + "billiards4.txt    diagonal1.txt     p10.txt           p1000-2K.txt      wallbouncing.txt\n"
            + "billiards5.txt    diagonal2.txt     p100-.125K.txt    p2000.txt         wallbouncing2.txt\n"
            + "brownian.txt      diffusion.txt     p100-.5K.txt      pendulum.txt      wallbouncing3.txt\n"
            + "brownian2.txt     diffusion2.txt    p100-2K.txt       squeeze.txt       crash.txt\n"
            + "=========================================================================================\n"
            + "press <ENTER> to continue, <q> to quit:\n");

    private static final String[] TESTS = {"billiards2.txt", "diagonal.txt", "diffusion3.txt", "p1000-.5K.txt",
            "squeeze2.txt", "billiards4.txt", "diagonal1.txt", "p10.txt", "p1000-2K.txt", "wallbouncing.txt",
            "billiards5.txt", "diagonal2.txt", "p100-.125K.txt", "p2000.txt", "wallbouncing2.txt", "brownian.txt",
            "diffusion.txt", "p100-.5K.txt", "pendulum.txt", "wallbouncing3.txt", "brownian2.txt", "diffusion2.txt",
            "p100-2K.txt", "squeeze.txt", "crash.txt"};



    private final static class Terminal extends JFrame {

        private static final long serialVersionUID = 1783931876524892226L;
        private final JTextArea textArea;
        private final JScrollPane scrollPane;
        private CollisionTest testThread = null;
        private int progressCount = 0;

        final KeyListener listenEnter = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (progressCount < TESTS.length) {
                        revalidate();
                        repaint();
                        if (testThread != null) {
                            testThread.stop();
                        }
                        textArea.append("This test is : " + TESTS[progressCount] + ":\n");
                        textArea.append("Press <ENTER> to view the next test (press <q> to Quit):\n");
                        revalidate();
                        repaint();
                        testThread = new CollisionTest(TESTS[progressCount]);
                        testThread.start();
                        progressCount++;
                    } else {
                        textArea.append("Press <ENTER> or <q> to Quit.\n");
                        revalidate();
                        repaint();
                        if (testThread != null) {
                            testThread.stop();
                        }
                        System.exit(0);
                    }
                    JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
                    vScrollBar.setValue(vScrollBar.getModel().getMaximum() - vScrollBar.getModel().getExtent());
                    requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        public Terminal() {
            setSize(800, 600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setBackground(Color.WHITE);
            setMinimumSize(new Dimension(800, 600));
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    Dimension d = getSize();
                    Dimension minD = getMinimumSize();
                    if (d.width < minD.width)
                        d.width = minD.width;
                    if (d.height < minD.height)
                        d.height = minD.height;
                    setSize(d);
                    revalidate();
                    repaint();
                }
            });
            addKeyListener(listenEnter);
            setVisible(true);
            textArea = new JTextArea() {
                private static final long serialVersionUID = 603962318788033892L;
                {

                    setForeground(Color.BLACK);
                    setBackground(Color.WHITE);
                    setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
                    setEditable(false);
                    addKeyListener(listenEnter);
                    setVisible(true);
                }
            };
            scrollPane = new JScrollPane(textArea);
            scrollPane.addKeyListener(listenEnter);
            add(scrollPane);
            SpringLayout windowLayout = new SpringLayout();
            setLayout(windowLayout);
            windowLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, getContentPane());
            windowLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, getContentPane());
            windowLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, getContentPane());
            windowLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, getContentPane());
            textArea.append(TEST_FILES);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        new Terminal();
    }
}
