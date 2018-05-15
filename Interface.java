package RMI_Avaliacao;

import java.rmi.Remote;
import java.time.LocalDateTime;

public interface Interface extends Remote 
{
	//Métodos a que o Cliente tem acesso

	public void adicionaEvento(String sala, LocalDateTime dateTime,LocalDateTime finalDateTime,String responsavel,String descricao, ClienteObj cliente) throws Exception;
	public String obterEventosCliente(ClienteObj cliente) throws Exception;
	public boolean encontrarEvento(ClienteObj cliente, String sala, LocalDateTime dataInicio) throws Exception;
	public void removeEvento(ClienteObj cliente, String sala, LocalDateTime dataInicio) throws Exception;
	public String percOcupacao() throws Exception;
	public String consNumReservas() throws Exception;
	public String consNumResPUtiPTem() throws Exception;
}
