package com.marcelodamasceno.main;

import com.marcelodamasceno.util.ArffConector;

import weka.core.Instances;

public class VerticalInstances {
	
	private String PROJECT_PATH="/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque";

	public String getPROJECT_PATH() {
		return PROJECT_PATH;
	}


	public void setPROJECT_PATH(String pROJECT_PATH) {
		PROJECT_PATH = pROJECT_PATH;
	}


	/**
	 * Method to create a dataSet with just Vertical Strokes
	 * @param data Original Data
	 * @return DataSet with just vertical strokes
	 */
	private Instances verticalInstances(Instances data){
		Editor editor=new Editor(data);
		//removing instances with horizontal moviments
		editor.removeInstances(9, 1.0);
		editor.removeInstances(9, 0.666667);
		return editor.getInstances();		
	}
	
	
	/**
	 * Method to extract only the vertical strokes and save in the PROJECT_PATH Folder
	 * @param data Original DataSet
	 */
	public void execute(Instances data){
		//Creating Vertical DataSets
		ArffConector conector=new ArffConector();
		int classValue=1;
		for(;classValue<=data.numClasses();classValue++){
			Instances dataSet=conector.openDataSet(PROJECT_PATH+"/IntraSession/IntraSet Balanced-"+classValue+".arff");
			conector.save(verticalInstances(dataSet),PROJECT_PATH+"/IntraSession","Vertical IntraSet Balanced-"+classValue+".arff");
		}
	}

}
