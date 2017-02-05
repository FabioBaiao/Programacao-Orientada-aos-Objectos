import java.util.Comparator;

public class ComparadorPreco implements Comparator<Imovel>
{
    public int compare(Imovel i1, Imovel i2){
        double p1 = i1.getPrecoPedido();
        double p2 = i2.getPrecoPedido();
        if (p1 < p2){
            return -1;
        }
        if (p1 == p2){
            return 0;
        }
        return 1;
    }
}