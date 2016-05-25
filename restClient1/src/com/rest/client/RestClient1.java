package com.rest.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

public class RestClient1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		makeJson();
	}
	
	private static void makeJson(){
		
		String string = "";
		try {
 
			// Step1: Let's 1st read file from fileSystem
			// Change restTest.txt path here
			InputStream input = new FileInputStream("D:\\DEV\\restApi\\jsonFormatSample.txt");
			//InputStream input = new FileInputStream("D:\\DEV\\restApi\\restTest.txt");
			
			InputStreamReader reader = new InputStreamReader(input,"UTF-8");
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}
			System.out.println(string);
			
			int i = string.indexOf("{");
			string = string.substring(i);
			
			
 
			// Step2: Now pass JSON File Data to REST Service
			try {				
				
				URL url = new URL("http://localhost:8093/resinTest2/CustomerInsert");
				//URL url = new URL("http://192.168.56.1/rest/CustomerInsert");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");				
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(string);
				out.close();
 
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
				while (in.readLine() != null) {
				}
				System.out.println("\nCrunchify REST Service Invoked Successfully..");
				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				System.out.println(e);
			}
 
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

}
