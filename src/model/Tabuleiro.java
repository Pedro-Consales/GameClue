package model;

class Tabuleiro {

    // Grade 24 linhas x 22 colunas — mesma proporção do tabuleiro original
    static final int LINHAS = 24;
    static final int COLUNAS = 22;

    // id = lin * COLUNAS + col
    // Posição na grade: lin = id / COLUNAS   col = id % COLUNAS
    private Casa[][] grade;

    // Inicializador
    {
        grade = new Casa[LINHAS][COLUNAS];
    }

    // -------------------------------------------------------
    // FUNÇÕES AUXILIARES
    // -------------------------------------------------------

    /** Insere uma casa na posição correta da grade usando seu id. */
    public void adicionarCasa(Casa casa) {
        int lin = casa.getId() / COLUNAS;
        int col = casa.getId() % COLUNAS;
        grade[lin][col] = casa;
    }

    /**
     * Recupera uma casa pelo id.
     * id = lin * COLUNAS + col
     * Retorna null se a posição não contiver casa navegável.
     */
    public Casa getCasa(int id) {
        int lin = id / COLUNAS;
        int col = id % COLUNAS;
        if (lin < 0 || lin >= LINHAS || col < 0 || col >= COLUNAS) {
            return null;
        }
        return grade[lin][col];
    }

    /** Recupera uma casa diretamente pela linha e coluna. */
    public Casa getCasa(int lin, int col) {
        if (lin < 0 || lin >= LINHAS || col < 0 || col >= COLUNAS) {
            return null;
        }
        return grade[lin][col];
    }

    /**
     * Conecta duas casas como vizinhas.
     * Usa ids para localizar as casas na grade.
     */
    public void conectarCasas(int id1, int id2) {
        Casa casa1 = getCasa(id1);
        Casa casa2 = getCasa(id2);

        if (casa1 == null || casa2 == null) {
            throw new IllegalArgumentException(
                String.format("Casa de id %d ou %d não existe na grade.", id1, id2)
            );
        }

        casa1.adicionarVizinho(casa2);
        casa2.adicionarVizinho(casa1);
    }

    // -------------------------------------------------------
    // GETTERS
    // -------------------------------------------------------

    public Casa[][] getGrade() {
        return grade;
    }

    // -------------------------------------------------------
    // UTILITÁRIOS
    // -------------------------------------------------------

    /** Monta o id de uma casa a partir de linha e coluna. */
    public static int toId(int lin, int col) {
        return lin * COLUNAS + col;
    }

    /** Retorna a linha correspondente a um id. */
    public static int toLinha(int id) {
        return id / COLUNAS;
    }

    /** Retorna a coluna correspondente a um id. */
    public static int toColuna(int id) {
        return id % COLUNAS;
    }
}
