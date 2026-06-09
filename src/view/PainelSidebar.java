package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import view.TelaBlocoDeNotas;

public class PainelSidebar extends JPanel {

    // Referência ao modelo para chamar setDadosParaTeste e usarPassagemSecreta
    private PainelTabuleiro painelTabuleiro;

    public PainelSidebar(ArrayList<String> jogadores, PainelTabuleiro painelTabuleiro) {

        this.painelTabuleiro = painelTabuleiro;

        setPreferredSize(new Dimension(220, 0));
        setBackground(new Color(45, 45, 45));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // --- Botão Passagem Secreta ---
        JButton botaoPassagem = criarBotao("Passagem Secreta");
        botaoPassagem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                painelTabuleiro.usarPassagemSecreta();
            }
        });
        add(botaoPassagem);

        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Próximo"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Mostrar Cartas"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        JButton botaoBlocoNotas = criarBotao("Bloco de Notas");
        botaoBlocoNotas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaBlocoDeNotas();
            }
        });
        add(botaoBlocoNotas);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Palpite"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Acusar"));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(criarBotao("Salvar Jogo"));

        add(Box.createVerticalGlue());

        // --- Info jogador da vez ---
        JLabel labelNome = new JLabel("Vez de: " + jogadores.get(0));
        labelNome.setForeground(Color.RED); // Scarlet começa
        painelTabuleiro.setLabelVezDe(labelNome);

        labelNome.setForeground(Color.WHITE);
        labelNome.setFont(new Font("Arial", Font.BOLD, 14));
        labelNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(labelNome);

        add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Imagens dos dados ---
        JLabel labelDado1 = new JLabel();
        JLabel labelDado2 = new JLabel();
        labelDado1.setPreferredSize(new Dimension(80, 80));
        labelDado2.setPreferredSize(new Dimension(80, 80));

        JPanel painelDados = new JPanel();
        painelDados.setOpaque(false);
        painelDados.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        painelDados.add(labelDado1);
        painelDados.add(labelDado2);
        add(painelDados);

        add(Box.createRigidArea(new Dimension(0, 5)));

        // --- Total ---
        JLabel labelNumero = new JLabel("Total: ?");
        labelNumero.setForeground(Color.WHITE);
        labelNumero.setFont(new Font("Arial", Font.BOLD, 16));
        labelNumero.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(labelNumero);

        add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Botão Jogar Dados ---
        JButton botaoJogarDados = criarBotao("Jogar Dados");
        botaoJogarDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                painelTabuleiro.rolarDados(labelDado1, labelDado2, labelNumero);
            }
        });
        add(botaoJogarDados);

        add(Box.createRigidArea(new Dimension(0, 12)));

        // -----------------------------------------------
        // JComboBox para forçar valores dos dados (teste)
        // -----------------------------------------------
        JLabel labelEscolher = new JLabel("Forçar dados (teste):");
        labelEscolher.setForeground(new Color(180, 180, 180));
        labelEscolher.setFont(new Font("Arial", Font.PLAIN, 11));
        labelEscolher.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(labelEscolher);

        add(Box.createRigidArea(new Dimension(0, 4)));

        // Cria os dois JComboBox com valores 1 a 6
        Integer[] valores = {1, 2, 3, 4, 5, 6};
        JComboBox<Integer> combo1 = new JComboBox<Integer>(valores);
        JComboBox<Integer> combo2 = new JComboBox<Integer>(valores);

        combo1.setMaximumSize(new Dimension(80, 30));
        combo2.setMaximumSize(new Dimension(80, 30));

        JPanel painelCombos = new JPanel();
        painelCombos.setOpaque(false);
        painelCombos.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 0));
        painelCombos.add(combo1);
        painelCombos.add(combo2);
        add(painelCombos);

        add(Box.createRigidArea(new Dimension(0, 4)));

        // Botão "Escolher Dados" — aplica os valores selecionados
        JButton botaoEscolherDados = criarBotao("Escolher Dados");
        botaoEscolherDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int v1 = (Integer) combo1.getSelectedItem();
                int v2 = (Integer) combo2.getSelectedItem();
                // Chama o método de teste no PainelTabuleiro,
                // que repassa ao ClueModel e atualiza os labels
                painelTabuleiro.forcarDados(v1, v2, labelDado1, labelDado2, labelNumero);
            }
        });
        add(botaoEscolherDados);

        add(Box.createRigidArea(new Dimension(0, 8)));
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
