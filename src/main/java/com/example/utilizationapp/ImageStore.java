package com.example.utilizationapp;

import java.io.Serializable;

public class ImageStore implements Serializable{

	private static final long SerialVersionUID = 102348824383834L;
	String filename;
	String album;
	byte[] content;
	

	
	public ImageStore(String filename, String album, byte[] content) {
		this.filename = filename;
		this.album = album;
		this.content = content;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	
}
