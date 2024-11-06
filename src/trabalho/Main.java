package trabalho;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.RandomStringUtils;

import dao.ClienteDAO;
import model.Cliente;

public class Main {
	
	public static void main(String[] args) throws SQLException {
		
		ClienteDAO clienteDAO = new ClienteDAO();
		List<Cliente> clientes = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);
		
		int op = 0;

        while (op != 10) {
            System.out.println("Menu:");
            
            System.out.println("1. Realizar uma consulta na tabela Cliente");
            
            System.out.println("2. Realizar um update na tabela Cliente");
          
            System.out.println("3. Realizar um delete na tabela Cliente");
            
            System.out.println("4. Realizar 100 inserts na tabela Cliente");
      
            System.out.println("5. Sair");
            
            System.out.print("Escolha uma opção: ");
            
            op = sc.nextInt();

            try {
                switch (op) {
	                case 1:
	            		clientes = clienteDAO.selecione();
	            		for (Cliente cliente : clientes) {
	            			System.out.println("ID: " + cliente.getId_cliente() + ", Nome: " + cliente.getNome() 
	            			+ ", Email: " + cliente.getEmail() + ", Telefone: " + cliente.getTelefone() + ", Endereço: " + cliente.getEndereco());
	            		}
	                    break;
	                    
	                case 2:
	                	// Exemplo de alterar cliente de id = 2
	            		Cliente clienteA = new Cliente();
	            		clienteA.setId_cliente(2); 
	            		clienteA.setNome("Ícaro");
	            		clienteA.setEmail("icaro@gmail.com");
	            		clienteA.setTelefone("1111-2222");
	            		clienteA.setEndereco("Rua. Exemplo, 123");
	            		clienteDAO.atualizar(clienteA);
	                    break;
	                    
	                case 3:
	                	// Exemplo de excluir habilidade de id = 2
	            		int id = 2; 
	            		clienteDAO.deletar(id);
	                    break;
	                    
	                case 4:
	            		Random random = new Random();
	            		for (int i = 0; i < 100; i++) {
	            		    Cliente clienteI = new Cliente();
	            		    // Gera registros aleatórios
	            		    clienteI.setNome(RandomStringUtils.randomAlphabetic(5)); 
	            		    clienteI.setEmail(RandomStringUtils.randomAlphabetic(5));
		            		clienteI.setTelefone(RandomStringUtils.randomAlphabetic(5));
		            		clienteI.setEndereco(RandomStringUtils.randomAlphabetic(5));
		            		clientes.add(clienteI);
	            		}
	            		
	            		// Com Transação
	            		clienteDAO.criarTransacao(clientes);
	            		
	            		// Sem Transação
	            		// clienteDAO.criar(clientes);
	                    break;           
                        
                    case 5:
                        System.out.println("Saindo...");
                        break;
                        
                    default:
                        System.out.println("Opção inválida. Tente novamente");
                        
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        }    

	}

}
