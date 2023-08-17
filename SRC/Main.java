package SRC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	private static Connection connection;
	private static Statement statement;
	private static String PASSWORD="Du1k3rKnows!";
	private static String DataBaseNAME="negozioDB";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DataBaseNAME+"?characterEncoding=latin1&useConfigs=maxPerformance","root",PASSWORD);
			statement = connection.createStatement();
		}catch (ClassNotFoundException e) {System.out.println("MySQL Library missing");}
		catch (SQLException e) {System.out.println("MySQL DATABASE missing");}
		System.out.println("CONNECTION ");
		leggiCliID(0);
	}
	
	
	static public String leggiCliID(int x) {
		try {
			String sql="SELECT * FROM Clienti WHERE ID_CLIENTE="+x;
			ResultSet result = statement.executeQuery(sql);
			String inser="";
				
			while (result.next()) {
				int id = result.getInt("ID_Cliente");
				String nome=result.getString("name");
				String cognome=result.getString("last_name");
				String telefono=result.getString("phone");
				String email=result.getString("Mail");
				String iva=result.getString("vatn");
				String stato=result.getString("state");
				String citta=result.getString("city");
				String indirizzo=result.getString("street");
				Double saldo=result.getDouble("tot_sold");
				String note=result.getString("note");
				System.out.println(id+ nome+ cognome+ telefono+ email+ iva+ indirizzo+ saldo);
				inser=id+","+nome+","+cognome+","+telefono+","+email+","+stato+","+citta+","+indirizzo+","+iva+","+saldo+","+note;
			}
			return inser;
		}catch (SQLException e) {e.printStackTrace();}
		return "Fail";
	}
}
