package com.marcelodamasceno.main;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

import com.marcelodamasceno.util.ArffConector;
import com.marcelodamasceno.util.BinaryTransformation;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ArffConector conector=new ArffConector();
		Instances data=conector.openDataSet("C:/Users/Marcelo/Documents/Google Drive/doutorado/projeto/dataset/Base de Toque/TouchAnalytics/dataset_processada_artigo2.arff");
		Instances dataTransformed;
		//CustomBalanced balanced = new CustomBalanced();
		BinaryTransformation transformation= new BinaryTransformation();
		int classValue=1;
		for(;classValue<=data.numClasses();classValue++){
			//Instances dataTransformed=balanced.customBalanced(data, String.valueOf(classValue));
			dataTransformed=transformation.transformation(data, String.valueOf(classValue));
			ArffSaver saver=new ArffSaver();
			saver.setInstances(dataTransformed);
			saver.setFile(new File("binaryComplete"+String.valueOf(classValue)+".arff"));
			saver.writeBatch();
		}

	}

}
