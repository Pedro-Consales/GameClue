package model;

import java.util.ArrayList;
import java.util.List;

class Jogador {

    private int id;
    private String nome;
    private int posicao;

    private List<Carta> cartas;
    private BlocoDeNotas bloco;

    // Jogador que errou a acusação final
    private boolean eliminado;

    public Jogador() {
        cartas = new ArrayList<>();
        bloco = new BlocoDeNotas();
        eliminado = false;
    }

    // =========================
    // FUNÇÕES AUXILIARES
    // =========================

    public void adicionarCarta(Carta carta) {

        if (carta != null && !cartas.contains(carta)) {
            cartas.add(carta);
        }
    }

    public void removerCarta(Carta carta) {
        cartas.remove(carta);
    }

    public int quantidadeCartas() {
        return cartas.size();
    }

    public void mover(int novaPosicao) {
        this.posicao = novaPosicao;
    }

    /*
     * Procura a primeira carta da mão que consiga
     * refutar o palpite recebido.
     *
     * Mesmo eliminado, o jogador continua podendo
     * mostrar cartas para refutar outros jogadores.
     */
    public Carta encontrarCartaQueRefuta(
            String suspeito,
            String arma,
            String comodo) {

        for (Carta carta : cartas) {

            String nomeCarta = carta.getNome();

            if (nomeCarta.equals(suspeito)
                    || nomeCarta.equals(arma)
                    || nomeCarta.equals(comodo)) {

                return carta;
            }
        }

        return null;
    }

    // =========================
    // GETTERS
    // =========================

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPosicao() {
        return posicao;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public BlocoDeNotas getBloco() {
        return bloco;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    // =========================
    // SETTERS
    // =========================

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}