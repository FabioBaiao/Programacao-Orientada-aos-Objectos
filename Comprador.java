import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.io.Serializable;

public class Comprador extends Utilizador implements Serializable
{
    //Variáveis de instância
    private List<Imovel> favoritos;
    
    //Construtores
    public Comprador(){
        super();
        favoritos = new ArrayList<Imovel>();
    }
    
    public Comprador(String email, String nome, String password, String morada, LocalDate dataNascimento){
        super(email,nome,password,morada,dataNascimento);
        this.favoritos = new ArrayList<Imovel>();
    }

    public Comprador(Comprador c){
        super(c);
        favoritos = c.getFavoritos();
    }
    
    //Métodos de instância
    public List<Imovel> getFavoritos(){
        List<Imovel> res = new ArrayList<>();
         for(Imovel i : favoritos)
            res.add(i.clone());
        return res;  
    }
    
    public void setFavoritos(List<Imovel> fav){
        favoritos.clear();
        for(Imovel i : fav)
            favoritos.add(i.clone());
    }
    
    public Comprador clone(){
        return new Comprador(this);
    }
    
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Comprador o = (Comprador) obj;
        return super.equals(o) && o.getFavoritos().equals(favoritos);
    }
    
    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("Favoritos: ").append(favoritos.toString()).append("\n");
        return sb.toString();
    }
    
    public void setFavorito(Imovel im){
        favoritos.add(im);
    }

    public List<Imovel> getFavoritosConsulta(String email){
        List<Imovel> res = new ArrayList<>();
         for(Imovel i : favoritos){
            i.addConsulta (email);
            res.add(i.clone());
        }
        return res;  
    }
   
    private double limite;
    private double incrementos;
    private double minutos;
    private double minutosRest;
    private double precoFinal;

    public void adicionaInfoLeilao (double limite, double incrementos, double minutos){
        this.limite = limite;
        this.incrementos = incrementos;
        this.minutos = minutos;
        this.minutosRest = 0;
    }

    public double getLimite(){
        return limite;
    }

    public double getIncrementos(){
        return incrementos;
    }

    public double getMinutosRest (){
        return minutosRest;
    }

    public void decMinutosRest (){
        minutosRest--;
    }
    public void resetMinutosRest(){
        minutosRest = minutos;
    }
    
    public double getPrecoFinal (){
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal){
        this.precoFinal = precoFinal;
    }
    
    public void setMinutosRest(double minutosRest){
        this.minutosRest = minutosRest;
    }
    
    public void setMinutos(double minutos){
        this.minutos = minutos;
    }
    
    public void setIncrementos(double incrementos){
        this.incrementos = incrementos;
    }
    
    public void setLimite(double limite){
        this.limite = limite;
    }
}
