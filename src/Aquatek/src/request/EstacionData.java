package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import connection.DBConnectionHandler;
import tools.Convertor;

/**
 * Servlet implementation class DataServlet
 */
@SuppressWarnings("serial")
@WebServlet("/Analyzer/Data")
public class EstacionData extends HttpServlet {
      
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject json = new JSONObject();
		
		//Se recoge los parametros enviados por el usuario
		
		String SQL_VAL_TEMP = "select id,valor,tiempo from analyzer order by id desc limit 400";
		

		String[] QUERYS = {SQL_VAL_TEMP};
		
		String TB_NAME = "analyzer";
		Connection con = DBConnectionHandler.getConnecion();
			
				
		try{
			PreparedStatement ps;
			ResultSet rs;
			for(int j=0;j<QUERYS.length;j++){
				ps = con.prepareStatement(QUERYS[j]);
				rs = ps.executeQuery();
				
				if(rs!= null){
					json.put(TB_NAME, Convertor.convertToJSON(rs));
				}
				ps.close(); //Cierra statement
				rs.close(); //cierra resultset
			}
			
			con.close(); //Cierra connection
			
			json.put("status", "succes");
				
		}catch(Exception e){
			e.printStackTrace();
			//response.getWriter().write(e.getMessage());
			JSONObject json_e = new JSONObject();
			json_e.put("status", "fail");
			json = json_e;
		}
				
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Se recive un JSONArray con medidas de sensores
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		JSONObject res = new JSONObject();
		try{
			BufferedReader reader = request.getReader();
			while((line = reader.readLine())!= null)
				jb.append(line);
			
			JSONObject json = new JSONObject(jb.toString());
			JSONArray jarray = json.getJSONArray("valores_med");
			
			String SQL_INSERT = "insert into analyzer(valor,tiempo) values (?,?)";
			
			Connection con = DBConnectionHandler.getConnecion();
			PreparedStatement ps;
			
			for(int i=0;i<jarray.length();i++){
				ps = con.prepareStatement(SQL_INSERT);
				ps.setString(1, jarray.getJSONObject(i).getString("valor"));
				ps.setString(2, jarray.getJSONObject(i).getString("tiempo"));
				ps.execute();
				ps.close();
			}
			
			con.close();
			
			res.put("status", "succes");
			
		}catch(Exception e){
			res.put("status", "fail");
			res.put("error", e.getMessage());
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(res.toString());
	}

}
