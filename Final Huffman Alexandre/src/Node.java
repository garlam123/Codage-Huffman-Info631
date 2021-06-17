//Class des noeuds utilisées dans l'arbre, créee avec toujours la frequence le caractere, le filsdroit, le filsgauche et le code binnaire associé au caractere

public class Node {
	private int freq;
	private char caractere='¤';
	private Node filsd= null;
	private Node filsg=null ;
	private String cbinaire = "";
	

	public Node(int freq, char caractere, Node filsd, Node filsg, String cbinaire) {
		super();
		this.freq = freq;
		this.caractere = caractere;
		this.filsd = filsd;
		this.filsg = filsg;
		this.cbinaire = cbinaire;
	}


	@Override
	public String toString() {
		return "[frequence=" + freq + ", caractere=" + caractere +  ", codebinaire=" + cbinaire
				+ "]";
	}


	public int getFreq() {
		return freq;
	}


	public void setFreq(int freq) {
		this.freq = freq;
	}


	public char getcaractere() {
		return caractere;
	}


	public void setcaractere(char caractere) {
		this.caractere = caractere;
	}


	public Node getfilsd() {
		return filsd;
	}
	

	public void setfilsd(Node filsd) {
		this.filsd = filsd;
	}


	public Node getfilsg() {
		return filsg;
	}


	public void setfilsg(Node filsg) {
		this.filsg = filsg;
	}


	public String getcbinaire() {
		return cbinaire;
	}


	public void setcbinaire(String cbinaire) {
		this.cbinaire = cbinaire;
	}


	public boolean testFeuille() {
			return  ((this.filsg == null) && (this.filsd == null));
		}


}