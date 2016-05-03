package com.org.ne;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Set;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ner.NamedEntityParser;
import org.apache.tika.parser.ner.opennlp.OpenNLPNERecogniser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.parser.ner.corenlp.CoreNLPNERecogniser;
import org.apache.tika.parser.ner.nltk.NLTKNERecogniser;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;



public class Ner_Extract {

  public static final String CONFIG_FILE = "tika-config.xml";



  public void call_OpenNLP(String data, String outputDirectory) throws Exception {
      String classNames = OpenNLPNERecogniser.class.getName();

      System.setProperty(NamedEntityParser.SYS_PROP_NER_IMPL, classNames);
      TikaConfig config = new TikaConfig(getClass().getClassLoader().getResource(CONFIG_FILE));
      Tika tika = new Tika(config);

      Metadata md = new Metadata();

      tika.parse(new ByteArrayInputStream(data.getBytes(Charset.defaultCharset())), md);
      HashSet<String> keys = new HashSet<>(Arrays.asList(md.names()));
      HashSet<String> values = new HashSet<String>();
   
      Iterator key_iterator = keys.iterator(); 
      System.out.println("before");
      String v = null;
      String[] entity_data = null;

      
      while (key_iterator.hasNext()){
    	  v = (String) key_iterator.next();

      	 if (v.equals("NER_PERSON") ||v.equals("NER_ORGANIZATION")||v.equals("NER_LOCATION")||v.equals("NER_DATE")){
      	 values.addAll(Arrays.asList(md.getValues(v)));
      	 }
      	   }
    

      Iterator value_iterator = values.iterator(); 
      v = null;
      
      String outputFile = outputDirectory+ "opennlpoutput.txt";
        
      try(FileWriter fw = new FileWriter(outputFile, true);
  		    BufferedWriter bw = new BufferedWriter(fw);
  		    PrintWriter out = new PrintWriter(bw))
  		{
		      while (value_iterator.hasNext()){
		    	  v = (String) value_iterator.next();
		    	  entity_data = v.split(",");
		    	  
		    	  for (String entity_string: entity_data){
		    	
		    		  out.println(entity_string);
		    	  }
		      	}
  		}
      catch (Exception ex){
    	  System.out.println(ex.getMessage());
      }
      System.out.println("--------------------open nlp call completed----------------------------");

  }
  
  public void call_CoreNLP(String data, String outputDirectory) throws Exception {
      String classNames = CoreNLPNERecogniser.class.getName();
      System.setProperty(NamedEntityParser.SYS_PROP_NER_IMPL, classNames);
      TikaConfig config = new TikaConfig(getClass().getClassLoader().getResource(CONFIG_FILE));
      Tika tika = new Tika(config);
      Metadata md = new Metadata();
      tika.parse(new ByteArrayInputStream(data.getBytes(Charset.defaultCharset())), md);
      HashSet<String> keys = new HashSet<>(Arrays.asList(md.names()));
      HashSet<String> values = new HashSet<String>();
   
      Iterator key_iterator = keys.iterator(); 
      System.out.println("before");
      String v = null;
      String[] entity_data = null;

      while (key_iterator.hasNext()){
    	  v = (String) key_iterator.next();
      	   System.out.println("Value: "+ v + " "); 
      	 if (v.equals("NER_PERSON") ||v.equals("NER_ORGANIZATION")||v.equals("NER_LOCATION")||v.equals("NER_DATE")){
      	 values.addAll(Arrays.asList(md.getValues(v)));
      	 }
      	}
      
      Iterator value_iterator = values.iterator(); 

      v = null;

      String outputFile = outputDirectory + "corenlpoutput.txt";
    
      try(FileWriter fw = new FileWriter(outputFile, true);
  		    BufferedWriter bw = new BufferedWriter(fw);
  		    PrintWriter out = new PrintWriter(bw))
  		{
		      while (value_iterator.hasNext()){
		    	  v = (String) value_iterator.next();
		    	  entity_data = v.split(",");
		    	  
		    	  for (String entity_string: entity_data){
		    		  System.out.println(entity_string);
		    		  out.println(entity_string);
		    	  }
		      	}
  		}
      catch (Exception ex){
    	  System.out.println(ex.getMessage());
      }
      
      System.out.println("-----------------------core nlp call completed----------------------"); 
  }
  
  
  //calling NLTK rest service. 
  //ensure NLTK server is running in http://localhost:8881 
  public void call_NLTK(String data, String outputDirectory) throws Exception {
      
      System.setProperty(NamedEntityParser.SYS_PROP_NER_IMPL, NLTKNERecogniser.class.getName());
      Tika tika = new Tika(new TikaConfig(getClass().getClassLoader().getResource(CONFIG_FILE)));
      Metadata md = new Metadata();
      
      tika.parse(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), md);
      Set<String> names = new HashSet<>(Arrays.asList(md.getValues("NER_NAMES")));
      
      
      String output_file = outputDirectory + "/nltkoutput.txt";
      
      try(FileWriter fw = new FileWriter(output_file, true);
    		    BufferedWriter bw = new BufferedWriter(fw);
    		    PrintWriter out = new PrintWriter(bw))
    		{
       		      if(names.size() == 0) {
    		          return;
    		      }
    		      else {
    		          Iterator names_iterator = names.iterator(); 

    		          String v = null;
    		          //System.out.println("in");
    		          while (names_iterator.hasNext()){
    		        	  v = (String) names_iterator.next();
    		          	  // System.out.println("Value: "+ v + " ");
    		          	   out.println(v);
       		          	   }
    		          //System.out.println("out");
     		      }
    		} catch (IOException ex) {
    		      System.out.println(ex.getCause());

    		}
           
      System.out.println("---------------NLTK call completed ------------------- ");
  }
  
  
  //parse the document using Tika 
  public String parseDoc(String FilePath, String FileName) throws IOException, SAXException, TikaException {
	    AutoDetectParser parser = new AutoDetectParser();
	    BodyContentHandler handler = new BodyContentHandler();
	    Metadata metadata = new Metadata();
	    
	    
	    try (InputStream stream = new FileInputStream(FilePath + FileName)) {
	        parser.parse(stream, handler, metadata);
	        return handler.toString();
	    }
	    catch(Exception ex) {
	    	
	    	return "No Data";
	    }
	    
	}
  
  public static void main(String[] args) throws Exception {
	  Ner_Extract obj = new Ner_Extract();

	//String myDirectoryPath = "/home/nandan/Documents/CSCI599/Assignment3/data/";

    //directory which contains the data  
    String myDirectoryPath = args[0];
    String outputDirectory = args[1];
    
	File dir = new File(myDirectoryPath);
	
	File[] directoryListing = dir.listFiles();
	
	String data = null;
	
	if(directoryListing != null){
		System.out.println("Processing in-progress");
		
		for (File child:directoryListing){
			try{
				data = obj.parseDoc(myDirectoryPath, child.getName());
					if(!data.equals("No Data")){
					obj.call_OpenNLP(data,outputDirectory);
					obj.call_NLTK(data,outputDirectory);
					obj.call_CoreNLP(data,outputDirectory);
				}
			}
			catch(Exception ex){
				continue;
			}
		}
	    System.out.println("Process complete");
	}
  }
}
