package projeto4_SI;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ReservasImpl extends UnicastRemoteObject implements Interface, Serializable
	{

		ArrayList<Evento> listaEventos = new ArrayList<Evento>();
		ClienteObj cliente;
		ArrayList<String> salas = new ArrayList<String>(Arrays.asList("A01","A02","A03","B01","B02","B03","C01","C02","C03","D01","D02","D03"));
		
		protected ReservasImpl() throws RemoteException
		{
		}
		//Método para adicionar eventos
		public String adicionaEvento(String sala, LocalDateTime dateTime,LocalDateTime finalDateTime,String responsavel,String descricao) throws Exception
		{
			Evento evento = new Evento(sala,dateTime,finalDateTime,responsavel,descricao);
			boolean adicionar = true;
			if (listaEventos.isEmpty() && salas.contains(sala)) 
			{
				listaEventos.add(evento);
				cliente.addEventosDoCliente(evento);
				System.out.println("Evento adicionado");
				return "Evento adicionado com sucesso";
				
			}
			else if (salas.contains(sala)){
				for (int i=0;i<listaEventos.size();i++) 
				{
					if (listaEventos.get(i).getSala().equals(evento.getSala())) 
					{
						if ((listaEventos.get(i).getHoraInicio().isBefore(evento.getHoraFim()) 
								&& listaEventos.get(i).getHoraFim().isAfter(evento.getHoraInicio()) 
								|| evento.getHoraFim().getHour()>18 
								|| evento.getHoraInicio().getHour()<10 )
								|| listaEventos.get(i).getHoraInicio().isEqual(evento.getHoraInicio()) 
								|| listaEventos.get(i).getHoraFim().isEqual(evento.getHoraFim())) 
						{
							adicionar = false;	
						}
					}
				}
					
				
				if (adicionar) 
				{
					cliente.addEventosDoCliente(evento);
					listaEventos.add(evento);
					System.out.println("Evento adicionado");
					return "Evento adicionado com sucesso";
					
					
				}
				else 
				{
					System.out.println("horário");
					return "Sala indisponível nesse horário.";
				}
			}
			else {
				System.out.println("sala inexistente");
				return "Sala inexistente";
			}
				
			
			
		}
		//Método para encontrar eventos
		public boolean encontrarEvento(String sala, LocalDateTime dataInicio) 
		{
			if (cliente.getEventosDoCliente(sala, dataInicio) == null)
			{
				return false;
			}
			else 
			{
				return true;
			}
		}
		public String obterSalas() throws Exception 
		{
			String[] salass = new String[salas.size()];
			for (int i = 0;i<salas.size();i++) {
				salass[i] = salas.get(i);
			}
			return Arrays.toString(salass);
		}
		//Método para obter os eventos do cliente
		public String obterEventosCliente() throws Exception
		{
			return cliente.getEventosDoClienteToString();
		}

		//Método para remover eventos
		
		//Método para mostrar a percentagem de ocupação de salas
		public String percOcupacao(String data) throws Exception
		{
			String[] datas = data.split("-");
			ArrayList<Evento> eventosDia = new ArrayList<Evento>();
			for (int i = 0; i<listaEventos.size();i++) {
				if (listaEventos.get(i).getHoraInicio().getDayOfMonth() == Integer.parseInt(datas[0]) && 
					listaEventos.get(i).getHoraInicio().getMonthValue() == Integer.parseInt(datas[1]) &&
					listaEventos.get(i).getHoraInicio().getYear() == Integer.parseInt(datas[2])) {
					eventosDia.add(listaEventos.get(i));
				}
			}
			int numeroTotalDeHoras = listaEventos.size()*8;
			int numeroHorasOcupado = 0;
			for (int i = 0; i<eventosDia.size();i++) {
				Duration duracao = Duration.between(eventosDia.get(i).getHoraInicio(),eventosDia.get(i).getHoraFim());
				int horas = duracao.toHoursPart();
				numeroHorasOcupado = numeroHorasOcupado + horas;
			}
			System.out.println(numeroHorasOcupado);
			double tempo = numeroHorasOcupado/numeroTotalDeHoras*100;
			return Double.toString(tempo);
		}
		//Método para mostrar o número de reservas efetuadas pelos utilizadores
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
					if (listaProvisoria.get(i).getResponsavel().equals(listaProvisoria.get(j).getResponsavel()))
					{
						listaProvisoria.remove(j);
						reservas[i]=reservas[i] + 1;
					}
				}
				resposta = resposta + "Utilizador: " + responsavel[i] + "       Reservas: " + reservas[i] + "\n";
			}
			return resposta;
		}
		//Método para mostrar o número de reservas de um determinado utilizador num determinado periodo de tempo
		public String consNumResPUtiPTem() throws Exception
		{
			return null;
		}
		//Método para remover eventos da lista de eventos
		public void removeEvento(String sala, LocalDateTime dataInicio)
		{
			for (int i = 0; i < listaEventos.size(); i++)
			{
				if (sala.equals(listaEventos.get(i).getSala()) && dataInicio.equals(listaEventos.get(i).getHoraInicio())) 
				{
					listaEventos.remove(i);
					cliente.removeEventosDoCliente(sala, dataInicio);
				}
			}
		}
		
		public void criarCliente(String nome) throws Exception
		{
			
			cliente = new ClienteObj(nome);
		}
}
