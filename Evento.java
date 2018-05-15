package projeto4_SI;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Evento implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LocalDateTime horaInicio;
	LocalDateTime horaFim;
	String responsavel;
	String descricao;
	String sala;
	
	public Evento(String sala, LocalDateTime horaInicio, LocalDateTime horaFim, String responsavel, String descricao) {
		this.horaInicio=horaInicio;
		this.horaFim=horaFim;
		this.responsavel=responsavel;
		this.descricao=descricao;
		this.sala=sala;
	}

	public String getSala()
	{
		return sala;
	}

	public void setSala(String sala)
	{
		this.sala = sala;
	}

	public LocalDateTime getHoraInicio()
	{
		return horaInicio;
	}

	public void setHoraInicio(LocalDateTime horaInicio)
	{
		this.horaInicio = horaInicio;
	}

	public LocalDateTime getHoraFim()
	{
		return horaFim;
	}

	public void setHoraFim(LocalDateTime horaFim)
	{
		this.horaFim = horaFim;
	}

	public String getResponsavel()
	{
		return responsavel;
	}

	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
}
