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

// ← import model.Jogador removido

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

    private static final int OFFSET_X = 0;
    private static final int OFFSET_Y = 0;
    private static final int CELL_W = 25;
    private static final int CELL_H = 25;
    private static final int IMG_W = 600;
    private static final int IMG_H = 625;

    private int jogadorAtualIdx = 0;
    private String nomeJogadorAtual = "";
    private Color corJogadorAtual = Color.RED;
    private boolean podeMover = false;

    private int clickCol = -1;
    private int clickLin = -1;

    private int scarletCol = 6,  scarletLin = 22;
    private int mustardCol = 0,  mustardLin = 15;
    private int whiteCol   = 7,  whiteLin   = 0;
    private int greenCol   = 12, greenLin   = 0;
    private int peacockCol = 19, peacockLin = 5;
    private int plumCol    = 19, plumLin    = 17;

    public PainelTabuleiro(ArrayList<String> jogadores) {

        this.jogadores = jogadores;

        model = new ClueModel();

        // ← mudança: usa o novo método sem expor Jogador
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

        JPanel painelDados = new JPanel();
        painelDados.setLayout(null);
        painelDados.setOpaque(false);
        painelDados.setBounds(580, 340, 300, 350);

        JPanel painelImagens = new JPanel();
        painelImagens.setLayout(null);
        painelImagens.setOpaque(false);
        painelImagens.setBounds(20, 20, 260, 120);

        JLabel labelDado1 = new JLabel();
        JLabel labelDado2 = new JLabel();
        labelDado1.setBounds(70, 40, 100, 100);
        labelDado2.setBounds(140, 40, 100, 100);
        painelImagens.add(labelDado1);
        painelImagens.add(labelDado2);

        JLabel labelNumero = new JLabel("Total: ?");
        labelNumero.setForeground(Color.BLACK);
        labelNumero.setFont(new Font("Arial", Font.BOLD, 24));
        labelNumero.setBounds(110, 240, 200, 30);

        JButton botaoDados = new JButton("Rolar Dados");
        botaoDados.setBounds(75, 200, 150, 40);

        botaoDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

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
                            int total = resultadoDado1 + resultadoDado2;
                            labelNumero.setText("Total: " + total);
                            podeMover = true;
                            timerDado.stop();
                        } else {
                            dadoAtual1 = (int)(Math.random() * 6);
                            dadoAtual2 = (int)(Math.random() * 6);
                        }

                        int tamanho;
                        if (frameAnimacao < 7) {
                            tamanho = 60 + frameAnimacao * 8;
                        } else {
                            tamanho = 116 - (frameAnimacao - 7) * 8;
                        }

                        Image img1 = imagensDado[dadoAtual1].getScaledInstance(
                            tamanho, tamanho, Image.SCALE_SMOOTH
                        );
                        Image img2 = imagensDado[dadoAtual2].getScaledInstance(
                            tamanho, tamanho, Image.SCALE_SMOOTH
                        );

                        labelDado1.setIcon(new ImageIcon(img1));
                        labelDado2.setIcon(new ImageIcon(img2));
                        labelDado1.repaint();
                        labelDado2.repaint();
                    }
                });

                timerDado.start();
            }
        });

        painelDados.add(painelImagens);
        painelDados.add(labelNumero);
        painelDados.add(botaoDados);
        add(painelDados);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (!podeMover) return;

                int mouseX = e.getX();
                int mouseY = e.getY();

                double escalaX = (double) getWidth()  / IMG_W;
                double escalaY = (double) getHeight() / IMG_H;

                int col = (int)((mouseX / escalaX) / CELL_W);
                int lin = (int)((mouseY / escalaY) / CELL_H);

                System.out.println("Clique → col: " + col + " lin: " + lin);

                int idCasa = lin * 24 + col;

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
        if (nome.equals("Mustard"))  return 15 * 24 + 0;
        if (nome.equals("White"))    return 0  * 24 + 7;
        if (nome.equals("Green"))    return 0  * 24 + 12;
        if (nome.equals("Peacock"))  return 5  * 24 + 19;
        if (nome.equals("Plum"))     return 17 * 24 + 19;
        return 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imagemTabuleiro, 0, 0, getWidth(), getHeight(), null);

        double escalaX = (double) getWidth()  / IMG_W;
        double escalaY = (double) getHeight() / IMG_H;

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(corJogadorAtual);
        g2d.fillRoundRect(20, 20, 280, 60, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 28));
        g2d.drawString("Vez de: " + nomeJogadorAtual, 40, 60);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        desenharPiao(g2d, escalaX, escalaY, scarletCol, scarletLin, Color.RED,              "Scarlet", jogadores);
        desenharPiao(g2d, escalaX, escalaY, mustardCol, mustardLin, Color.YELLOW,           "Mustard", jogadores);
        desenharPiao(g2d, escalaX, escalaY, whiteCol,   whiteLin,   Color.WHITE,            "White",   jogadores);
        desenharPiao(g2d, escalaX, escalaY, greenCol,   greenLin,   Color.GREEN,            "Green",   jogadores);
        desenharPiao(g2d, escalaX, escalaY, peacockCol, peacockLin, Color.BLUE,             "Peacock", jogadores);
        desenharPiao(g2d, escalaX, escalaY, plumCol,    plumLin,    new Color(128, 0, 128), "Plum",    jogadores);

        if (clickCol != -1 && clickLin != -1) {
            int x = (int)((clickCol * CELL_W) * escalaX);
            int y = (int)((clickLin * CELL_H) * escalaY);
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

        int x = (int)(col * CELL_W * escalaX);
        int y = (int)(lin * CELL_H * escalaY);
        int w = (int)(CELL_W * escalaX);
        int h = (int)(CELL_H * escalaY);

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

    private void atualizarJogadorAtual() {

        nomeJogadorAtual = jogadores.get(jogadorAtualIdx);

        if      (nomeJogadorAtual.equals("Scarlet"))  corJogadorAtual = Color.RED;
        else if (nomeJogadorAtual.equals("Mustard"))  corJogadorAtual = Color.YELLOW;
        else if (nomeJogadorAtual.equals("White"))    corJogadorAtual = Color.WHITE;
        else if (nomeJogadorAtual.equals("Green"))    corJogadorAtual = Color.GREEN;
        else if (nomeJogadorAtual.equals("Peacock"))  corJogadorAtual = Color.BLUE;
        else if (nomeJogadorAtual.equals("Plum"))     corJogadorAtual = new Color(128, 0, 128);
    }

    private void proximoJogador() {

        jogadorAtualIdx++;
        if (jogadorAtualIdx >= jogadores.size()) jogadorAtualIdx = 0;

        model.proximoJogador();
        atualizarJogadorAtual();
        repaint();
    }
}