package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class ClueModel implements Observado {

    // =========================================
    // SINGLETON
    // =========================================

    private static ClueModel instancia;

    // =========================================
    // ESTADO PRINCIPAL DA PARTIDA
    // =========================================

    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;

    private Dado dado1;
    private Dado dado2;

    private int jogadorAtual;
    private int[] ultimoDado;

    private EnvelopeConfidencial envelope;

    // =========================================
    // OBSERVER
    // =========================================

    private List<Observador> observadores;

    // =========================================
    // ESTADO DE PALPITE E ACUSAÇÃO
    // =========================================

    private boolean jogoEncerrado;
    private String nomeVencedor;

    private boolean palpiteUsadoNesteTurno;

    private String ultimaCartaExibida;

    private ResultadoPalpite ultimoResultadoPalpite;
    private ResultadoAcusacao ultimoResultadoAcusacao;

    /*
     * Os nomes das cartas de suspeito são completos,
     * enquanto os peões da View usam nomes menores.
     */
    private static final Map<String, String>
        NOME_CURTO_SUSPEITO;

    static {

        Map<String, String> nomes =
            new HashMap<>();

        nomes.put(
            "Coronel Mustard",
            "Mustard"
        );

        nomes.put(
            "Srta. Scarlet",
            "Scarlet"
        );

        nomes.put(
            "Professor Plum",
            "Plum"
        );

        nomes.put(
            "Reverendo Green",
            "Green"
        );

        nomes.put(
            "Sra. White",
            "White"
        );

        nomes.put(
            "Sra. Peacock",
            "Peacock"
        );

        NOME_CURTO_SUSPEITO =
            Collections.unmodifiableMap(nomes);
    }

    private ClueModel() {

        tabuleiro = new Tabuleiro();
        jogadores = new ArrayList<>();
        observadores = new ArrayList<>();

        dado1 = new Dado();
        dado2 = new Dado();

        jogadorAtual = 0;
        ultimoDado = new int[]{0, 0};

        jogoEncerrado = false;
        nomeVencedor = null;

        palpiteUsadoNesteTurno = false;

        ultimaCartaExibida = null;
        ultimoResultadoPalpite = null;
        ultimoResultadoAcusacao = null;

        TabuleiroBuilder.popular(tabuleiro);
    }

    public static ClueModel getInstance() {

        if (instancia == null) {
            instancia = new ClueModel();
        }

        return instancia;
    }

    // =========================================
    // OBSERVER
    // =========================================

    @Override
    public void adicionarObservador(
            Observador observador) {

        if (observador != null
                && !observadores.contains(observador)) {

            observadores.add(observador);
        }
    }

    @Override
    public void removerObservador(
            Observador observador) {

        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores() {

        /*
         * A cópia evita erro caso um observador
         * seja removido durante a notificação.
         */
        List<Observador> copia =
            new ArrayList<>(observadores);

        for (Observador observador : copia) {
            observador.atualizar();
        }
    }

    // =========================================
    // PREPARAÇÃO DA PARTIDA
    // =========================================

    public void novaPartida() {

        jogadores = new ArrayList<>();

        jogadorAtual = 0;
        ultimoDado = new int[]{0, 0};

        envelope = null;

        jogoEncerrado = false;
        nomeVencedor = null;

        palpiteUsadoNesteTurno = false;

        ultimaCartaExibida = null;
        ultimoResultadoPalpite = null;
        ultimoResultadoAcusacao = null;
    }

    public void prepararPartida() {

        if (jogadores.isEmpty()) {
            return;
        }

        Map<String, List<Carta>> grupos =
            new HashMap<>();

        grupos.put(
            "Suspeito",
            criarGrupo(
                Carta.SUSPEITOS,
                "Suspeito"
            )
        );

        grupos.put(
            "Arma",
            criarGrupo(
                Carta.ARMAS,
                "Arma"
            )
        );

        grupos.put(
            "Cômodo",
            criarGrupo(
                Carta.COMODOS,
                "Cômodo"
            )
        );

        for (List<Carta> grupo :
                grupos.values()) {

            Collections.shuffle(grupo);
        }

        Carta suspeito =
            grupos
                .get("Suspeito")
                .remove(0);

        Carta arma =
            grupos
                .get("Arma")
                .remove(0);

        Carta comodo =
            grupos
                .get("Cômodo")
                .remove(0);

        envelope =
            new EnvelopeConfidencial(
                suspeito,
                arma,
                comodo
            );

        List<Carta> restantes =
            new ArrayList<>();

        for (List<Carta> grupo :
                grupos.values()) {

            restantes.addAll(grupo);
        }

        Collections.shuffle(restantes);

        for (int i = 0;
                i < restantes.size();
                i++) {

            Jogador jogador =
                jogadores.get(
                    i % jogadores.size()
                );

            Carta carta =
                restantes.get(i);

            jogador.adicionarCarta(carta);

            jogador
                .getBloco()
                .adicionarCartaPropria(carta);
        }

        imprimirRaioX();

        notificarObservadores();
    }

    private List<Carta> criarGrupo(
            List<String> nomes,
            String tipo) {

        List<Carta> grupo =
            new ArrayList<>();

        for (String nome : nomes) {

            grupo.add(
                new Carta(nome, tipo)
            );
        }

        return grupo;
    }

    /*
     * Método de depuração.
     * Remova ou comente antes da entrega caso
     * não queira revelar a solução no console.
     */
    private void imprimirRaioX() {

        System.out.println(
            "\n==================== "
            + "RAIO X DA PARTIDA "
            + "===================="
        );

        System.out.println(
            "ENVELOPE CONFIDENCIAL:"
        );

        System.out.println(
            "Suspeito: "
            + envelope
                .getSuspeito()
                .getNome()
        );

        System.out.println(
            "Arma: "
            + envelope
                .getArma()
                .getNome()
        );

        System.out.println(
            "Cômodo: "
            + envelope
                .getComodo()
                .getNome()
        );

        for (Jogador jogador : jogadores) {

            System.out.println(
                "--------------------------------"
            );

            System.out.println(
                "Jogador "
                + jogador.getNome()
                + " - "
                + jogador.quantidadeCartas()
                + " cartas"
            );

            for (Carta carta :
                    jogador.getCartas()) {

                System.out.println(
                    carta.getTipo()
                    + " - "
                    + carta.getNome()
                );
            }
        }

        System.out.println(
            "================================"
        );
    }

    // =========================================
    // FACHADA DE CARTAS
    // =========================================

    public List<String>
            getNomesMaoJogadorAtual() {

        List<String> nomes =
            new ArrayList<>();

        for (Carta carta :
                getJogadorAtual().getCartas()) {

            nomes.add(carta.getNome());
        }

        return nomes;
    }

    public List<String> getNomesSuspeitos() {

        return new ArrayList<>(
            Carta.SUSPEITOS
        );
    }

    public List<String> getNomesArmas() {

        return new ArrayList<>(
            Carta.ARMAS
        );
    }

    public List<String> getNomesComodos() {

        return new ArrayList<>(
            Carta.COMODOS
        );
    }

    public boolean ehCartaPropriaJogadorAtual(
            String nome) {

        Carta carta =
            criarCartaPorNome(nome);

        return getJogadorAtual()
            .getBloco()
            .ehCartaPropria(carta);
    }

    public boolean cartaMarcadaJogadorAtual(
            String nome) {

        Carta carta =
            criarCartaPorNome(nome);

        return getJogadorAtual()
            .getBloco()
            .estaMarcada(carta);
    }

    public void marcarCartaReveladaJogadorAtual(
            String nome,
            boolean marcar) {

        BlocoDeNotas bloco =
            getJogadorAtual().getBloco();

        Carta carta =
            criarCartaPorNome(nome);

        if (marcar) {

            bloco.marcarCartaRevelada(
                carta
            );

        } else {

            bloco.desmarcarCartaRevelada(
                carta
            );
        }

        notificarObservadores();
    }

    public boolean verificarAcusacao(
            String suspeito,
            String arma,
            String comodo) {

        return envelope != null
            && envelope.confere(
                suspeito,
                arma,
                comodo
            );
    }

    private Carta criarCartaPorNome(
            String nome) {

        if (Carta.SUSPEITOS.contains(nome)) {

            return new Carta(
                nome,
                "Suspeito"
            );
        }

        if (Carta.ARMAS.contains(nome)) {

            return new Carta(
                nome,
                "Arma"
            );
        }

        if (Carta.COMODOS.contains(nome)) {

            return new Carta(
                nome,
                "Cômodo"
            );
        }

        throw new IllegalArgumentException(
            "Carta inexistente: " + nome
        );
    }

    // =========================================
    // DADOS
    // =========================================

    public int[] lancarDados() {

        validarPartidaEmAndamento();

        int valor1 =
            dado1.jogarDados();

        int valor2 =
            dado2.jogarDados();

        ultimoDado =
            new int[]{valor1, valor2};

        /*
         * Observer:
         * a View será atualizada após
         * o lançamento dos dados.
         */
        notificarObservadores();

        return ultimoDado.clone();
    }

    public void setDadosParaTeste(
            int valor1,
            int valor2) {

        validarValorDado(valor1);
        validarValorDado(valor2);

        ultimoDado =
            new int[]{valor1, valor2};

        notificarObservadores();
    }

    private void validarValorDado(
            int valor) {

        if (valor < 1 || valor > 6) {

            throw new IllegalArgumentException(
                "O valor do dado deve estar "
                + "entre 1 e 6."
            );
        }
    }

    public int[] getUltimoDado() {
        return ultimoDado.clone();
    }

    // =========================================
    // PALPITE
    // =========================================

    public ResultadoPalpite fazerPalpite(
            String suspeito,
            String arma) {

        validarPartidaEmAndamento();

        Jogador autor =
            getJogadorAtual();

        if (autor.isEliminado()) {

            throw new IllegalStateException(
                "Jogador eliminado não pode "
                + "fazer palpites."
            );
        }

        if (!jogadorAtualEstaEmComodo()) {

            throw new IllegalStateException(
                "O jogador precisa estar em "
                + "um cômodo para fazer um palpite."
            );
        }

        if (palpiteUsadoNesteTurno) {

            throw new IllegalStateException(
                "O jogador já fez um palpite "
                + "neste turno."
            );
        }

        if (!Carta.SUSPEITOS.contains(
                suspeito)) {

            throw new IllegalArgumentException(
                "Suspeito inválido: "
                + suspeito
            );
        }

        if (!Carta.ARMAS.contains(arma)) {

            throw new IllegalArgumentException(
                "Arma inválida: "
                + arma
            );
        }

        String comodo =
            getNomeComodoAtual();

        palpiteUsadoNesteTurno = true;

        /*
         * Move o peão do suspeito sugerido
         * para o cômodo atual.
         */
        boolean suspeitoMovido =
            moverSuspeitoSugeridoParaComodoAtual(
                suspeito
            );

        Jogador jogadorQueRefutou = null;
        Carta cartaMostrada = null;

        /*
         * A busca começa pelo jogador seguinte
         * e segue a ordem da partida.
         *
         * Jogadores eliminados não são ignorados,
         * pois ainda podem refutar palpites.
         */
        for (int deslocamento = 1;
                deslocamento < jogadores.size();
                deslocamento++) {

            int indice =
                (jogadorAtual + deslocamento)
                % jogadores.size();

            Jogador candidato =
                jogadores.get(indice);

            Carta encontrada =
                candidato
                    .encontrarCartaQueRefuta(
                        suspeito,
                        arma,
                        comodo
                    );

            if (encontrada != null) {

                jogadorQueRefutou =
                    candidato;

                cartaMostrada =
                    encontrada;

                break;
            }
        }

        boolean refutado =
            cartaMostrada != null;

        String mensagem;

        if (refutado) {

            /*
             * A carta mostrada passa a fazer
             * parte do bloco de notas do jogador
             * que fez o palpite.
             */
            autor
                .getBloco()
                .marcarCartaRevelada(
                    cartaMostrada
                );

            ultimaCartaExibida =
                cartaMostrada.getNome();

            mensagem =
                jogadorQueRefutou.getNome()
                + " refutou o palpite "
                + "mostrando a carta "
                + cartaMostrada.getNome()
                + ".";

        } else {

            ultimaCartaExibida = null;

            mensagem =
                "Nenhum jogador conseguiu "
                + "refutar o palpite.";
        }

        ultimoResultadoPalpite =
            new ResultadoPalpite(
                autor.getNome(),
                suspeito,
                arma,
                comodo,
                suspeitoMovido,
                refutado,
                jogadorQueRefutou == null
                    ? null
                    : jogadorQueRefutou.getNome(),
                cartaMostrada == null
                    ? null
                    : cartaMostrada.getNome(),
                mensagem
            );

        /*
         * Observer:
         * permite atualizar o peão,
         * exibir a carta e atualizar o bloco.
         */
        notificarObservadores();

        return ultimoResultadoPalpite;
    }

    public boolean podeFazerPalpiteJogadorAtual() {

        return !jogoEncerrado
            && !jogadores.isEmpty()
            && !getJogadorAtual()
                .isEliminado()
            && jogadorAtualEstaEmComodo()
            && !palpiteUsadoNesteTurno;
    }

    // =========================================
    // ACUSAÇÃO FINAL
    // =========================================

    public ResultadoAcusacao fazerAcusacaoFinal(
            String suspeito,
            String arma,
            String comodo) {

        validarPartidaEmAndamento();

        Jogador acusador =
            getJogadorAtual();

        if (acusador.isEliminado()) {

            throw new IllegalStateException(
                "Jogador eliminado não pode "
                + "fazer uma acusação."
            );
        }

        if (!jogadorAtualEstaEmComodo()) {

            throw new IllegalStateException(
                "O jogador precisa estar em "
                + "um cômodo para fazer "
                + "a acusação final."
            );
        }

        if (!Carta.SUSPEITOS.contains(
                suspeito)) {

            throw new IllegalArgumentException(
                "Suspeito inválido: "
                + suspeito
            );
        }

        if (!Carta.ARMAS.contains(arma)) {

            throw new IllegalArgumentException(
                "Arma inválida: "
                + arma
            );
        }

        if (!Carta.COMODOS.contains(
                comodo)) {

            throw new IllegalArgumentException(
                "Cômodo inválido: "
                + comodo
            );
        }

        boolean correta =
            envelope != null
            && envelope.confere(
                suspeito,
                arma,
                comodo
            );

        String mensagem;

        if (correta) {

            jogoEncerrado = true;

            nomeVencedor =
                acusador.getNome();

            mensagem =
                acusador.getNome()
                + " venceu a partida!";

        } else {

            acusador.setEliminado(true);

            mensagem =
                "Acusação incorreta. "
                + acusador.getNome()
                + " foi eliminado dos turnos, "
                + "mas ainda pode refutar palpites.";

            if (!existeJogadorAtivo()) {

                jogoEncerrado = true;
                nomeVencedor = null;

                mensagem +=
                    " Não restaram jogadores ativos.";
            }
        }

        ultimoResultadoAcusacao =
            new ResultadoAcusacao(
                acusador.getNome(),
                suspeito,
                arma,
                comodo,
                correta,
                !correta,
                jogoEncerrado,
                nomeVencedor,
                mensagem
            );

        notificarObservadores();

        return ultimoResultadoAcusacao;
    }

    public boolean podeFazerAcusacaoJogadorAtual() {

        return !jogoEncerrado
            && !jogadores.isEmpty()
            && !getJogadorAtual()
                .isEliminado()
            && jogadorAtualEstaEmComodo();
    }

    // =========================================
    // RESULTADOS PARA VIEW/CONTROLLER
    // =========================================

    public ResultadoPalpite
            getUltimoResultadoPalpite() {

        return ultimoResultadoPalpite;
    }

    public ResultadoAcusacao
            getUltimoResultadoAcusacao() {

        return ultimoResultadoAcusacao;
    }

    public String getUltimaCartaExibida() {
        return ultimaCartaExibida;
    }

    public boolean isJogoEncerrado() {
        return jogoEncerrado;
    }

    public String getNomeVencedor() {
        return nomeVencedor;
    }

    public boolean isJogadorAtualEliminado() {

        return !jogadores.isEmpty()
            && getJogadorAtual()
                .isEliminado();
    }

    // =========================================
    // IDENTIFICAÇÃO DO CÔMODO
    // =========================================

    public boolean jogadorAtualEstaEmComodo() {

        if (jogadores.isEmpty()) {
            return false;
        }

        Casa casaAtual =
            tabuleiro.getCasa(
                getJogadorAtual()
                    .getPosicao()
            );

        return casaAtual != null
            && casaAtual.isComodo()
            && casaAtual.getNomeComodo() != null;
    }

    public String getNomeComodoAtual() {

        if (!jogadorAtualEstaEmComodo()) {
            return null;
        }

        Casa casaAtual =
            tabuleiro.getCasa(
                getJogadorAtual()
                    .getPosicao()
            );

        return casaAtual.getNomeComodo();
    }

    // =========================================
    // MOVIMENTAÇÃO DO SUSPEITO DO PALPITE
    // =========================================

    private boolean
            moverSuspeitoSugeridoParaComodoAtual(
                String suspeito) {

        String nomeCurto =
            NOME_CURTO_SUSPEITO.get(
                suspeito
            );

        if (nomeCurto == null) {
            return false;
        }

        Jogador jogadorSuspeito = null;

        for (Jogador jogador : jogadores) {

            if (jogador
                    .getNome()
                    .equals(nomeCurto)) {

                jogadorSuspeito =
                    jogador;

                break;
            }
        }

        /*
         * Atualmente só existem no Model
         * os peões selecionados na tela inicial.
         */
        if (jogadorSuspeito == null) {
            return false;
        }

        String nomeComodo =
            getNomeComodoAtual();

        Casa destino =
            encontrarCasaLivreNoComodo(
                nomeComodo,
                jogadorSuspeito
            );

        if (destino == null) {
            return false;
        }

        jogadorSuspeito.mover(
            destino.getId()
        );

        return true;
    }

    private Casa encontrarCasaLivreNoComodo(
            String nomeComodo,
            Jogador jogadorQueSeraMovido) {

        Casa[][] grade =
            tabuleiro.getGrade();

        for (int linha = 0;
                linha < grade.length;
                linha++) {

            for (int coluna = 0;
                    coluna < grade[linha].length;
                    coluna++) {

                Casa casa =
                    grade[linha][coluna];

                if (casa == null
                        || !casa.isComodo()
                        || !nomeComodo.equals(
                            casa.getNomeComodo())) {

                    continue;
                }

                boolean ocupada = false;

                for (Jogador jogador :
                        jogadores) {

                    if (jogador
                            != jogadorQueSeraMovido
                            && jogador.getPosicao()
                            == casa.getId()) {

                        ocupada = true;
                        break;
                    }
                }

                if (!ocupada) {
                    return casa;
                }
            }
        }

        return null;
    }

    // =========================================
    // BFS DE MOVIMENTAÇÃO
    // =========================================

    public List<Casa> mapearCasas(
            int[] dados) {

        int passos =
            dados[0] + dados[1];

        Casa origem =
            tabuleiro.getCasa(
                getJogadorAtual()
                    .getPosicao()
            );

        Set<Casa> visitadas =
            new HashSet<>();

        Set<Casa> destinos =
            new HashSet<>();

        Queue<Casa> fila =
            new LinkedList<>();

        visitadas.add(origem);

        if (origem.isComodo()) {

            Set<Casa> celulasComodo =
                new HashSet<>();

            Queue<Casa> filaComodo =
                new LinkedList<>();

            filaComodo.add(origem);
            celulasComodo.add(origem);

            while (!filaComodo.isEmpty()) {

                Casa atual =
                    filaComodo.poll();

                for (Casa vizinho :
                        atual.getVizinhos()) {

                    if (vizinho.isComodo()
                            && !celulasComodo
                                .contains(vizinho)) {

                        celulasComodo.add(
                            vizinho
                        );

                        filaComodo.add(
                            vizinho
                        );
                    }
                }
            }

            for (Casa celula :
                    celulasComodo) {

                for (Casa vizinho :
                        celula.getVizinhos()) {

                    if (vizinho.isPorta()
                            && !visitadas
                                .contains(vizinho)) {

                        visitadas.add(
                            vizinho
                        );

                        if (passos > 1) {

                            destinos.add(
                                vizinho
                            );

                            fila.add(
                                vizinho
                            );
                        }
                    }
                }
            }

        } else {

            for (Casa vizinho :
                    origem.getVizinhos()) {

                expandir(
                    origem,
                    vizinho,
                    visitadas,
                    destinos,
                    fila
                );
            }
        }

        int niveisRestantes =
            passos - 1;

        for (int nivel = 0;
                nivel < niveisRestantes;
                nivel++) {

            int tamanho =
                fila.size();

            for (int i = 0;
                    i < tamanho;
                    i++) {

                Casa atual =
                    fila.poll();

                if (atual == null) {
                    continue;
                }

                if (atual.isComodo()) {
                    continue;
                }

                for (Casa vizinho :
                        atual.getVizinhos()) {

                    expandir(
                        atual,
                        vizinho,
                        visitadas,
                        destinos,
                        fila
                    );
                }
            }
        }

        return new ArrayList<>(
            destinos
        );
    }

    private void expandir(
            Casa atual,
            Casa vizinho,
            Set<Casa> visitadas,
            Set<Casa> destinos,
            Queue<Casa> fila) {

        if (vizinho.isParede()) {
            return;
        }

        if (vizinho.isComodo()) {

            if (atual.isPorta()) {

                floodFillComodo(
                    vizinho,
                    destinos
                );
            }

            return;
        }

        if (!visitadas.contains(
                vizinho)) {

            visitadas.add(vizinho);
            destinos.add(vizinho);
            fila.add(vizinho);
        }
    }

    private void floodFillComodo(
            Casa inicio,
            Set<Casa> destinos) {

        Queue<Casa> fila =
            new LinkedList<>();

        Set<Casa> visitadas =
            new HashSet<>();

        fila.add(inicio);
        visitadas.add(inicio);

        while (!fila.isEmpty()) {

            Casa atual =
                fila.poll();

            destinos.add(atual);

            for (Casa vizinho :
                    atual.getVizinhos()) {

                if (!visitadas
                        .contains(vizinho)
                        && vizinho.isComodo()) {

                    visitadas.add(
                        vizinho
                    );

                    fila.add(
                        vizinho
                    );
                }
            }
        }
    }

    public List<Casa> getCasasPossiveis() {

        return mapearCasas(
            ultimoDado
        );
    }

    // =========================================
    // MOVIMENTAÇÃO
    // =========================================

    public void deslocarPiao(
            int idCasa) {

        validarPartidaEmAndamento();

        Casa casa =
            tabuleiro.getCasa(idCasa);

        if (casa == null) {

            throw new IllegalArgumentException(
                "Movimento inválido! "
                + "Casa não existe."
            );
        }

        if (casa.isParede()) {

            throw new IllegalArgumentException(
                "Movimento inválido! "
                + "Casa é parede."
            );
        }

        for (Jogador jogador :
                jogadores) {

            if (jogador != getJogadorAtual()
                    && jogador.getPosicao()
                    == idCasa) {

                throw new IllegalArgumentException(
                    "Movimento inválido! "
                    + "Casa ocupada por "
                    + jogador.getNome()
                );
            }
        }

        for (Casa casaPossivel :
                getCasasPossiveis()) {

            if (casaPossivel.getId()
                    == idCasa) {

                getJogadorAtual()
                    .mover(idCasa);

                /*
                 * Observer:
                 * atualiza o pião na View.
                 */
                notificarObservadores();

                return;
            }
        }

        throw new IllegalArgumentException(
            "Movimento inválido! Casa id="
            + idCasa
            + " fora do alcance."
        );
    }

    public void usarPassagemSecreta(
            int idDestino) {

        validarPartidaEmAndamento();

        Casa destino =
            tabuleiro.getCasa(idDestino);

        if (destino == null
                || destino.isParede()) {

            throw new IllegalArgumentException(
                "Passagem Secreta: "
                + "destino inválido."
            );
        }

        getJogadorAtual()
            .mover(idDestino);

        notificarObservadores();
    }

    // =========================================
    // TURNO
    // =========================================

    public void proximoJogador() {

        if (jogadores.isEmpty()
                || jogoEncerrado) {

            return;
        }

        if (!existeJogadorAtivo()) {

            jogoEncerrado = true;
            nomeVencedor = null;

            notificarObservadores();

            return;
        }

        /*
         * Jogadores eliminados não recebem
         * novos turnos.
         */
        do {

            jogadorAtual =
                (jogadorAtual + 1)
                % jogadores.size();

        } while (
            getJogadorAtual()
                .isEliminado()
        );

        palpiteUsadoNesteTurno = false;
        ultimaCartaExibida = null;
        ultimoDado = new int[]{0, 0};

        notificarObservadores();
    }

    private boolean existeJogadorAtivo() {

        for (Jogador jogador :
                jogadores) {

            if (!jogador.isEliminado()) {
                return true;
            }
        }

        return false;
    }

    private void validarPartidaEmAndamento() {

        if (jogoEncerrado) {

            throw new IllegalStateException(
                "A partida já foi encerrada."
            );
        }

        if (jogadores.isEmpty()) {

            throw new IllegalStateException(
                "A partida não possui jogadores."
            );
        }
    }

    // =========================================
    // JOGADORES E TABULEIRO
    // =========================================

    public void adicionarJogador(
            Jogador jogador) {

        if (jogador != null) {
            jogadores.add(jogador);
        }
    }

    public void adicionarJogador(
            int id,
            String nome,
            int posicaoInicial) {

        Jogador jogador =
            new Jogador();

        jogador.setId(id);
        jogador.setNome(nome);
        jogador.mover(posicaoInicial);

        jogadores.add(jogador);
    }

    public Jogador getJogadorAtual() {

        return jogadores.get(
            jogadorAtual
        );
    }

    public int getPosicaoJogadorAtual() {

        return getJogadorAtual()
            .getPosicao();
    }

    public String getNomeJogadorAtual() {

        return getJogadorAtual()
            .getNome();
    }

    public Map<String, Integer>
            getPosicoesJogadores() {

        Map<String, Integer> posicoes =
            new LinkedHashMap<>();

        for (Jogador jogador :
                jogadores) {

            posicoes.put(
                jogador.getNome(),
                jogador.getPosicao()
            );
        }

        return posicoes;
    }

    public List<String> getNomesJogadores() {

        List<String> nomes =
            new ArrayList<>();

        for (Jogador jogador :
                jogadores) {

            nomes.add(
                jogador.getNome()
            );
        }

        return nomes;
    }

    public int getValorGrade(
            int linha,
            int coluna) {

        return TabuleiroBuilder
            .GRADE[linha][coluna];
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /*
     * Acesso apenas para testes do pacote model.
     */
    EnvelopeConfidencial getEnvelope() {
        return envelope;
    }
}