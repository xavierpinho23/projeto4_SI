package RMI_Avaliacao;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server implements Serializable
{
	public static void main(String[] args)
	{
		try
		{
			ReservasImpl obj = new ReservasImpl();
			Registry registry = LocateRegistry.createRegistry(2000);

			registry.bind("evento", obj);
		}
		catch (Exception e) 
		{                   

			System.err.println("Ocorreu um erro:");
			e.printStackTrace(); 
		}
	}
}