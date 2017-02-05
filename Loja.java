import java.util.List;
import java.io.Serializable;
public class Loja extends Imovel implements Serializable
{
    //Variáveis de instância
    private double area;
    private boolean wc;
    private String tipoNegocio;
    private int numeroPorta;

    //Construtores
    public Loja(){
        super ();
        area = 0;
        wc = false;
        tipoNegocio = "";
        numeroPorta = 0;
    }

    public Loja (String rua, double precoPedido, double precoMinimo, String estado, String id, double area, boolean wc, 
        String tipoNegocio, int numeroPorta){

        super (rua, precoPedido, precoMinimo, estado, id);
        this.area = area;
        this.wc = wc;
        this.tipoNegocio = tipoNegocio;
        this.numeroPorta = numeroPorta;
    }

    public Loja (Loja loja){
    	super (loja);
    	area = loja.getArea();
    	wc = loja.getWc();
    	tipoNegocio = loja.getTipoNegocio();
    	numeroPorta = loja.getNumeroPorta();
    }

    //Métodos de instância
    public double getArea (){
    	return area;
    }

    public boolean getWc (){
    	return wc;
    }

    public String getTipoNegocio (){
    	return tipoNegocio;
    }

    public int getNumeroPorta (){
    	return numeroPorta;
    }

    public void setArea (double area){
        this.area = area;
    }

    public void setWc (boolean wc){
        this.wc = wc;
    }

    public void setTipoNegocio (String tipoNegocio){
        this.tipoNegocio = tipoNegocio;
    }

    public void setNumeroPorta (int numeroPorta){
        this.numeroPorta = numeroPorta;
    }

    public Loja clone (){
    	return new Loja (this);
    }
    
    public boolean equals (Object obj){
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Loja l = (Loja) obj;
        return super.equals(l) && l.getArea() == area && l.getWc() == wc &&
               l.getTipoNegocio().equals(tipoNegocio) && l.getNumeroPorta() == numeroPorta;
    }
    
    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("Área: ").append(area).append("\n")
          .append("Tem WC? ").append(wc).append("\n")
          .append("Tipo Negócio: ").append(tipoNegocio).append("\n")
          .append("Nº Porta: ").append(numeroPorta).append("\n");
        return sb.toString();
    }
}
