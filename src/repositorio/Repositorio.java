package repositorio;

import java.util.ArrayList;
import java.util.TreeMap;

import modelo.Mensagem;
import modelo.Pessoa;

public class Repositorio {
	private TreeMap<String, Pessoa> pessoas = new TreeMap<>();
	private ArrayList<Mensagem> mensagens = new ArrayList<>();
	
	public void adicionar(Pessoa p) {
		pessoas.put(p.getEmail()+p.getSenha(), p);
	}
	
	public void remover(Pessoa p) {
		pessoas.remove(p.getEmail()+p.getSenha());
	}
	
	public Pessoa localizarPessoa(String email, String senha) {
		return pessoas.get(email+senha);
	}
}
