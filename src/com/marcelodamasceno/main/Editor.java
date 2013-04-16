package com.marcelodamasceno.main;

import java.util.ArrayList;
import java.util.Enumeration;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Editor {
	Instances instances;
	Editor(Instances instances){
		this.instances=instances;
	}

	/**
	 * Removes Instances with a certaind value in a specific attribute
	 * @param attributeIndex Attribute's index of the DataSet
	 * @param attributeValue Value of Attribute
	 */
	public void removeInstances(int attributeIndex,Object attributeValue){
		for(int i=0; i<instances.numInstances();i++){		
			Instance instance =instances.instance(i);
			Attribute att=instance.attribute(attributeIndex);
			if(instance.attribute(attributeIndex).isNominal()){
				if(instance.stringValue(att).equals(attributeValue)){
					instances.delete(i);
				}
			}if (instance.attribute(attributeIndex).isNumeric()) {
				if(instance.value(att)==(Double)attributeValue){
					instances.delete(i);
				}
			} else {
				//Add a log4j entry (The attribute isnt a string or numeric)
				System.out.println(attributeValue.getClass());
			}
		}
	}

	/**
	 * Write in the screen the name of all Instances
	 * @return A ArrayList of attribute's name
	 */
	public ArrayList<String> listAtributes(){
		ArrayList<String> attributeName=new ArrayList<String>();
		for(int i=0;i<instances.numAttributes();i++){
			attributeName.add(instances.attribute(i).name());
			System.out.println("Attribute "+i+" "+instances.attribute(i).name());
		}
		return attributeName;
	}
	
	
	/**
	 * Replace a value of a Attribute
	 * @param attributeIndex Index of attribute
	 * @param oldValue Attribute value that will be replace
	 * @param newValue New attribute value that will be replace 
	 */
	public void replace(int attributeIndex,Object oldValue, Object newValue){
		for(int i=0; i<instances.numInstances();i++){		
			Instance instance =instances.instance(i);
			Attribute att=instance.attribute(attributeIndex);
			if(instance.attribute(attributeIndex).isNominal()){
				if(instance.stringValue(att).equals(oldValue)){
					instance.setValue(att, (String)newValue);
				}
			}if (instance.attribute(attributeIndex).isNumeric()) {
				if(instance.value(att)==(Double)oldValue){
					instance.setValue(att, (Double)newValue);
				}
			} else {
				//Add a log4j entry (The attribute isnt a string or numeric)
				System.out.println(oldValue.getClass());
			}
		}
	}
}
