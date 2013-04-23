package com.marcelodamasceno.main;

import java.util.ArrayList;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class Editor {
	Instances _instances;
	Editor(Instances instances){
		this._instances=instances;
	}

	/**
	 * Removes Instances with a certaind value in a specific attribute
	 * @param attributeIndex Attribute's index of the DataSet
	 * @param attributeValue Value of Attribute
	 */
	public void removeInstances(int attributeIndex,Object attributeValue){
		for(int i=0; i<_instances.numInstances();i++){		
			Instance instance =_instances.instance(i);
			Attribute att=instance.attribute(attributeIndex);
			if(instance.attribute(attributeIndex).isNominal()){
				if(instance.stringValue(att).equals(attributeValue)){
					_instances.delete(i);
					i=i-1;
				}
			}if (instance.attribute(attributeIndex).isNumeric()) {				
				if(instance.value(att)==(Double)attributeValue){		
					_instances.delete(i);
					i=i-1;
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
		for(int i=0;i<_instances.numAttributes();i++){
			attributeName.add(_instances.attribute(i).name());
			System.out.println("Attribute "+i+" "+_instances.attribute(i).name());
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
		for(int i=0; i<_instances.numInstances();i++){		
			Instance instance =_instances.instance(i);
			Attribute att=instance.attribute(attributeIndex);
			if(instance.attribute(attributeIndex).isNominal()){
				if(instance.stringValue(att).equals(oldValue)){
					instance.setValue(att, (String)newValue);
				}
			}else{
				if (instance.attribute(attributeIndex).isNumeric()) {
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

	public Instances getInstancesAttributeValues(Instances data,int attrIndex,ArrayList<Integer> values){
		RemoveWithValues remove=new RemoveWithValues();
		remove.setAttributeIndex(String.valueOf(attrIndex));		
		remove.setModifyHeader(true);
		
		for (int i = 0; i < data.numInstances(); i++) {
			if(!values.contains(data.instance(i).value(attrIndex))){
				
			}
			
		}
		removeInstances(attrIndex, values);
		return data;
	}
	
	public Instances getInstances(){
		return _instances;
	}

	public Instances getInstancesAttributeValue(Instances data,int attributeIndex,Object attributeValue){
		//Dataset which will has the instances with attributeValue in a attribute with a index equal to attributeIndex
		Instances dataSet=new Instances(data, data.numInstances());
		
		for(int i=0; i<data.numInstances();i++){	
			Instance instance =data.instance(i);
			Attribute att=instance.attribute(attributeIndex);			
			if(att.isNominal()){
				String atrValue=String.valueOf(attributeValue);
				if(instance.stringValue(att).equals(atrValue)){
					dataSet.add(instance);					
				}
			}else{
				if (att.isNumeric()) {
					if(instance.value(att)==(Double)attributeValue){
						dataSet.add(instance);
					}
				} else {
					//Add a log4j entry (The attribute isnt a string or numeric)
					System.out.println(attributeValue.getClass());
				}
			}
		}			
		return dataSet;
	}

	public Instances getInstancesAttributeValue(int attributeIndex,Object attributeValue){
		return getInstancesAttributeValue(_instances,attributeIndex,attributeValue);
	}	
}
