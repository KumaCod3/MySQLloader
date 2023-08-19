package SRC;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	private static Connection connection;
	private static Statement statement;
	private static String PASSWORD="Du1k3rKnows!";
	private static String DataBaseNAME="pro"/*+"negozioDB"*/;
	
	public static void main(String[] args) {
		PASSWORD=chiediPW();		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DataBaseNAME+"?characterEncoding=latin1&useConfigs=maxPerformance","root",PASSWORD);
			statement = connection.createStatement();
		}catch (ClassNotFoundException e) {System.out.println("MySQL Library missing");}
		catch (SQLException e) {
			System.out.println("MySQL DATABASE missing");
			creaDB();
		}
		tryQuery(0);
	}
	
	private static String chiediPW() {
		System.out.println("Insert DataBase Password:");
		Scanner sc=new Scanner(System.in);
		String pw=sc.next();
		sc.close();
		return pw;
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
		}catch (SQLException e) { 
			System.out.println("manca software MySQL");
			scaricaMySQL();
			return;
			}
		importaDB();
	}
	
	private static void scaricaMySQL() {
		System.out.println("MySQL not installed, install it please.");
		try {
	        Desktop.getDesktop().browse(new URL("https://dev.mysql.com/downloads/mysql/").toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
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
