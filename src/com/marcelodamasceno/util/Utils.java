package com.marcelodamasceno.util;

import weka.core.Instances;

public class Utils {
	
	public Instances mergeDataSets(Instances data1, Instances data2){
		for(int instance=0;instance<data2.numInstances();instance++){
			data1.add(data2.instance(instance));
		}
		return data1;
	}

}
