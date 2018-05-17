package RMI_Avaliacao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClienteObj implements Serializable
{
	private String nome;
	private ArrayList<Evento> eventosDoCliente = new ArrayList<Evento>();
	
	//Construtor Cliente
	public ClienteObj(String nome) {
		this.nome=nome;
	}
	//Método para aceder ao nome do cliente
	public String getNome()
	{
		return nome;
	}
	//Método para aceder à lista de eventos do cliente
	public Evento getEventosDoCliente(String sala, LocalDateTime dataInicio) 
	{
		boolean eventoEncontrado = false;
		Evento evento = null;
		if (eventosDoCliente.isEmpty())
		{
			return null;
		}
		for (int i = 0;i<eventosDoCliente.size();i++) 
		{
			if (sala.equals(eventosDoCliente.get(i).getSala()) && dataInicio.equals(eventosDoCliente.get(i).getHoraInicio())) 
			{
				eventoEncontrado = true;
				evento = eventosDoCliente.get(i);
			}
		}
		if (eventoEncontrado)
		{
			return evento;
		}
		else 
		{
			return null;
		}
	}

	//Método para mostrar a lista de eventos do cliente
	public String getEventosDoClienteToString()
	{
		String str = "";
		if (eventosDoCliente.isEmpty()) {
			str = "Não existem eventos agendados por si. \n";
		}
		else {
			for (int i = 0;i<eventosDoCliente.size();i++) {
				str= str + "Sala: " + eventosDoCliente.get(i).getSala() + ". Inicio: " + eventosDoCliente.get(i).getHoraInicio().toString() + " Fim: " + eventosDoCliente.get(i).getHoraFim().toString()+ ".\n";
			}
		}
		return str;
	}
	//Método para adicionar eventos à lista de eventos do cliente
	public void addEventosDoCliente(Evento evento)
	{
		this.eventosDoCliente.add(evento);
	}
	//
	public ArrayList<Evento> obterListaDeEventos()
	{
		return eventosDoCliente;
	}
	//Método para remover eventos da lista de eventos do cliente
	public void removeEventosDoCliente(String sala, LocalDateTime dataInicio)
	{
		for (int i = 0; i < eventosDoCliente.size(); i++)
		{
			if (sala.equals(eventosDoCliente.get(i).getSala()) && dataInicio.equals(eventosDoCliente.get(i).getHoraInicio())) 
			{
				eventosDoCliente.remove(i);
			}
		}
	}
}