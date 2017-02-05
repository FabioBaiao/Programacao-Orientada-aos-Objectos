import java.util.Comparator;

public class ComparadorConsultas implements Comparator<Consulta>
{
     public int compare (Consulta m ,Consulta a) {
            if (m.getData().compareTo(a.getData())>0){
                return 1;
            }
            if (m.getData().compareTo(a.getData())==0){
                return 0;
            }
            else return -1;
     }
}
