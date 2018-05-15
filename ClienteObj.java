package projeto4_SI;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClienteObj
{
	private String nome;
	private ArrayList<Evento> eventosDoCliente = new ArrayList<Evento>();
	
	public ClienteObj(String nome) {
		this.nome=nome;
	}

	public String getNome()
	{
		return nome;
	}
	public Evento getEventosDoCliente(String sala, LocalDateTime dataInicio) 
	{
		boolean eventoEncontrado = false;
		Evento evento = null;
		for (int i = 0;i<eventosDoCliente.size();i++) {
			if (sala.equals(eventosDoCliente.get(i).getSala()) && dataInicio.equals(eventosDoCliente.get(i).getHoraInicio())) {
				eventoEncontrado = true;
				evento = eventosDoCliente.get(i);
			}
		}
		if (eventoEncontrado)
		{
			return evento;
		}
		else {
			return null;
		}
	}

	public String getEventosDoClienteToString()
	{
		String str = "";
		if (eventosDoCliente.isEmpty()) {
			str = "Não existem eventos agendados.";
		}
		else {
			for (int i = 0;i<eventosDoCliente.size();i++) {
				str= str + "Sala: " + eventosDoCliente.get(i).getSala() + ". Das: " + eventosDoCliente.get(i).getHoraInicio().toString() + " às: " + eventosDoCliente.get(i).getHoraFim().toString()+ ".\n";
			}
		}
		return str;
	}

	public void addEventosDoCliente(Evento evento)
	{
		this.eventosDoCliente.add(evento);
	}
}
