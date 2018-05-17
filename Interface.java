package RMI_Avaliacao;

import java.rmi.Remote;
import java.time.LocalDateTime;

public interface Interface extends Remote 
{
	//Métodos a que o Cliente tem acesso

	public String adicionaEvento(String sala, LocalDateTime dateTime,LocalDateTime finalDateTime,String responsavel,String descricao) throws Exception;
	public String obterEventosCliente() throws Exception;
	public boolean encontrarEvento( String sala, LocalDateTime dataInicio) throws Exception;
	public void removeEvento( String sala, LocalDateTime dataInicio) throws Exception;
	public String percOcupacao(String data) throws Exception;
	public String consNumReservas() throws Exception;
	public String consNumResPUtiPTem(String responsavel, LocalDateTime dataInicio, LocalDateTime dataFim) throws Exception;
	public void criarCliente(String nome) throws Exception;
	public String obterSalas() throws Exception;
	public void guardarReservas() throws Exception;
}
