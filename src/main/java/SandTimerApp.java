import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SandTimerApp extends JFrame {
    private SandTimerPanel sandTimerPanel;

    public SandTimerApp() {
        super("Sand Timer App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        sandTimerPanel = new SandTimerPanel();
        add(sandTimerPanel);
        setVisible(true);

        // Создаем и запускаем поток для изменения размеров песка
        Thread sandThread = new Thread(() -> {
            while (true) {
                sandTimerPanel.updateSand();
                try {
                    Thread.sleep(50); // Интервал между обновлениями
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sandThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SandTimerApp());
    }
}

class SandTimerPanel extends JPanel {
    private ImageIcon sandTop, sandBottom, clockBackground;
    private int sandTopHeight, sandBottomHeight;

    public SandTimerPanel() {
        sandTop = new ImageIcon("sand.png");
        sandBottom = new ImageIcon("sand.png");
        clockBackground = new ImageIcon("clocks.png");

        sandTopHeight = 100;
        sandBottomHeight = 0;

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Изменяем высоту верхнего и нижнего песка
                sandTopHeight += 1 ;
                sandBottomHeight -= 1;

                // Перерисовываем панель
                repaint();
            }
        });
        timer.start();
    }

    public void updateSand() {
        sandTopHeight += 1;
        sandBottomHeight -= 1;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // Рисуем фоновое изображение часов
        g.drawImage(clockBackground.getImage(), 0, 0, width, height, null);

        // Рисуем верхний песок
        g.drawImage(sandTop.getImage(), width / 4, height / 4, width / 2, sandTopHeight, null);

        // Рисуем нижний песок
        g.drawImage(sandBottom.getImage(), width / 4, height / 2 + height / 1 - sandBottomHeight, width / 2, sandBottomHeight, null);
    }
}

