package com.xformation.recruitment;

public class MainFileMerging {
	
	public static void main(String[] args) {
		
		FileMerging fileMerging = new FileMergingWithStreams();
		fileMerging.merge(args[0], args[1], args[2]);
	}

}
