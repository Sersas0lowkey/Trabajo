import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    // Variables básicas (los 'int' estáticos para usarlos directamente)
    static int pelotaX = 350, pelotaY = 250;
    static int dirX = -3, dirY = 3;
    static int jugadorY = 200, iaY = 200;
    static int estado = 0; // 0 = Menu, 1 = Juego

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ping Pong");
        ventana.setSize(800, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        // Carga de imagen.
        Image fondo = new ImageIcon("src/imagen/20260411_171856.jpg").getImage();

        // Panel para dibujar
        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Dibuja el fondo
                if (fondo != null && fondo.getWidth(null) > -1) {
                    g.drawImage(fondo, 0, 0, 800, 500, this);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, 800, 500);
                }

                g.setColor(Color.WHITE);

                if (estado == 0) {
                    g.setFont(new Font("Arial", Font.BOLD, 20));
                    g.drawString("PRESIONA ENTER PARA JUGAR", 240, 250);
                } else {
                    g.fillRect(20, jugadorY, 15, 80); // Jugador
                    g.fillRect(750, iaY, 15, 80);     // Computadora
                    g.fillOval(pelotaX, pelotaY, 15, 15); // Pelota
                }
            }
        };

        // Controles del teclado integrados
        ventana.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int tecla = e.getKeyCode();
                if (tecla == KeyEvent.VK_ENTER) estado = 1;
                // Mueve al jugador directamente al presionar
                if (tecla == KeyEvent.VK_UP && jugadorY > 0) jugadorY -= 20;
                if (tecla == KeyEvent.VK_DOWN && jugadorY < 380) jugadorY += 20;
            }
        });

        ventana.add(panel);
        ventana.setVisible(true);

        // Llama al método estático para iniciar el movimiento
        iniciarJuego(panel);
    }

    // Método con public static void para la lógica principal
    public static void iniciarJuego(JPanel panel) {
        while (true) {
            if (estado == 1) {
                // La computadora sigue la pelota
                if (iaY + 40 < pelotaY) iaY += 2;
                if (iaY + 40 > pelotaY) iaY -= 2;

                // Mover la pelota
                pelotaX += dirX;
                pelotaY += dirY;

                // Rebote arriba y abajo
                if (pelotaY <= 0 || pelotaY >= 445) dirY = -dirY;

                // Rebote en las paletas
                if (pelotaX <= 35 && pelotaY >= jugadorY && pelotaY <= jugadorY + 80) dirX = -dirX;
                if (pelotaX >= 735 && pelotaY >= iaY && pelotaY <= iaY + 80) dirX = -dirX;

                // Reiniciar si alguien anota
                if (pelotaX < 0 || pelotaX > 800) {
                    pelotaX = 350;
                    pelotaY = 250;
                }
            }

            // Actualiza la pantalla
            panel.repaint();

            // Pausa para que el juego no vaya demasiado rápido
            try {
                Thread.sleep(10);
            } catch (Exception e) {}
        }
    }
}