import java.io.*;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;


public class ContentMetadata {
    public static void main(String args[]) throws IOException, SAXException, TikaException {
        if(args.length != 1){
            System.out.println("Usage ContentMetadata <source_folder>");
        }
        
        File dir  = new File(args[0]);
        File contentFile = new File("D:\\PolarDump\\message\\content.txt");
        File metadataFile = new File("D:\\PolarDump\\message\\metadata.txt");

        // if file doesnt exists, then create it
        if (!contentFile.exists()) {
            contentFile.createNewFile();
        }

        if (!metadataFile.exists()) {
            metadataFile.createNewFile();
        }

        FileWriter fw = new FileWriter(contentFile.getAbsoluteFile());
        BufferedWriter contentbw = new BufferedWriter(fw);

        FileWriter fw1 = new FileWriter(metadataFile.getAbsoluteFile());
        BufferedWriter metadatabw = new BufferedWriter(fw1);

        if(dir.isDirectory()) {
            for (File f : dir.listFiles()) {

                FileInputStream inputStream = new FileInputStream(f);
                Parser parser = new AutoDetectParser();
                BodyContentHandler handler = new BodyContentHandler(10000000);
                Metadata metadata = new Metadata();
                ParseContext parseContext = new ParseContext();
                try {
                    parser.parse(inputStream, handler, metadata, parseContext);
                }catch (Exception e){
                    e.printStackTrace();
                }
                contentbw.write(handler.toString());
                String[] metadataNames = metadata.names();

                for(String name: metadataNames){
                    metadatabw.write(name+":"+metadata.get(name));
                }
            }
        }
        metadatabw.close();
        contentbw.close();

    }
}
