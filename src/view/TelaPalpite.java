package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import model.ClueModel;

/**
 * JDialog reutilizável para Palpite e Acusação Final.
 *
 * No Palpite, o cômodo é travado no cômodo onde o jogador atual
 * está (regra do Clue: o palpite sempre usa o cômodo atual).
 *
 * Na Acusação, o jogador escolhe livremente os três combos,
 * já que a acusação pode apontar para qualquer cômodo do tabuleiro.
 */
public class TelaPalpite extends JDialog {

    public interface OnConfirmar {
        void confirmar(String suspeito, String arma, String comodo);
    }

    private static final Color COR_FUNDO   = new Color(26, 36, 43);
    private static final Color COR_TEXTO   = Color.WHITE;
    private static final Color COR_BOTAO   = new Color(70, 70, 70);
    private static final Color COR_BORDA   = new Color(100, 100, 100);

    private JComboBox<String> comboSuspeito;
    private JComboBox<String> comboArma;
    private JComboBox<String> comboComodo;

    public TelaPalpite(
            Window owner,
            boolean ehAcusacao,
            String comodoTravado,
            OnConfirmar callback) {

        super(owner, ehAcusacao ? "Acusação Final" : "Palpite", ModalityType.APPLICATION_MODAL);

        ClueModel model = ClueModel.getInstance();

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(COR_FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titulo = new JLabel(ehAcusacao ? "Faça sua Acusação Final" : "Faça seu Palpite");
        titulo.setFont(new Font("Georgia", Font.BOLD, 20));
        titulo.setForeground(COR_TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        conteudo.add(titulo);
        conteudo.add(Box.createRigidArea(new Dimension(0, 15)));

        List<String> suspeitos = model.getNomesSuspeitos();
        List<String> armas     = model.getNomesArmas();
        List<String> comodos   = model.getNomesComodos();

        comboSuspeito = new JComboBox<>(suspeitos.toArray(new String[0]));
        comboArma     = new JComboBox<>(armas.toArray(new String[0]));
        comboComodo   = new JComboBox<>(comodos.toArray(new String[0]));

        conteudo.add(criarLinhaCombo("Suspeito:", comboSuspeito));
        conteudo.add(Box.createRigidArea(new Dimension(0, 10)));
        conteudo.add(criarLinhaCombo("Arma:", comboArma));
        conteudo.add(Box.createRigidArea(new Dimension(0, 10)));
        conteudo.add(criarLinhaCombo("Cômodo:", comboComodo));

        if (!ehAcusacao && comodoTravado != null) {
            comboComodo.setSelectedItem(comodoTravado);
            comboComodo.setEnabled(false);
        }

        conteudo.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        painelBotoes.setOpaque(false);

        JButton botaoConfirmar = criarBotao(ehAcusacao ? "Acusar" : "Palpitar");
        JButton botaoCancelar  = criarBotao("Cancelar");

        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suspeito = (String) comboSuspeito.getSelectedItem();
                String arma     = (String) comboArma.getSelectedItem();
                String comodo   = (String) comboComodo.getSelectedItem();

                dispose();
                callback.confirmar(suspeito, arma, comodo);
            }
        });

        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        painelBotoes.add(botaoConfirmar);
        painelBotoes.add(botaoCancelar);
        conteudo.add(painelBotoes);

        setContentPane(conteudo);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private JPanel criarLinhaCombo(String rotulo, JComboBox<String> combo) {
        JPanel linha = new JPanel();
        linha.setLayout(new BoxLayout(linha, BoxLayout.Y_AXIS));
        linha.setOpaque(false);
        linha.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(rotulo);
        label.setForeground(COR_TEXTO);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        combo.setMaximumSize(new Dimension(300, 30));
        combo.setPreferredSize(new Dimension(300, 30));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);

        linha.add(label);
        linha.add(Box.createRigidArea(new Dimension(0, 4)));
        linha.add(combo);

        return linha;
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(COR_BOTAO);
        btn.setForeground(COR_TEXTO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_BORDA),
            BorderFactory.createEmptyBorder(6, 16, 6, 16)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}