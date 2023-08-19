package SRC;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	private static Connection connection;
	private static Statement statement;
	private static String PASSWORD="Du1k3rKnows!";
	private static String DataBaseNAME="pro"/*+"negozioDB"*/;
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DataBaseNAME+"?characterEncoding=latin1&useConfigs=maxPerformance","root",PASSWORD);
			statement = connection.createStatement();
		}catch (ClassNotFoundException e) {System.out.println("MySQL Library missing");}
		catch (SQLException e) {
			System.out.println("MySQL DATABASE missing");
			creaDB();
			importaDB();
		}
		tryQuery(0);
	}
	static public String tryQuery(int x) {
		try {
			String sql="SELECT * FROM Clienti WHERE ID_CLIENTE="+x;
			ResultSet result = statement.executeQuery(sql);
			String inser="";
				
			while (result.next()) {
				int id = result.getInt("ID_Cliente");
				String nome=result.getString("name");
				System.out.println(id+ nome);
				inser=id+","+nome;
			}
			return inser;
		}catch (SQLException e) {e.printStackTrace();}
		return "Fail";
	}
	
	static void creaDB() {
		System.out.println("creoDB ");
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/?characterEncoding=latin1&useConfigs=maxPerformance", "root", PASSWORD);
			Statement st = connection.createStatement();
			String sql="CREATE DATABASE "+DataBaseNAME;
			st.executeUpdate(sql);
		}catch (SQLException e) { System.out.println("imposs creare DB");}
	}
	
	private static void importaDB() {  
		try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DataBaseNAME+"?characterEncoding=latin1&useConfigs=maxPerformance","root",PASSWORD);
				statement = connection.createStatement();
				
				ScriptRunner runner = new ScriptRunner(connection , false, false);
				runner.runScript(new BufferedReader(new FileReader("proDB.sql")));
		} catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}   
}
