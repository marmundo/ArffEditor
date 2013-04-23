package com.marcelodamasceno.main;

import java.io.IOException;

import weka.core.Instances;

import com.marcelodamasceno.util.ArffConector;
import com.marcelodamasceno.util.CustomBalanced;

public class Balancer {

	static ArffConector conector=new ArffConector();
	
	public void customBalancedSaver(Instances data, int classValue) throws IOException{
		CustomBalanced balanced = new CustomBalanced();
		if(classValue==0){					
			classValue=1;
			for(;classValue<=data.numClasses();classValue++){
				Instances dataTransformed=balanced.customBalanced(data, String.valueOf(classValue));
				conector.save(dataTransformed, "Balanced"+String.valueOf(classValue)+".arff");
			}
		}else{
			Instances dataTransformed=balanced.customBalanced(data,"positive");
			try{
				//TODO: Não está salvando na pasta
				conector.save(dataTransformed, "/home/marcelo","IntraSet Balanced-"+classValue+".arff");
				//conector.save(dataTransformed, PROJECT_PATH+"/IntraSession2","IntraSet Balanced-"+classValue+".arff");
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public Instances customBalanced(Instances data, int classValue) throws IOException{
		CustomBalanced balanced = new CustomBalanced();	
		if(classValue==0){					
			classValue=1;
			for(;classValue<=data.numClasses();classValue++){
				Instances dataTransformed=balanced.customBalanced(data, String.valueOf(classValue));
				conector.save(dataTransformed, "Balanced"+String.valueOf(classValue)+".arff");
			}
		}else{
			Instances dataTransformed=balanced.customBalanced(data,"positive");
			return dataTransformed;
		}
		return null;		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
