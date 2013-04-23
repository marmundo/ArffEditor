package com.marcelodamasceno.util;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;

import com.marcelodamasceno.main.BinaryTransformation;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class CustomBalanced {
	ArffConector conector;

	public CustomBalanced(){
		conector=new ArffConector();
	}

	/**
	 * This method balanced a unbalanced dataset.
	 * The approach is: Suppose n classes with differents number of instances in each class. 
	 * Chossing class #1 with x instances, this method will choose x/n instances of each n-1 remaining classes.
	 * @param data Instances to be balanced
	 * @param positiveClass The class name that will be positive
	 * @return
	 */
	public Instances customBalanced(Instances data,String positiveClass){		
		Instances dataPositive=conector.getInstancesWithClasse(data, positiveClass);
		//dataPositive.numInstances()/data.numClasses()-1 is a number of instances that I have to get from each class	
		int numInstancesGotFromNegativeClass=dataPositive.numInstances()/(data.numClasses()-1);		
		Enumeration enu=data.classAttribute().enumerateValues();
			while(enu.hasMoreElements()){
			String classe=(String) enu.nextElement();
			if(!classe.equals(positiveClass)){				
				Instances dataNegative = conector.getInstancesWithClasse(data, classe, numInstancesGotFromNegativeClass);
				conector.mergeInstances(dataPositive, dataNegative);				
			}
		}		
		return dataPositive;
	}



	public static void main(String args[]) throws IOException{
		ArffConector conector=new ArffConector();
		Instances data=conector.openDataSet("C:/Users/Marcelo/Documents/Google Drive/doutorado/projeto/dataset/Base de Toque/TouchAnalytics/dataset_processada_artigo2.arff");
		CustomBalanced balanced = new CustomBalanced();
		BinaryTransformation transformation= new BinaryTransformation();
		int classValue=1;
		for(;classValue<=data.numClasses();classValue++){
			Instances dataTransformed=balanced.customBalanced(data, String.valueOf(classValue));
			dataTransformed=transformation.transformation(dataTransformed, String.valueOf(classValue));
			ArffSaver saver=new ArffSaver();
			saver.setInstances(dataTransformed);
			saver.setFile(new File("binary"+String.valueOf(classValue)+".arff"));
			saver.writeBatch();
		}
	}

}
