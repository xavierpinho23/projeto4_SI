package RMI_Avaliacao;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Evento implements Serializable
{

	//private static final long serialVersionUID = 1L;
	LocalDateTime horaInicio;
	LocalDateTime horaFim;
	String responsavel;
	String descricao;
	String sala;
	
	//Construtor Evento
	public Evento(String sala, LocalDateTime horaInicio, LocalDateTime horaFim, String responsavel, String descricao) {
		this.horaInicio=horaInicio;
		this.horaFim=horaFim;
		this.responsavel=responsavel;
		this.descricao=descricao;
		this.sala=sala;
	}
	//Método para aceder à Sala
	public String getSala()
	{
		return sala;
	}
	//Método para alterar a sala
	public void setSala(String sala)
	{
		this.sala = sala;
	}
	//Método para aceder à hora de inicio
	public LocalDateTime getHoraInicio()
	{
		return horaInicio;
	}
	//Método para alterar a hora de inicio
	public void setHoraInicio(LocalDateTime horaInicio)
	{
		this.horaInicio = horaInicio;
	}

	//Método para aceder à hora de fim
	public LocalDateTime getHoraFim()
	{
		return horaFim;
	}
	//Método para alterar a hora de fim
	public void setHoraFim(LocalDateTime horaFim)
	{
		this.horaFim = horaFim;
	}

	//Método para aceder ao responsável
	public String getResponsavel()
	{
		return responsavel;
	}
	//Método para alterar o responsável
	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}

	//Método para aceder à descrição
	public String getDescricao()
	{
		return descricao;
	}
	//Método para alterar a descrição
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
}
