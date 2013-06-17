package com.marcelodamasceno.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.marcelodamasceno.util.ArffConector;
import com.marcelodamasceno.util.Utils;

import weka.core.Instances;

public class IntraSessionGenerator {

	private ArffConector conector=new ArffConector();
	private String PROJECT_PATH="/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque";
	final int userIndex=29;
	final int docIndex=0;

	/**
	 * Method to generate dataset to a user in a session, i.e, each interaction with a document
	 * @param data Original DataSet
	 * @param user Subject
	 * @param doc Id of the Document or Session Number (1 to 7)
	 * @return
	 */
	protected Instances intraSessionInstances(Instances data, String user, int doc){
		Editor editor=new Editor(data);		
		Instances userDataSet=editor.getInstancesAttributeValue(userIndex, user);
		return editor.getInstancesAttributeValue(userDataSet,docIndex, doc);
	}

	/**
	 * Method to generate dataset to a user in a session, i.e, each interaction with a document
	 * @param data Original DataSet
	 * @param user Subject
	 * @param doc Id of the Document or Session Number (1 to 7)
	 * @return
	 */
	protected Instances intraSessionInstances(Instances data, int user, int doc){
		Editor editor=new Editor(data);		
		Instances userDataSet=editor.getInstancesAttributeValue(userIndex, user);
		return editor.getInstancesAttributeValue(userDataSet,docIndex, doc);
	}

	/**
	 * Method to generate dataset to each user in each session, i.e, each interaction with a document
	 * @param data Original DataSet
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void generateSettoIntraSessionExperiments2(Instances data){
		int numUsers=data.numDistinctValues(29);
		int numDocs=data.numDistinctValues(0);		
		for(int user=1;user<=numUsers;user++){			
			for (int doc = 1; doc <= numDocs; doc++) {				
				Instances dataSet=intraSessionInstances(data, user, doc);		
				if(dataSet.numInstances()>0){
					conector.save(dataSet,PROJECT_PATH+"/IntraSession","IntraSession-User_"+user+"_Doc_"+doc+".arff");
				}
			}			
		}
	}

	private void generateSettoIntraSessionExperiments(Instances data, boolean save){


		//creating intra-session dataset for each user
		Balancer balancer=new Balancer();
		BinaryTransformation binary=new BinaryTransformation();
		try {
			//Binary dataSets
			ArrayList<Instances> dataSetsBinary=binary.binaryTransformation(data);
			//Balanced dataSets
			ArrayList<Instances> dataSetsBalanced= new ArrayList<Instances>();
			//Intra-Session dataSets
			ArrayList<Instances> dataSetsIntraSession= new ArrayList<Instances>();
			//Balancing and generating Intra-Session for each user (user=classe+1)
			for(int classe=0;classe<dataSetsBinary.size();classe++){
				dataSetsBalanced.add(balancer.customBalanced(dataSetsBinary.get(classe),classe+1));
				Utils util=new Utils();
				//Temporary dataSet
				Instances dataSet= new Instances(dataSetsBalanced.get(0), data.numInstances());
				InterSessionGenerator inter=new InterSessionGenerator();
				//Day 1 - Scrolling
				for (int doc = 1; doc <= 3; doc++) {
					dataSet=util.mergeDataSets(dataSet, inter.utils.getInstancesWithDoc(dataSetsBalanced.get(classe), doc));
				}
				if(dataSet.numInstances()>0){					
					dataSetsIntraSession.add(classe, dataSet);					
					if(save)
						conector.save(dataSet,PROJECT_PATH+"/IntraSession","IntraSession-User_"+(classe+1)+"_Day_1_Scrolling.arff");
				}			
				dataSet.delete();

				//Day 1 - Horizontal
				for (int doc = 4; doc <= 5; doc++) {
					dataSet=util.mergeDataSets(dataSet, inter.utils.getInstancesWithDoc(dataSetsBalanced.get(classe), doc));
				}			
				if(dataSet.numInstances()>0){					
					dataSetsIntraSession.add(classe, dataSet);	
					if(save)
						conector.save(dataSet,PROJECT_PATH+"/IntraSession","IntraSession-User_"+(classe+1)+"_Day_1_Horizontal.arff");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void execute(Instances data) {
		generateSettoIntraSessionExperiments(data,true);
	}

	public String getPROJECT_PATH() {
		return PROJECT_PATH;
	}

	public void setPROJECT_PATH(String pROJECT_PATH) {
		PROJECT_PATH = pROJECT_PATH;
	}

	public static void main(String args[]) {
		IntraSessionGenerator intra=new IntraSessionGenerator();
		ArffConector conector=new ArffConector();
		Instances data;
		try {
			data = conector.openDataSet("/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque/TouchAnalytics/dataset_processada_artigo2.arff");
			intra.execute(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
