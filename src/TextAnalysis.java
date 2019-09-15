import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Text is entered by keyboard and analyzed
 * 
 * @author manuel
 *
 */
public class TextAnalysis {

	/**
	 * @param args None is used
	 */
	public static void main(String[] args) {
		// Test strings
		//String str = "Hacia un buen día. Las mariposas.com revoloteaban sobre los campos en flor. "
		//		+ "Los pajaritos.com inundaban el aire con sus trinos. Y los mosquitos.com"
		//		+ " se merendaban mis brazos llenándolos de ronchas.";
		//String str = "z y La la le. le b a";
		//String str = "z y Le le la. la b a";
		String str = KeyboardInput.getString("Texto para su análisis");

		textAnalysis(str);
	};
	
	private static void textAnalysis(String str) {
		ArrayList<String> alsParagraphs = new ArrayList<>();
		ArrayList<String> alsWords = new ArrayList<>();
		int cnt = 0;
		String word = "";
		
		Scanner scn = new Scanner(str);
		// I look for paragraphs separated by dots and spaces 
		// to avoid, for example, file names will be truncated.
		scn.useDelimiter("\\s*\\.+\\s+|\\.$");
		
		while(scn.hasNext()) 
			alsParagraphs.add(scn.next());
		
		Collections.sort(alsParagraphs, (a, b)->Integer.compare(a.length(), b.length()));
		
		// Prints the ordered paragraphs and search for words (alsWords)
		// In addition to spaces I search for words separated by punctuation marks, quotes, etc.
		scn.useDelimiter("\\W+");
		System.out.println("\nFRASES ORDENADAS DE MENOR A MAYOR LONGITUD: \n");
		for(String s:alsParagraphs) {
			System.out.println(s + ".");
			scn = new Scanner(s);
			while(scn.hasNext()) 
				// With all the words in lowercase we'll have more repetitions
				alsWords.add(scn.next().toLowerCase());
		}
		
		// Sort words alphabetically (very important);
		Collections.sort(alsWords);
		
		// Counts the word repetitions and then saves them in another ArrayList (alsCountedWords)
		ArrayList<String> alsCountedWords = new ArrayList<>(); 
		for(String s:alsWords)
			if(s.equals(word))
				cnt++;
			else if(word.equals("")) {
				cnt++;
				word = s;
			}else {
				alsCountedWords.add(word + " (" + cnt);
				cnt = 1;
				word = s;
			};
		if(!word.equals(""))
			alsCountedWords.add(word + " (" + cnt);
		
		// Sort the words by the number of repetitions with a comparator. 
		// By exchanging the variables in the comparator, the order is reversed. 
		// It is guaranteed that the Collections.sort method is stable: equal elements 
		// will not be rearranged as a result of sorting, this means that the words of 
		// the same number of iterations keep them ordered alphabetically. 
		//
		// https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#sort(java.util.List,%20java.util.Comparator)
		Collections.sort(alsCountedWords, (a, b)->Integer.compare(
				Integer.parseInt(b.substring(b.lastIndexOf('(') + 1)), 
				Integer.parseInt(a.substring(a.lastIndexOf('(') + 1))));
		// Another reverse order method.
		//Collections.sort(alsCountedWords, Collections.reverseOrder(
		//		(a, b)->Integer.compare(
		//				Integer.parseInt(a.substring(a.lastIndexOf('(') + 1)), 
		//				Integer.parseInt(b.substring(b.lastIndexOf('(') + 1)))
		//		));
		
		// Show ordered words 
		System.out.println("\nPALABRAS ORDENADAS POR NÚMERO DE ITERACIONES Y ALFABÉTICAMENTE: \n");
		for(String s:alsCountedWords)
			System.out.println(s + ")");
		scn.close();
	};
};
