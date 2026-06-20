package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import model.ClueModel;
import model.Observador;

// Janela que exibe as cartas da mão do jogador atual (imagens via drawImage).
public class TelaCartas extends JFrame implements Observador {

    private static final Color COR_FUNDO = new Color(26, 36, 43);

    // nome da carta -> caminho da imagem (recuperação em tempo constante)
    private static final Map<String, String> CAMINHOS = new HashMap<>();
    static {
        CAMINHOS.put("Srta. Scarlet",   "Imagens/Suspeitos/Scarlet.jpg");
        CAMINHOS.put("Coronel Mustard", "Imagens/Suspeitos/Mustard.jpg");
        CAMINHOS.put("Sra. White",      "Imagens/Suspeitos/White.jpg");
        CAMINHOS.put("Reverendo Green", "Imagens/Suspeitos/Green.jpg");
        CAMINHOS.put("Sra. Peacock",    "Imagens/Suspeitos/Peacock.jpg");
        CAMINHOS.put("Professor Plum",  "Imagens/Suspeitos/Plum.jpg");

        CAMINHOS.put("Corda",          "Imagens/Armas/Corda.jpg");
        CAMINHOS.put("Cano de Chumbo", "Imagens/Armas/Cano.jpg");
        CAMINHOS.put("Faca",           "Imagens/Armas/Faca.jpg");
        CAMINHOS.put("Chave Inglesa",  "Imagens/Armas/ChaveInglesa.jpg");
        CAMINHOS.put("Castiçal",       "Imagens/Armas/Castical.jpg");
        CAMINHOS.put("Revólver",       "Imagens/Armas/Revolver.jpg");

        CAMINHOS.put("Cozinha",           "Imagens/Comodos/Cozinha.jpg");
        CAMINHOS.put("Sala de Música",    "Imagens/Comodos/SalaDeMusica.jpg");
        CAMINHOS.put("Salão de Jogos",    "Imagens/Comodos/SalaoDeJogos.jpg");
        CAMINHOS.put("Biblioteca",        "Imagens/Comodos/Biblioteca.jpg");
        CAMINHOS.put("Sala de Jantar",    "Imagens/Comodos/SalaDeJantar.jpg");
        CAMINHOS.put("Sala de Estar",     "Imagens/Comodos/SalaDeEstar.jpg");
        CAMINHOS.put("Entrada",           "Imagens/Comodos/Entrada.jpg");
        CAMINHOS.put("Escritório",        "Imagens/Comodos/Escritorio.jpg");
        CAMINHOS.put("Jardim de Inverno", "Imagens/Comodos/JardimInverno.jpg");
    }

    private final ClueModel model;
    private final JLabel titulo;
    private final JPanel painelCartas;

    public TelaCartas() {
        this.model = ClueModel.getInstance();

        setTitle("Cartas do Jogador");
        setSize(760, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(COR_FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        titulo = new JLabel("", SwingConstants.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        conteudo.add(titulo, BorderLayout.NORTH);

        painelCartas = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelCartas.setBackground(COR_FUNDO);

        JScrollPane scroll = new JScrollPane(painelCartas);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COR_FUNDO);
        conteudo.add(scroll, BorderLayout.CENTER);

        add(conteudo);

        model.adicionarObservador(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                model.removerObservador(TelaCartas.this);
            }
        });

        montarCartas();
        setVisible(true);
    }

    @Override
    public void atualizar() {
        montarCartas();
    }

    private void montarCartas() {
        titulo.setText("Cartas de " + model.getNomeJogadorAtual());

        painelCartas.removeAll();
        List<String> nomes = model.getNomesMaoJogadorAtual();
        for (String nome : nomes) {
            String caminho = CAMINHOS.get(nome);
            painelCartas.add(new PainelImagem(caminho, 170, 240));
        }

        painelCartas.revalidate();
        painelCartas.repaint();
    }
}
