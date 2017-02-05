import java.util.List;
import java.io.Serializable;
public class Moradia extends Imovel implements Habitavel, Serializable
{
    // Variáveis de Instância
    private String tipo;
    private int areaI;
    private int areaTotal;
    private int areaTerrenoI;
    private int nQuartos;
    private int nWCs;
    private int nPorta;

    /**
     * Construtor vazio
     */
    public Moradia()
    {
        super ();
        this.tipo = "";
        this.areaI = 0;
        this.areaTotal = 0;
        this.areaTerrenoI = 0;
        this.nQuartos = 0;
        this.nWCs = 0;
        this.nPorta = 0;
    }
    
    /**
     * Construtor por parâmetro
     */
    
    public Moradia (String rua, double precoPedido, double precoMinimo, String estado, String id, String tipo, int areaI, 
        int areaTotal, int areaTerrenoI, int nQuartos, int nWCs, int nPorta){

        super (rua, precoPedido, precoMinimo, estado, id);
        this.tipo = tipo;
        this. areaI = areaI;
        this.areaTotal = areaTotal;
        this.areaTerrenoI = areaTerrenoI;
        this.nQuartos = nQuartos;
        this.nWCs = nWCs;
        this.nPorta = nPorta;
    }
    
    /**
     * Construtor por cópia
     */
    
    public Moradia (Moradia m) {
        super (m);
        this.tipo = m.getTipo();
        this. areaI = m.getAreaI();
        this.areaTotal = m.getAreaTotal();
        this.areaTerrenoI = m.getAreaTerrenoI();
        this.nQuartos = m.getNQuartos();
        this.nWCs = m.getNWCs();
        this.nPorta = m.getNPorta();
    }
    
    // Getters e Setters
    
    public String getTipo () {
        return this.tipo;
    }
    
    public void setTipo (String tipo) {
        this.tipo = tipo;
    }
    
    public int getAreaI () {
        return this.areaI;
    }
    
    public void setAreaI (int areaI) {
        this.areaI = areaI;
    }
    
    public int getAreaTotal () {
        return this.areaTotal;
    }
    
    public void setAreaTotal (int areaTotal) {
        this.areaTotal = areaTotal;
    }
    
    public int getAreaTerrenoI () {
        return this.areaTerrenoI;
    }
    
    public void setAreaTerrenoI (int areaTerrenoI) {
        this.areaTerrenoI = areaTerrenoI;
    }
    
    public int getNQuartos () {
        return this.nQuartos;
    }
    
    public void setNQuartos (int nQuartos) {
        this.nQuartos = nQuartos;
    }
    
    public int getNWCs () {
        return this.nWCs;
    }
    
    public void setNWCs (int nWCs) {
        this.nWCs = nWCs;
    }
    
    public int getNPorta () {
        return this.nPorta;
    }
    
    public void setNPorta (int nPorta) {
        this.nPorta = nPorta;
    }

    public Moradia clone (){
        return new Moradia (this);
    }
    
    public boolean equals (Object obj){
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Moradia m = (Moradia) obj;
        return super.equals(m) && m.getTipo().equals(tipo) &&
               m.getAreaTotal() == areaTotal && m.getAreaTerrenoI() == areaTerrenoI &&
               m.getNQuartos() == nQuartos && m.getNWCs() == nWCs && m.getNPorta() == nPorta;
    }
    
    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo: ").append(tipo).append("\n")
          .append("Área Total: ").append(areaTotal).append("\n")
          .append("Área Terreno Involvente: ").append(areaTerrenoI).append("\n")
          .append("Nº Quartos: ").append(nQuartos).append("\n")
          .append("Nº WC's: ").append(nWCs).append("\n")
          .append("Nº Porta: ").append(nPorta).append("\n");
        return sb.toString();
    }
    /*    
    public boolean possuiJardim(){
        return true;
    }
    
    int animaisDeEstimacao;
    
    public int getNAnimaisEstimacao(){
        return animaisDeEstimacao;
    }
    
    public void definirNAnimaisEstimacao(int n){
        animaisDeEstimacao = n;
    }
    */
}
