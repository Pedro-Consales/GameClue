package model;


class EnvelopeConfidencial {
	
	private Carta arma;
	
	
	private Carta comodo;
	
	
	private Carta suspeito;
	
	EnvelopeConfidencial(Carta suspeito, Carta arma, Carta comodo) {
	    this.suspeito = suspeito;
	    this.arma = arma;
	    this.comodo = comodo;
	}
	
	public Carta getArma() {
		return this.arma;
	}
	
	public Carta getComodo() {
		return this.comodo;
	}
	
	public Carta getSuspeito() {
		return this.suspeito;
	}

	// Compara a solução com os nomes de uma acusação
	public boolean confere(String suspeitoNome, String armaNome, String comodoNome) {
		return suspeito.getNome().equals(suspeitoNome)
		    && arma.getNome().equals(armaNome)
		    && comodo.getNome().equals(comodoNome);
	}
	
	public void showCardsInEnvelope() {
		
		System.out.printf("Suspeito: %s", suspeito.getNome());
		System.out.printf("Arma: %s", arma.getNome());
		System.out.printf("Comodo: %s", comodo.getNome());
	}
	

}
