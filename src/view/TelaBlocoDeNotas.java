package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import model.ClueModel;
import model.Observador;

public class TelaBlocoDeNotas extends JFrame implements Observador {

    private final ClueModel model;
    private final PainelBlocoDeNotas painel;

    public TelaBlocoDeNotas() {
        this.model = ClueModel.getInstance();

        setTitle("Bloco de Notas - Clue");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painel = new PainelBlocoDeNotas(model);
        add(painel);

        model.adicionarObservador(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                model.removerObservador(TelaBlocoDeNotas.this);
            }
        });

        setVisible(true);
    }

    @Override
    public void atualizar() {
        painel.sincronizar();
    }
}


class PainelBlocoDeNotas extends JPanel {

    // Cores do tema escuro, consistentes com o resto do jogo
    private static final Color COR_FUNDO        = new Color(26, 36, 43);
    private static final Color COR_SECAO_FUNDO  = new Color(40, 50, 60);
    private static final Color COR_BORDA        = new Color(70, 90, 100);
    private static final Color COR_TITULO_GERAL = Color.WHITE;
    private static final Color COR_LABEL_SECAO  = new Color(200, 200, 200);
    private static final Color COR_TEXTO_ITEM   = Color.WHITE;
    private static final Color COR_BOTAO_FUNDO  = new Color(70, 70, 70);
    private static final Color COR_BOTAO_BORDA  = new Color(100, 100, 100);

    private final ClueModel model;

    // Mapeia cada carta ao seu JCheckBox correspondente
    private final Map<String, JCheckBox> checkboxes = new LinkedHashMap<>();

    public PainelBlocoDeNotas(ClueModel model) {
        this.model = model;

        setLayout(new BorderLayout());
        setBackground(COR_FUNDO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Bloco de Notas", SwingConstants.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD, 26));
        titulo.setForeground(COR_TITULO_GERAL);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(COR_FUNDO);

        painelCentral.add(criarSecao("Armas",     model.getNomesArmas()));
        painelCentral.add(Box.createRigidArea(new Dimension(0, 12)));
        painelCentral.add(criarSecao("Suspeitos", model.getNomesSuspeitos()));
        painelCentral.add(Box.createRigidArea(new Dimension(0, 12)));
        painelCentral.add(criarSecao("Cômodos",   model.getNomesComodos()));

        JScrollPane scroll = new JScrollPane(painelCentral);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COR_FUNDO);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelRodape.setBackground(COR_FUNDO);

        JButton botaoLimpar = criarBotao("Limpar Reveladas");
        JButton botaoFechar = criarBotao("Fechar");

        botaoLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparReveladas();
            }
        });
        botaoFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(PainelBlocoDeNotas.this).dispose();
            }
        });

        painelRodape.add(botaoLimpar);
        painelRodape.add(botaoFechar);
        add(painelRodape, BorderLayout.SOUTH);

        sincronizar();
    }

    // Cria uma seção do bloco de notas (Armas, Suspeitos ou Cômodos)
    private JPanel criarSecao(String titulo, List<String> itens) {
        PainelSecaoDesenhado secao = new PainelSecaoDesenhado(COR_SECAO_FUNDO, COR_BORDA);
        secao.setLayout(new BoxLayout(secao, BoxLayout.Y_AXIS));
        secao.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        secao.setAlignmentX(Component.LEFT_ALIGNMENT);
        secao.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Georgia", Font.BOLD, 16));
        labelTitulo.setForeground(COR_LABEL_SECAO);
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        secao.add(labelTitulo);
        secao.add(Box.createRigidArea(new Dimension(0, 8)));

        JPanel grid = new JPanel(new GridLayout(0, 2, 4, 4));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String item : itens) {
            final String nome = item;
            JCheckBox cb = new JCheckBox(nome);
            cb.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cb.setForeground(COR_TEXTO_ITEM);
            cb.setOpaque(false);
            cb.setFocusPainted(false);
            cb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.marcarCartaReveladaJogadorAtual(nome, cb.isSelected());
                }
            });
            checkboxes.put(nome, cb);
            grid.add(cb);
        }

        secao.add(grid);
        return secao;
    }

    // Reflete o estado do bloco do jogador atual: próprias marcadas e travadas;
    // reveladas marcáveis.
    void sincronizar() {
        for (Map.Entry<String, JCheckBox> entry : checkboxes.entrySet()) {
            String nome = entry.getKey();
            JCheckBox cb = entry.getValue();
            boolean propria = model.ehCartaPropriaJogadorAtual(nome);
            cb.setSelected(model.cartaMarcadaJogadorAtual(nome));
            cb.setEnabled(!propria);
        }
    }

    // Desmarca todas as cartas reveladas (mantém as próprias)
    private void limparReveladas() {
        for (String nome : checkboxes.keySet()) {
            if (!model.ehCartaPropriaJogadorAtual(nome)) {
                model.marcarCartaReveladaJogadorAtual(nome, false);
            }
        }
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(COR_BOTAO_FUNDO);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_BOTAO_BORDA),
            BorderFactory.createEmptyBorder(6, 18, 6, 18)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}

// Painel personalizado que desenha um fundo e borda arredondados usando Java2D
class PainelSecaoDesenhado extends JPanel {

    private final Color corFundo;
    private final Color corBorda;

    public PainelSecaoDesenhado(Color corFundo, Color corBorda) {
        this.corFundo = corFundo;
        this.corBorda = corBorda;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arco = 12;

        g2d.setColor(corFundo);
        g2d.fillRoundRect(0, 0, w, h, arco, arco);

        g2d.setColor(corBorda);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(0, 0, w - 1, h - 1, arco, arco);

        g2d.dispose();
    }
}
