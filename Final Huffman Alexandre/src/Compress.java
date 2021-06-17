import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;



public class Compress {
		
	ArrayList<calc> listecalc = new ArrayList<calc>();
	Set<Character> setcaractere = new TreeSet<Character>();
	String txt;

	
	private ArrayList<Node> listedenode = new ArrayList<Node>();
	HashMap<Character, String> dict = new HashMap<Character, String>(); //HashMap est une liste contenant des strings, simple d'utilisation.





	public void codage(String doctxt) {
		


		String str2 =doctxt.replaceAll("\\p{Punct}", "");
		str2 = str2.substring(0, str2.length()-3);
		str2 = str2 + "Chiffré.bin";
		String str3 = str2 + "Stats.txt";
		
		this.alphaFreq();
		this.triage();
		this.listnode();
		Node n1 = this.new_tree();
		this.implement_tree(n1,"");
		this.textstats(str3);
		this.fichiercoder(str2);
		System.out.println("Compression terminée !");
		System.out.println("taux de compression " + this.tcompress(doctxt, str2) +" nombre de bits moyen "+ this.bitmoy());
		 
	}
	

	public ArrayList<Node> getlistedenode() {
		return listedenode;
	}
	

	public HashMap<Character, String> getDict() {
		return dict;
	}


	public void setDict(HashMap<Character, String> dict) {
		this.dict = dict;
	}


	public void setlistedenode(ArrayList<Node> listedenode) {
		this.listedenode = listedenode;
	}

	public Compress(String fichier) {
		this.txt=this.ouvrir(fichier);
	}
	
	public Set<Character> getsetcaractere() {
		return setcaractere;

	}

	public String getTxt() {
		return txt;
	}

	public ArrayList<calc> getListecalc() {
		return listecalc;
	}





	public String ouvrir(String adresse) { //fichier servant a transformer mon texte en variable string utilisable dans notre porgramme.
		String texte = " ";
		  try{
			   InputStream ips=new FileInputStream(adresse); 
			   InputStreamReader ipsr=new InputStreamReader(ips);
			   BufferedReader br=new BufferedReader(ipsr);
			   String ligne;
			   
			   while ((ligne=br.readLine())!=null){
				   texte += ligne;
			   }
			   br.close(); 
			   
			  }  
			  catch (Exception e){
			   System.out.println(e.toString());
			  }
		  return texte;
		  
	}

	
	public calc getcalcBycaract(char caract) { //fonction servant a retourner le couple d'un caractere
		for(calc i: this.listecalc ) {
			if(caract == i.getcaractere()) {
				return i;
			}
			
		}
		return null;}

		
	public void alphaFreq() {
		for (int i = 0; i< this.txt.length();i++) {
			//on parcours le texte d'entrée
			
			if(setcaractere.contains(this.txt.charAt(i)) == false) { //si le caractere n'est pas contenue dans notre variable
				
				setcaractere.add(this.txt.charAt(i)); //alors l'ajouter
				
				calc t = new calc(this.txt.charAt(i),1); // Puis on crée son "couple" ainsi que sa frequence 1 et on l'ajoute a notre liste de couple

				this.listecalc.add(t);	
			}

			else //si on a deja vu le caractere
			{
				int s = (this.getcalcBycaract(this.txt.charAt(i)).getFreq() + 1); //s prend la valeur de la freqeunce + 1
				this.getcalcBycaract(this.txt.charAt(i)).setFreq(s); // on set notre nouvelle freqeunce s.
			}
		}
	}



	public void triage() 
	{
		for(int i = 1;i< this.listecalc.size();i++) //on parcour notre liste de calc (couple)
		{
			
			int frequencei = this.listecalc.get(i).freq; //prend la freqeunce du i actuel
			char caracterei = this.listecalc.get(i).caractere; //prend le caractere du i actuel
			int z = i;
			while(z>0 && this.listecalc.get(z-1).freq > frequencei ) //si la frequence du terme avant i est plus grande, alors
			{
				this.listecalc.get(z).setFreq(this.listecalc.get(z-1).freq); // on intervertis les valeurs des deux variables des deux couples
				this.listecalc.get(z).setcaractere(this.listecalc.get(z-1).caractere); //pour pouvoir "echanger" leurs places
				z = z-1;
			}
			this.listecalc.get(z).setFreq(frequencei); // ici on met la valeur du i actuelle au couple i-1 initialement plus grand que i 
			this.listecalc.get(z).setcaractere(caracterei); // en terme de frequence
		}
	}





	public ArrayList<Node> listnode() {
		for(int i = 0; i< this.listecalc.size();i++) { //on parcour notre liste de couple
			int a =this.listecalc.get(i).freq; // on prend la frequence du couple de i.
			char b =this.listecalc.get(i).caractere; // on prend le caractere du couple de i.
			this.listedenode.add(new Node(a,b,null,null,""));	//on ajoute a notre liste de noeud
		}
		return this.listedenode;
		
		
	}



	public Node new_tree() {
		ArrayList<Node> noeuds = (ArrayList<Node>) listedenode.clone(); //clone la liste des noeuds existant et defini grace a listnode.
		while(noeuds.size()>1) {
	
			Node na = noeuds.get(0); 
			noeuds.remove(0);
			Node nb = noeuds.get(0);
			noeuds.remove(0);
			
			noeuds.add(new Node(na.getFreq() +nb.getFreq(),'¤',na,nb,""));
			noeuds=this.triagenode(noeuds);
			
		}
		return noeuds.get(0); //ici, le derniers noeuds restant est appelé la racine.
	}




	public ArrayList<Node> triagenode(ArrayList<Node> list) //prend la liste des noeuds et la tri par ordre croissant.
	{
		for(int i = 1;i< list.size();i++) 
		{
			
			int frequencei = list.get(i).getFreq(); //on recupere la valeur de chaque variable d'un objet Node
			char caracterei = list.get(i).getcaractere(); // pour pouvoir comparer a la valeurs correspondante au noeud d'avant.
			Node fdi= list.get(i).getfilsd();
			Node fgi =list.get(i).getfilsg() ;
			String cbinaire = list.get(i).getcbinaire();
			
			int h = i;
			while(h>0 && list.get(h-1).getFreq() > frequencei ) // on trie en fonction de la frequence des noeuds.
			{
				list.get(h).setFreq(list.get(h-1).getFreq());
				list.get(h).setfilsg(list.get(h-1).getfilsg());
				list.get(h).setcbinaire(list.get(h-1).getcbinaire());
				list.get(h).setcaractere(list.get(h-1).getcaractere());
				
				list.get(h).setfilsd(list.get(h-1).getfilsd());

				h = h-1;
			}
			list.get(h).setFreq(frequencei);
			list.get(h).setcbinaire(cbinaire);
			list.get(h).setfilsd(fdi);
			list.get(h).setcaractere(caracterei);
			list.get(h).setfilsg(fgi);

		}
		return list;
	}
	




	public void implement_tree(Node racine,String codeBin) { //on donne la racine ainsi que le code binaire qui est au debut "" vide.
		
		if(racine.testFeuille()==true) { //dans le cas ou on arrive au bout de larbre, si test feuille fonctionne c'est que les fils n'existent pas. donc bout d'arbe.
			racine.setcbinaire(codeBin); // Si on est en bout d'arbre alors, on set le code binaire correspondant a ce qui est dans les parametres fonctions
			this.dict.put(racine.getcaractere(), racine.getcbinaire()); //dans la list HashMap on inclut le caractere et son code binaire pour avoir l'index et permettre un decodage simple.
			
		}
		
		else {
			implement_tree(racine.getfilsg(), codeBin + "0");
			implement_tree(racine.getfilsd(), codeBin + "1");	
		}
	}


	
	public void textstats(String chemin) {
		try{
			File ff=new File(chemin); // cree le fichier Stat correspondant a notre texte et notre codage
			ff.createNewFile();
			FileWriter ffw=new FileWriter(ff); //crée un "ecriteur " pour pouvoir implementer les données dans le fichier
			 ffw.write("\n");
			 ffw.write("Fréquence");
			 ffw.write("\n");
			 for (calc i: this.getListecalc()) { //boucle pour pouvoir obtenir la frequence des caracteres
				 ffw.write(i.toString());  // écrire une ligne dans le fichier resultat.txt
				 ffw.write("\n"); // sauter une ligne
		    	  
		    	}
			 ffw.write("\n");
			 ffw.write("dictionnaire");
			 ffw.write("\n");
			 for (char i : this.getDict().keySet()) { //boucle pour avoir le code binaire de chaque caractere. Permet de parcourir la liste des couples .
				 ffw.write(i + "-->" + this.getDict().get(i));  // écrire une ligne dans le fichier resultat.txt
				 ffw.write("\n"); // sauter une ligne
		    	  
		    	}
			
			ffw.close(); 
			} catch (Exception e) {}
			}
	     

	   
	
	public void fichiercoder(String chemin) {
		String texte ="";
		for (int i = 0; i< this.txt.length();i++) {
			texte = texte + this.getDict().get(this.txt.charAt(i)); // comme au dessus, on met la valeurs binaires de chaque caractere dans une varible, ici texte.
			//System.out.println("texte = " + texte);
			
		
	}
		while(texte.length()%8 != 0) { //on fait attention de remplir les octets qui sont de 8 valeurs
			texte= texte +'0';
			
		}
		ArrayList<String> octet = new ArrayList<String>(); //on crée une nouvelle liste de string pour stocker les 8 chiffres de chaque caractere.
		for(int i = 8;i<=texte.length();i=i+8) {
			
			octet.add(texte.substring(i-8, i)); // on remplis la liste.
			
		}
		
		try {
			
			File fff=new File(chemin); // on crée le fichier
			fff.createNewFile();
			FileWriter fffw=new FileWriter(fff); //on crée l'editeur
			
			for(String i: octet) {
				int s = Integer.parseInt(i, 2); // on decoute nos octet en 4 bytes disctinct que l'on inclus dans le fichier.
				fffw.write((byte)s);
			}
			
			
			fffw.close();
			
			
		}
		
		catch (Exception e) {}
	}

	public float tcompress(String doctxt, String docbin) { //on a besoin ici d'avoir le doc binaire.
		File txt = new File(doctxt);
		File bin = new File(docbin);
		double txtl = txt.length();
		double nbtxt = txtl *8; // on recupere ici le nombre de byte de chaque document ici le .txt
		double bin1 = bin.length();
		double nbbin = bin1 *8; // ici le .bin
		double txtcompress = nbtxt/1024; // 1024o dans 1ko
		double bincompress = nbbin/1024;
		return (float)(1-(bincompress/txtcompress));
		
	}

	public float bitmoy() {
		float tot = 0;
		float freq =0;
		for(calc cal : this.getListecalc()) {
			tot = tot + cal.getFreq()* this.getDict().get(cal.getcaractere()).length();
			freq = freq + cal.getFreq();
		}
		return tot/freq;
	}
		
			
			

	

	}