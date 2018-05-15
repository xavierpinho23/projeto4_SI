package projeto4_SI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;



public class ReservasImpl extends UnicastRemoteObject implements Interface, Serializable
	{
		ArrayList<Evento> listaEventos = new ArrayList<Evento>();
		
		protected ReservasImpl() throws RemoteException
		{
		}

		public void adicionaEvento(String sala, LocalDateTime dateTime,LocalDateTime finalDateTime,String responsavel,String descricao, ClienteObj cliente) throws Exception
		{
			Evento evento = new Evento(sala,dateTime,finalDateTime,responsavel,descricao);
			boolean adicionar = true;
			if (listaEventos.isEmpty()) {
				listaEventos.add(evento);
				System.out.println("Evento adicionado com sucesso!");
				cliente.addEventosDoCliente(evento);
				
			}
			else {
				for (int i=0;i<listaEventos.size();i++) {
					if (listaEventos.get(i).getSala().equals(evento.getSala())) {
						if ((listaEventos.get(i).getHoraInicio().isBefore(evento.getHoraFim()) && listaEventos.get(i).getHoraFim().isAfter(evento.getHoraInicio()))
								|| listaEventos.get(i).getHoraInicio().isEqual(evento.getHoraInicio()) 
								|| listaEventos.get(i).getHoraFim().isEqual(evento.getHoraFim())) {
							adicionar = false;
							
						}
					}
				}
				if (adicionar) {
					listaEventos.add(evento);
					System.out.println("Evento adicionado com sucesso!");
				}
				else {
					System.out.println("Sala indisponível nesse horário.");
				}
			}
		}

		public String atualizaEvento(ClienteObj cliente) throws Exception
		{
			return null;
		}
		public boolean encontrarEvento(ClienteObj cliente, String sala, LocalDateTime dataInicio) {
			if (cliente.getEventosDoCliente(sala, dataInicio).equals(null)) {
				return false;
			}
			else {
				return true;
			}
		}
		public String obterEventosCliente(ClienteObj cliente) throws Exception
		{
			return cliente.getEventosDoClienteToString();
		}

		public String removeEvento() throws Exception
		{
			return null;
		}

		public String percOcupacao() throws Exception
		{
			return null;
		}

		public String consNumReservas() throws Exception
		{
			int[] reservas = new int[listaEventos.size()];
			String[] responsavel = new String[listaEventos.size()];
			ArrayList<Evento> listaProvisoria = listaEventos;
			String resposta = "";
			for (int i = 0;i<listaProvisoria.size();i++) {
				reservas[i] = 1;
				responsavel[i] = listaProvisoria.get(i).getResponsavel();
				for (int j = i+1;j<listaProvisoria.size();j++) {
					if (listaProvisoria.get(i).getResponsavel().equals(listaProvisoria.get(j).getResponsavel())) {
						listaProvisoria.remove(j);
						reservas[i]=reservas[i] + 1;
					}
				}
				resposta = resposta + "Utilizador: " + responsavel[i] + "       Reservas: " + reservas[i] + "\n";
			}
			return resposta;
		}

		public String consNumResPUtiPTem() throws Exception
		{
			return null;
		}
}
