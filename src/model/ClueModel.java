package model;

import java.util.*;

public class ClueModel {

    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private Dado dado1;
    private Dado dado2;
    private int jogadorAtual;
    private int[] ultimoDado;

    public ClueModel() {
        tabuleiro = new Tabuleiro();
        jogadores = new ArrayList<>();
        dado1 = new Dado();
        dado2 = new Dado();
        jogadorAtual = 0;
        TabuleiroBuilder.popular(tabuleiro);
    }

    public int[] lancarDados() {
        int d1 = dado1.jogarDados();
        int d2 = dado2.jogarDados();
        ultimoDado = new int[]{d1, d2};
        return ultimoDado;
    }

    public void setDadosParaTeste(int d1, int d2) {
        ultimoDado = new int[]{d1, d2};
    }

    public List<Casa> mapearCasas(int[] dados) {
        int passos = dados[0] + dados[1];
        Jogador jogador = getJogadorAtual();
        Casa origem = tabuleiro.getCasa(jogador.getPosicao());
        Set<Casa> visitadas = new HashSet<>();
        Queue<Casa> fila = new LinkedList<>();
        fila.add(origem);
        visitadas.add(origem);
        int nivel = 0;
        while (nivel < passos) {
            int tamanho = fila.size();
            for (int i = 0; i < tamanho; i++) {
                Casa atual = fila.poll();
                for (Casa vizinho : atual.getVizinhos()) {
                    if (!visitadas.contains(vizinho)) {
                        visitadas.add(vizinho);
                        fila.add(vizinho);
                    }
                }
            }
            nivel++;
        }
        return new ArrayList<>(fila);
    }

    public void deslocarPiao(int idCasa) {
        Jogador jogador = getJogadorAtual();
        List<Casa> possiveis = mapearCasas(ultimoDado);
        for (Casa c : possiveis) {
            if (c.getId() == idCasa) {
                jogador.mover(idCasa);
                return;
            }
        }
        throw new IllegalArgumentException("Movimento inválido!");
    }

    public Jogador getJogadorAtual() {
        return jogadores.get(jogadorAtual);
    }
    
    public int getPosicaoJogadorAtual() {
        return getJogadorAtual().getPosicao();
    }

    public void proximoJogador() {
        jogadorAtual = (jogadorAtual + 1) % jogadores.size();
    }

    // ← método antigo mantido para os testes existentes não quebrarem
    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    // ← novo método — View usa esse, sem precisar conhecer Jogador
    public void adicionarJogador(int id, String nome, int posicaoInicial) {
        Jogador j = new Jogador();
        j.setId(id);
        j.setNome(nome);
        j.mover(posicaoInicial);
        jogadores.add(j);
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}