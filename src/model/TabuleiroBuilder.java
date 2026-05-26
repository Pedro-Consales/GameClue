package model;

public class TabuleiroBuilder {

    // -----------------------------------
    // LEGENDA DA MATRIZ:
    // 0  = parede/inacessível
    // 1  = casa navegável
    // 2  = Cozinha
    // 3  = Salão de Baile
    // 4  = Conservatório
    // 5  = Sala de Jantar
    // 6  = Sala de Bilhar
    // 7  = Biblioteca
    // 8  = Salão
    // 9  = Corredor
    // 10 = Estudo
    // 11 = sala central
    // 12 = Porta
    // -----------------------------------

    /*
    //MATRIZ NOVA 
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
{2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 3, 3, 3, 3, 1, 1, 1, 0, 4, 4, 4, 4, 4, 4},
{2, 2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 4, 4, 4, 4, 4, 4},
{2, 2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 4, 4, 4, 4, 4, 4},
{2, 2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 4, 4, 4, 4, 4, 4},
{2, 2, 2, 2, 2, 2, 1, 1, 12, 3, 3, 3, 3, 3, 3, 12, 1, 1, 1, 12, 4, 4, 4, 0},
{2, 2, 2, 2, 12, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1},
{1, 1, 1, 1, 1, 1, 1, 1, 3, 12, 3, 3, 3, 3, 12, 3, 1, 1, 1, 1, 1, 1, 0},
{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6},
{5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 6, 6, 6, 6, 6},
{5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 12, 11, 11, 11, 11, 1, 1, 1, 6, 6, 6, 6, 6, 6},
{5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 11, 11, 11, 11, 11, 1, 1, 1, 6, 6, 6, 6, 6, 6},
{5, 5, 5, 5, 5, 5, 5, 12, 1, 1, 11, 11, 11, 11, 11, 1, 1, 1, 6, 6, 6, 6, 12, 6},
{5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 11, 11, 11, 11, 12, 1, 1, 1, 1, 1, 1, 1, 1, 0},
{5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 11, 11, 11, 11, 11, 1, 1, 1, 7, 7, 12, 7, 7, 7},
{5, 5, 5, 5, 5, 5, 12, 5, 1, 1, 11, 11, 11, 11, 11, 1, 1, 7, 7, 7, 7, 7, 7, 7},
{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 11, 11, 11, 11, 1, 1, 12, 7, 7, 7, 7, 7, 7},
{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 7},
{0, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 12, 12, 9, 9, 1, 1, 1, 7, 7, 7, 7, 7, 7},
{8, 8, 8, 8, 8, 8, 12, 1, 1, 9, 9, 9, 9, 9, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1},
{8, 8, 8, 8, 8, 8, 8, 1, 1, 9, 9, 9, 9, 9, 12, 1, 1, 1, 1, 1, 1, 1, 1, 0},
{8, 8, 8, 8, 8, 8, 8, 1, 1, 9, 9, 9, 9, 9, 9, 1, 1, 12, 10, 10, 10, 10, 10, 10},
{8, 8, 8, 8, 8, 8, 8, 1, 1, 9, 9, 9, 9, 9, 9, 1, 1, 10, 10, 10, 10, 10, 10, 10},
{8, 8, 8, 8, 8, 8, 8, 1, 1, 9, 9, 9, 9, 9, 9, 1, 1, 10, 10, 10, 10, 10, 10, 10},
{8, 8, 8, 8, 8, 8, 0, 1, 0, 9, 9, 9, 9, 9, 9, 0, 1, 0, 10, 10, 10, 10, 10, 10}, */

//MATRIZ ANTIGA
  static final int[][] GRADE = {
    //   0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23
        {2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 4, 4, 4, 4, 4, 4, 0}, // 0
        {2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 4, 4, 4, 4, 4, 4, 0}, // 1
        {2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 0, 4, 4, 4, 4, 4, 4, 0}, // 2
        {2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 0, 4, 4, 4, 4, 4, 4, 0}, // 3
        {2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 4, 4, 4, 4, 4, 4, 1}, // 4
        {2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // 5
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // 6
        {5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 0}, // 7
        {5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 0}, // 8
        {5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 0}, // 9
        {5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 0}, // 10
        {5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // 11
        {5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 0}, // 12
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 0}, // 13
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 0}, // 14
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 0}, // 15
        {8, 8, 8, 8, 8, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 1}, // 16
        {8, 8, 8, 8, 8, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // 17
        {8, 8, 8, 8, 8, 8, 1, 1, 1, 1, 9, 9, 9, 9, 1, 1, 1, 10,10,10,10,10,10, 0}, // 18
        {8, 8, 8, 8, 8, 8, 1, 1, 1, 1, 9, 9, 9, 9, 1, 1, 1, 10,10,10,10,10,10, 0}, // 19
        {8, 8, 8, 8, 8, 8, 1, 1, 1, 1, 9, 9, 9, 9, 1, 1, 1, 10,10,10,10,10,10, 0}, // 20
        {8, 8, 8, 8, 8, 8, 1, 1, 1, 1, 9, 9, 9, 9, 1, 1, 1, 10,10,10,10,10,10, 0}, // 21
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 1, 1, 1, 10,10,10,10,10,10, 0}, // 22
        {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 9, 9, 9, 9, 1, 1, 1,  1, 1, 1, 1, 1, 0, 0}, // 23
        {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 9, 9, 9, 9, 1, 1, 0,  0, 0, 0, 0, 0, 0, 0}, // 24
    };

    // -----------------------------------
    // NOMES DOS CÔMODOS
    // índice = valor na matriz
    // -----------------------------------
    private static final String[] NOMES_COMODOS = {
        null,           // 0 = parede
        null,           // 1 = casa navegável
        "Cozinha",      // 2
        "Salão de Baile", // 3
        "Conservatório",  // 4
        "Sala de Jantar", // 5
        "Sala de Bilhar", // 6
        "Biblioteca",     // 7
        "Salão",          // 8
        "Corredor",       // 9
        "Estudo"          // 10
    };

    // -----------------------------------
    // IDs ESPECIAIS para os cômodos
    // cada cômodo tem um ID único alto
    // para não conflitar com as casas normais
    // id de casa normal = lin * 24 + col
    // id de cômodo = 1000 + valor da matriz
    // -----------------------------------
    private static final int ID_COMODO_BASE = 1000;

    public static void popular(Tabuleiro tabuleiro) {

        int linhas = GRADE.length;       // 25
        int colunas = GRADE[0].length;   // 24

        // -----------------------------------
        // PASSO 1 — cria os cômodos (um por tipo)
        // -----------------------------------
        for (int tipo = 2; tipo <= 10; tipo++) {
            Comodo comodo = new Comodo(
                ID_COMODO_BASE + tipo,
                NOMES_COMODOS[tipo]
            );
            tabuleiro.adicionarCasa(comodo);
        }

        // -----------------------------------
        // PASSO 2 — cria as casas navegáveis (valor == 1)
        // -----------------------------------
        for (int lin = 0; lin < linhas; lin++) {
            for (int col = 0; col < colunas; col++) {

                if (GRADE[lin][col] == 1) {
                    Casa casa = new Casa();
                    casa.setId(lin * colunas + col);
                    tabuleiro.adicionarCasa(casa);
                }
            }
        }

        // -----------------------------------
        // PASSO 3 — conecta vizinhos
        // casa navegável vizinha de casa navegável → conecta direto
        // casa navegável vizinha de cômodo → conecta com o cômodo
        // cômodo vizinho de cômodo → não conecta (mesmo cômodo)
        // -----------------------------------
        int[] dlin = {-1, 1, 0, 0}; // cima, baixo
        int[] dcol = { 0, 0,-1, 1}; // esquerda, direita

        for (int lin = 0; lin < linhas; lin++) {
            for (int col = 0; col < colunas; col++) {

                int valor = GRADE[lin][col];

                // só processa casas navegáveis
                if (valor != 1) continue;

                int idAtual = lin * colunas + col;
                Casa casaAtual = tabuleiro.getCasa(idAtual);

                for (int d = 0; d < 4; d++) {

                    int nLin = lin + dlin[d];
                    int nCol = col + dcol[d];

                    // fora dos limites
                    if (nLin < 0 || nLin >= linhas || nCol < 0 || nCol >= colunas) continue;

                    int valorVizinho = GRADE[nLin][nCol];

                    // parede — ignora
                    if (valorVizinho == 0) continue;

                    if (valorVizinho == 1) {
                        // casa navegável — conecta se ainda não conectou
                        int idVizinho = nLin * colunas + nCol;
                        Casa vizinho = tabuleiro.getCasa(idVizinho);
                        if (!casaAtual.getVizinhos().contains(vizinho)) {
                            casaAtual.adicionarVizinho(vizinho);
                            vizinho.adicionarVizinho(casaAtual);
                        }

                    } else {
                        // cômodo — conecta casa com o cômodo (é uma porta!)
                        int idComodo = ID_COMODO_BASE + valorVizinho;
                        Casa comodo = tabuleiro.getCasa(idComodo);
                        if (!casaAtual.getVizinhos().contains(comodo)) {
                            casaAtual.adicionarVizinho(comodo);
                            comodo.adicionarVizinho(casaAtual);
                        }
                    }
                }
            }
        }
    }
}