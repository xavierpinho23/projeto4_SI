package projeto4_SI;

import java.rmi.Remote;
public interface Interface extends Remote 
{
	public void adicionaEvento(Evento evento) throws Exception;
	public String atualizaEvento() throws Exception;
	public String removeEvento() throws Exception;
	public String percOcupacao() throws Exception;
	public String consNumReservas() throws Exception;
	public String consNumResPUtiPTem() throws Exception;
}
