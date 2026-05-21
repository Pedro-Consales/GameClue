package view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Component;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import javax.swing.SwingUtilities;


public class TelaSelecao extends JFrame {

    public TelaSelecao() {

        setTitle("Seleção de Personagens");
        setSize(1400, 1050);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new PainelSelecao());

        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaSelecao();
    }
}

// ===========================================
// PAINEL PRINCIPAL
// Equivalente ao <body> no HTML
// display: flex; flex-direction: column;
// background-color: #1a242b;
// ===========================================
class PainelSelecao extends JPanel {

    private JButton botaoJogar;
    private ArrayList<String> selecionados = new ArrayList<>();
    private JLabel labelErro;
    private JLabel labelSuccess;

    public void printJogadoresSelecionados(ArrayList<String> selecionados) {
    	
    	System.out.println("=== JOGADORES SELECIONADOS ===\n");
    	for (String s : selecionados) {
            System.out.println(s);
        }
    	
    }

    public PainelSelecao() {


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        setBackground(new Color(26, 36, 43));


        setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 0, 40, 0));

        // ===========================================
        // Label TÍTULO
       
        JLabel titulo = new JLabel("Selecione os personagens a serem controlados");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titulo);

        add(Box.createRigidArea(new Dimension(0, 40)));

        // ===========================================
        // JPanel (Caixa) DE SELEÇÃO
        // 
        // ===========================================
        JPanel painelCards = new JPanel();

        painelCards.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        painelCards.setBackground(new Color(26, 36, 43));

    
        String[] nomes = {"Scarlet", "Mustard", "White", "Green", "Peacock", "Plum"};

        for (String nome : nomes) {

            String caminho = "Imagens/Suspeitos/" + nome + ".jpg";

            painelCards.add(criarCard(nome, caminho));
        }

        add(painelCards);

        add(Box.createRigidArea(new Dimension(0, 30)));

        // ===========================================
        // Caixa do BOTÃO
        // ===========================================
        botaoJogar = new JButton("JOGAR");
        botaoJogar.setPreferredSize(new Dimension(200, 50));
        botaoJogar.setMinimumSize(new Dimension(200, 50));
        botaoJogar.setMaximumSize(new Dimension(200, 50));
        botaoJogar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        labelErro = new JLabel("");
        labelErro.setForeground(Color.RED);
        labelErro.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        labelSuccess = new JLabel("");
        labelSuccess.setForeground(Color.GREEN);
        labelSuccess.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(labelErro);
        add(labelSuccess);
        add(Box.createRigidArea(new Dimension(0, 10))); 
        
        
        

        add(botaoJogar);

        // ===========================================
        // EVENTO BOTÃO JOGAR
        // ===========================================
        botaoJogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (selecionados.size() < 2) {
                	
                	labelErro.setText("Mínimo de 2 personagens para jogar!");
                     
                	
                	printJogadoresSelecionados(selecionados);
                	System.out.printf("\n\nQuantidade de jogadores inválida, precisa ter no mínimo 2 jogadores\n");
                	System.out.println("==============================\n\n");
                	
                	return;
                }
                
                if (selecionados.size() > 6) { //Caso impossível de cair já que só tem 6 personagens, verificação por segurança
                    labelErro.setText("Máximo de 6 personagens!");
                    
                    printJogadoresSelecionados(selecionados);
                	System.out.printf("\n\nQuantidade de jogadores inválida, precisa ter no máximo 6 jogadores\n");
                	System.out.println("==============================\n\n");
                    
                    return;
                }
                
                labelErro.setText("");
                labelSuccess.setText("Jogadores Selecionados: " + selecionados);
                System.out.println("Jogadores Selecionados: " + selecionados);
                
                new TelaTabuleiro(selecionados);
                ((JFrame) SwingUtilities.getWindowAncestor(PainelSelecao.this)).dispose();
                
            }
        });
    }

    // ===========================================
    // MÉTODO CRIAR CARD
    // ===========================================
    private JPanel criarCard(String nome, String caminhoImagem) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(40, 50, 60));
        card.setPreferredSize(new Dimension(180, 310));

        // ===========================================
        // Caixa IMAGEM
        // ===========================================
        PainelImagem painelImg = new PainelImagem(caminhoImagem, 180, 260);
        painelImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===========================================
        // Caixa CHECKBOX
        // ===========================================
        JCheckBox check = new JCheckBox(nome);
        check.setBackground(new Color(40, 50, 60));
        check.setForeground(Color.WHITE);
        check.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Evento sem lambda
        check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizar(nome, check.isSelected());
            }
        });

        card.add(painelImg);
        card.add(check);

        return card;
    }

    // Atualiza lista de selecionados
    private void atualizar(String nome, boolean selecionado) {
        if (selecionado) {
            if (!selecionados.contains(nome)) {
                selecionados.add(nome);
            }
        } else {
            selecionados.remove(nome);
        }
    }
}

// ===========================================
// PAINEL DE IMAGEM
// em vez de JLabel + ImageIcon,
// criamos um JPanel que sobrescreve o
// paintComponent() e usa drawImage()
// ===========================================
class PainelImagem extends JPanel {

    private Image imagem;
    private int largura;
    private int altura;

    public PainelImagem(String caminho, int largura, int altura) {

        this.largura = largura;
        this.altura = altura;

       
        setPreferredSize(new Dimension(largura, altura));
        setMaximumSize(new Dimension(largura, altura));
        setMinimumSize(new Dimension(largura, altura));

        try {
            imagem = ImageIO.read(new File(caminho));
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + caminho);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagem != null) {
            // drawImage() — obrigatório
            g.drawImage(imagem, 0, 0, largura, altura, null);
        }
    }
}