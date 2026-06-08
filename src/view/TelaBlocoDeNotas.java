package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

public class TelaBlocoDeNotas extends JFrame {

    public TelaBlocoDeNotas() {
        setTitle("Bloco de Notas - Clue");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        add(new PainelBlocoDeNotas());
        setVisible(true);
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

    // Categorias e cartas do Clue clássico
    private static final String[] SUSPEITOS = {
        "Scarlet", "Mustard", "White", "Green", "Peacock", "Plum"
    };
    private static final String[] ARMAS = {
        "Faca", "Candelabro", "Revólver", "Corda", "Cano de Chumbo", "Chave Inglesa"
    };
    private static final String[] COMODOS = {
        "Cozinha", "Salão de Baile", "Conservatório", "Sala de Bilhar",
        "Biblioteca", "Estudo", "Hall", "Sala de Jantar", "Lounge"
    };

    // Mapeia cada carta ao seu JCheckBox correspondente
    private final Map<String, JCheckBox> checkboxes = new LinkedHashMap<>();

    public PainelBlocoDeNotas() {
        setLayout(new BorderLayout());
        setBackground(COR_FUNDO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ---- Título ----
        JLabel titulo = new JLabel("Bloco de Notas", SwingConstants.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD, 26));
        titulo.setForeground(COR_TITULO_GERAL);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // ---- Painel central com as três seções ----
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(COR_FUNDO);

        painelCentral.add(criarSecao("🔪 Armas",     ARMAS));
        painelCentral.add(Box.createRigidArea(new Dimension(0, 12)));
        painelCentral.add(criarSecao("👤 Suspeitos",  SUSPEITOS));
        painelCentral.add(Box.createRigidArea(new Dimension(0, 12)));
        painelCentral.add(criarSecao("🏠 Cômodos",   COMODOS));

        // Scroll caso a janela fique pequena
        JScrollPane scroll = new JScrollPane(painelCentral);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COR_FUNDO);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        // ---- Rodapé com botões ----
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelRodape.setBackground(COR_FUNDO);

        JButton botaoLimpar = criarBotao("Limpar Tudo");
        JButton botaoFechar = criarBotao("Fechar");

        botaoLimpar.addActionListener(e -> limparTudo());
        botaoFechar.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());

        painelRodape.add(botaoLimpar);
        painelRodape.add(botaoFechar);
        add(painelRodape, BorderLayout.SOUTH);
    }

    // Cria uma seção do bloco de notas (Armas, Suspeitos ou Cômodos)
    private JPanel criarSecao(String titulo, String[] itens) {
        // Painel externo que usa Java2D para o visual
        PainelSecaoDesenhado secao = new PainelSecaoDesenhado(COR_SECAO_FUNDO, COR_BORDA);
        secao.setLayout(new BoxLayout(secao, BoxLayout.Y_AXIS));
        secao.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        secao.setAlignmentX(Component.LEFT_ALIGNMENT);
        secao.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Título da seção
        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Georgia", Font.BOLD, 16));
        labelTitulo.setForeground(COR_LABEL_SECAO);
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        secao.add(labelTitulo);
        secao.add(Box.createRigidArea(new Dimension(0, 8)));

        // Grid de checkboxes: 2 colunas
        JPanel grid = new JPanel(new GridLayout(0, 2, 4, 4));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String item : itens) {
            JCheckBox cb = new JCheckBox(item);
            cb.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cb.setForeground(COR_TEXTO_ITEM);
            cb.setOpaque(false);
            cb.setFocusPainted(false);
            cb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            checkboxes.put(item, cb);
            grid.add(cb);
        }

        secao.add(grid);
        return secao;
    }

    // Desmarca todos os checkboxes
    private void limparTudo() {
        for (JCheckBox cb : checkboxes.values()) {
            cb.setSelected(false);
        }
    }

    // Retorna quais cartas estão marcadas (para uso externo pelo controlador)
    public ArrayList<String> getCartasMarcadas() {
        ArrayList<String> marcadas = new ArrayList<>();
        for (Map.Entry<String, JCheckBox> entry : checkboxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                marcadas.add(entry.getKey());
            }
        }
        return marcadas;
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

        // Fundo arredondado
        g2d.setColor(corFundo);
        g2d.fillRoundRect(0, 0, w, h, arco, arco);

        // Borda arredondada
        g2d.setColor(corBorda);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(0, 0, w - 1, h - 1, arco, arco);

        g2d.dispose();
    }
}
