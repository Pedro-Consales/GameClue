package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

public class TelaInicial extends JFrame
                  implements ActionListener {

    private JButton botaoNovoJogo;

    private JButton botaoContinuar;

    public TelaInicial() {

        setTitle("Clue");

        setSize(1400, 1050);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        //-----------------------------------
        // FUNDO
        //-----------------------------------

        TelaFundo tela = new TelaFundo();

        //-----------------------------------
        // LAYOUT
        //-----------------------------------

        tela.setLayout(null);

        //-----------------------------------
        // BOTÕES
        //-----------------------------------

        botaoNovoJogo = new JButton("Novo Jogo");
        botaoNovoJogo.setBounds(213, 290, 157, 45);

        botaoContinuar = new JButton("Continuar");
        botaoContinuar.setBounds(213, 357, 157, 45);

        //-----------------------------------
        // EVENTOS
        //-----------------------------------

        botaoNovoJogo.addActionListener(this);

        botaoContinuar.addActionListener(this);

       
        //-----------------------------------
        // ADICIONA BOTÕES
        //-----------------------------------

        tela.add(botaoNovoJogo);

        tela.add(botaoContinuar);

        //-----------------------------------
        // ADICIONA TELA
        //-----------------------------------

        add(tela);

        setVisible(true);
    }

    //-----------------------------------
    // EVENTOS DOS BOTÕES
    //-----------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == botaoNovoJogo) {

            new TelaSelecao();
            this.dispose();
        }

        if(e.getSource() == botaoContinuar) {

            System.out.println("Continuar clicado");
        }
    }

    //-----------------------------------
    // MAIN
    //-----------------------------------

    public static void main(String[] args) {

        new TelaInicial();
    }
}

class TelaFundo extends JPanel {

    private Image imagemFundo;

    public TelaFundo() {

        try {

            imagemFundo = ImageIO.read(
                    new File("GameClue/Imagens/fundoInicio.png")
            );

        } catch(IOException e) {

            System.out.println("Erro ao carregar imagem");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(
                imagemFundo,
                0,
                0,
                getWidth(),
                getHeight(),
                null
        );
    }
}