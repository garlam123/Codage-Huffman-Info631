//Class des "couples" caractere,frequence
//Getters, setters; toString crée automatiquement.
public class calc {
	
	char caractere;
	int freq;
	
	public calc(char caractere, int freq) {
		super();
		this.caractere = caractere;
		this.freq = freq;
	}
	
	
	public char getcaractere() {
		return caractere;
	}


	public void setcaractere(char caractere) {
		this.caractere = caractere;
	}


	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}


	@Override
	public String toString() {
		return "caractere=" + caractere + " frequence=" + freq  ;
	}
	

}