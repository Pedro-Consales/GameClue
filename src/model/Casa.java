package model;

import java.util.ArrayList;
import java.util.List;

public class Casa {

    private int id;
    private int linha;
    private int coluna;
    private TipoCasa tipo;

    // Nome do cômodo ao qual a casa pertence.
    // Será null quando a casa não for de um cômodo.
    private String nomeComodo;

    private List<Casa> vizinhos;

    // Construtor mantido para compatibilidade
    public Casa(
            int id,
            int linha,
            int coluna,
            TipoCasa tipo) {

        this(id, linha, coluna, tipo, null);
    }

    // Construtor com nome do cômodo
    public Casa(
            int id,
            int linha,
            int coluna,
            TipoCasa tipo,
            String nomeComodo) {

        this.id = id;
        this.linha = linha;
        this.coluna = coluna;
        this.tipo = tipo;
        this.nomeComodo = nomeComodo;

        this.vizinhos = new ArrayList<Casa>();
    }

    // =========================
    // FUNÇÕES AUXILIARES
    // =========================

    public void adicionarVizinho(Casa casa) {

        if (casa == null) {
            return;
        }

        if (!vizinhos.contains(casa)) {
            vizinhos.add(casa);
        }
    }

    public boolean isParede() {
        return tipo == TipoCasa.PAREDE;
    }

    public boolean isPorta() {
        return tipo == TipoCasa.PORTA;
    }

    public boolean isComodo() {
        return tipo == TipoCasa.COMODO;
    }

    public boolean isCaminho() {
        return tipo == TipoCasa.CAMINHO;
    }

    // =========================
    // GETTERS
    // =========================

    public int getId() {
        return id;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public TipoCasa getTipo() {
        return tipo;
    }

    public String getNomeComodo() {
        return nomeComodo;
    }

    public List<Casa> getVizinhos() {
        return vizinhos;
    }

    // =========================
    // SETTERS
    // =========================

    public void setTipo(TipoCasa tipo) {
        this.tipo = tipo;
    }

    public void setNomeComodo(String nomeComodo) {
        this.nomeComodo = nomeComodo;
    }

    @Override
    public String toString() {

        String descricao =
            "Casa(id=" + id
            + ", lin=" + linha
            + ", col=" + coluna
            + ", tipo=" + tipo;

        if (nomeComodo != null) {
            descricao += ", comodo=" + nomeComodo;
        }

        return descricao + ")";
    }
}