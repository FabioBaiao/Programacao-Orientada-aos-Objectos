import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.io.Serializable;

public abstract class Imovel implements Serializable
{
    //Variáveis de instância
    private String rua;
    private double precoPedido;
    private double precoMinimo;
    private String estado;
    private String id;
    private List<Consulta> consultas;

    //Construtores
    public Imovel (){
    	this ("", 0, 0, "", "");
    }

    public Imovel (String rua, double precoPedido, double precoMinimo, String estado, String idImovel){
    	this.rua = rua;
    	this.precoPedido = precoPedido;
    	this.precoMinimo = precoMinimo;
    	this.estado = estado;
    	this.id = idImovel;
        consultas = new ArrayList<>();
    }

    public Imovel (Imovel imovel){
    	this (imovel.getRua(), imovel.getPrecoPedido(), imovel.getPrecoMinimo(), imovel.getEstado(), imovel.getId());
    }

    //Métodos de instância
    public String getRua (){
    	return rua;
    }

    public double getPrecoPedido (){
    	return precoPedido;
    }

    public double getPrecoMinimo (){
    	return precoMinimo;
    }

    public String getEstado (){
    	return estado;
    }

    public String getId (){
    	return id;
    }

    public List<Consulta> getConsultas (){
    	return consultas.stream()
    				   .map (c -> c.clone())
    				   .collect (Collectors.toList());
    }

    public void setRua (String rua){
    	this.rua = rua;
    }

    public void setPrecoPedido (int precoPedido){
    	this.precoPedido = precoPedido;
    }

    public void setPrecoMinimo (int precoMinimo){
    	this.precoMinimo = precoMinimo;
    }

    public void setEstado (String estado){
    	this.estado = estado;
    }

    public void setId (String id){
    	this.id = id;
    }

    public void setConsulta (List<Consulta> consulta){
    	this.consultas = consulta.stream()
    							.map (c -> c.clone())
    							.collect (Collectors.toList());
    }

    public abstract Imovel clone();
    
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Imovel o = (Imovel) obj;
        return o.getRua().equals(rua) && o.getEstado().equals(estado) && o.getConsultas().equals(consultas) &&
                o.getId().equals(id) && o.getPrecoPedido() == precoPedido && o.getPrecoMinimo() == precoMinimo;
    }
    
    public String toString (){
    	StringBuilder sb = new StringBuilder();
        sb.append("Rua: ").append(rua).append("\n")
          .append("Preço Pedido: ").append(precoPedido).append("\n")
          .append("Preço Mínimo: ").append(precoMinimo).append("\n")
          .append("Estado: ").append(estado).append("\n")
          .append("ID: ").append(id).append("\n")
          .append("Consultas: ").append(consultas.toString()).append("\n");
        return sb.toString();
    }

    public int hashCode(){
    	return id.hashCode();
    }

    public void addConsulta (String email){
    	consultas.add(new Consulta (LocalDate.now(), email));
    }
}
