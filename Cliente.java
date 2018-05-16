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
	public static void main(String[] args) {
		try
		{
			Scanner scan = new Scanner(System.in);

			Boolean registando = true;
			// Returns a reference to the remote object Registry on the specified host and port.
			Registry registry = LocateRegistry.getRegistry("localhost", 2000);

			// 'lookup' returns the remote reference bound to the specified name in this registry.
			Interface DEI = (Interface) registry.lookup("evento");

			//Será que é aqui?
			Thread.sleep(2000);
			
			System.out.println("==========Bem-vindo ao DEI!=========");
			System.out.println("Nome: ");
			String resposta = scan.nextLine();
			String responsavel = resposta;
			DEI.criarCliente(responsavel);
			
			////////////////////////////////////////
			//Evento random
			String sala1 = "A01";
			CharSequence xxx = "2000-01-01 10:00";
			CharSequence yyy = "2000-01-01 15:00";
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime xx = LocalDateTime.parse(xxx, formatter1);
			LocalDateTime yy = LocalDateTime.parse(yyy, formatter1);
			Evento evento = new Evento("A01",xx,yy,responsavel,"blaabla");
			System.out.println("hora fim: " +evento.horaFim);
			DEI.adicionaEvento("A01",xx,yy,responsavel,"blaabla");
			///////////////////////////////////////
			
			while (registando) 
			{
				System.out.print("Deseja:\n"
						+ "[1] - Adicionar um evento a uma sala.\n"
						+ "[2] - Atualizar evento.\n"
						+ "[3] - Remover um evento.\n"
						+ "[4] - Percentagem de ocupação.\n"
						+ "[5] - Consultar o número de reservas efetuadas pelos utilizadores.\n"
						+ "[6] - Consultar o número de reservas para um certo utilizador num certo período temporal.\n");
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
					LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
					System.out.println("Duração da reserva (HH:mm):");
					resposta = scan.nextLine();
					String[] str2 = resposta.split(":");
					//Adicionar a duração à hora de inicio (minutos e horas)
					LocalDateTime finalDateTime = dateTime.plusMinutes(Integer.parseInt(str2[1]));
					finalDateTime = finalDateTime.plusHours(Integer.parseInt(str2[0]));

					System.out.println("Adicione uma descrição ao evento:");
					resposta = scan.nextLine();
					String descricao = resposta;

					//Criar objeto evento através do método adicionaEvento
					System.out.println(DEI.adicionaEvento(sala,dateTime,finalDateTime,responsavel,descricao));
					
					evento = new Evento(sala,dateTime,finalDateTime,responsavel,descricao);
					System.out.println("hora fim: " +evento.horaFim );
				}
				//Atualizar evento
				else if (resposta.equals("2")) 
				{
					//Mostrar os eventos do cliente
					System.out.println(DEI.obterEventosCliente());
					//Se o cliente não tiver eventos 
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
						LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

						//Procurar o evento através da sala e da data de início
						boolean encontrar = DEI.encontrarEvento(sala, dateTime);
						
						//Caso o evento exista
						if(encontrar)
						{
							//remove evento
							DEI.removeEvento(sala, dateTime);
							
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
							dateTime = LocalDateTime.parse(str, formatter);
							System.out.println("Duração da reserva (HH:mm):");
							resposta = scan.nextLine();
							String[] str2 = resposta.split(":");
							//Adicionar a duração à hora de inicio (minutos e horas)
							LocalDateTime finalDateTime = dateTime.plusMinutes(Integer.parseInt(str2[1]));
							finalDateTime = finalDateTime.plusHours(Integer.parseInt(str2[0]));
							System.out.println("Adicione uma descrição ao evento:");
							resposta = scan.nextLine();
							String descricao = resposta;
							
							//adicionar evento
							
							System.out.println(DEI.adicionaEvento(sala,dateTime,finalDateTime,responsavel,descricao));

						}
						else
						{
							System.out.println("O evento não existe.");
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
						LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
						
						//Caso o evento exista
						if(DEI.encontrarEvento(sala, dateTime))
						{
							//remove evento
							DEI.removeEvento(sala, dateTime);
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
					System.out.println("A percentagem de ocupação para o dia escolhido é: " + DEI.percOcupacao(data));
				}
				//Consultar o número de reservas efetuadas pelos utilizadores
				else if (resposta.equals("5")) 
				{
					System.out.println(DEI.consNumReservas());
				}
				//Consultar o número de reservas para um certo utilizador num certo período temporal
				else if (resposta.equals("6")) 
				{
					System.out.println("Insira a data inicial (aaaa-MM-dd): ");
					String data1 = scan.nextLine();							
					System.out.println("Insira a data final (aaaa-MM-dd): ");
					String data2 = scan.nextLine();
							
				}

			}
		}		
		catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{                   // usually it is advisable to use multiple catch blocks and perform different error handling actions
			// depending on the specific exception type caught
			
			System.err.println("Ocorreu um erro: ");
			e.printStackTrace(); // prints detailed information about the exception
			
		}
	}
}


