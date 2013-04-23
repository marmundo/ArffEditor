package com.marcelodamasceno.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import com.marcelodamasceno.util.ArffConector;

import weka.core.Instance;
import weka.core.Instances;


public class BinaryTransformation {
	
	ArffConector conector=new ArffConector();
	
	/**
	 * * This method transform the balanced dataset balanced using the customBalanced method in a binary dataset. 
	 * Where class passed by argument will be the positive. 
	 * @param data DataSet
	 * @param classe Positive Class
	 * @return Binary DataSet
	 */
	public Instances transformation(Instances data, String classe){
		ArffConector conector=new ArffConector();
		Instances dataTransformed = conector.changeAttributeNames(data, data.classIndex(),classe);
		Instance inst=dataTransformed.instance(0);	
		dataTransformed.delete();
		@SuppressWarnings("unchecked")
		Enumeration<Instance> enu=data.enumerateInstances();		
		while (enu.hasMoreElements()) {			
			Instance instance = (Instance) enu.nextElement();	
			if(instance.stringValue(instance.classIndex()).equals(classe)){
				inst=conector.copyIntanceWithoutClass(instance, inst, "positive");	
				dataTransformed.add(inst);				
			}else{				
				inst=conector.copyIntanceWithoutClass(instance, inst, "negative");
				dataTransformed.add(inst);							
			}
		}
		return dataTransformed;
	}


	/**
	 * Method to binary all the users from the TouchAnalytics DataSet
	 * @param data
	 * @throws IOException
	 */
	protected void binaryTransformationSaver(Instances data) throws IOException{
		Instances dataTransformed;
		BinaryTransformation transformation= new BinaryTransformation();
		int classValue=1;
		for(;classValue<=data.numClasses();classValue++){
			dataTransformed=transformation.transformation(data, String.valueOf(classValue));
			conector.save(dataTransformed, "binaryComplete"+String.valueOf(classValue)+".arff");
		}
	}

	protected ArrayList<Instances> binaryTransformation(Instances data) throws IOException{
		Instances dataTransformed;
		ArrayList<Instances> dataSets=new ArrayList<Instances>();
		BinaryTransformation transformation= new BinaryTransformation();
		int classValue=1;
		for(;classValue<=data.numClasses();classValue++){
			dataTransformed=transformation.transformation(data, String.valueOf(classValue));
			dataSets.add(dataTransformed);
		}
		return dataSets;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
