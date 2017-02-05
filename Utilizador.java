import java.time.LocalDate;
import java.io.Serializable;

public abstract class Utilizador implements Serializable
{
	//Variáveis de instância
	private String email;
	private String nome;
	private String password;
	private String morada;
	private LocalDate dataNascimento;

	//Construtores
	public Utilizador (){
		this ("", "", "", "", LocalDate.now());
	}

	public Utilizador (String email, String nome, String password, String morada, LocalDate dataNascimento){
		this.email = email;
		this.nome = nome;
		this.password = password;
		this.morada = morada;
		this.dataNascimento = dataNascimento;
	}
	
	public Utilizador (Utilizador utilizador){
		this (utilizador.getEmail(), utilizador.getNome(), utilizador.getPassword(), utilizador.getMorada(), utilizador.getDataNascimento());
	}
	
	//Métodos de instância
	public String getEmail (){
		return email;
	}

	public String getNome (){
		return nome;
	}

	public String getPassword (){
		return password;
	}

	public String getMorada (){
		return morada;
	}

	public LocalDate getDataNascimento (){
		return dataNascimento;
	}

	public void setEmail (String email){
		this.email = email;
	}

	public void setNome (String nome){
		this.nome = nome;
	}

	public void setPassword (String password){
		this.password = password;
	}

	public void setMorada (String morada){
		this.morada = morada;
	}

	public void setDataNascimento (LocalDate dataNascimento){
		this.dataNascimento = dataNascimento;
	}
	
	public abstract Utilizador clone();

	public boolean equals (Object o){
		if (this == o){
			return true;
		}
		if (o == null || o.getClass() != this.getClass()){
			return false;
		}
		Utilizador u = (Utilizador) o;
		return this.email.equals(u.getEmail()) && this.nome.equals(u.getNome()) && this.password.equals(u.getPassword()) && 
			this.morada.equals(u.getMorada()) && this.dataNascimento.equals(u.getDataNascimento());
	}
	
	public String toString (){
		StringBuilder sb = new StringBuilder();
        sb.append("Email: ").append(email).append("\n").append("Nome: ")
                .append(nome).append("\n").append("Password: ").append(password).append("\n")
                .append("Data Nascimento: ").append(dataNascimento).append("\n");
        return sb.toString();
	}
}
