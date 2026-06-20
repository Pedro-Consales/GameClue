package model;

public class ResultadoAcusacao {

    private final String jogadorQueAcusou;

    private final String suspeito;
    private final String arma;
    private final String comodo;

    private final boolean correta;
    private final boolean jogadorEliminado;
    private final boolean jogoEncerrado;

    private final String vencedor;
    private final String mensagem;

    public ResultadoAcusacao(
            String jogadorQueAcusou,
            String suspeito,
            String arma,
            String comodo,
            boolean correta,
            boolean jogadorEliminado,
            boolean jogoEncerrado,
            String vencedor,
            String mensagem) {

        this.jogadorQueAcusou = jogadorQueAcusou;
        this.suspeito = suspeito;
        this.arma = arma;
        this.comodo = comodo;
        this.correta = correta;
        this.jogadorEliminado = jogadorEliminado;
        this.jogoEncerrado = jogoEncerrado;
        this.vencedor = vencedor;
        this.mensagem = mensagem;
    }

    public String getJogadorQueAcusou() {
        return jogadorQueAcusou;
    }

    public String getSuspeito() {
        return suspeito;
    }

    public String getArma() {
        return arma;
    }

    public String getComodo() {
        return comodo;
    }

    public boolean isCorreta() {
        return correta;
    }

    public boolean isJogadorEliminado() {
        return jogadorEliminado;
    }

    public boolean isJogoEncerrado() {
        return jogoEncerrado;
    }

    public String getVencedor() {
        return vencedor;
    }

    public String getMensagem() {
        return mensagem;
    }
}