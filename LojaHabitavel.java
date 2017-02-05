import java.util.List;
import java.io.Serializable;
public class LojaHabitavel extends Loja implements Habitavel, Serializable
{
    // variáveis de instância
    private Apartamento apartamento;

    /**
     * Construtor para objetos da classe LojaHabitavel
     */
    public LojaHabitavel (){
        super ();
        apartamento = new Apartamento();
    }

    public LojaHabitavel (String rua, double precoPedido, double precoMinimo, String estado, String id, double area, boolean wc, 
        String tipoNegocio, int numeroPorta, Apartamento apartamento){

        super (rua, precoPedido, precoMinimo, estado, id, area, wc, tipoNegocio, numeroPorta);
        this.apartamento = apartamento.clone();
    }

    public LojaHabitavel(LojaHabitavel lh)
    {
        super (lh);
        apartamento = lh.getApartamento();
    }

    public Apartamento getApartamento()
    {
        return apartamento.clone();
    }
    
    public int getNWCs (){
        return apartamento.getNWCs();
    }

    public int getNQuartos (){
        return apartamento.getNQuartos();
    }
    
    public void setNWCs (int nWC){
        apartamento.setNWCs (nWC);
    }

    public void setNQuartos (int nQuartos){
        apartamento.setNQuartos (nQuartos);
    }
    /*
    public boolean possuiJardim (){
        return false;
    }
    */
    public LojaHabitavel clone(){
        return new LojaHabitavel (this);
    }
    
    public boolean equals (Object obj){
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        LojaHabitavel l = (LojaHabitavel) obj;
        return super.equals(l);
    }
    
    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("Apartamento").append("\n").append(apartamento.toString()).append("\n");
        return sb.toString();
    }
}
