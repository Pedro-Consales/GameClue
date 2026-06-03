package view;

import javax.swing.JFrame;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.AlphaComposite;

import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.ClueModel;
import model.Casa;
import java.util.List;

public class TelaTabuleiro extends JFrame {

	public TelaTabuleiro(ArrayList<String> jogadores) {
        setTitle("Clue - Tabuleiro");
        setSize(1400, 1050);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new java.awt.BorderLayout());
        
        PainelTabuleiro painelTabuleiro = new PainelTabuleiro(jogadores);
        add(painelTabuleiro, java.awt.BorderLayout.CENTER);
        add(new PainelSidebar(jogadores, painelTabuleiro), java.awt.BorderLayout.EAST);

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
    private ArrayList<String> jogadores;
    private ClueModel model;

    private Image[] imagensDado = new Image[6];
    private int dadoAtual1 = 0;
    private int dadoAtual2 = 0;
    private int resultadoDado1 = 0;
    private int resultadoDado2 = 0;
    private int frameAnimacao = 0;
    private boolean animando = false;
    private Timer timerDado;

    private static final int OFFSET_X = 10; // ← ajuste aqui
    private static final int OFFSET_Y = 10; // ← ajuste aqui
    private static final double CELL_W = (600 - 2.0 * OFFSET_X) / 24.0;  // ← double
    private static final double CELL_H = (625 - 2.0 * OFFSET_Y) / 25.0;  // ← double
    private static final int IMG_W = 600;
    private static final int IMG_H = 625;

    private int jogadorAtualIdx = 0;
    private String nomeJogadorAtual = "";
    private Color corJogadorAtual = Color.RED;
    private boolean podeMover = false;

    private int clickCol = -1;
    private int clickLin = -1;

    private int scarletCol = 7,  scarletLin = 24;
    private int mustardCol = 0,  mustardLin = 17;
    private int whiteCol   = 9,  whiteLin   = 0;
    private int greenCol   = 14, greenLin   = 0;
    private int peacockCol = 23, peacockLin = 6;
    private int plumCol    = 23, plumLin    = 19;

    public void rolarDados(JLabel labelDado1, JLabel labelDado2, JLabel labelNumero) {

        if (animando) return;
        if (podeMover) return;

        int[] dados = model.lancarDados();
        resultadoDado1 = dados[0];
        resultadoDado2 = dados[1];

        frameAnimacao = 0;
        animando = true;

        timerDado = new Timer(80, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameAnimacao++;
                if (frameAnimacao >= 15) {
                    animando = false;
                    dadoAtual1 = resultadoDado1 - 1;
                    dadoAtual2 = resultadoDado2 - 1;
                    labelNumero.setText("Total: " + (resultadoDado1 + resultadoDado2));
                    podeMover = true;
                    timerDado.stop();
                    repaint();
                } else {
                    dadoAtual1 = (int)(Math.random() * 6);
                    dadoAtual2 = (int)(Math.random() * 6);
                }

                int tamanho = frameAnimacao < 7 ? 60 + frameAnimacao * 8 : 116 - (frameAnimacao - 7) * 8;

                labelDado1.setIcon(new ImageIcon(imagensDado[dadoAtual1].getScaledInstance(tamanho, tamanho, Image.SCALE_SMOOTH)));
                labelDado2.setIcon(new ImageIcon(imagensDado[dadoAtual2].getScaledInstance(tamanho, tamanho, Image.SCALE_SMOOTH)));
            }
        });
        timerDado.start();
    }

    public PainelTabuleiro(ArrayList<String> jogadores) {

        this.jogadores = jogadores;

        model = new ClueModel();

        for (int i = 0; i < jogadores.size(); i++) {
            model.adicionarJogador(
                i,
                jogadores.get(i),
                getPosicaoInicial(jogadores.get(i))
            );
        }

        atualizarJogadorAtual();

        try {
            imagemTabuleiro = ImageIO.read(
                new File("Imagens/Tabuleiros/Tabuleiro-Clue-C.jpg")
            );
        } catch (IOException e) {
            System.out.println("Erro ao carregar tabuleiro");
        }

        for (int i = 0; i < 6; i++) {
            try {
                imagensDado[i] = ImageIO.read(
                    new File("Imagens/Tabuleiros/dado" + (i + 1) + ".jpg")
                );
            } catch (IOException e) {
                System.out.println("Erro ao carregar dado" + (i + 1));
            }
        }

        setLayout(null);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (!podeMover) return;

                int mouseX = e.getX();
                int mouseY = e.getY();

                double escalaX = (double) getWidth()  / IMG_W;
                double escalaY = (double) getHeight() / IMG_H;

                // ← desconta o offset ao calcular col/lin
                int col = (int)(((mouseX / escalaX) - OFFSET_X) / CELL_W);
                int lin = (int)(((mouseY / escalaY) - OFFSET_Y) / CELL_H);

                System.out.println("Clique → col: " + col + " lin: " + lin);

                int idCasa = lin * 24 + col;

                System.out.println("=== DEBUG ===");
                System.out.println("Posicao jogador: " + model.getPosicaoJogadorAtual());
                System.out.println("idCasa clicada: " + idCasa);
                System.out.println("col: " + col + " lin: " + lin);
                System.out.println("Casas possíveis:" + model.getCasasPossiveis());
                System.out.println("=============");

                try {
                    model.deslocarPiao(idCasa);
                    clickCol = col;
                    clickLin = lin;
                    moverPiaoVisual(col, lin);
                    podeMover = false;
                    proximoJogador();
                } catch (IllegalArgumentException ex) {
                    System.out.println("Movimento inválido para col:" + col + " lin:" + lin);
                }
            }
        });
    }

    private int getPosicaoInicial(String nome) {
        if (nome.equals("Scarlet"))  return 22 * 24 + 6;
        if (nome.equals("Mustard"))  return 17 * 24 + 1;
        if (nome.equals("White"))    return 1  * 24 + 9;
        if (nome.equals("Green"))    return 1  * 24 + 14;
        if (nome.equals("Peacock"))  return 6  * 24 + 22;
        if (nome.equals("Plum"))     return 19 * 24 + 19;
        return 0;
    }

    private void desenharGrid(Graphics2D g2d, double escalaX, double escalaY) {
        g2d.setColor(new Color(255, 0, 0, 80));
        g2d.setFont(new Font("Arial", Font.PLAIN, 8));

        for (int lin = 0; lin < 25; lin++) {
            for (int col = 0; col < 24; col++) {
                // ← offset aplicado
                int x = (int)(OFFSET_X * escalaX + col * CELL_W * escalaX);
                int y = (int)(OFFSET_Y * escalaY + lin * CELL_H * escalaY);
                int w = (int)(CELL_W * escalaX);
                int h = (int)(CELL_H * escalaY);

                g2d.drawRect(x, y, w, h);
                g2d.setColor(new Color(255, 0, 0, 180));
                g2d.drawString(col + "," + lin, x + 2, y + 10);
                g2d.setColor(new Color(255, 0, 0, 80));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imagemTabuleiro, 0, 0, getWidth(), getHeight(), null);

        double escalaX = (double) getWidth()  / IMG_W;
        double escalaY = (double) getHeight() / IMG_H;

        Graphics2D g2d = (Graphics2D) g;

        desenharGrid(g2d, escalaX, escalaY);

        if (podeMover) {
            List<Casa> possiveis = model.getCasasPossiveis();
            g2d.setColor(new Color(0, 255, 0, 80));
            for (Casa casa : possiveis) {
                int col = casa.getColuna();
                int lin = casa.getLinha();
                // ← offset aplicado
                int x = (int)(OFFSET_X * escalaX + col * CELL_W * escalaX);
                int y = (int)(OFFSET_Y * escalaY + lin * CELL_H * escalaY);
                int w = (int)(CELL_W * escalaX);
                int h = (int)(CELL_H * escalaY);
                g2d.fillRect(x, y, w, h);
            }
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        desenharPiao(g2d, escalaX, escalaY, scarletCol, scarletLin, Color.RED,              "Scarlet", jogadores);
        desenharPiao(g2d, escalaX, escalaY, mustardCol, mustardLin, Color.YELLOW,           "Mustard", jogadores);
        desenharPiao(g2d, escalaX, escalaY, whiteCol,   whiteLin,   Color.WHITE,            "White",   jogadores);
        desenharPiao(g2d, escalaX, escalaY, greenCol,   greenLin,   Color.GREEN,            "Green",   jogadores);
        desenharPiao(g2d, escalaX, escalaY, peacockCol, peacockLin, Color.BLUE,             "Peacock", jogadores);
        desenharPiao(g2d, escalaX, escalaY, plumCol,    plumLin,    new Color(128, 0, 128), "Plum",    jogadores);

        if (clickCol != -1 && clickLin != -1) {
            // ← offset aplicado
            int x = (int)(OFFSET_X * escalaX + clickCol * CELL_W * escalaX);
            int y = (int)(OFFSET_Y * escalaY + clickLin * CELL_H * escalaY);
            int w = (int)(CELL_W * escalaX);
            int h = (int)(CELL_H * escalaY);
            g2d.setColor(new Color(0, 255, 0, 120));
            g2d.fillRect(x, y, w, h);
        }
    }

    private void desenharPiao(Graphics2D g2d,
            double escalaX, double escalaY,
            int col, int lin,
            Color cor, String nome,
            ArrayList<String> jogadores) {

        if (!jogadores.contains(nome)) return;

        int cellPxW = (int)(CELL_W * escalaX);
        int cellPxH = (int)(CELL_H * escalaY);

        // ← offset aplicado
        int cx = (int)(OFFSET_X * escalaX + col * CELL_W * escalaX);
        int cy = (int)(OFFSET_Y * escalaY + lin * CELL_H * escalaY);

        int margem = 3;
        int x = cx + margem;
        int y = cy + margem;
        int w = cellPxW - margem * 2;
        int h = cellPxH - margem * 2;

        g2d.setColor(cor);
        g2d.fillOval(x, y, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, w, h);
    }

    private void moverPiaoVisual(int col, int lin) {

        String nomeAtual = jogadores.get(jogadorAtualIdx);

        if      (nomeAtual.equals("Scarlet"))  { scarletCol = col; scarletLin = lin; }
        else if (nomeAtual.equals("Mustard"))  { mustardCol = col; mustardLin = lin; }
        else if (nomeAtual.equals("White"))    { whiteCol   = col; whiteLin   = lin; }
        else if (nomeAtual.equals("Green"))    { greenCol   = col; greenLin   = lin; }
        else if (nomeAtual.equals("Peacock"))  { peacockCol = col; peacockLin = lin; }
        else if (nomeAtual.equals("Plum"))     { plumCol    = col; plumLin    = lin; }

        repaint();
    }

    private JLabel labelVezDe; // ← adiciona como variável da classe

    public void setLabelVezDe(JLabel label) {
        this.labelVezDe = label;
    }

    private void atualizarJogadorAtual() {

        nomeJogadorAtual = jogadores.get(jogadorAtualIdx);

        if      (nomeJogadorAtual.equals("Scarlet"))  corJogadorAtual = Color.RED;
        else if (nomeJogadorAtual.equals("Mustard"))  corJogadorAtual = Color.YELLOW;
        else if (nomeJogadorAtual.equals("White"))    corJogadorAtual = Color.WHITE;
        else if (nomeJogadorAtual.equals("Green"))    corJogadorAtual = Color.GREEN;
        else if (nomeJogadorAtual.equals("Peacock"))  corJogadorAtual = Color.BLUE;
        else if (nomeJogadorAtual.equals("Plum"))     corJogadorAtual = new Color(128, 0, 128);

        if (labelVezDe != null) {
            labelVezDe.setText("Vez de: " + nomeJogadorAtual);
            labelVezDe.setForeground(corJogadorAtual);
        }
    }

    private void proximoJogador() {
        clickCol = -1;
        clickLin = -1;

        jogadorAtualIdx++;
        if (jogadorAtualIdx >= jogadores.size()) jogadorAtualIdx = 0;

        model.proximoJogador();
        atualizarJogadorAtual();
        repaint();
    }
}