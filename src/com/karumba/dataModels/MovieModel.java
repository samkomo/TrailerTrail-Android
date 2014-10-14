package com.karumba.dataModels;

import android.graphics.Bitmap;

public class MovieModel {
	String title, imbID, type, year, poster;

	public MovieModel (String title,String imbID,String type, String year, String poster){
		this.title = title;
		this.imbID = imbID;
		this.type = type;
		this.year = year;
		this.poster = poster;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getYea(){
		return this.year;
	}
	
	public String getImbID(){
		return this.imbID;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getPoster(){
		return this.poster;
	}
}

