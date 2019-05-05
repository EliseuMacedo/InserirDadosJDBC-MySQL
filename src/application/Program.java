package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("mm/DD/yyyy");
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DB.getConnection();
			ps = conn.prepareStatement("INSERT INTO seller " + "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES" + "(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS); //sobrecarga que gera outro parâmetro para retornar o id

			ps.setString(1, "Carl Purple");
			ps.setString(2, "carl@gmail.com");
			ps.setDate(3, new java.sql.Date(sdf.parse("21/09/1983").getTime()));
			ps.setDouble(4, 3000.0);
			ps.setInt(5, 4);

			int linhasAlteradas = ps.executeUpdate();
			 
			if(linhasAlteradas > 0 ) {
				ResultSet rs = ps.getGeneratedKeys(); //essa função retorno um result set da Key do banco
				
				while(rs.next()) {
					int id = rs.getInt(1); // 1 para indicar primeira conluna no banco, = coluna ID
					System.out.println("Pronto, ID = " + id);
				}
				
			}
			else {
				System.out.println("Nenhuma linha alterada");
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		} 
		finally {
			DB.closeStatement(ps);
			DB.closeConnection();
		}
	}
}
