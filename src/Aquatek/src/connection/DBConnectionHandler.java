/**
 * Clase de conexion a la base de datos Mysql local
 * devuelve conexion
 */

package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionHandler {
	
	private static final String db = "aquatek";
	private static final String uname = "root";
	private static final String pass = "q1w2e3r4t5y6u7i8o9p0";
	
	public static Connection getConnecion(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");//Conexion a Mysql
		}catch(ClassNotFoundException ex){
			Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
		try{
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db,uname,pass);
		}catch(SQLException ex){
			Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
		return con;
	}
	
}