//Main servant à récuperer le nom de fichier à coder et à lancer codage()
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Sasir le Nom du fichier à coder :");
		String str1 = sc.nextLine();


		

		Compress test = new Compress(str1);
	    test.codage(str1);
	   
	   
	  
	  
	   
	}
}
	