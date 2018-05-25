package RMI_Avaliacao;



import java.io.Serializable;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import RMI_Avaliacao.Interface;

public class Cliente implements Serializable
{
	public static void main(String[] args) 
	{
		Boolean sair = false;
		int contador = 0;

		//Caso haja um erro na liga��o ao servidor, ele tenta 5 vezes fazer a lig��o
		//se n�o for poss�vel, encerra

		while (contador < 5)
		{
			try
			{
				Scanner scan = new Scanner(System.in);

				Boolean registando = true;
				// Returns a reference to the remote object Registry on the specified host and port.
				Registry registry = LocateRegistry.getRegistry("localhost", 2000);

				// 'lookup' returns the remote reference bound to the specified name in this registry.
				Interface DEI = (Interface) registry.lookup("evento");

				while (sair == false)
				{
					System.out.println("==========Bem-vindo ao DEI!=========");
					System.out.println("Nome: ");
					String resposta = scan.nextLine();
					String responsavel = resposta;
					DEI.criarCliente(responsavel);

					while (registando) 
					{
						System.out.print("Deseja:\n"
								+ "[1] - Adicionar um evento a uma sala.\n"
								+ "[2] - Atualizar evento.\n"
								+ "[3] - Remover um evento.\n"
								+ "[4] - Percentagem de ocupa��o.\n"
								+ "[5] - Consultar o n�mero de reservas efetuadas pelos utilizadores.\n"
								+ "[6] - Consultar o n�mero de reservas para um certo utilizador num certo per�odo temporal.\n"
								+ "[7] - Terminar sess�o. \n");
						resposta = scan.nextLine();

						//Adicionar um evento a uma sala
						if (resposta.equals("1")) 
						{
							System.out.println("As salas dispon�veis s�o: A01,A02,A03,B01,B02,B03,C01,C02,C03,D01,D02,D03");
							System.out.println("Que sala deseja (LNN)?");
							resposta = scan.nextLine();
							String sala = resposta;
							System.out.println("Insira a data em que deseja a sala (aaaa-MM-dd):");
							resposta = scan.nextLine();
							String dataInicio = resposta;
							System.out.println("A partir de que horas deseja a sala (HH:mm) (Entre as 10h e as 18h)?");
							resposta = scan.nextLine();
							dataInicio = dataInicio + " " + resposta;

							System.out.println("Dura��o da reserva (HH:mm):");
							resposta = scan.nextLine();
							String[] duracao = resposta.split(":");

							System.out.println("Adicione uma descri��o ao evento:");
							resposta = scan.nextLine();
							String descricao = resposta;

							//Criar objeto evento atrav�s do m�todo adicionaEvento
							System.out.println(DEI.adicionaEvento(sala,dataInicio,duracao,responsavel,descricao));
						}
						//Atualizar evento
						else if (resposta.equals("2")) 
						{
							System.out.println("Sala do evento que quer atualizar: ");
							resposta = scan.nextLine();
							String salaAntiga = resposta;

							System.out.println("Data do evento a atualizar (aaaa-MM-dd)? ");
							resposta = scan.nextLine();
							String dataInicioAntiga = resposta;

							System.out.println("Horas de in�cio do evento a atualizar (HH:mm) (Entre as 10h e as 18h)?");
							resposta = scan.nextLine();

							dataInicioAntiga = dataInicioAntiga + " " + resposta;

							System.out.println("Que sala deseja (LNN)?");
							resposta = scan.nextLine();
							String salaNova = resposta;
							System.out.println("Data (aaaa-MM-dd):");
							resposta = scan.nextLine();
							String dataInicioNova = resposta;
							System.out.println("Hora de Inicio (HH:mm) (Entre as 10h e as 18h)?");
							resposta = scan.nextLine();
							dataInicioNova = dataInicioNova + " " + resposta;

							System.out.println("Dura��o da reserva (HH:mm):");
							resposta = scan.nextLine();
							String[] duracao = resposta.split(":");

							System.out.println("Adicione uma descri��o ao evento:");
							resposta = scan.nextLine();
							String descricao = resposta;

							String atualiza = DEI.atualizarEvento(salaAntiga, dataInicioAntiga, salaNova, dataInicioNova, duracao, descricao);

							if(atualiza.contains("naoExiste"))
							{
								System.out.println("O evento n�o existe.");
							}
							else if (atualiza.contains("parametros"))
							{
								System.out.println("A atualiza��o n�o � poss�vel.");							
							}
							else
							{
								System.out.println("Atualiza��o bem sucedida");
							}
						}									
						//Remover evento
						else if(resposta.equals("3"))
						{

							System.out.println("Sala: ");
							resposta = scan.nextLine();
							String sala = resposta;

							System.out.println("Data do evento (aaaa-MM-dd)? ");
							resposta = scan.nextLine();
							String dataInicio = resposta;
							System.out.println("Horas de in�cio do evento (HH:mm) (Entre as 10h e as 18h)?");
							resposta = scan.nextLine();

							dataInicio = dataInicio + " " + resposta;

							resposta = DEI.removeEvento(sala, dataInicio);
							if(!resposta.contains("Insucesso"))
							{
								System.out.println("Evento removido com sucesso.");
							}
							else
							{
								System.out.println("O evento que tentou remover n�o existe.");
							}


						}
						//Percentagem de ocupa��o
						else if (resposta.equals("4"))
						{
							System.out.println("Insira a data que deseja analisar (aaaa-MM-dd): ");
							resposta = scan.nextLine();
							String data = resposta;
							System.out.println("A percentagem de ocupa��o para o dia escolhido �: " + DEI.percOcupacao(data) + "% \n");
						}
						//Consultar o n�mero de reservas efetuadas pelos utilizadores
						else if (resposta.equals("5")) 
						{
							System.out.println(DEI.consNumReservas());
						}
						//Consultar o n�mero de reservas para um certo utilizador num certo per�odo temporal
						else if (resposta.equals("6")) 
						{
							System.out.println("Diga o nome do respons�vel:");
							responsavel = scan.nextLine();

							System.out.println("Data inicio (aaaa-MM-dd):");
							resposta = scan.nextLine();
							String dataInicio = resposta;
							System.out.println("Hora  (HH:mm) ?");
							resposta = scan.nextLine();

							dataInicio = dataInicio + " " + resposta;

							System.out.println("Data fim (aaaa-MM-dd):");
							resposta = scan.nextLine();
							String dataFim = resposta;
							System.out.println("Hora  (HH:mm) ?");
							resposta = scan.nextLine();

							dataFim = dataFim + " " + resposta;

							System.out.println(DEI.consNumResPUtiPTem(responsavel, dataInicio, dataFim));		
						}
						else if (resposta.equals("7"))
						{
							sair = true;
							registando = false;
							contador = 5;
							System.out.println("Sess�o terminada. \n");
						}
						else
						{
							System.out.println("Op�ao introduzida n�o v�lida.");							
						}					
					}
				}	
			}

			catch (Exception e) // catching Exception means that we are handling all errors in the same block
			{                   // usually it is advisable to use multiple catch blocks and perform different error handling actions
				// depending on the specific exception type caught
				contador = contador + 1;
				System.err.println("Ocorreu um erro. ");
				e.printStackTrace(); // prints detailed information about the exception
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e1)
				{

				}
			}

		}
	}
}