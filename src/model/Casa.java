package model;

import java.util.ArrayList;
import java.util.List;



public class Casa {

    private int id;

    private int linha;
    private int coluna;
   
    private TipoCasa tipo;

    private List<Casa> vizinhos;

    // referência opcional para um cômodo
    // você pode implementar depois
    // private Comodo comodo;

    // CONSTRUTOR
    public Casa(int id, int linha, int coluna, TipoCasa tipo) {

        this.id = id;
        this.linha = linha;
        this.coluna = coluna;
        this.tipo = tipo;

        this.vizinhos = new ArrayList<Casa>();
    }

    // =========================
    // FUNÇÕES AUXILIARES
    // =========================

    public void adicionarVizinho(Casa casa) {

        // evita vizinho nulo
        if (casa == null) {
            return;
        }

        // evita duplicação
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

    public List<Casa> getVizinhos() {
        return vizinhos;
    }

    // =========================
    // SETTERS
    // =========================

    public void setTipo(TipoCasa tipo) {
        this.tipo = tipo;
    }
}