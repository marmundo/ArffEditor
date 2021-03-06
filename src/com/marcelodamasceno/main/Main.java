package com.marcelodamasceno.main;


import java.io.IOException;
import java.util.Enumeration;
import weka.core.Attribute;
import com.marcelodamasceno.util.ArffConector;



public class Main {

	static ArffConector conector=new ArffConector();
	static String PROJECT_PATH="/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque";
	static final int userIndex=29;
	static final int docIndex=0;

	public Attribute removeValueFromAttributeHeader(Attribute a,int value){
		System.out.println(a.name());
		@SuppressWarnings("unchecked")
		Enumeration<String> en = a.enumerateValues();
		while (en.hasMoreElements()) {
			String type = en.nextElement();
			System.out.println(type);			
		}		
		return a;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args){	
		/*
		//Original DataSet 
		Instances data=conector.openDataSet("/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque/TouchAnalytics/dataset_processada_artigo2.arff");
		Main main=new Main();
		 */

		/*
		//creating intra-session dataset for each user
		try {
			//Binary dataSets
			ArrayList<Instances> dataSets=main.binaryTransformation(data);
			//Balancing
			for(int classe=0;classe<dataSets.size();classe++){
				main.customBalanced(dataSets.get(classe),classe+1);
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */







		//main.removeValueFromAttributeHeader(data.attribute(0), 5);

		//main.generateSettoInterSessionExperiments(data);
		//main.generateSettoIntraSessionExperiments(data);
		//main.scrollingInstances(data);
		//conector.save(main.verticalInstances(data), PROJECT_PATH ,"VerticalInstances.arff");
		//conector.save(main.scrollingInstances(data), PROJECT_PATH ,"ScrollingInstances.arff");
	}

}
