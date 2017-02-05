import java.util.List;
import java.io.Serializable;
public class Apartamento extends Imovel implements Habitavel, Serializable
{
	// Variáveis de Instância
	private String tipo;
	private int areaTotal;
	private int nQuartos;
	private int nWCs;
	private int nPorta;
	private int andar;
	private boolean temGaragem;

	/**
	 * Construtor vazio
	 */
	public Apartamento()
	{
		super ();
		this.tipo = "";
		this.areaTotal = 0;
		this.nQuartos = 0;
		this.nWCs = 0;
		this.nPorta = 0;
		this.andar = 0;
		this.temGaragem = false;
	}
	
	/**
	 * Construtor por parâmetro
	 */
	
	public Apartamento (String rua, double precoPedido, double precoMinimo, String estado, String id, String tipo, 
		int areaTotal, int nQuartos, int nWCs, int nPorta, int andar, boolean temGaragem) {

		super (rua, precoPedido, precoMinimo, estado, id);
		this.tipo = getTipo();
		this.areaTotal = getAreaTotal();
		this.nQuartos = getNQuartos();
		this.nWCs = getNWCs();
		this.nPorta = getNPorta();
		this.andar = getAndar();
		this.temGaragem = getTemGaragem();
	}
	
	/**
	 * Construtor por cópia
	 */
	
	public Apartamento (Apartamento a) {
		super (a);
		this.tipo = a.tipo;
		this.areaTotal = a.areaTotal;
		this.nQuartos = a.nQuartos;
		this.nWCs = a.nWCs;
		this.nPorta = a.nPorta;
		this.andar = a.andar;
		this.temGaragem = a.temGaragem;
	}
	
	// Getters e Setters
	
	public String getTipo () {
		return this.tipo;
	}
	
	public void setTipo (String tipo) {
		this.tipo = tipo;
	}
	
	public int getAreaTotal () {
		return this.areaTotal;
	}
	
	public void setAreaTotal (int areaTotal) {
		this.areaTotal = areaTotal;
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
	
	int getNPorta () {
		return this.nPorta;
	}
	
	public void setNPorta (int nPorta) {
		this.nPorta = nPorta;
	}
	
	public int getAndar () {
		return this.andar;
	}
	
	public void setAndar (int andar) {
		this.andar = andar;
	}
	
	public boolean getTemGaragem () {
		return this.temGaragem;
	}
	
	public void setTemGaragem (boolean temGaragem) {
		this.temGaragem = temGaragem;
	}

	public Apartamento clone (){
		return new Apartamento (this);
	}
	
	public boolean equals (Object obj){
		if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Apartamento a = (Apartamento) obj;
        return super.equals(a) && a.getTipo().equals(tipo) && a.getAreaTotal() == areaTotal && 
               a.getNQuartos() == nQuartos && a.getNWCs() == nWCs && a.getNPorta() == nPorta &&
               a.getAndar() == andar && a.getTemGaragem() == temGaragem;
    }
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
        sb.append("Tipo: ").append(tipo).append("\n")
          .append("Área Total: ").append(areaTotal).append("\n")
          .append("Nº Quartos: ").append(nQuartos).append("\n")
          .append("Nº WC's: ").append(nWCs).append("\n")
          .append("Nº Porta: ").append(nPorta).append("\n")
          .append("Andar: ").append(andar).append("\n")
          .append("Tem Garagem? ").append(temGaragem).append("\n");
        return sb.toString();
	}
	/*
	public boolean possuiJardim(){
		return false;
	}
	
	int animaisDeEstimacao;
	
	public int getNAnimaisEstimacao(){
		return animaisDeEstimacao;
	}
	
	public void definirNAnimaisEstimacao(int n){
		animaisDeEstimacao = n;
	}*/
}
