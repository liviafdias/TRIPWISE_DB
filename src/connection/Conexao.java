package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
	
	private static final String url = "jdbc:mysql://localhost:3306/tripwise";
	private static final String usuario = "root";
	private static final String senha = "12345678";
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	
	private static Connection conexao;
	
	public Connection abrirConexao() {
		
		try {
			// Carrega a classe do driver JDBC do MySQL
			Class.forName(driver);
			// Estabelece a conexão com o banco de dados
			conexao = DriverManager.getConnection(url, usuario, senha);
			return conexao;
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Erro na conexão: ", e);
		}
				
	}
	
	public static void fecharConexao(Connection conexao) {
		
		try {
			if (conexao != null) {
				conexao.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro na desconexão: ", e);
		}

	}
	
	public static void fecharConexao(Connection conexao, PreparedStatement stmt) {
		
		fecharConexao(conexao);
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro na desconexão: ", e);
		}

	}
	
	public static void fecharConexao(Connection conexao, PreparedStatement stmt, ResultSet rs) {
		
		fecharConexao(conexao, stmt);
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro na desconexão: ", e);
		}

	}
		
		
}
	
		

	
	

	
	


