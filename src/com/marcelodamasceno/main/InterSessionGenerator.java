package com.marcelodamasceno.main;

import java.io.IOException;
import java.util.ArrayList;

import com.marcelodamasceno.util.ArffConector;
import com.marcelodamasceno.util.Utils;

import weka.core.Instances;

public class InterSessionGenerator {

	private ArffConector conector=new ArffConector();
	private String PROJECT_PATH="/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque";
	final int userIndex=29;
	final int docIndex=0;



	public String getPROJECT_PATH() {
		return PROJECT_PATH;
	}
	public void setPROJECT_PATH(String pROJECT_PATH) {
		PROJECT_PATH = pROJECT_PATH;
	}

	protected Instances getInstancesWithDoc(Instances data, int doc){
		Editor editor=new Editor(data);		
		return editor.getInstancesAttributeValue(data,docIndex, doc);
	}

	private ArrayList<Instances> generateSettoInterSessionExperiments(Instances data, boolean save, int user){
		Instances dataSet= new Instances(data, data.numInstances());
		ArrayList<Instances> interSessionDataSets= new ArrayList<Instances>();
		Utils util=new Utils();
		//Day 1 - Scrolling
		for (int doc = 1; doc <= 3; doc++) {
			dataSet=util.mergeDataSets(dataSet, getInstancesWithDoc(data, doc));
		}
		if(dataSet.numInstances()>0){
			interSessionDataSets.add(dataSet);
			if(save)
				conector.save(dataSet,PROJECT_PATH+"/InterSession","InterSession-User_"+user+"_Day_1_Scrolling.arff");
		}			
		dataSet.delete();

		//Day 1 - Horizontal
		for (int doc = 4; doc <= 5; doc++) {
			dataSet=util.mergeDataSets(dataSet, getInstancesWithDoc(data, doc));
			//Instances.mergeInstances(dataSet, getInstancesWithDoc(data, doc));				
		}			
		if(dataSet.numInstances()>0){
			interSessionDataSets.add(dataSet);
			if(save)
				conector.save(dataSet,PROJECT_PATH+"/InterSession","InterSession-User_"+user+"_Day_1_Horizontal.arff");
		}		
		return interSessionDataSets;
	}


	/**
	 * @param data
	 * @param save
	 * @return
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private ArrayList<Instances> generateSettoInterSessionExperiments2(Instances data, boolean save){
		ArrayList<Instances> dataSets= new ArrayList<Instances>();
		Instances dataSet= new Instances(data, data.numInstances());
		IntraSessionGenerator intra=new IntraSessionGenerator();
		//Day 1 - Scrolling
		for(int user=1;user<=data.numClasses();user++){
			for (int doc = 1; doc <= 3; doc++) {				
				Instances.mergeInstances(dataSet, intra.intraSessionInstances(data, "positive", doc));
				Instances.mergeInstances(dataSet, intra.intraSessionInstances(data, "negative", doc));
			}			
			if(dataSet.numInstances()>0){
				dataSets.add(dataSet);
				if(save)
					conector.save(dataSet,PROJECT_PATH+"/InterSession","InterSession-User_"+user+"_Day_1_Scrolling.arff");
			}	
			dataSet.delete();
			//Day 1 - Vertical
			for (int doc = 4; doc <= 5; doc++) {				
				dataSet=intra.intraSessionInstances(data, user, doc);				
			}			
			if(dataSet.numInstances()>0){
				dataSets.add(dataSet);
				if(save)
					conector.save(dataSet,PROJECT_PATH+"/InterSession","InterSession-User_"+user+"_Day_1_Vertical.arff");

			}	/*
			dataSet.delete();
			//Day 2 - Scrolling						
			dataSet=intra.intraSessionInstances(data, user, 6);
			if(dataSet.numInstances()>0){
				dataSets.add(dataSet);
				if(save)
					conector.save(dataSet,PROJECT_PATH+"/InterSession","InterSession-User_"+user+"_Day_2_Scrolling.arff");
			}
			//Day 2 - Vertical						
			dataSet=intra.intraSessionInstances(data, user, 7);
			if(dataSet.numInstances()>0){
				dataSets.add(dataSet);
				if(save)
					conector.save(dataSet,PROJECT_PATH+"/InterSession","InterSession-User_"+user+"_Day_2_Vertical.arff");
			}*/
		}
		return dataSets;
	}
	@SuppressWarnings("unused")
	private Instances interSessionInstances(Instances data, ArrayList<Integer> user, ArrayList<Integer> doc){
		Editor editor=new Editor(data);		
		Instances userDataSet=editor.getInstancesAttributeValue(userIndex, user);
		return editor.getInstancesAttributeValue(userDataSet,docIndex, doc);
	}

	@SuppressWarnings("unused")
	private Instances interSessionInstances(Instances data, int user, int doc){
		Editor editor=new Editor(data);		
		Instances userDataSet=editor.getInstancesAttributeValue(userIndex, user);
		return editor.getInstancesAttributeValue(userDataSet,docIndex, doc);
	}

	/**
	 * Method to execute the Generator to InterSession DataSets
	 * @param data Original DataSet
	 * @param save Save the generated files
	 * @return Set of generated files
	 */
	public ArrayList<Instances> execute(Instances data,boolean save, int user){
		return generateSettoInterSessionExperiments(data,save,user);
	}

	public static void main(String args[]){
		InterSessionGenerator inter=new InterSessionGenerator();
		ArffConector conector=new ArffConector();


		/*Original DataSet*/ 
		Instances data=conector.openDataSet("/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque/TouchAnalytics/dataset_processada_artigo2.arff");
		Instances dataBalanced=new Instances(data);
		Main main=new Main();

		Balancer balancer=new Balancer();
		BinaryTransformation binary= new BinaryTransformation();

		//creating intra-session dataset for each user
		try {
			//Binary dataSets
			ArrayList<Instances> dataSets= new ArrayList<Instances>();
			dataSets=binary.binaryTransformation(data);
			//Balancing
			for(int classe=0;classe<dataSets.size();classe++){
				dataBalanced=balancer.customBalanced(dataSets.get(classe),classe+1);
				inter.execute(dataBalanced, true,classe+1);
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
