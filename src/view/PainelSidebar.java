package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PainelSidebar extends JPanel {

    public PainelSidebar(ArrayList<String> jogadores) {

        setPreferredSize(new Dimension(220, 0));
        setBackground(new Color(45, 45, 45));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        add(criarBotao("Passagem Secreta"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Próximo"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Mostrar Cartas"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Bloco de Notas"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Palpite"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Acusar"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Salvar Jogo"));

        add(Box.createVerticalGlue());

        // --- Info jogador da vez ---
        JLabel labelNome = new JLabel("Vez de: ...");
        labelNome.setForeground(Color.WHITE);
        labelNome.setFont(new Font("Arial", Font.BOLD, 14));
        labelNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(labelNome);

        add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Imagens dos dados ---
        JPanel painelDados = new JPanel();
        painelDados.setOpaque(false);
        painelDados.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JLabel dado1 = new JLabel("🎲");
        JLabel dado2 = new JLabel("🎲");
        dado1.setFont(new Font("Arial", Font.PLAIN, 40));
        dado2.setFont(new Font("Arial", Font.PLAIN, 40));
        painelDados.add(dado1);
        painelDados.add(dado2);
        add(painelDados);

        add(Box.createRigidArea(new Dimension(0, 10)));

        add(criarBotao("Jogar Dados"));
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        return btn;
    }
}