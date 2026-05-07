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
	
	public void showCardsInEnvelope() {
		
		System.out.printf("Suspeito: %s", suspeito.getNome());
		System.out.printf("Arma: %s", arma.getNome());
		System.out.printf("Comodo: %s", comodo.getNome());
	}
	

}
