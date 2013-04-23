package com.marcelodamasceno.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ArffLoader.ArffReader;

public class ArffConector {

	public Instances openDataSet(String fileName) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArffReader arff = null;
		try {
			arff = new ArffReader(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Instances data = arff.getData();
		data.setClassIndex(data.numAttributes() - 1);
		return data;
	}

	public int numberOfInstances(Instances data) {
		if (data != null) {
			return data.numInstances();
		} else {
			return 0;
		}
	}

	private void typeOfAttribute(Instances data, int attributeIndex){
		switch (data.attribute(attributeIndex).type()) {
		case weka.core.Attribute.DATE:
			System.out.println("Date");
			break;
		case weka.core.Attribute.NOMINAL:
			System.out.println("Nominal");
			break;
		case weka.core.Attribute.NUMERIC: 
			System.out.println("Date");
			break;
		default:
			break;
		} 
	}

	public Instances getInstancesWithClasse(Instances data,String classe){	
		return getInstancesWithClasse(data,classe, data.numInstances());	
	}

	public Instances getInstancesWithClasse(Instances data,String classe, int numInstances){	
		Instances instClass = new Instances(data, data.numInstances());
		@SuppressWarnings("unchecked")
		Enumeration<Instance> enumInstances = data.enumerateInstances();
		int counter=0;
		while( enumInstances.hasMoreElements() && counter<numInstances ) {
			Instance inst=enumInstances.nextElement();	
			if(inst.classAttribute().isNominal()){				
				if(inst.stringValue(data.numAttributes()-1).equals(classe)){
					instClass.add(inst);
					counter++;
				}
			}
		}
		return instClass;
	}

	public Instances mergeInstances(Instances data,Instances data2){
		@SuppressWarnings("unchecked")
		Enumeration<Instance> enu=data2.enumerateInstances();
		while(enu.hasMoreElements())
			data.add(enu.nextElement());
		return data;
	}

	public Instances getInstancesWithClasse(Instances data,double classe) {
		return getInstancesWithClasse(data,classe, data.numInstances());		
	}

	public Instances getInstancesWithClasse(Instances data,double classe, int numInstances) {
		Instances instClass = new Instances(data, data.numInstances());
		@SuppressWarnings("unchecked")
		Enumeration<Instance> enumInstances = data.enumerateInstances();
		int counter=0;
		while(enumInstances.hasMoreElements() && counter<=numInstances) {
			Instance inst = enumInstances.nextElement();
			if (inst.classAttribute().isNumeric()) {
				System.out.println(inst.value(data.numAttributes() - 1));
				if (inst.value(data.numAttributes() - 1)==classe) {
					instClass.add(inst);
				}
			}
		}
		return instClass;
	}

	public Instance copyIntanceWithoutClass(Instance original, Instance copy, String classValue){
		int arg=0;
		for(;arg<original.numAttributes()-1;arg++){
			if(original.attribute(arg).isNominal())
				copy.setValue(arg, original.stringValue(arg));
			else
				copy.setValue(arg, original.value(arg));
		}		
		copy.setValue(original.numAttributes()-1, classValue);
		return copy;
	}

	public Instances changeAttributeNames(Instances data, int attributeIndex, String newName){

		Instances dataTransformed=new Instances(data);

		if(dataTransformed.classIndex()==attributeIndex){
			//This line is because the Instances class don't delete a class attribute
			dataTransformed.setClassIndex(0);
			dataTransformed.deleteAttributeAt(data.numAttributes()-1);
		}else{
			dataTransformed.deleteAttributeAt(attributeIndex);
		}		
		//Creating a new Attribute
		FastVector fvNominalVal = new FastVector(2);
		fvNominalVal.addElement("positive");
		fvNominalVal.addElement("negative");		 
		Attribute classAttribute = new Attribute("class", fvNominalVal);
		
		if(dataTransformed.classIndex()==attributeIndex){
		dataTransformed.insertAttributeAt(classAttribute, data.numAttributes()-1);
		dataTransformed.setClassIndex(data.numAttributes()-1);
		}else{
			dataTransformed.insertAttributeAt(classAttribute, attributeIndex);
		}

		return dataTransformed;
	}

	/**
	 * Save as Arff format
	 * @param data Instances
	 * @param path Path
	 * @param fileName File Name
	 */
	public void save(Instances data, String path, String fileName){
		ArffSaver saver=new ArffSaver();
		saver.setInstances(data);
		try {
			saver.setFile(new File(path,fileName));
			saver.writeBatch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Save as Arff format
	 * @param data
	 * @param fileName
	 */
	public void save(Instances data,String fileName){
		save(data, ".", fileName);		
	}

	public static void main(String args[]){
		ArffConector conector=new ArffConector();
		Instances data=conector.openDataSet("C:/Users/Marcelo/Documents/Google Drive/doutorado/projeto/dataset/Base de Toque/TouchAnalytics/dataset_processada_artigo2.arff");
		System.out.println(conector.getInstancesWithClasse(data,"36").toString());
	}
}
