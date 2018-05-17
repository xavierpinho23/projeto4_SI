package RMI_Avaliacao;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class ReservasImpl extends UnicastRemoteObject implements Interface, Serializable
	{

		private static final LocalDateTime DateTime = null;
		ArrayList<Evento> listaEventos = new ArrayList<Evento>();
		ArrayList<ClienteObj> listaClientes = new ArrayList<ClienteObj>();
		ClienteObj cliente;
		ArrayList<String> salas = new ArrayList<String>(Arrays.asList("A01","A02","A03","B01","B02","B03","C01","C02","C03","D01","D02","D03"));
		
		public ReservasImpl() throws RemoteException
		{
			try
			{
				FileInputStream documento = new FileInputStream("reservas.txt");
				ObjectInputStream doc = new ObjectInputStream(documento);
				this.listaEventos = (ArrayList<Evento>) doc.readObject();
				documento.close();						
			}
			catch (Exception e)
			{
				//não faz nada
			}
		}
		//Método para adicionar eventos
		public String adicionaEvento(String sala, LocalDateTime dateTime,LocalDateTime finalDateTime,String responsavel,String descricao) throws Exception
		{
			Evento evento = new Evento(sala,dateTime,finalDateTime,responsavel,descricao);
			boolean adicionar = true;
			
			LocalDateTime now = DateTime.now();
			CharSequence horaMax = "2000-01-01 18:00";
			CharSequence horaMin = "2000-01-01 10:00";
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime horaMaxima = LocalDateTime.parse(horaMax, formatter1);
			LocalDateTime horaMinima = LocalDateTime.parse(horaMin, formatter1);
			
			if (listaEventos.isEmpty() && salas.contains(sala)) 
			{
				listaEventos.add(evento);
				cliente.addEventosDoCliente(evento);
				System.out.println("Evento adicionado");
				return "Evento adicionado com sucesso";
				
			}
			
			else if (salas.contains(sala))
			{
				for (int i=0;i<listaEventos.size();i++) 
				{
					if (listaEventos.get(i).getSala().equals(evento.getSala())) 
					{
						if (listaEventos.get(i).getHoraFim().isAfter(evento.getHoraInicio())
								&& listaEventos.get(i).getHoraInicio().isBefore(evento.getHoraFim())
								|| evento.getHoraFim().getHour()>(horaMaxima.getHour())
								|| evento.getHoraInicio().getHour()<(horaMinima.getHour())
								|| listaEventos.get(i).getHoraInicio().isEqual(evento.getHoraInicio()) 
								|| listaEventos.get(i).getHoraFim().isEqual(evento.getHoraFim())
								|| evento.getHoraFim().isBefore(now)) 
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
					return "Data escolhida não válida.";
				}
			}
			else 
			{
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
		//Método para obter as salas
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
		
		//Método para mostrar a percentagem de ocupação de salas para um determinado dia
		public String percOcupacao(String data) throws Exception
		{
			//Fazer o split da data em ano-mês-dia
			String[] datas = data.split("-");
			//Lista dos eventos para o dia escolhido
			ArrayList<Evento> eventosDia = new ArrayList<Evento>();
			System.out.println("data: " + datas[0] + datas[1] + datas[2]);
			
			for (int i = 0; i<listaEventos.size();i++) {
				if (listaEventos.get(i).getHoraInicio().getYear() == Integer.parseInt(datas[0]) && 
					listaEventos.get(i).getHoraInicio().getMonthValue() == Integer.parseInt(datas[1]) &&
					listaEventos.get(i).getHoraInicio().getDayOfMonth() == Integer.parseInt(datas[2])) {
					eventosDia.add(listaEventos.get(i));
					System.out.println("entrou aqui?");
				}
			}
			//Numero total de horas para todas as salas
			int numeroTotalDeHoras = salas.size()*8;
			double numeroHorasOcupado = 0;
			
			for (int i = 0; i<eventosDia.size();i++) 
			{
				Duration duracao = Duration.between(eventosDia.get(i).getHoraInicio(),eventosDia.get(i).getHoraFim());
				double horas = duracao.toHoursPart();
				numeroHorasOcupado = numeroHorasOcupado + horas;
			}

			double tempo = (numeroHorasOcupado/numeroTotalDeHoras)*100;
			return Double.toString(tempo);
		}
		//Método para mostrar o número de reservas efetuadas pelos utilizadores
		public String consNumReservas() throws Exception
		{
			int[] reservas = new int[listaEventos.size()];
			String[] responsavel = new String[listaEventos.size()];
			ArrayList<Evento> listaProvisoria = listaEventos;
			String resposta = "";
			if (listaProvisoria.isEmpty())
			{
				return "Não existem reservas efetuadas pelos utilizadores. \n";
			}
			else
			{
				for (int i = 0;i<listaProvisoria.size();i++) 
				{
					reservas[i] = 1;
					responsavel[i] = listaProvisoria.get(i).getResponsavel();
					
					for (int j = i+1;j<listaProvisoria.size();j++) 
					{
						if (listaProvisoria.get(i).getResponsavel().equals(listaProvisoria.get(j).getResponsavel()))
						{
							listaProvisoria.remove(j);
							reservas[i] = reservas[i] + 1;
						}
					}
					resposta = resposta + "Utilizador: " + responsavel[i] + "       Reservas: " + reservas[i] + "\n";
				}
				return resposta;
			}
		}
		//Método para mostrar o número de reservas de um determinado utilizador num determinado periodo de tempo
		public String consNumResPUtiPTem(String responsavel, LocalDateTime dataInicio, LocalDateTime dataFim) throws Exception
		{
			
			ArrayList<Evento> listaProvisoria = listaEventos;
			int contador = 0;
			String resposta = "";
			boolean existe = false;
			ClienteObj cliente = null;

			for (int i = 0; i<listaClientes.size();i++)
			{
				if (listaClientes.get(i).getNome().equals(responsavel))
				{
					cliente = listaClientes.get(i);
					existe = true;
				}

			}
			if (existe) 
			{
				for (int i = 0; i < cliente.obterListaDeEventos().size();i++)
				{
					if (cliente.obterListaDeEventos().get(i).getHoraFim().isBefore(dataFim) && cliente.obterListaDeEventos().get(i).getHoraInicio().isAfter(dataInicio))
					{
						contador = contador + 1;
					}


				}
				return "No período selecionado o " + cliente.getNome() + " tem " + Integer.toString(contador) + " eventos.\n";

			}
			else
			{
				return "O nome que procurou não existe.";
			}

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
			listaClientes.add(cliente);
		}

		public void guardarReservas()
		{
			try
			{
				FileOutputStream documento = new FileOutputStream("reservas.txt");
				ObjectOutputStream doc = new ObjectOutputStream(documento);
				doc.writeObject(this.listaEventos);
				documento.close();
			}
			catch (Exception e)
			{
				// não faz nada
			}
		}
}
