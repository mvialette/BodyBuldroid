package com.vialette.maxime.android.bodybuldroid;

import java.util.ArrayList;
import java.util.List;

public class Constantes {
	public static List<String> getSeries(){
		List<String> result = new ArrayList<String>();
		
		result.add("J1 - Les pecs");
		result.add("J2 - Le dos");
		
		return result;
	}
	
	public static List<String> getExerciesBySerie(String serie){
		List<String> result = new ArrayList<String>();
		
		if("J1 - Les pecs".equals(serie)){
			result.add("1-pousee vertical");
			result.add("2-papillon");
			result.add("3-levee horizontal");
			result.add("4-biceps");
			result.add("5-bas haut");
			result.add("6-abdo");
		}else if("J2 - Le dos".equals(serie)){
			result.add("1-trapez");
			result.add("2-tirage dos");
			result.add("3-banc leve");
			result.add("4-dips");
			result.add("5-haut bas");
			result.add("6-abdo");
		}
		
		return result;
	}
}
