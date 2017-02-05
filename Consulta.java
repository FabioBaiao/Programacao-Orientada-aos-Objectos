import java.time.LocalDate;
import java.io.Serializable;

public class Consulta implements Serializable
{
    //Variáveis de instânica
    private LocalDate data;
    private String email;

    //Construtores
    public Consulta (){
    	data = LocalDate.now();
    	email = null;
    }

    public Consulta (LocalDate data, String email){
    	this.data = data; //Não é preciso clone. LocalDate é imutável.
    	this.email = email;
    }

    public Consulta (Consulta consulta){
    	this (consulta.getData(), consulta.getEmail());
    }

    //Métodos de instância
    public LocalDate getData (){
    	return data;
    }

    public String getEmail (){
    	return email;
    }

    public void setData (LocalDate data){
        this.data = data;
    }

    public void setEmail (String email){
        this.email = email;
    }

    public Consulta clone (){
    	return new Consulta (this);
    }
    
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Consulta o = (Consulta) obj;
        return o.getData().equals(data) && o.getEmail().equals(email);
    }
    
    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("Data: ").append(data).append("\n")
          .append("Email: ").append(email).append("\n");
        return sb.toString();
    }
}
