package com.marcelodamasceno.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Instances;

import com.marcelodamasceno.util.ArffConector;
import com.marcelodamasceno.util.Utils;

public class InterWeekGenerator {

	private Utils utils=new Utils();
	private ArffConector conector=new ArffConector();

	private ArrayList<Instances> generateSettoInterWeekExperiments(Instances data, boolean save, int user){
		Instances dataSet= new Instances(data, data.numInstances());
		ArrayList<Instances> interWeekDataSets= new ArrayList<Instances>();
		Utils util=new Utils();
		//Day 1 - Scrolling - Training
		for (int doc = 1; doc <= 3; doc++) {
			dataSet=util.mergeDataSets(dataSet, utils.getInstancesWithDoc(data, doc));
		}
		if(dataSet.numInstances()>0){
			interWeekDataSets.add(dataSet);
			if(save)
				conector.save(dataSet,utils.getPROJECT_PATH()+"/InterWeek","InterWeek-User_"+user+"_Day_1_Scrolling_Training.arff");
		}			
		dataSet.delete();

		//Next Week - Scrolling - Testing		
		dataSet=util.mergeDataSets(dataSet, utils.getInstancesWithDoc(data, 6));

		if(dataSet.numInstances()>0){
			interWeekDataSets.add(dataSet);
			if(save)
				conector.save(dataSet,utils.getPROJECT_PATH()+"/InterWeek","InterWeek-User_"+user+"_NextWeek_Scrolling_Testing.arff");
		}			
		dataSet.delete();

		//Day 1 - Horizontal
		for (int doc = 4; doc <= 5; doc++) {
			dataSet=util.mergeDataSets(dataSet, utils.getInstancesWithDoc(data, doc));
			//Instances.mergeInstances(dataSet, getInstancesWithDoc(data, doc));				
		}			
		if(dataSet.numInstances()>0){
			interWeekDataSets.add(dataSet);
			if(save)
				conector.save(dataSet,utils.getPROJECT_PATH()+"/InterWeek","InterWeek-User_"+user+"_Day_1_Horizontal_Training.arff");
		}		
		//Next Week - Scrolling - Testing		
		dataSet=util.mergeDataSets(dataSet, utils.getInstancesWithDoc(data, 7));

		if(dataSet.numInstances()>0){
			interWeekDataSets.add(dataSet);
			if(save)
				conector.save(dataSet,utils.getPROJECT_PATH()+"/InterWeek","InterWeek-User_"+user+"_NextWeek_Horizontal_Testing.arff");
		}			
		dataSet.delete();
		return interWeekDataSets;
	}

	/**
	 * Method to execute the Generator to InterSession DataSets
	 * @param data Original DataSet
	 * @param save Save the generated files
	 * @return Set of generated files
	 */
	public ArrayList<Instances> execute(Instances data,boolean save, int user){
		return generateSettoInterWeekExperiments(data,save,user);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InterWeekGenerator inter=new InterWeekGenerator();
		ArffConector conector=new ArffConector();
		/*Original DataSet*/ 
		Instances data;
		Balancer balancer=new Balancer();
		BinaryTransformation binary= new BinaryTransformation();
		try {
			data = conector.openDataSet("/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque/TouchAnalytics/dataset_processada_artigo2.arff");
			Instances dataBalanced=new Instances(data);
			//creating intra-week dataset for each user
			//Binary dataSets. One dataset for each user
			ArrayList<Instances> dataSets= new ArrayList<Instances>();
			dataSets=binary.binaryTransformation(data);
			//Balancing
			for(int classe=0;classe<dataSets.size();classe++){
				dataBalanced=balancer.customBalanced(dataSets.get(classe),classe+1);
				inter.execute(dataBalanced, true,classe+1);
			}		
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
