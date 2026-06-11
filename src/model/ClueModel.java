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
        ultimoDado = new int[]{0, 0};
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

    // =========================================================
    // BFS principal
    // =========================================================
   public List<Casa> mapearCasas(int[] dados) {
    int passos = dados[0] + dados[1];
    Casa origem = tabuleiro.getCasa(getJogadorAtual().getPosicao());

    Set<Casa> visitadas = new HashSet<>();
    Set<Casa> destinos  = new HashSet<>();
    Queue<Casa> fila    = new LinkedList<>();

    visitadas.add(origem);

    if (origem.isComodo()) {
        // Flood fill para achar todas as células do cômodo
        Set<Casa> celulasComodo = new HashSet<>();
        Queue<Casa> filaComodo = new LinkedList<>();
        filaComodo.add(origem);
        celulasComodo.add(origem);

        while (!filaComodo.isEmpty()) {
            Casa atual = filaComodo.poll();
            for (Casa v : atual.getVizinhos()) {
                if (v.isComodo() && !celulasComodo.contains(v)) {
                    celulasComodo.add(v);
                    filaComodo.add(v);
                }
            }
        }

        // Pega todas as portas adjacentes ao cômodo
        for (Casa celula : celulasComodo) {
            for (Casa v : celula.getVizinhos()) {
                if (v.isPorta() && !visitadas.contains(v)) {
                    visitadas.add(v);
                    if (passos > 1) {
                        destinos.add(v);
                        fila.add(v);
                    }
                }
            }
        }

    } else {
        for (Casa vizinho : origem.getVizinhos()) {
            expandir(origem, vizinho, visitadas, destinos, fila);
        }
    }

    int niveisRestantes = passos - 1;

    for (int nivel = 0; nivel < niveisRestantes; nivel++) {
        int tamanho = fila.size();
        for (int i = 0; i < tamanho; i++) {
            Casa atual = fila.poll();
            if (atual == null) continue;
            if (atual.isComodo()) continue;
            for (Casa vizinho : atual.getVizinhos()) {
                expandir(atual, vizinho, visitadas, destinos, fila);
            }
        }
    }

    return new ArrayList<>(destinos);
}
    /**
     * Quando chega numa porta adjacente a um cômodo,
     * faz flood fill para adicionar TODAS as células do cômodo.
     */
    private void expandir(Casa atual, Casa vizinho,
                          Set<Casa> visitadas,
                          Set<Casa> destinos,
                          Queue<Casa> fila) {

        if (vizinho.isParede()) return;

        if (vizinho.isComodo()) {
            // Só entra se veio de uma porta
            if (atual.isPorta()) {
                floodFillComodo(vizinho, destinos);
            }
            return;
        }

        // Caminho ou Porta
        if (!visitadas.contains(vizinho)) {
            visitadas.add(vizinho);
            destinos.add(vizinho);
            fila.add(vizinho);
        }
    }

    /**
     * Flood fill: a partir de uma célula de cômodo,
     * adiciona todas as células contíguas do mesmo cômodo aos destinos.
     */
    private void floodFillComodo(Casa inicio, Set<Casa> destinos) {
        Queue<Casa> fila = new LinkedList<>();
        Set<Casa> visitadas = new HashSet<>();

        fila.add(inicio);
        visitadas.add(inicio);

        while (!fila.isEmpty()) {
            Casa atual = fila.poll();
            destinos.add(atual);

            for (Casa vizinho : atual.getVizinhos()) {
                if (!visitadas.contains(vizinho) && vizinho.isComodo()) {
                    visitadas.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }
    }

    public List<Casa> getCasasPossiveis() {
        return mapearCasas(ultimoDado);
    }

    public void deslocarPiao(int idCasa) {
        Casa casa = tabuleiro.getCasa(idCasa);

        if (casa == null)    throw new IllegalArgumentException("Movimento inválido! Casa não existe.");
        if (casa.isParede()) throw new IllegalArgumentException("Movimento inválido! Casa é parede.");

        for (Jogador j : jogadores) {
            if (j != getJogadorAtual() && j.getPosicao() == idCasa) {
                throw new IllegalArgumentException("Movimento inválido! Casa ocupada por " + j.getNome());
            }
        }

        for (Casa c : getCasasPossiveis()) {
            if (c.getId() == idCasa) {
                getJogadorAtual().mover(idCasa);
                return;
            }
        }

        throw new IllegalArgumentException("Movimento inválido! Casa id=" + idCasa + " fora do alcance.");
    }

    public void usarPassagemSecreta(int idDestino) {
        Casa destino = tabuleiro.getCasa(idDestino);
        if (destino == null || destino.isParede()) {
            throw new IllegalArgumentException("Passagem Secreta: destino inválido.");
        }
        getJogadorAtual().mover(idDestino);
    }

    public int getValorGrade(int lin, int col) {
        return TabuleiroBuilder.GRADE[lin][col];
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

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

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