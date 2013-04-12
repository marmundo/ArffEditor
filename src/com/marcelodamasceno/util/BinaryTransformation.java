package com.marcelodamasceno.util;

import java.util.Enumeration;

import weka.core.Instance;
import weka.core.Instances;

public class BinaryTransformation {
	
	/**
	 * * This method transform the balanced dataset balanced using the customBalanced method in a binary dataset. 
	 * Where class passed by argument will be the positive. 
	 * @param data DataSet
	 * @param classe Positive Class
	 * @return Binary DataSet
	 */
	public Instances transformation(Instances data, String classe){
		ArffConector conector=new ArffConector();
		Instances dataTransformed = conector.changeAttributeNames(data, classe);
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

}
