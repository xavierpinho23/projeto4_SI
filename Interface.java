package RMI_Avaliacao;

import java.rmi.Remote;
import java.time.LocalDateTime;

public interface Interface extends Remote 
{
	//Métodos a que o Cliente tem acesso
	public String adicionaEvento(String sala, String dateTime,String[] duracao,String responsavel,String descricao) throws Exception;
	public String removeEvento( String sala, String dataInicio) throws Exception;
	public String percOcupacao(String data) throws Exception;
	public String consNumReservas() throws Exception;
	public String consNumResPUtiPTem(String responsavel, String dataInicio, String dataFim) throws Exception;
	public void criarCliente(String nome) throws Exception;
	public String atualizarEvento(String salaAntiga,String dataInicioAntiga,String salaNova,String dataInicioNova,String[] duracao,String descricao) throws Exception;
}
