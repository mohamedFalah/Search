import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Search {
	
		Read SearchIn = new Read();
		Hashtable<Float, Integer> term = new Hashtable<Float,Integer>();
		ArrayList<Float> frequency = new ArrayList<Float>();
		
		
		int numberOfOcccurence,numberOfOcccurence2 ;
		int indexOfList ;
		int fileTotalWords ;
		float frequencyOfWord,frequencyOfWord2;
		float productOfrelativeFrequency;
		float sumOfRelativefrequency;
		
		
		
		
		
	public void main() throws IOException{
		SearchIn.fileNames();
		SearchIn.words();
		SearchIn.writer();
		SearchIn.numberOfwords();
		System.out.println("Search Engine.... ");
		System.out.println("instruction: use  AND, OR, NOT as operators ");
		
		
		Scanner sc = new Scanner(System.in);
		String searchLine =null;
		Boolean condition = true;
		
		do{
		System.out.println("\nEnter a search term: ");
		 searchLine = sc.nextLine();
		 
		//takes the words in the input scanner
		String[] a = searchLine.split(" ");
		
		//recognize the operators in term
		if(a.length == 1){
			oneWord(a[0]);
			condition = false;
		}
		else if(a.length == 2){
			notOperator(a[1]);
			condition = false;
		}
		else if(a.length == 3){
			if(a[1].equals("AND")){
				andOperator(a[0],a[2]);
				condition = false;
			}
			else{
				orOperator(a[0],a[2]);
				condition = false;
			}
				
		}
		else{
			andNotOperator(a[0],a[2],a[4]);
			condition = false;
		}
		
	    
		}while(!condition);
		
		
		//clse the scanner
		sc.close();
		
		
	}
	
	//one word term
	public void oneWord(String word) throws FileNotFoundException{
		
		//find the word and store its information to a hashtable
		for(List<String> list : SearchIn.AllWords){
			if(list.contains(word)){
				 numberOfOcccurence = Collections.frequency(list, word);
				 indexOfList = SearchIn.AllWords.indexOf(list);
				 fileTotalWords = SearchIn.NumberOfWords.get(indexOfList);
				 frequencyOfWord = (float)numberOfOcccurence/(float)fileTotalWords;
				term.put(frequencyOfWord, indexOfList);
			}
		}
		
		/// adding frequency of words to an array to sort them
		for(Float frequencyOfWord :  term.keySet()){
			frequency.add(frequencyOfWord);	
			Collections.sort(frequency);	
		}
		
		//print the file name sorted by relative frequency
		for(int i =frequency.size()-1; i > 0; i--){
			String fileName = SearchIn.fileNames.get(term.get(frequency.get(i)));
			String someText = SearchIn.findContext(fileName,word,null,1);
			System.out.println(fileName +" "  + someText);			
		}
		
		term.clear();
		frequency.clear();
	}
	
	//term with and operator
	public void andOperator(String word, String word2) throws FileNotFoundException{
		
		
		for(List<String> list : SearchIn.AllWords){
			if(list.contains(word) && list.contains(word2)){
				numberOfOcccurence = Collections.frequency(list, word);
				numberOfOcccurence2 = Collections.frequency(list, word2);
				indexOfList = SearchIn.AllWords.indexOf(list);
				fileTotalWords = SearchIn.NumberOfWords.get(indexOfList);
				frequencyOfWord = (float)numberOfOcccurence/(float)fileTotalWords;
				frequencyOfWord2 = (float)numberOfOcccurence2/(float)fileTotalWords;
				productOfrelativeFrequency = frequencyOfWord*frequencyOfWord2;
				
				//put things in hashtable
				term.put(productOfrelativeFrequency,indexOfList);
			}
		}
		
		//sort the frequencies
		for(Float productOfrelativefrequency :  term.keySet()){
			frequency.add(productOfrelativefrequency);	
			Collections.sort(frequency);	
		}
		
		//print files name
		for(int i =frequency.size()-1; i > 0; i--){
			String fileName = SearchIn.fileNames.get(term.get(frequency.get(i)));
			String someText = SearchIn.findContext(fileName,word,word2,2);
			System.out.println(fileName +" "+ someText);
			
		}
		
		//clear hashtable and array after printing
		term.clear();
		frequency.clear();
	}
	
	
	//term with or operator
	public void orOperator(String word, String word2){
		
		for(List<String> list : SearchIn.AllWords){
			if(list.contains(word) && list.contains(word2)){
				numberOfOcccurence = Collections.frequency(list, word);
				numberOfOcccurence2 = Collections.frequency(list, word2);
				indexOfList = SearchIn.AllWords.indexOf(list);
				fileTotalWords = SearchIn.NumberOfWords.get(indexOfList);
				frequencyOfWord = (float)numberOfOcccurence/(float)fileTotalWords;
				frequencyOfWord2 = (float)numberOfOcccurence2/(float)fileTotalWords;
				sumOfRelativefrequency = frequencyOfWord+frequencyOfWord2;
				
				//put things in hashtable
				term.put(sumOfRelativefrequency,indexOfList);
			}
		}
		
		//sort the frequencies
		for(Float sumOfRelativefrequency :  term.keySet()){
			frequency.add(sumOfRelativefrequency);	
			Collections.sort(frequency);	
		}
		
		//print files name
		for(int i =frequency.size()-1; i >0; i--)
			System.out.println(SearchIn.fileNames.get(term.get(frequency.get(i))));
	
		
		//clear hashtable and array after printing
		term.clear();
		frequency.clear();
	}
	
	
	//term with not operator
	public void notOperator(String word){
		
		for(List<String> list : SearchIn.AllWords){
			if(!(list.contains(word))){
			  indexOfList = SearchIn.AllWords.indexOf(list);
			  System.out.println(SearchIn.fileNames.get(indexOfList));
			}
		}
	}
	
	
	//term with and not operators
	public void andNotOperator(String word, String word2,String word3){
		
		for(List<String> list : SearchIn.AllWords){
			if(list.contains(word) && list.contains(word2) && !(list.contains(word3)) ){
			  numberOfOcccurence = Collections.frequency(list, word);
			  numberOfOcccurence2 = Collections.frequency(list, word2);
			  indexOfList = SearchIn.AllWords.indexOf(list);
			  fileTotalWords = SearchIn.NumberOfWords.get(indexOfList);
			  frequencyOfWord = (float)numberOfOcccurence/(float)fileTotalWords;
			  frequencyOfWord2 = (float)numberOfOcccurence2/(float)fileTotalWords;
			  productOfrelativeFrequency = frequencyOfWord*frequencyOfWord2;
			  
			  term.put(productOfrelativeFrequency,indexOfList);
			}
		}
			
			for(Float productOfrelativeFrequency :  term.keySet()){
				frequency.add(productOfrelativeFrequency);	
				Collections.sort(frequency);	
			}
			
			//print files name
			for(int i =frequency.size()-1; i >0; i--)
				System.out.println(SearchIn.fileNames.get(term.get(frequency.get(i))));
			
			
			//clear hashtable and array after printing
			term.clear();
			frequency.clear();
		}
	}
	
	

