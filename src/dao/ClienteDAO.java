package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.Conexao;
import model.Cliente;

public class ClienteDAO {
	
	Connection conexao = null;
	PreparedStatement ps = null; // Prepara e executa comandos SQL
	ResultSet rs = null; // Armazena os resultados de consultas
	
	
	// ---------------------------------------- SELECT ----------------------------------------
	public List<Cliente> selecione() throws SQLException {
		
		List<Cliente> clientes = new ArrayList<>();
		
		conexao = new Conexao().abrirConexao();
	    
		try {
			ps = conexao.prepareStatement("SELECT * FROM Cliente");
			rs = ps.executeQuery();
	        
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId_cliente(rs.getInt("id_cliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setEndereco(rs.getString("endereco"));
				
				clientes.add(cliente);
				
			}
	        
		} catch (SQLException e) {
			throw new RuntimeException("Erro na leitura: ", e);
	        
		} finally {
			Conexao.fecharConexao(conexao, ps);
		}
	    
		return clientes;
	}
	
	
	// ---------------------------------------- UPDATE ----------------------------------------
	public void atualizar(Cliente cliente) throws SQLException {
		
		conexao = new Conexao().abrirConexao();
	    
	    try {
			ps = conexao.prepareStatement("UPDATE Cliente SET nome = ?, email = ?, telefone = ?, endereco = ? WHERE id_cliente = ?");
			
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getTelefone());
			ps.setString(4, cliente.getEndereco());
			ps.setInt(5, cliente.getId_cliente());
			
			int quantResgistros = ps.executeUpdate();
			
			if (quantResgistros == 1) {
				System.out.println("Registro atualizado.");
			} else {
			    System.out.println("Não foi possível atualizar o registro.");
			}	
	        
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro na atualização: ", e);
	        
	    } finally {
	        Conexao.fecharConexao(conexao, ps);
	    }
	    
	}
	
	
	// ---------------------------------------- DELETE ----------------------------------------
	public void deletar(int cliente_id) throws SQLException {
        //disableSafeUpdates(); // Desabilita SQL_SAFE_UPDATES antes de deletar
        
        conexao = new Conexao().abrirConexao();
        
        try {
            ps = conexao.prepareStatement("DELETE FROM Cliente WHERE id_cliente = ?");
            
            ps.setInt(1, cliente_id);
            
			int quantResgistros = ps.executeUpdate();
			
			if (quantResgistros == 1) {
				System.out.println("Registro deletado.");
			} else {
			    System.out.println("Não foi possível deletar o registro.");
			}				
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro na exclusão: ", e);
            
        } finally {
            Conexao.fecharConexao(conexao, ps);
        }
        
    }
	
	
	// ---------------------------------------- INSERT S/ TRANSAÇÃO ----------------------------------------
	public void criar(List<Cliente> clientes) throws SQLException {		
		
		conexao = new Conexao().abrirConexao();
		long comeco = System.nanoTime();
		
		try {
			ps = conexao.prepareStatement("INSERT INTO Cliente (nome, email, telefone, endereco) VALUES (?, ?, ?, ?)");
	
	        for (Cliente cliente : clientes) {
				ps.setString(1, cliente.getNome());
				ps.setString(2, cliente.getEmail());
				ps.setString(3, cliente.getTelefone());
				ps.setString(4, cliente.getEndereco());
				
				ps.executeUpdate();
				
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro na inserção: ", e);
			
		} finally {
			Conexao.fecharConexao(conexao, ps);
		}
	
		long fim = System.nanoTime();
		double tempo = (fim - comeco) / 1000000000.0; // converte para segundos
		System.out.println("Tempo de execução sem transação: " + tempo + " s");
		
	}
	
	
	// ---------------------------------------- INSERT C/ TRANSAÇÃO ----------------------------------------
	public void criarTransacao(List<Cliente> clientes) throws SQLException {
		
		conexao = new Conexao().abrirConexao();
		long comeco = System.nanoTime();
	    
	    try {
			conexao.setAutoCommit(false); // Inicia a transação
			ps = conexao.prepareStatement("INSERT INTO Cliente (nome, email, telefone, endereco) VALUES (?, ?, ?, ?)");
	        
	        for (Cliente cliente : clientes) {
				ps.setString(1, cliente.getNome());
				ps.setString(2, cliente.getEmail());
				ps.setString(3, cliente.getTelefone());
				ps.setString(4, cliente.getEndereco());
				
				ps.executeUpdate();

	        }
	        
			conexao.commit(); // Confirma a transação
	        
	    } catch (SQLException e) {
	        if (conexao != null) {
				conexao.rollback(); // Desfaz a transação em caso de erro
				throw new RuntimeException("Erro na inserção: ", e);
	        }
	
	    } finally {
	       if (conexao != null) {
				conexao.setAutoCommit(true); // Restaura o estado de auto-commit
	       }
	
	       Conexao.fecharConexao(conexao, ps);            
	    }
	    
		long fim = System.nanoTime();
		double tempo = (fim - comeco) / 1000000000.0; // converte para segundos
		System.out.println("Tempo de execução com transação: " + tempo + " s");
	    
	}
	
}
