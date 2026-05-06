package model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClueModelTest {

    private ClueModel model;

    @Before
    public void setUp() {
        model = new ClueModel();

        // Criando casas
        Casa c1 = new Casa(); c1.setId(1);
        Casa c2 = new Casa(); c2.setId(2);
        Casa c3 = new Casa(); c3.setId(3);

        // Adicionando no tabuleiro
        model.getTabuleiro().adicionarCasa(c1);
        model.getTabuleiro().adicionarCasa(c2);
        model.getTabuleiro().adicionarCasa(c3);

        // Conectando
        model.getTabuleiro().conectarCasas(1, 2);
        model.getTabuleiro().conectarCasas(2, 3);

        // Criando jogador
        Jogador j = new Jogador();
        j.setId(1);
        j.setNome("Jogador 1");
        j.mover(1);

        model.adicionarJogador(j);
    }

    // ----------------------------
    // 🎲 TESTE DADOS
    // ----------------------------
    @Test
    public void deveLancarDadosValidos() {
        int[] dados = model.lancarDados();

        assertEquals(2, dados.length);

        assertTrue(dados[0] >= 1 && dados[0] <= 6);
        assertTrue(dados[1] >= 1 && dados[1] <= 6);
    }

    // ----------------------------
    // 🗺️ TESTE MAPEAR CASAS
    // ----------------------------
    @Test
    public void deveMapearCasasAlcancaveis() {

        int[] dados = {1, 0}; // 1 passo

        List<Casa> casas = model.mapearCasas(dados);

        assertEquals(1, casas.size());
        assertEquals(2, casas.get(0).getId());
    }

    // ----------------------------
    // 🚶 TESTE MOVIMENTO
    // ----------------------------
    @Test
    public void deveMoverJogadorParaCasaValida() {

        model.setDadosParaTeste(1, 0); // 1 passo

        model.deslocarPiao(2);

        Jogador j = model.getJogadorAtual();

        assertEquals(2, j.getPosicao());
    }

    // ----------------------------
    // ❌ TESTE MOVIMENTO INVALIDO
    // ----------------------------
    @Test(expected = IllegalArgumentException.class)
    public void naoDeveMoverParaCasaInvalida() {

        model.setDadosParaTeste(1, 0); // só 1 passo

        model.deslocarPiao(3); // impossível
    }
    // ----------------------------
    // 🔄 TESTE PROXIMO JOGADOR
    // ----------------------------
    @Test
    public void deveAlternarJogador() {

        Jogador j1 = model.getJogadorAtual();

        Jogador j2 = new Jogador();
        j2.setId(2);
        j2.setNome("Jogador 2");
        j2.mover(1);

        model.adicionarJogador(j2);

        model.proximoJogador();

        Jogador atual = model.getJogadorAtual();

        assertNotEquals(j1.getId(), atual.getId());
    }
}