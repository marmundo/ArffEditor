package com.marcelodamasceno.main;

import java.io.FileNotFoundException;

import com.marcelodamasceno.util.ArffConector;

import weka.core.Instances;

public class Attribute {

    Instances dataset;
    public Attribute(Instances dataset) {
	this.dataset=dataset;
    }

    public void remove(String attName){
	
    }
    
    public void remove(int index){
	dataset.deleteAttributeAt(index);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
	String projectPath = "/home/marcelo/Área de Trabalho/Documentos-Windows/Google Drive/doutorado/projeto/dataset/Base de Toque/";
	String folderResults = "InterSession/";
	ArffConector conector=new ArffConector();
	for (int user = 1; user <= 41; user++) {
	    String fileName = "InterSession-User_" + user
			+ "_Day_1_Scrolling.arff";
	    try {
		Instances dataset = conector.openDataSet(projectPath
			+ folderResults + fileName);
		Attribute atr= new Attribute(dataset);
		atr.remove(0);
		atr.remove(9);
		conector.save(dataset, projectPath+"/InterSession-SemNominal","InterSession-User_" + user
			+ "_Day_1_Scrolling.arff");
		
		fileName = "InterSession-User_" + user
			+ "_Day_1_Horizontal.arff";
		dataset = conector.openDataSet(projectPath
			+ folderResults + fileName);
		atr= new Attribute(dataset);
		atr.remove(0);
		atr.remove(9);
		conector.save(dataset, projectPath+"/InterSession-SemNominal","InterSession-User_" + user
			+ "_Day_1_Horizontal.arff");
		
		
	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	}

    }

}
