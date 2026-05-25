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

    private Image[] imagensDado = new Image[6];
    private int dadoAtual1 = 0;
    private int dadoAtual2 = 0;
    private int resultadoDado1 = 0;
    private int resultadoDado2 = 0;
    private int frameAnimacao = 0;
    private boolean animando = false;
    private Timer timerDado;

    private static final int OFFSET_X = 110;
    private static final int OFFSET_Y = 120;
    private static final int CELL_W = 71;
    private static final int CELL_H = 66;

    private int jogadorAtual = 0;
    private String nomeJogadorAtual = "";
    private Color corJogadorAtual = Color.RED;
    private boolean podeMover = false;
    private int passosDisponiveis = 0;

    private int scarletCol = 6;
    private int scarletLin = 22;

    private int mustardCol = 0;
    private int mustardLin = 15;

    private int whiteCol = 7;
    private int whiteLin = 0;

    private int greenCol = 12;
    private int greenLin = 0;

    private int peacockCol = 19;
    private int peacockLin = 5;

    private int plumCol = 19;
    private int plumLin = 17;

    private int clickCol = -1;
    private int clickLin = -1;
   
    public PainelTabuleiro(ArrayList<String> jogadores) {

        this.jogadores = jogadores;
        atualizarJogadorAtual();
        // -----------------------------------
        // CARREGA TABULEIRO
        // -----------------------------------
        try {
            imagemTabuleiro = ImageIO.read(
                new File("Imagens/Tabuleiros/Tabuleiro-Original.JPG")
            );
        } catch (IOException e) {
            System.out.println("Erro ao carregar tabuleiro");
        }

        // -----------------------------------
        // CARREGA FACES DOS DADOS
        // -----------------------------------
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

        // -----------------------------------
        // PAINEL OVERLAY DOS DADOS
        // -----------------------------------
        JPanel painelDados = new JPanel();
        painelDados.setLayout(null);
        painelDados.setOpaque(false);
        painelDados.setBounds(580, 340, 300, 350);

        // -----------------------------------
        // PAINEL DAS IMAGENS DOS DADOS
        // -----------------------------------
        JPanel painelImagens = new JPanel();
        painelImagens.setLayout(null);
        painelImagens.setOpaque(false);
        painelImagens.setBounds(20, 20, 260, 120);

        // -----------------------------------
        // LABELS DOS DADOS
        // -----------------------------------
        JLabel labelDado1 = new JLabel();
        JLabel labelDado2 = new JLabel();

        labelDado1.setBounds(70, 40, 100, 100);
        labelDado2.setBounds(140, 40, 100, 100);

        painelImagens.add(labelDado1);
        painelImagens.add(labelDado2);

        // -----------------------------------
        // LABEL TOTAL
        // -----------------------------------
        JLabel labelNumero = new JLabel("Total: ?");
        labelNumero.setForeground(Color.BLACK);
        labelNumero.setFont(new Font("Arial", Font.BOLD, 24));
        labelNumero.setBounds(110, 240, 200, 30);

        // -----------------------------------
        // BOTÃO
        // -----------------------------------
        JButton botaoDados = new JButton("Rolar Dados");
        botaoDados.setBounds(75, 200, 150, 40);

        // -----------------------------------
        // EVENTO
        // -----------------------------------
        botaoDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (animando) return;

                resultadoDado1 = (int)(Math.random() * 6) + 1;
                resultadoDado2 = (int)(Math.random() * 6) + 1;

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

                            passosDisponiveis = total;
                            podeMover = true;

                            timerDado.stop();

                        } else {

                            dadoAtual1 = (int)(Math.random() * 6);
                            dadoAtual2 = (int)(Math.random() * 6);
                        }

                        // -----------------------------------
                        // ANIMAÇÃO TAMANHO
                        // -----------------------------------
                        int tamanho;

                        if (frameAnimacao < 7) {
                            tamanho = 60 + frameAnimacao * 8;
                        } else {
                            tamanho = 116 - (frameAnimacao - 7) * 8;
                        }

                        // -----------------------------------
                        // REDIMENSIONA DADOS
                        // -----------------------------------
                        Image img1 = imagensDado[dadoAtual1].getScaledInstance(
                            tamanho,
                            tamanho,
                            Image.SCALE_SMOOTH
                        );

                        Image img2 = imagensDado[dadoAtual2].getScaledInstance(
                            tamanho,
                            tamanho,
                            Image.SCALE_SMOOTH
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

        // -----------------------------------
        // ADICIONA COMPONENTES
        // -----------------------------------
        painelDados.add(painelImagens);
        painelDados.add(labelNumero);
        painelDados.add(botaoDados);

        add(painelDados);

        // -----------------------------------
        // CLIQUE NO TABULEIRO
        // -----------------------------------
        addMouseListener(new MouseAdapter() {

            @Override
         public void mouseClicked(MouseEvent e) {

            if (!podeMover) return;

            int mouseX = e.getX();
            int mouseY = e.getY();
            System.out.println(
                        "getX: " + mouseX +
                        " getY: " + mouseY
                    );

            int col = (mouseX - OFFSET_X) / CELL_W;
            int lin = (mouseY - OFFSET_Y) / CELL_H;

            clickCol = col;
            clickLin = lin;

            System.out.println(
                "Linha: " + lin +
                " Coluna: " + col
            );

            moverJogador(col, lin);

            podeMover = false;

            proximoJogador();
            }
        });
        }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imagemTabuleiro, 0, 0, getWidth(), getHeight(), null);

        double escalaX = (double) getWidth()  / 1707;
        double escalaY = (double) getHeight() / 1714;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(corJogadorAtual);
        g2d.fillRoundRect(20, 20, 280, 60, 20, 20);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 28));

        g2d.drawString(
            "Vez de: " + nomeJogadorAtual,
            40,
            60
        );
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
        // PIÕES DOS JOGADORES
        // -----------------------------------
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        desenharPiao(g2d, escalaX, escalaY, scarletCol,  scarletLin, Color.RED, "Scarlet", jogadores);
        desenharPiao(g2d, escalaX, escalaY, mustardCol,  mustardLin, Color.YELLOW, "Mustard", jogadores);
        desenharPiao(g2d, escalaX, escalaY, whiteCol,  whiteLin,  Color.WHITE, "White", jogadores);
        desenharPiao(g2d, escalaX, escalaY, greenCol, greenLin,  Color.GREEN,"Green", jogadores);
        desenharPiao(g2d, escalaX, escalaY, peacockCol, peacockLin,  Color.BLUE, "Peacock", jogadores);
        desenharPiao(g2d, escalaX, escalaY, plumCol, plumLin, new Color(128, 0, 128), "Plum", jogadores);
    
        if (clickCol != -1 && clickLin != -1) {

            int x = (int)((OFFSET_X + clickCol * CELL_W) * escalaX);
            int y = (int)((OFFSET_Y + clickLin * CELL_H) * escalaY);

            int w = (int)(CELL_W * escalaX);
            int h = (int)(CELL_H * escalaY);

            g2d.setColor(new Color(0, 255, 0, 120));
            g2d.fillRect(x, y, w, h);
        }
        g2d.setColor(Color.BLACK);
/*
        for (int lin = 0; lin < 25; lin++) {

            for (int col = 0; col < 25; col++) {

                int x = (int)((OFFSET_X + col * CELL_W) * escalaX);
                int y = (int)((OFFSET_Y + lin * CELL_H) * escalaY);

                int w = (int)(CELL_W * escalaX);
                int h = (int)(CELL_H * escalaY);

                g2d.drawRect(x, y, w, h);

                g2d.drawString(
                    col + "," + lin,
                    x + 5,
                    y + 20
                );
            }
        }*/
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

    private void desenharPiao(Graphics2D g2d,
                              double escalaX, double escalaY,
                              int col, int lin,
                              Color cor, String nome,
                              ArrayList<String> jogadores) {

        if (!jogadores.contains(nome)) return;

        int x = (int) ((OFFSET_X + col * CELL_W) * escalaX);
        int y = (int) ((OFFSET_Y + lin * CELL_H) * escalaY);
        int w = (int) (CELL_W * escalaX);
        int h = (int) (CELL_H * escalaY);

        g2d.setColor(cor);
        g2d.fillOval(x, y, w, h);

        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, w, h);
    }

    private void atualizarJogadorAtual() {

        nomeJogadorAtual = jogadores.get(jogadorAtual);

        if (nomeJogadorAtual.equals("Scarlet")) {
            corJogadorAtual = Color.RED;
        }
        else if (nomeJogadorAtual.equals("Mustard")) {
            corJogadorAtual = Color.YELLOW;
        }
        else if (nomeJogadorAtual.equals("White")) {
            corJogadorAtual = Color.WHITE;
        }
        else if (nomeJogadorAtual.equals("Green")) {
            corJogadorAtual = Color.GREEN;
        }
        else if (nomeJogadorAtual.equals("Peacock")) {
            corJogadorAtual = Color.BLUE;
        }
        else if (nomeJogadorAtual.equals("Plum")) {
            corJogadorAtual = new Color(128, 0, 128);
        }
    }

    private void proximoJogador() {

        jogadorAtual++;

        if (jogadorAtual >= jogadores.size()) {
            jogadorAtual = 0;
        }

        atualizarJogadorAtual();

        repaint();
    }

    private void moverJogador(int col, int lin) {

        String nomeAtual = jogadores.get(jogadorAtual);

        if (nomeAtual.equals("Scarlet")) {
            scarletCol = col;
            scarletLin = lin;
        }
        else if (nomeAtual.equals("Mustard")) {
            mustardCol = col;
            mustardLin = lin;
        }
        else if (nomeAtual.equals("White")) {
            whiteCol = col;
            whiteLin = lin;
        }
        else if (nomeAtual.equals("Green")) {
            greenCol = col;
            greenLin = lin;
        }
        else if (nomeAtual.equals("Peacock")) {
            peacockCol = col;
            peacockLin = lin;
        }
        else if (nomeAtual.equals("Plum")) {
            plumCol = col;
            plumLin = lin;
        }

        repaint();
    }
}