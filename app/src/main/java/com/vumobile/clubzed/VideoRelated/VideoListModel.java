package com.vumobile.clubzed.VideoRelated;

public class VideoListModel {
	private String content_code, CategoryCode,content_name,sContentType,sPhysicalFileName,ZedID,content_img;

	public VideoListModel(String content_code, String categoryCode,
						  String content_name, String sContentType, String sPhysicalFileName,
						  String zedID, String content_img) {
		
		this.content_code = content_code;
		this.CategoryCode = categoryCode;
		this.content_name = content_name;
		this.sContentType = sContentType;
		this.sPhysicalFileName = sPhysicalFileName;
		this.ZedID = zedID;
		this.content_img = content_img;
	}

	public VideoListModel() {
		// TODO Auto-generated constructor stub
	}

	public String getContent_code() {
		return content_code;
	}

	public void setContent_code(String content_code) {
		this.content_code = content_code;
	}

	public String getCategoryCode() {
		return CategoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		CategoryCode = categoryCode;
	}

	public String getContent_name() {
		return content_name;
	}

	public void setContent_name(String content_name) {
		this.content_name = content_name;
	}

	public String getsContentType() {
		return sContentType;
	}

	public void setsContentType(String sContentType) {
		this.sContentType = sContentType;
	}

	public String getsPhysicalFileName() {
		return sPhysicalFileName;
	}

	public void setsPhysicalFileName(String sPhysicalFileName) {
		this.sPhysicalFileName = sPhysicalFileName;
	}

	public String getZedID() {
		return ZedID;
	}

	public void setZedID(String zedID) {
		ZedID = zedID;
	}

	public String getContent_img() {
		return content_img;
	}

	public void setContent_img(String content_img) {
		this.content_img = content_img;
	}
	
	
}
