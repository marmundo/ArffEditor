package com.marcelodamasceno.main;

import java.io.FileNotFoundException;

import com.marcelodamasceno.util.ArffConector;

import weka.core.Instances;

public class ScrollingInstances {
	
	
	private String PROJECT_PATH="/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque";

	public String getPROJECT_PATH() {
		return PROJECT_PATH;
	}


	public void setPROJECT_PATH(String pROJECT_PATH) {
		PROJECT_PATH = pROJECT_PATH;
	}

	/**
	 * Method to create a dataSet with just Horizontal Strokes
	 * @param data Original Data
	 * @return DataSet with just horizontal strokes
	 */
	private Instances scrollingInstances(Instances data){
		Editor editor=new Editor(data);
		//removing instances with vertical moviments
		editor.removeInstances(9, 0.0);
		editor.removeInstances(9, 0.333333);
		return editor.getInstances();		
	}


	public void execute(Instances data){
		//Creating Horizontal DataSets
		ArffConector conector=new ArffConector();
		int classValue=1;
		for(;classValue<=data.numClasses();classValue++){
			Instances dataSet;
			try {
				dataSet = conector.openDataSet(PROJECT_PATH+"/IntraSession/IntraSet Balanced-"+classValue+".arff");
				conector.save(scrollingInstances(dataSet),PROJECT_PATH+"/IntraSession","Scrolling IntraSet Balanced-"+classValue+".arff");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}
}
