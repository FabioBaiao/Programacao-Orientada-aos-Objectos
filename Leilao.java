import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
public class Leilao implements Serializable
{
    private Imovel imovel;
    private int minutos;
    private double preco;
    private Comprador vencedor;
    private List<Comprador> compradores;

    public Leilao (Imovel im, int horas){
        imovel = im.clone();
        minutos = horas * 60;
        preco = 0;
        compradores = new ArrayList<>();
        vencedor = null;
    }

    public void adicionaComprador (Comprador comprador){
        compradores.add(comprador);
    }

    public Comprador encerraLeilao (){
        for (int i = 0; i < minutos; i++){
            for (Comprador c: compradores){
                if (c.getMinutosRest() == 0){
                    if (c != vencedor){
                        if (c.getLimite() <= preco){
                            c.setMinutosRest(-1);
                        }
                        else{
                            preco += c.getIncrementos();
                            vencedor = c;
                            c.resetMinutosRest();
                        }
                    }
                }
                else
                    c.decMinutosRest();
            }
        }
        if (imovel.getPrecoMinimo() <= preco){
            vencedor.setPrecoFinal(preco);
            return vencedor;
        }
        return null;
    }
    
    void setImovel (Imovel imovel) {
        this.imovel = imovel.clone();
    }
    
    void setMinutos (int minutos) {
        this.minutos = minutos;
    }
    
    void setPreco (double preco) {
        this.preco = preco;
    }
    
    void setVencedor (Comprador vencedor) {
        this.vencedor = new Comprador (vencedor);
    }
    
    void setCompradores (List<Comprador> compradores) {
        for (Comprador c : compradores) {
            this.compradores.add(c.clone());
        }
    }
    
    public String toString(){
		StringBuilder sb = new StringBuilder();
        sb.append("Imóvel ").append("\n").append(imovel.toString()).append("\n")
          .append("Minutos: ").append(minutos).append("\n")
          .append("Preço: ").append(preco).append("\n")
          .append("Vencedor: ").append(vencedor.toString()).append("\n")
          .append("Compradores: ").append(compradores.toString()).append("\n");
        return sb.toString();
	}
}

