package model;

public class ResultadoPalpite {

    private final String jogadorQuePalpitou;

    private final String suspeito;
    private final String arma;
    private final String comodo;

    private final boolean suspeitoMovido;
    private final boolean refutado;

    private final String jogadorQueRefutou;
    private final String cartaMostrada;
    private final String mensagem;

    public ResultadoPalpite(
            String jogadorQuePalpitou,
            String suspeito,
            String arma,
            String comodo,
            boolean suspeitoMovido,
            boolean refutado,
            String jogadorQueRefutou,
            String cartaMostrada,
            String mensagem) {

        this.jogadorQuePalpitou = jogadorQuePalpitou;
        this.suspeito = suspeito;
        this.arma = arma;
        this.comodo = comodo;
        this.suspeitoMovido = suspeitoMovido;
        this.refutado = refutado;
        this.jogadorQueRefutou = jogadorQueRefutou;
        this.cartaMostrada = cartaMostrada;
        this.mensagem = mensagem;
    }

    public String getJogadorQuePalpitou() {
        return jogadorQuePalpitou;
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

    public boolean isSuspeitoMovido() {
        return suspeitoMovido;
    }

    public boolean isRefutado() {
        return refutado;
    }

    public String getJogadorQueRefutou() {
        return jogadorQueRefutou;
    }

    public String getCartaMostrada() {
        return cartaMostrada;
    }

    public String getMensagem() {
        return mensagem;
    }
}