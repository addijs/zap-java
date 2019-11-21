package modelo;

import java.time.LocalDateTime;

public class Mensagem {
	private static int class_id = 0;
	private int id;
	private Pessoa emitente;
	private Pessoa destinatario;
	private String texto;
	private LocalDateTime data;
	
	public Mensagem(int id, Pessoa emitente, Pessoa dest, String texto) {
		Mensagem.class_id++;
		this.id = Mensagem.class_id;
		this.emitente = emitente;
		this.destinatario = dest;
		this.texto = texto;
	}
}
