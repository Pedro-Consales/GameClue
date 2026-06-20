package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ClueModelTest {

    private ClueModel model;

    @Before
    public void setUp() {
        model = ClueModel.getInstance();
        model.novaPartida();
        model.adicionarJogador(1, "Jogador 1", 0);
        model.adicionarJogador(2, "Jogador 2", 0);
    }

    // ----------------------------
    // DADOS
    // ----------------------------
    @Test
    public void deveLancarDadosValidos() {
        int[] dados = model.lancarDados();

        assertEquals(2, dados.length);
        assertTrue(dados[0] >= 1 && dados[0] <= 6);
        assertTrue(dados[1] >= 1 && dados[1] <= 6);
    }

    // ----------------------------
    // PROXIMO JOGADOR
    // ----------------------------
    @Test
    public void deveAlternarJogador() {
        String primeiro = model.getNomeJogadorAtual();

        model.proximoJogador();

        assertNotEquals(primeiro, model.getNomeJogadorAtual());
    }

    // ----------------------------
    // DISTRIBUIÇÃO DE CARTAS
    // ----------------------------
    @Test
    public void deveDistribuirDezoitoCartasSemRepeticao() {
        model.prepararPartida();

        List<String> todas = new ArrayList<>();
        todas.addAll(model.getNomesMaoJogadorAtual());
        model.proximoJogador();
        todas.addAll(model.getNomesMaoJogadorAtual());

        assertEquals(18, todas.size());

        Set<String> unicas = new HashSet<>(todas);
        assertEquals(18, unicas.size());
    }

    @Test
    public void todasAsCartasSomamVinteEUma() {
        model.prepararPartida();

        Set<String> nomes = new HashSet<>();
        EnvelopeConfidencial env = model.getEnvelope();
        nomes.add(env.getSuspeito().getNome());
        nomes.add(env.getArma().getNome());
        nomes.add(env.getComodo().getNome());

        nomes.addAll(model.getNomesMaoJogadorAtual());
        model.proximoJogador();
        nomes.addAll(model.getNomesMaoJogadorAtual());

        assertEquals(21, nomes.size());
    }

    // ----------------------------
    // ACUSAÇÃO
    // ----------------------------
    @Test
    public void verificarAcusacaoCorreta() {
        model.prepararPartida();
        EnvelopeConfidencial env = model.getEnvelope();

        assertTrue(model.verificarAcusacao(
            env.getSuspeito().getNome(),
            env.getArma().getNome(),
            env.getComodo().getNome()));
    }

    @Test
    public void verificarAcusacaoErrada() {
        model.prepararPartida();
        EnvelopeConfidencial env = model.getEnvelope();

        String armaErrada = null;
        for (String arma : model.getNomesArmas()) {
            if (!arma.equals(env.getArma().getNome())) {
                armaErrada = arma;
                break;
            }
        }

        assertFalse(model.verificarAcusacao(
            env.getSuspeito().getNome(),
            armaErrada,
            env.getComodo().getNome()));
    }
}
