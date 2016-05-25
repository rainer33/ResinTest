package com.resin.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ServiceController {
	private InputStream in = null;
	public ServiceController(InputStream inputStream){
		this.in = inputStream;
	}
	public Response getResponse(){
		
		Response response = null;
		Connection dbConnection = null;
		CallableStatement stmt = null;
		
		try {
			dbConnection = this.getDBConnection();
			stmt = this.getCallableStatement(dbConnection);
			JSONArray jsonArray = this.getJSONArray();
			response = this.doService(jsonArray, stmt);	
		} catch (ClassNotFoundException | IOException | SQLException| ParseException   e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			DBConn dbConn = new DBConn();
			dbConn.closeConnection(stmt, dbConnection);
		}
		
		return response;
	}
	
	private Connection getDBConnection()throws IOException,SQLException,ClassNotFoundException{
		DBConn dbConn = new DBConn();
		Connection conn = dbConn.getFbpDBConnection();
		return conn;
	}
	
	private CallableStatement getCallableStatement(Connection conn)throws ParseException,IOException,SQLException,ClassNotFoundException{
		//sql = "{call SYT_CUSTOMER_INS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";		
		CallableStatement stmt = null;	
		String sql = "{call SYT_CUSTOMER_INS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		stmt = conn.prepareCall(sql);	
		return stmt;
	}// function callSP
	
	private JSONArray getJSONArray()throws ParseException,UnsupportedEncodingException,IOException{
		JSONArray jsonArray = null;
		
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(this.in, "UTF-8"));
		
		String inputStr = null;
		StringBuilder sb = new StringBuilder();
		
		while ((inputStr = streamReader.readLine()) != null) {
			sb.append(inputStr);
		}	
		
		int i = sb.toString().indexOf("{");
		String result = sb.toString().substring(i);
		result = result.replace("\\", "");
		
		JSONParser jsonParser = new JSONParser();   
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result); 
		
        jsonArray = (JSONArray)jsonObject.get("customer");  
		
		return jsonArray;
	}
	
	private Response doService(JSONArray array,CallableStatement stmt)throws IOException,SQLException,ClassNotFoundException{
		JSONObject ojn = null;			
			
		for(int j = 0; j < array.size(); j++){
			ojn = (JSONObject)array.get(j);
			
			String in_seq 			= ojn.get("SEQ").toString();
			String in_idcustomer 	= ojn.get("IDCUSTOMER").toString();
			String in_idorg 		= ojn.get("IDORG").toString();
			String in_idtype 		= ojn.get("IDTYPE").toString();
			String in_name 			= ojn.get("NAME").toString();
			String in_identifier 	= ojn.get("IDENTIFIER").toString();
			String in_mail 			= ojn.get("MAIL").toString();
			String in_mobile 		= ojn.get("MOBILE").toString();
			String in_phone 		= ojn.get("PHONE").toString();
			String in_point 		= ojn.get("POINT").toString();
			String in_idsecurity 	= ojn.get("IDSECURITY").toString();
			String in_states 		= ojn.get("STATES").toString();
			String in_redjointime 	= ojn.get("REDJOINTIME").toString();
			String in_redexpiretime = ojn.get("REDEXPIRETIME").toString();
			String in_zipcode 		= ojn.get("ZIPCODE").toString();
			String in_address 		= ojn.get("ADDRESS").toString();
			String in_isagree 		= ojn.get("ISAGREE").toString();
			String in_isfreefee 	= ojn.get("ISFREEFEE").toString();
			String in_grade 		= ojn.get("GRADE").toString();
			String in_gender 		= ojn.get("GENDER").toString();
			
			stmt.setInt(1, Integer.parseInt(in_seq));
			stmt.setString(2, in_idcustomer);
			stmt.setInt(3, Integer.parseInt(in_idorg));
			stmt.setInt(4, Integer.parseInt(in_idtype));
			stmt.setString(5, in_name);
			stmt.setString(6, in_identifier);
			stmt.setString(7, in_mail);
			stmt.setString(8, in_mobile);
			stmt.setString(9, in_phone);
			stmt.setInt(10, Integer.parseInt(in_point));
			stmt.setString(11, in_idsecurity);
			stmt.setString(12, in_states);
			stmt.setString(13, in_redjointime);
			stmt.setString(14, in_redexpiretime);
			stmt.setString(15, in_zipcode);
			stmt.setString(16, in_address);
			stmt.setString(17, in_isagree);
			stmt.setString(18, in_isfreefee);
			stmt.setString(19, in_grade);
			stmt.setString(20, in_gender);
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
			//stmt.executeUpdate();		
		System.out.println("Error Parsing: - ");
		
		String transOk = stmt.executeBatch().toString();		
		
		// return HTTP response 200 in case of success
		// return Response.status(200).entity(sb.toString()).build();
		return Response.status(200).entity(transOk).build();
	}
}// class
