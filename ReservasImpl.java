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
	//Lista de Eventos
	ArrayList<Evento> listaEventos = new ArrayList<Evento>();
	//Lista de Clientes
	ArrayList<ClienteObj> listaClientes = new ArrayList<ClienteObj>();
	ClienteObj cliente;

	//hora maxima
	CharSequence horaMax = "2000-01-01 18:00";
	//hora minima
	CharSequence horaMin = "2000-01-01 10:00";
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	LocalDateTime horaMaxima = LocalDateTime.parse(horaMax, formatter1);
	LocalDateTime horaMinima = LocalDateTime.parse(horaMin, formatter1);
	//Hora atual
	LocalDateTime now = DateTime.now();
	//Lista de salas
	ArrayList<String> salas = new ArrayList<String>(Arrays.asList("A01","A02","A03","B01","B02","B03","C01","C02","C03","D01","D02","D03"));

	public ReservasImpl() throws RemoteException
	{
		try
		{
			FileInputStream documento = new FileInputStream("reservas.txt");
			ObjectInputStream doc = new ObjectInputStream(documento);
			this.listaClientes = (ArrayList<ClienteObj>) doc.readObject();
			this.listaEventos = (ArrayList<Evento>) doc.readObject();
			documento.close();						
		}
		catch (Exception e)
		{
			//não faz nada
		}
	}
	//Método para adicionar eventos
	public String adicionaEvento(String sala, String dataInicio ,String[] duracao, String responsavel,String descricao) throws Exception
	{
		try
		{
			//Cria um formatador de data
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dataInicial = LocalDateTime.parse(dataInicio, formatter);
			//Adicionar a duração à hora de inicio (minutos e horas)
			LocalDateTime dataFim = dataInicial.plusMinutes(Integer.parseInt(duracao[1]));
			dataFim = dataFim.plusHours(Integer.parseInt(duracao[0]));

			Evento evento = new Evento(sala,dataInicial,dataFim,responsavel,descricao);
			boolean adicionar = true;

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
					guardarReservas();
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
		catch (Exception e)
		{
			return "Dados inseridos inválidos.";
		}
	}
	//Método para encontrar eventos
	public Evento encontrarEvento(String sala, LocalDateTime dataInicio) 
	{
		Evento evento = cliente.getEventosDoCliente(sala, dataInicio);

		if (evento == null)
		{
			return null;
		}
		else 
		{
			return evento;
		}
	}
	//Método para atualizar evento
	public String atualizarEvento (String salaAntiga, String dataInicioAntiga, String salaNova, String dataInicioNova, String[] duracao, String descricao)
	{
		try {

			int index = 0;
			boolean adicionar = true;

			String str = "";

			//Cria um formatador de data
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dataInicialAntiga = LocalDateTime.parse(dataInicioAntiga, formatter);
			LocalDateTime dataInicialNova = LocalDateTime.parse(dataInicioNova, formatter);

			//Adicionar a duração à hora de inicio (minutos e horas)
			LocalDateTime dataFim = dataInicialNova.plusMinutes(Integer.parseInt(duracao[1]));
			dataFim = dataFim.plusHours(Integer.parseInt(duracao[0]));

			Evento evento = cliente.getEventosDoCliente(salaAntiga,dataInicialAntiga);

			//Criar um evento temporario para averiguar se é possível
			Evento eventoTemporario = evento;

			if (evento == null)
			{
				str = "Insucesso:naoExiste";
			}
			else
			{
				for (int i = 0; i<listaEventos.size();i++)
				{
					if (listaEventos.get(i) == evento)
					{
						index = i;
					}
				}
				//Atualizar os parametros do evento temporario
				eventoTemporario.setSala(salaNova);
				eventoTemporario.setDescricao(descricao);
				eventoTemporario.setHoraFim(dataFim);
				eventoTemporario.setHoraInicio(dataInicialNova);

				//Verificar se o evento temporário é possível
				if (salas.contains(salaNova))
				{
					for (int i=0;i<listaEventos.size();i++) 
					{
						//Saltar o index
						if (i == index)
						{
							if (i+1 == listaEventos.size())
							{
								break;
							}
							else
							{
								i++;
							}
						}
						if (listaEventos.get(i).getSala().equals(eventoTemporario.getSala())) 
						{
							if (listaEventos.get(i).getHoraFim().isAfter(eventoTemporario.getHoraInicio())
									&& listaEventos.get(i).getHoraInicio().isBefore(eventoTemporario.getHoraFim())
									|| eventoTemporario.getHoraFim().getHour()>(horaMaxima.getHour())
									|| eventoTemporario.getHoraInicio().getHour()<(horaMinima.getHour())
									|| listaEventos.get(i).getHoraInicio().isEqual(eventoTemporario.getHoraInicio()) 
									|| listaEventos.get(i).getHoraFim().isEqual(eventoTemporario.getHoraFim())
									|| eventoTemporario.getHoraFim().isBefore(now)) 
							{
								adicionar = false;	
							}
						}
					}
				}
				else
				{
					adicionar = false;
				}
				if (adicionar)
				{
					listaEventos.get(index).setDescricao(descricao);
					listaEventos.get(index).setSala(salaNova);
					listaEventos.get(index).setHoraFim(dataFim);
					listaEventos.get(index).setHoraInicio(dataInicialNova);
					str = "Sucesso";

				}
				else
				{
					str = "Insucesso:parametros";
				}
			}

			return str;
		}
		catch(Exception e) 
		{
			return "Dados inseridos inválidos.";
		}
	}

	//Método para remover eventos da lista de eventos
	public String removeEvento(String sala, String dataInicio)
	{
		try {
			//Cria um formatador de data
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dataInicial = LocalDateTime.parse(dataInicio, formatter);
			Evento evento =  cliente.getEventosDoCliente(sala, dataInicial);

			String str = "";
			if (evento == null)
			{
				str =  "Insucesso:eventoNaoExiste";
			}
			else
			{
				for (int i = 0; i < listaEventos.size(); i++)
				{
					if (sala.equals(listaEventos.get(i).getSala()) && dataInicio.equals(listaEventos.get(i).getHoraInicio())) 
					{
						listaEventos.remove(i);
						cliente.removeEventosDoCliente(sala, dataInicio);

						str = "Sucesso";
					}
				}
			}
			guardarReservas();

			return str;
		}
		catch(Exception e) 
		{
			return "Dados inseridos inválidos.";
		}
	}
	//Método para mostrar a percentagem de ocupação de salas para um determinado dia
	public String percOcupacao(String data) throws Exception
	{
		try {
			//Fazer o split da data em ano-mês-dia
			String[] datas = data.split("-");
			
			//Lista dos eventos para o dia escolhido
			ArrayList<Evento> eventosDia = new ArrayList<Evento>();
			System.out.println("data: " + datas[0] + datas[1] + datas[2]);

			for (int i = 0; i<listaEventos.size();i++) 
			{
				if (listaEventos.get(i).getHoraInicio().getYear() == Integer.parseInt(datas[0]) && 
						listaEventos.get(i).getHoraInicio().getMonthValue() == Integer.parseInt(datas[1]) &&
						listaEventos.get(i).getHoraInicio().getDayOfMonth() == Integer.parseInt(datas[2])) 
				{
					eventosDia.add(listaEventos.get(i));
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
		catch(Exception e) {
			return "Dados inseridos inválidos.";
		}
	}
	//Método para mostrar o número de reservas efetuadas pelos utilizadores
	public String consNumReservas() throws Exception
	{
		try {

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
		catch(Exception e)
		{
			return "Dados inseridos inválidos.";
		}
	}
	//Método para mostrar o número de reservas de um determinado utilizador num determinado periodo de tempo
	public String consNumResPUtiPTem(String responsavel, String dataInicio, String dataFim) throws Exception
	{
		try {

			int contador = 0;
			boolean existe = false;
			ClienteObj cliente = null;
			ArrayList<Evento> listaEventosDoCliente = null;

			//Cria um formatador de data
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dataInicial = LocalDateTime.parse(dataInicio, formatter);
			LocalDateTime dataFinal = LocalDateTime.parse(dataFim, formatter);

			for (int i = 0; i<listaClientes.size();i++)
			{
				if (listaClientes.get(i).getNome().equals(responsavel))
				{
					cliente = listaClientes.get(i);
					existe = true;
					listaEventosDoCliente = cliente.obterListaDeEventos();		
				}
			}
			if (existe) 
			{
				for (int i = 0; i < listaEventosDoCliente.size();i++)
				{

					if (listaEventosDoCliente.get(i).getHoraFim().isBefore(dataFinal) && listaEventosDoCliente.get(i).getHoraInicio().isAfter(dataInicial))
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
		catch(Exception e) {
			return "Dados inseridos inválidos.";
		}
	}
	//Método para criar cliente
	public void criarCliente(String nome) throws Exception
	{
		boolean existe = false;

		for (int i = 0; i< listaClientes.size();i++)
		{
			if (listaClientes.get(i).getNome().equals(nome))
			{
				cliente = listaClientes.get(i);
				existe = true;
				break;
			}
		}
		if (!existe)
		{
			cliente = new ClienteObj(nome);
			listaClientes.add(cliente);
		}
		guardarReservas();
	}
	//Método para guardar as reservas											
	public void guardarReservas()
	{
		try
		{
			FileOutputStream documento = new FileOutputStream("reservas.txt");
			ObjectOutputStream doc = new ObjectOutputStream(documento);
			doc.writeObject(this.listaClientes);
			doc.writeObject(this.listaEventos);
			documento.close();
		}
		catch (Exception e)
		{
			// não faz nada
		}
	}
}
