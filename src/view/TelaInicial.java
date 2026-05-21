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

import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Component;

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
        // LAYOUT -> tela.setLayout()-> Não pode ser null pois ai tudo que a gente vai usar de estilização fica como posicionamento em pixels na tela, tipo absolute em css... Eh melhor usar o layouCorreto pois fazemos como se tivessem caixas e estilização para as caixas
        //-----------------------------------

        tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

        botaoNovoJogo = new JButton("Novo Jogo");
        botaoContinuar = new JButton("Continuar");
        
        int widthBotao = 300;
        int hightBotao = 50;
        
        Dimension tamanhoBotao = new Dimension(widthBotao, hightBotao);
        
        botaoNovoJogo.setPreferredSize(tamanhoBotao);  // tamanho ideal
        botaoNovoJogo.setMinimumSize(tamanhoBotao);    // tamanho mínimo
        botaoNovoJogo.setMaximumSize(tamanhoBotao);    // tamanho máximo
        botaoNovoJogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        botaoContinuar.setPreferredSize(tamanhoBotao);
        botaoContinuar.setMinimumSize(tamanhoBotao);
        botaoContinuar.setMaximumSize(tamanhoBotao);
        botaoContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);

        tela.add(Box.createVerticalGlue());           // espaço de cima
        tela.add(botaoNovoJogo);
        tela.add(Box.createRigidArea(new Dimension(0, 12))); // gap entre botões
        tela.add(botaoContinuar);
        tela.add(Box.createVerticalGlue()); 

        //-----------------------------------
        // EVENTOS
        //-----------------------------------

        botaoNovoJogo.addActionListener(this);

        botaoContinuar.addActionListener(this);

       


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
                    new File("Imagens/ClueMenuBg.png")
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