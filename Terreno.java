import java.util.List;
import java.io.Serializable;
public class Terreno extends Imovel implements Serializable
{
    // Variáveis de instância
    private String tipoConstrucao;
    private int diametro;
    private double maxKWH;
    private boolean esgotos;

    //Construtores
    
    public Terreno () {
        super();
        this.tipoConstrucao = "";
        this.diametro = 0;
        this. maxKWH = 0;
        this.esgotos = false;
    }

    public Terreno (String rua, double precoPedido, double precoMinimo, String estado, String id, String tipoConstrucao, 
        int diametro, double maxKWH, boolean esgotos){

        super (rua, precoPedido, precoMinimo, estado, id);
        this.tipoConstrucao = tipoConstrucao;
        this.diametro = diametro;
        this.maxKWH = maxKWH;
        this.esgotos = esgotos;
    }
    
    public Terreno (Terreno terreno){
        super (terreno);
        tipoConstrucao = terreno.getTipoConstrucao();
        diametro = terreno.getDiametro();
        maxKWH = terreno.getMaxKWH();
        esgotos = terreno.getEsgotos();
    }

    //Métodos de instância
    public String getTipoConstrucao (){
        return tipoConstrucao;
    }

    public int getDiametro (){
        return diametro;
    }

    public double getMaxKWH (){
        return maxKWH;
    }

    public boolean getEsgotos (){
        return esgotos;
    }

    public void setTipoConstrucao (String tipoConstrucao){
        this.tipoConstrucao = tipoConstrucao;
    }

    public void setDiametro (int diametro){
        this.diametro = diametro;
    }

    public void setMaxKWH (double maxKWH){
        this.maxKWH = maxKWH;
    }

    public void setEsgotos (boolean esgotos){
        this.esgotos = esgotos;
    }

    public Terreno clone (){
        return new Terreno (this);
    }
    
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Terreno o = (Terreno) obj;
        return super.equals(o) && o.getTipoConstrucao() == tipoConstrucao && 
               o.getDiametro() == diametro && o.getMaxKWH() == maxKWH && o.getEsgotos() == esgotos;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo de Construção: ").append(tipoConstrucao).append("\n")
          .append("Diâmetro: ").append(diametro).append("\n")
          .append("Máx. KWH: ").append(maxKWH).append("\n")
          .append("Esgotos: ").append(esgotos).append("\n");
        return sb.toString();
    }
}
