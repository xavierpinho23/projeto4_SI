package RMI_Avaliacao;


import java.io.Serializable;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import RMI_Avaliacao.Interface;

public class Cliente implements Serializable
{
	public static void main(String[] args) 
	{
		Boolean sair = false;
		int contador = 0;
		
		//Caso haja um erro na ligação ao servidor, ele tenta 5 vezes fazer a ligção
		//se não for possível, encerra
		
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
					
					////////////////////////////////////////
					//Evento random
					String sala1 = "A01";
					CharSequence xxx = "2020-01-01 10:00";
					CharSequence yyy = "2020-01-01 15:00";
					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					LocalDateTime xx = LocalDateTime.parse(xxx, formatter1);
					LocalDateTime yy = LocalDateTime.parse(yyy, formatter1);
					Evento evento = new Evento("A01",xx,yy,responsavel,"blaabla");
					//DEI.adicionaEvento("A01",xx,yy,responsavel,"blaabla");
					///////////////////////////////////////
					
					while (registando) 
					{
						System.out.print("Deseja:\n"
								+ "[1] - Adicionar um evento a uma sala.\n"
								+ "[2] - Atualizar evento.\n"
								+ "[3] - Remover um evento.\n"
								+ "[4] - Percentagem de ocupação.\n"
								+ "[5] - Consultar o número de reservas efetuadas pelos utilizadores.\n"
								+ "[6] - Consultar o número de reservas para um certo utilizador num certo período temporal.\n"
								+ "[7] - Terminar sessão. \n");
						resposta = scan.nextLine();
						
						//Adicionar um evento a uma sala
						if (resposta.equals("1")) {
							System.out.println("As salas disponíveis são: " + DEI.obterSalas());
							System.out.println("Que sala deseja (LNN)?");
							resposta = scan.nextLine();
							String sala = resposta;
							System.out.println("Insira a data em que deseja a sala (aaaa-MM-dd):");
							resposta = scan.nextLine();
							String str = resposta;
							System.out.println("A partir de que horas deseja a sala (HH:mm) (Entre as 10h e as 18h)?");
							resposta = scan.nextLine();
							str = str + " " + resposta;
							//Creates a formatter using the specified pattern.
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
							LocalDateTime dataInicio = LocalDateTime.parse(str, formatter);
							System.out.println("Duração da reserva (HH:mm):");
							resposta = scan.nextLine();
							String[] str2 = resposta.split(":");
							//Adicionar a duração à hora de inicio (minutos e horas)
							LocalDateTime dataFim = dataInicio.plusMinutes(Integer.parseInt(str2[1]));
							dataFim = dataFim.plusHours(Integer.parseInt(str2[0]));

							System.out.println("Adicione uma descrição ao evento:");
							resposta = scan.nextLine();
							String descricao = resposta;

							//Criar objeto evento através do método adicionaEvento
							System.out.println(DEI.adicionaEvento(sala,dataInicio,dataFim,responsavel,descricao));
							
							evento = new Evento(sala,dataInicio,dataFim,responsavel,descricao);
						}
						//Atualizar evento
						else if (resposta.equals("2")) 
						{
							//Se o cliente não tiver eventos 
							if (DEI.obterEventosCliente().contains("Não"))
							{
								System.out.println("Você não tem eventos.");
							}
							else
							{
								//Mostrar os eventos do cliente
								System.out.println(DEI.obterEventosCliente());

								System.out.println("Sala: ");
								resposta = scan.nextLine();
								String sala = resposta;

								System.out.println("Data do evento (aaaa-MM-dd)? ");
								resposta = scan.nextLine();
								String str = resposta;
								
								System.out.println("Horas de início do evento (HH:mm) (Entre as 10h e as 18h)?");
								resposta = scan.nextLine();

								str = str + " " + resposta;
								//Creates a formatter using the specified pattern.
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
								LocalDateTime dataInicio = LocalDateTime.parse(str, formatter);

								//Procurar o evento através da sala e da data de início
								Evento eventoAtualizar = DEI.encontrarEvento(sala, dataInicio);
								
								//Caso o evento exista
								if (eventoAtualizar == null)
								{
									System.out.println("Evento não encontrado");
								}
								else
								{
									System.out.println("Que sala deseja (LNN)?");
									resposta = scan.nextLine();
									sala = resposta;
									System.out.println("Data (aaaa-MM-dd):");
									resposta = scan.nextLine();
									str = resposta;
									System.out.println("Hora de Inicio (HH:mm) (Entre as 10h e as 18h)?");
									resposta = scan.nextLine();
									str = str + " " + resposta;
									//Creates a formatter using the specified pattern.
									formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
									dataInicio = LocalDateTime.parse(str, formatter);
									System.out.println("Duração da reserva (HH:mm):");
									resposta = scan.nextLine();
									String[] str2 = resposta.split(":");
									//Adicionar a duração à hora de inicio (minutos e horas)
									LocalDateTime dataFim = dataInicio.plusMinutes(Integer.parseInt(str2[1]));
									dataFim = dataFim.plusHours(Integer.parseInt(str2[0]));
									System.out.println("Adicione uma descrição ao evento:");
									resposta = scan.nextLine();
									String descricao = resposta;

									boolean atualiza = DEI.atualizarEvento(eventoAtualizar, sala, dataInicio, dataFim, descricao);
									
									if(atualiza)
									{
										System.out.println("Evento Atualizado com sucesso");
									}
									else
									{
										System.out.println("Atualização mal executada.");
									}
									
							}				
						}
						}
						//Remover evento
						else if(resposta.equals("3"))
						{
							//Mostrar os eventos do cliente
							System.out.println(DEI.obterEventosCliente());
							if (DEI.obterEventosCliente().contains("Não"))
							{
								continue;
							}
							else
							{
								System.out.println("Sala: ");
								resposta = scan.nextLine();
								String sala = resposta;

								System.out.println("Data do evento (aaaa-MM-dd)? ");
								resposta = scan.nextLine();
								String str = resposta;
								System.out.println("Horas de início do evento (HH:mm) (Entre as 10h e as 18h)?");
								resposta = scan.nextLine();

								str = str + " " + resposta;
								//Creates a formatter using the specified pattern.
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
								LocalDateTime dataInicio = LocalDateTime.parse(str, formatter);
								
								//Caso o evento exista
								if(DEI.encontrarEvento(sala, dataInicio)!=null)
								{
									//remove evento
									DEI.removeEvento(sala, dataInicio);
									System.out.println("Evento removido com sucesso.");
								}
								else
								{
									System.out.println("O evento não existe.");
								}
							}
							
							
						}
						//Percentagem de ocupação
						else if (resposta.equals("4"))
						{
							System.out.println("Insira a data que deseja analisar (aaaa-MM-dd): ");
							resposta = scan.nextLine();
							String data = resposta;
							System.out.println("A percentagem de ocupação para o dia escolhido é: " + DEI.percOcupacao(data) + "% \n");
						}
						//Consultar o número de reservas efetuadas pelos utilizadores
						else if (resposta.equals("5")) 
						{
							System.out.println(DEI.consNumReservas());
						}
						//Consultar o número de reservas para um certo utilizador num certo período temporal
						else if (resposta.equals("6")) 
						{
							System.out.println("Diga o nome do responsável:");
							responsavel = scan.nextLine();
									
							System.out.println("Data inicio (aaaa-MM-dd):");
							resposta = scan.nextLine();
							String str = resposta;
							System.out.println("Hora  (HH:mm) ?");
							resposta = scan.nextLine();
							
							str = str + " " + resposta;
							//Creates a formatter using the specified pattern.
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
							LocalDateTime dataInicio = LocalDateTime.parse(str, formatter);
							
							System.out.println("Data fim (aaaa-MM-dd):");
							resposta = scan.nextLine();
							str = resposta;
							System.out.println("Hora  (HH:mm) ?");
							resposta = scan.nextLine();
							
							str = str + " " + resposta;
							//Creates a formatter using the specified pattern.
							formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
							LocalDateTime dataFim = LocalDateTime.parse(str, formatter);
							
							System.out.println(DEI.consNumResPUtiPTem(responsavel, dataInicio, dataFim));
						
						}
						else if (resposta.equals("7"))
						{
							sair = true;
							registando = false;
							contador = 5;
							System.out.println("Sessão terminada. \n");
						}
						else
						{
							System.out.println("Opçao introduzida não válida.");
							
						}
						
						DEI.guardarReservas();

					}
				}
				
			}

		
		
				
		catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{                   // usually it is advisable to use multiple catch blocks and perform different error handling actions
			// depending on the specific exception type caught
			contador = contador + 1;
			System.err.println("Ocorreu um erro: ");
			e.printStackTrace(); // prints detailed information about the exception
			try
			{
				Thread.sleep(4000);
			}
			catch (InterruptedException e1)
			{
				
			}
		}

		}
	}
}