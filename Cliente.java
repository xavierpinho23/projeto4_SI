package projeto4_SI;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import projeto4_SI.Interface;

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

			//let's execute our remote operation and keep the return value in 'result'!
			while (registando) {
				System.out.println("Bem-vindo ao DEI!");
				System.out.print("Deseja:\n"
						+ "[1] - Adicionar um evento a uma sala.\n"
						+ "[2] - Atualizar evento.\n"
						+ "[3] - Remover um evento.\n"
						+ "[4] - Percentagem de ocupação.\n"
						+ "[5] - Consultar o número de reservas efetuadas pelos utilizadores."
						+ "\n[6] - Consultar o número de reservas para um certo utilizador num certo período temporal.\n");
				String resposta = scan.nextLine();
				if (resposta.equals("1")) {
					System.out.println("Que sala deseja (LNN)?");
					resposta = scan.nextLine();
					String sala = resposta;
					System.out.println("Insira a data em que deseja a sala (aaaa-MM-dd):");
					resposta = scan.nextLine();
					String str = resposta;
					System.out.println("A partir de que horas deseja a sala (HH:mm)?");
					resposta = scan.nextLine();
					String horasInicio = resposta;
					str = str + " " + resposta;
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
					System.out.println("Duração da reserva (HH:mm):");
					resposta = scan.nextLine();
					String[] str2 = resposta.split(":");
					LocalDateTime finalDateTime = dateTime.plusMinutes(Integer.parseInt(str2[1]));
					finalDateTime = finalDateTime.plusHours(Integer.parseInt(str2[0]));
					
					System.out.println("Responsável:");
					resposta = scan.nextLine();
					String responsavel = resposta;
					
					System.out.println("Adicione uma descrição ao evento:");
					resposta = scan.nextLine();
					String descricao = resposta;
					
					Evento evento = new Evento(sala,dateTime,finalDateTime,responsavel,descricao);
					DEI.adicionaEvento(evento);
					
			}
				if (resposta.equals("5")) {
					System.out.println(DEI.consNumReservas());
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
	

