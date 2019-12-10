package fachada;
import java.util.ArrayList;


import repositorio.Repositorio;
import modelo.Pessoa;
import modelo.Mensagem;
import modelo.Administrador;

public class Fachada {
	private static Pessoa logado;
	private static Repositorio repo = new Repositorio();
	
	public static Pessoa login(String email,  String senha) throws Exception {
		if(Fachada.obterLogada() != null) {
			throw new Exception("J� existe um usu�rio logado!");
		}
		logado = repo.localizarPessoa(email, senha);
		return logado;
	}
	
	public static void logoff() {
		logado = null;
	}
	
	public static Pessoa obterLogada() {
		return logado;
	}
	
	public static Pessoa cadastrarPessoa(String email, String senha, String nome) {
		Pessoa p = new Pessoa(email, senha, nome);
		repo.adicionar(p);
		return p;
	}
	
	public static Administrador cadastrarAdministrador(String email, String senha, String nome, String setor) {
		Administrador adm = new Administrador(email,senha,nome,setor);
		repo.adicionar(adm);
		return adm;
	}
	
	public static ArrayList<Pessoa> listarPessoas() throws Exception {
		if(repo.localizarPessoa().isEmpty()) {
			throw new Exception("N�o h� nenhuma pessoa cadastrada!");
		}
		return repo.localizarPessoa();
	}
	
	public static ArrayList<Pessoa> listarPessoas(String termo) throws Exception {
		if(repo.localizarPessoa(termo).isEmpty()) {
			throw new Exception("N�o h� nenhum nome de pessoa com esse termo!");
		}
		return repo.localizarPessoa(termo);
	}
	
	
	public static Mensagem enviarMensagem(String emaildest, String texto) throws Exception {
		Pessoa logada = Fachada.obterLogada();
		int saida_len = logada.getCaixaSaida().size();
		
		if(logada == null)
			throw new Exception("� necess�rio fazer login!");
		else {
			
			if(texto.length() > 200)
				throw new Exception("Ultrapassa 200 caracteres");
			
			for(Pessoa p: repo.localizarPessoa()) {
				if(p.getEmail() == emaildest) {
					Mensagem new_message = new Mensagem(
							logada,
							p,
							texto
					);
					
					repo.adicionar(new_message);
					p.adicionarEntrada(new_message);
					logada.adicionarSaida(new_message);
					return new_message;
				}
			}
			
			throw new Exception("Esse email n�o existe!");
		}
	}
		
	
	public static ArrayList<Mensagem> listarCaixaEntrada() throws Exception {
		if(Fachada.obterLogada() == null)
			throw new Exception("� necess�rio fazer login!");
	
		return Fachada.obterLogada().getCaixaEntrada();
	}
	
	public static ArrayList<Mensagem> listarCaixaSaida() throws Exception {
		if(Fachada.obterLogada() == null)
			throw new Exception("� necess�rio fazer login!");
		
		return Fachada.obterLogada().getCaixaSaida();
	}
	
	public static Mensagem apagarMensagem(int id) throws Exception {
		Pessoa logada = Fachada.obterLogada();
		
		if(logada == null)
			throw new Exception("� necess�rio fazer login!");
		
		Mensagem msg = repo.localizarMensagem(id);
		
		if(msg != null) {
			
			int status_cx_entrada = Fachada.listarCaixaEntrada().indexOf(msg);
			int status_cx_saida = Fachada.listarCaixaSaida().indexOf(msg);
			
			if(status_cx_entrada != -1) {
				logada.getCaixaEntrada().remove(msg);
				return msg;
			}
			
			if(status_cx_saida != -1) {
				logada.getCaixaSaida().remove(msg);
				return msg;
			}
		}
		
		throw new Exception("Esta mensagem n�o existe!");
	}
	
	public static ArrayList<Mensagem> espionarMensagens(String termo) throws Exception {
		if(Fachada.obterLogada().getClass().getSimpleName().equals("Administrador")) {
			return repo.localizarMensagem(termo);
		}
		return null;
	}
	
	public static ArrayList<Mensagem> espionarMensagens() throws Exception {
		if(Fachada.obterLogada().getClass().getSimpleName().equals("Administrador")) {
			return repo.localizarMensagem();
		}
		return null;
	}
	
	public static ArrayList<Pessoa> relatorio1() throws Exception {
		ArrayList<Pessoa> pessoas = new ArrayList<>();
		
		for(Pessoa p : repo.localizarPessoa()) {
			if(p.getCaixaSaida().isEmpty()) {
				pessoas.add(p);
			}
		}
		
		if(pessoas.isEmpty()) {
			throw new Exception("N�o existem pessoas que n�o enviaram mensagens!");
		}
		return pessoas;
	}
	
	public static ArrayList<Mensagem> relatorio2() throws Exception {
		ArrayList<Mensagem> msgs = new ArrayList<>();
		
		for(Mensagem m : repo.localizarMensagem()) {
			if(m.getEmitente().equals(m.getDestinatario())) {
				msgs.add(m);
			}
		}
		
		if(msgs.isEmpty()) {
			throw new Exception("N�o existem mensagens com remetente igual a destinatario");
		}
		return msgs;
	}
}
