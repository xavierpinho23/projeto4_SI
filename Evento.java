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
	//M�todo para aceder � Sala
	public String getSala()
	{
		return sala;
	}
	//M�todo para alterar a sala
	public void setSala(String sala)
	{
		this.sala = sala;
	}
	//M�todo para aceder � hora de inicio
	public LocalDateTime getHoraInicio()
	{
		return horaInicio;
	}
	//M�todo para alterar a hora de inicio
	public void setHoraInicio(LocalDateTime horaInicio)
	{
		this.horaInicio = horaInicio;
	}

	//M�todo para aceder � hora de fim
	public LocalDateTime getHoraFim()
	{
		return horaFim;
	}
	//M�todo para alterar a hora de fim
	public void setHoraFim(LocalDateTime horaFim)
	{
		this.horaFim = horaFim;
	}

	//M�todo para aceder ao respons�vel
	public String getResponsavel()
	{
		return responsavel;
	}
	//M�todo para alterar o respons�vel
	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}

	//M�todo para aceder � descri��o
	public String getDescricao()
	{
		return descricao;
	}
	//M�todo para alterar a descri��o
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
}
