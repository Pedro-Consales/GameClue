package model;
import java.util.Random;

class Dado {
	

	 public int jogarDados() {
		 
		 Random gerador = new Random();
		 
		 int numero = gerador.nextInt(6) + 1; // 0 a 5, + 1 = 1 a 6 -> Melhor alternativa pois tem maior adaptabilidade à outras versões do java
		 
		 return numero;
	 }
	 
}
