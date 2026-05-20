package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class TelaSelecao extends JFrame {

    public TelaSelecao() {

        setTitle("Seleção de Personagens");

        setSize(1400, 1050);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        add(new PainelSelecao());

        setVisible(true);
    }
}

class PainelSelecao extends JPanel {

    private Image  img1, img2, img3, img4, img5, img6;

    private JCheckBox scarlet;
    private JCheckBox mustard;
    private JCheckBox white;
    private JCheckBox green;
    private JCheckBox peacock;
    private JCheckBox plum;

    private JButton botaoJogar;

    private ArrayList<String> selecionados = new ArrayList<>();

    public PainelSelecao() {

        setLayout(null);

        try {
    

            img1 = ImageIO.read(new File("GameClue/Imagens/Suspeitos/Scarlet.jpg"));
            img2 = ImageIO.read(new File("GameClue/Imagens/Suspeitos/Mustard.jpg"));
            img3 = ImageIO.read(new File("GameClue/Imagens/Suspeitos/White.jpg"));
            img4 = ImageIO.read(new File("GameClue/Imagens/Suspeitos/Green.jpg"));
            img5 = ImageIO.read(new File("GameClue/Imagens/Suspeitos/Peacock.jpg"));
            img6 = ImageIO.read(new File("GameClue/Imagens/Suspeitos/Plum.jpg"));

        } catch (IOException e) {

            System.out.println("Erro ao carregar imagens");
        }

        //-----------------------------------
        // CHECKBOXES
        //-----------------------------------

        scarlet = new JCheckBox("Scarlet");
        mustard = new JCheckBox("Mustard");
        white = new JCheckBox("White");
        green = new JCheckBox("Green");
        peacock = new JCheckBox("Peacock");
        plum = new JCheckBox("Plum");

        scarlet.setBounds(1050, 600, 200, 30);
        mustard.setBounds(1050, 640, 200, 30);
        white.setBounds(1050, 680, 200, 30);
        green.setBounds(1050, 720, 200, 30);
        peacock.setBounds(1050, 760, 200, 30);
        plum.setBounds(1050, 800, 200, 30);

        add(scarlet);
        add(mustard);
        add(white);
        add(green);
        add(peacock);
        add(plum);

        //-----------------------------------
        // BOTÃO JOGAR
        //-----------------------------------

        botaoJogar = new JButton("JOGAR");
        botaoJogar.setBounds(1050, 850, 200, 50);

        add(botaoJogar);

        //-----------------------------------
        // EVENTOS CHECKBOX
        //-----------------------------------

        scarlet.addActionListener(e -> atualizar("Scarlet", scarlet.isSelected()));
        mustard.addActionListener(e -> atualizar("Mustard", mustard.isSelected()));
        white.addActionListener(e -> atualizar("White", white.isSelected()));
        green.addActionListener(e -> atualizar("Green", green.isSelected()));
        peacock.addActionListener(e -> atualizar("Peacock", peacock.isSelected()));
        plum.addActionListener(e -> atualizar("Plum", plum.isSelected()));

        //-----------------------------------
        // BOTÃO JOGAR
        //-----------------------------------

        botaoJogar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                System.out.println("=== JOGADORES SELECIONADOS ===");

                for (String s : selecionados) {
                    System.out.println(s);
                }

                System.out.println("==============================");
            }
        });
    }

    private void atualizar(String nome, boolean selecionado) {

        if (selecionado) {

            if (!selecionados.contains(nome)) {
                selecionados.add(nome);
            }

        } else {

            selecionados.remove(nome);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        
        setBackground(new Color(26, 36, 43));
        

        int w = 310;
        int h = 490;

        int x = 20;
        int y = 10;

        int gapX = 20;
        int gapY = 20;

        g.drawImage(img1, x, y, w, h, null);
        g.drawImage(img2, x + (w + gapX), y, w, h, null);
        g.drawImage(img3, x + 2 * (w + gapX), y, w, h, null);
        g.drawImage(img4, x + 3 * (w + gapX), y, w, h, null);

        int y2 = y + h + gapY;

        g.drawImage(img5, x, y2, w, h, null);
        g.drawImage(img6, x + (w + gapX), y2, w, h, null);
    }
}