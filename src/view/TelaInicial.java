package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.imageio.ImageIO;

import model.SalvarJogo;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Component;

public class TelaInicial extends JFrame implements ActionListener {

    private JButton botaoNovoJogo;
    private JButton botaoContinuar;

    public TelaInicial() {

        setTitle("Clue");
        setSize(1400, 1050);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        TelaFundo tela = new TelaFundo();
        tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

        botaoNovoJogo = new JButton("Novo Jogo");
        botaoContinuar = new JButton("Continuar");

        int widthBotao = 300;
        int hightBotao = 50;
        Dimension tamanhoBotao = new Dimension(widthBotao, hightBotao);

        botaoNovoJogo.setPreferredSize(tamanhoBotao);
        botaoNovoJogo.setMinimumSize(tamanhoBotao);
        botaoNovoJogo.setMaximumSize(tamanhoBotao);
        botaoNovoJogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        botaoContinuar.setPreferredSize(tamanhoBotao);
        botaoContinuar.setMinimumSize(tamanhoBotao);
        botaoContinuar.setMaximumSize(tamanhoBotao);
        botaoContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);

        tela.add(Box.createVerticalGlue());
        tela.add(botaoNovoJogo);
        tela.add(Box.createRigidArea(new Dimension(0, 12)));
        tela.add(botaoContinuar);
        tela.add(Box.createVerticalGlue());

        botaoNovoJogo.addActionListener(this);
        botaoContinuar.addActionListener(this);

        add(tela);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == botaoNovoJogo) {
            new TelaSelecao();
            this.dispose();
        }

        if (e.getSource() == botaoContinuar) {
            if (SalvarJogo.existeSave()) {
                Map<String, String> save = SalvarJogo.carregar();
                new TelaTabuleiro(save);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum jogo salvo encontrado!");
            }
        }
    }

    public static void main(String[] args) {
        new TelaInicial();
    }
}

class TelaFundo extends JPanel {

    private Image imagemFundo;

    public TelaFundo() {
        try {
            imagemFundo = ImageIO.read(new File("Imagens/ClueMenuBg.png"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar imagem");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), null);
    }
}