package projeto4_SI;

import java.rmi.Remote;
import java.time.LocalDateTime;
public interface Interface extends Remote 
{
	public void adicionaEvento(String sala, LocalDateTime dateTime,LocalDateTime finalDateTime,String responsavel,String descricao, ClienteObj cliente) throws Exception;
	public String atualizaEvento() throws Exception;
	public String obterEventosCliente(ClienteObj cliente) throws Exception;
	public boolean encontrarEvento(ClienteObj cliente, String sala, LocalDateTime dataInicio);
	public String removeEvento() throws Exception;
	public String percOcupacao() throws Exception;
	public String consNumReservas() throws Exception;
	public String consNumResPUtiPTem() throws Exception;
}
