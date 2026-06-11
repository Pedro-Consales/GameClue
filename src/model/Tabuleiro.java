package model;

public class Tabuleiro {

    static final int LINHAS = 25;
    static final int COLUNAS = 24;

    private Casa[][] grade;

    // CONSTRUTOR
    public Tabuleiro() {
        grade = new Casa[LINHAS][COLUNAS];
    }

    // =========================================
    // SALVA A MATRIZ MONTADA PELO BUILDER
    // =========================================
    public void setMatriz(Casa[][] matriz) {
        this.grade = matriz;
    }

    // =========================================
    // BUSCA POR ID
    // id = lin * COLUNAS + col
    // =========================================
    public Casa getCasa(int id) {
        int lin = id / COLUNAS;
        int col = id % COLUNAS;
        if (lin < 0 || lin >= LINHAS || col < 0 || col >= COLUNAS) {
            return null;
        }
        return grade[lin][col];
    }

    // =========================================
    // BUSCA POR LINHA E COLUNA
    // =========================================
    public Casa getCasa(int lin, int col) {
        if (lin < 0 || lin >= LINHAS || col < 0 || col >= COLUNAS) {
            return null;
        }
        return grade[lin][col];
    }

    // =========================================
    // GETTERS E UTILITÁRIOS
    // =========================================
    public Casa[][] getGrade() { return grade; }

    public static int toId(int lin, int col) { return lin * COLUNAS + col; }
    public static int toLinha(int id) { return id / COLUNAS; }
    public static int toColuna(int id) { return id % COLUNAS; }
}