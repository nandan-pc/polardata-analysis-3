import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.tika.language.*;


import org.xml.sax.SAXException;

public class LangaugeAnalysis {

	static HashMap<String, ArrayList<String>> languageMap = new HashMap<String, ArrayList<String>>();
	
 
	public static String detectLanguage(String FilePath) throws IOException, SAXException, TikaException{
				
		  String filePath = FilePath.replace("\\", "\\\\");

		  File file = new File(filePath);

	      //Parser method parameters
	      Parser parser = new AutoDetectParser();
	      BodyContentHandler handler = new BodyContentHandler();
	      Metadata metadata = new Metadata();

	      FileInputStream content = new FileInputStream(file);

	      //Parsing the given document
	      parser.parse(content, handler, metadata, new ParseContext());

	      LanguageIdentifier object = new LanguageIdentifier(handler.toString());
	      
	      return object.getLanguage();
	
}
	public static void showFiles(File[] file,String outputDirectorys)throws Exception{
		String language = null;
		
		for (File file: files){
			if (file.isDirectory()){
				//System.out.println("Directory : " + file.getName());
				showFiles(file.listFiles());
				
			} else {
				//System.out.println("File : " + file.getName());
				//System.out.println("File Path : " + file.getCanonicalPath());
				language = detectLanguage(file.getCanonicalPath());
				//System.out.println("Language : " + language);
				
				mapLanguage(file.getCanonicalPath());
				
				printLanguageMap();
			}
			
		}
		
		writeJson(outputDirectory);
		
	}
	
	public static void mapLanguage(String FileName ) throws IOException, SAXException, TikaException{
		
		String language = detectLanguage(FileName);
		
		boolean keyPresent = languageMap.containsKey(language);
		
		if (keyPresent){
			languageMap.get(language).add(FileName);
		}
		else{
			ArrayList<String> fileNameList = new ArrayList<String>();
			
			fileNameList.add(FileName);
			
			languageMap.put(language, fileNameList);
		}
	}
	
	public static void printLanguageMap(){
		
		
		for(String language : languageMap.keySet()){
			
			System.out.println("language : " + language);
			System.out.println("Number of files : " + languageMap.get(language).size());
			System.out.println("Files : " + languageMap.get(language));
		}
		
		
	}
	
	public static void writeJson(String outputDirectory) throws IOException{
		
		JSONArray langDetail = new JSONArray();
		
		JSONObject[] langObject = new JSONObject[languageMap.size()];
		
		int i =0 ;
		
		for(String language : languageMap.keySet()){
			langObject[i] = new JSONObject();
			langObject[i].put("Language", language);
			langObject[i].put("Num", languageMap.get(language).size());
			
			langDetail.add(langObject[i]);
			i++;
		}
		
		try (FileWriter file = new FileWriter(outputDirectory + "file1.json")) {
			file.write(langDetail.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + langDetail);
		}
		
	}


	
	
	public static void main(String[] args) throws Exception{
		
		String inputDirectory = args[0];
		String outputDirectory = args[1]
		File[] files = new File(inputDirectory).listFiles();
		showFiles(files,outputDirectory);
	}
	
}
