package fachada;
import java.util.ArrayList;
import repositorio.Repositorio;
import modelo.Pessoa;
import modelo.Mensagem;

public class Fachada {
	private static Pessoa logado;
	private static Repositorio repo = new Repositorio();
	
	public static Pessoa login(String email,  String senha) {
		logado = repo.localizarPessoa(email, senha);
		return logado;
	}
	
	public static void logoff() {
		logado = null;
	}
	
	public static Pessoa obterLogada() {
		return logado;
	}
	
	public static void cadastrarPessoa(String email, String senha, String nome) {
		Pessoa p = new Pessoa(email, senha, nome);
		repo.adicionar(p);
	}
	
	public static ArrayList<Pessoa> listarPessoas() {
		
	}
	
	public static ArrayList<Pessoa> listarPessoas(String termo) {
		
	}
	
	
	public static Mensagem enviarMensagem(String emaildest, String texto) {
		
	}
	
	public static ArrayList<Mensagem> listarCaixaEntrada() {
		
	}
	
	public static ArrayList<Mensagem> listarCaixaSaida() {
		
	}
	
	public static Mensagem apagarMensagem(int id) {
		
	}
	
	public static ArrayList<Mensagem> espionarMensagem(String termo) {
		
	}
	
	public static ArrayList<Pessoa> relatorio1() {
		
	}
	
	public static ArrayList<Mensagem> relatorio2() {
		
	}
}
