package view;

import javax.swing.JFrame;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.AlphaComposite;

import java.io.File;
import java.io.IOException;

public class TelaTabuleiro extends JFrame {

    public TelaTabuleiro(ArrayList<String> jogadores) {
        setTitle("Clue - Tabuleiro");
        setSize(1400, 1050);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new PainelTabuleiro(jogadores));
        setVisible(true);
    }

    public static void main(String[] args) {
        ArrayList<String> jogadores = new ArrayList<>();
        
        jogadores.add("Scarlet");
        jogadores.add("Mustard");
        jogadores.add("White");
        jogadores.add("Green");
        jogadores.add("Peacock");
        jogadores.add("Plum");
        
        new TelaTabuleiro(jogadores);
    }
}

class PainelTabuleiro extends JPanel {

    private Image imagemTabuleiro;
    private ArrayList<String> jogadores; // ← adicionado

    private static final int OFFSET_X = 110;
    private static final int OFFSET_Y = 124;
    private static final int CELL_W = 72;
    private static final int CELL_H = 66;

    public PainelTabuleiro(ArrayList<String> jogadores) {
        this.jogadores = jogadores; // ← adicionado
        try {
            imagemTabuleiro = ImageIO.read(
                new File("Imagens/Tabuleiros/Tabuleiro-Original.JPG")
            );
        } catch (IOException e) {
            System.out.println("Erro ao carregar tabuleiro");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imagemTabuleiro, 0, 0, getWidth(), getHeight(), null);

        double escalaX = (double) getWidth()  / 1707;
        double escalaY = (double) getHeight() / 1714;

        Graphics2D g2d = (Graphics2D) g;

        // -----------------------------------
        // HITBOXES DOS CÔMODOS
        // -----------------------------------
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        g2d.setColor(Color.RED);

        desenharHitbox(g2d, escalaX, escalaY, 0,  0,  5,  6);  // Cozinha
        desenharHitbox(g2d, escalaX, escalaY, 7,  2,  7,  5);  // Sala de Música
        desenharHitbox(g2d, escalaX, escalaY, 15, 1,  6,  4);  // Jardim de Inverno
        desenharHitbox(g2d, escalaX, escalaY, 0,  8,  7,  7);  // Sala de Jantar
        desenharHitbox(g2d, escalaX, escalaY, 15, 7,  6,  4);  // Salão de Jogos
        desenharHitbox(g2d, escalaX, escalaY, 0,  17, 6,  5);  // Sala de Estar
        desenharHitbox(g2d, escalaX, escalaY, 8,  16, 5,  8);  // Entrada
        desenharHitbox(g2d, escalaX, escalaY, 15, 12, 5,  5);  // Biblioteca
        desenharHitbox(g2d, escalaX, escalaY, 15, 19, 6,  5);  // Escritório

        // -----------------------------------
        // PIÕES DOS JOGADORES Posições iniciais
        // -----------------------------------
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        desenharPiao(g2d, escalaX, escalaY, 6,  22, Color.RED,              "Scarlet", jogadores);
        desenharPiao(g2d, escalaX, escalaY, 0,  15, Color.YELLOW,           "Mustard", jogadores);
        desenharPiao(g2d, escalaX, escalaY, 7,  0,  Color.WHITE,            "White",   jogadores);
        desenharPiao(g2d, escalaX, escalaY, 12, 0,  Color.GREEN,            "Green",   jogadores);
        desenharPiao(g2d, escalaX, escalaY, 19, 5, Color.BLUE,             "Peacock", jogadores);
        desenharPiao(g2d, escalaX, escalaY, 19, 17, new Color(128, 0, 128), "Plum",    jogadores);
    }

    private void desenharHitbox(Graphics2D g2d,
                                double escalaX, double escalaY,
                                int col, int lin,
                                int largCelulas, int altCelulas) {

        int x = (int) ((OFFSET_X + col * CELL_W) * escalaX);
        int y = (int) ((OFFSET_Y + lin * CELL_H) * escalaY);
        int w = (int) (largCelulas * CELL_W * escalaX);
        int h = (int) (altCelulas  * CELL_H * escalaY);

        g2d.fillRect(x, y, w, h);
    }

    // -----------------------------------
    // Desenha o pião apenas se o personagem
    // foi selecionado na TelaSelecao
    // -----------------------------------
    private void desenharPiao(Graphics2D g2d,
                              double escalaX, double escalaY,
                              int col, int lin,
                              Color cor, String nome,
                              ArrayList<String> jogadores) {

        // Só desenha se foi selecionado
        if (!jogadores.contains(nome)) return;

        int x = (int) ((OFFSET_X + col * CELL_W) * escalaX);
        int y = (int) ((OFFSET_Y + lin * CELL_H) * escalaY);
        int w = (int) (CELL_W * escalaX);
        int h = (int) (CELL_H * escalaY);

        // Círculo colorido
        g2d.setColor(cor);
        g2d.fillOval(x, y, w, h);

        // Borda preta para destacar
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, w, h);
    }
}