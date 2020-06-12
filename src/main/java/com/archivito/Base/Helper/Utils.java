package com.archivito.Base.Helper;


import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

	public static final List<String> listTypes = Collections.unmodifiableList(
			new ArrayList<String>() {{
				add("application/msword");
				add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
				add("application/pdf");
				add("image/jpeg");
				add("image/pjpeg");
				add("image/png");
				add("application/vnd.ms-excel");
				add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			}});

	/*public static String resToString(String error, List<Message> objets) {
		return new Gson().toJson(new Objeto(error, objets));
	}*/



}
