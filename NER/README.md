Named Entinty Recognition  

In this program we use CoreNLP, OpenNLP and NLTK Ner over Polar data.   
The output of the data is used by maximal_joint program to get the maximal joint of the result of above three NER's

Usage of Ner_Extract program. 

>java Ner_Extract \<input_directory\> \<output_directory\>

The NER output is present in ner_result for CoreNLP, OpenNLP and NLTK respectively. 

To view the visualization we have used D3 venn.js which uses  lastfm.jsonp 
> /node_modules/venn.js/examples/intersection_tooltip.html
