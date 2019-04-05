import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Read {
	
	
	
	ArrayList<String> fileNames = new ArrayList<String>();
	ArrayList<Integer> NumberOfWords = new ArrayList<Integer>();
	List<String> wordsList = new ArrayList<String>() ;
	LinkedList<List<String>> AllWords = new LinkedList<List<String>>();
	Set<String> StopWords = new HashSet<String>();
	
	String target_dir = "src/files";
    File dir = new File(target_dir);
    File[] files = dir.listFiles();
	
    //insert file names in an array
	public void fileNames(){
	    for(File f : files){
	    	if(!(f.getName().equals(".DS_Store"))){
	    		fileNames.add(f.getName());
	    	}
	    }
	}
	
	
	//count number of words for each file
	public void numberOfwords() throws FileNotFoundException{
		for(File f : files){
			Scanner sc = new Scanner(new FileInputStream(f));
	        	 int count=0;
	        	 while(sc.hasNext()){
	        	       if(!(sc.next().equals("[^A-Za-z]")))
	        	        count++;
	        	    }
	        	 if(!(f.getName().equals(".DS_Store"))){
	        		 NumberOfWords.add(count);
	        	 }
	     	 sc.close();	
			}
		}
	
	
	
	//Store words 
	public void words() throws IOException{
		
		for(File f : files){
		
		 if(f.isFile() && !(f.getName().equals(".DS_Store"))) {
			 Scanner sc = new Scanner(new FileInputStream(f));
                 readStopWords();
        	   wordsList = new ArrayList<String>();
                 while (sc.hasNext()) {
                	 String theWord = sc.next().toLowerCase();
                	 theWord = theWord.replaceAll("[^A-Za-z]", "");
                	// String addingWord = deletePunctuation(theWord);
                    	if(!(StopWords.contains(theWord))){
                    		wordsList.add(theWord);
                    	  Collections.sort(wordsList);
                      }
                     }
                 AllWords.add(wordsList);
                 
          
                 
                
                 sc.close();
             }

	}
	}
	
	//write words in external file
	public void writer() throws IOException{
		
		FileWriter writer = new FileWriter("src/wordsnew.txt");
		
		int fileIndex = 0;
	    for(List<String> str: AllWords) {
	    	if(fileNames.size() > fileIndex)
	    		writer.write(fileNames.get(fileIndex)+ "\n");
	    	for(int j = 0; j < str.size(); j++){
	    		writer.write(j +"- "+str.get(j)+ "\n");
	    	}
	    	
	    	writer.write("\n\n");
	      fileIndex++;
	    }
	    
	    writer.close();
	}
	
	
	
	//delete stop words
	public  void readStopWords() throws FileNotFoundException{
		Scanner sc = new Scanner(new FileInputStream("src/StopWords.txt"));
		
		while(sc.hasNext()){
			String newWord = sc.nextLine();
			newWord.replaceAll("\"", "");
			StopWords.add(newWord);
		}
		
		sc.close();
	
	}
	
	
	//print context of file
	public String findContext(String FileName, String word, String word2, int operator) throws FileNotFoundException{
		ArrayList<String> temp = new ArrayList<String>();
		Scanner sc = new Scanner(new FileInputStream("src/files/"+FileName));
		String context = null;
		while(sc.hasNext()){
			String fileWords = sc.next().replaceAll("[^A-Za-z]", "");
			
			temp.add(fileWords.toLowerCase());
		}
		if(operator == 1){
			if(temp.contains(word)){
			int index = temp.indexOf(word);
			context = "..."+temp.get(index)+" "+temp.get(index+1)+" "+temp.get(index+2)+ "...";
		}
		}
		else
		{
			if(temp.contains(word) && temp.contains(word2)){
				int index = temp.indexOf(word);
				if(temp.indexOf(word2) == index+1)
					context = "..."+temp.get(index)+" "+temp.get(index+1)+" "+temp.get(index+2)+" "+temp.get(index+3)+"...";
				else
					context = "...";
			}
			
		}
		
		sc.close();
		
		return context;
		
	}
	
	
	

}
