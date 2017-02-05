import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Collections;
import java.io.Serializable;

public class Vendedor extends Utilizador implements Serializable    
{
    //Variáveis de Instância
    private Map<String, Imovel> emVenda;
    private Map<String, Imovel> historicoVendidos;

    //Construtores
    public Vendedor (){
        super ();
        emVenda = new HashMap<>();
        historicoVendidos = new HashMap<>();
    }

    public Vendedor(String email, String nome, String password, String morada, LocalDate dataNascimento){
        super (email, nome, password, morada, dataNascimento);
        emVenda = new HashMap<>();
        historicoVendidos = new HashMap<>();
    }

    public Vendedor (Vendedor vendedor){
        super (vendedor);
        emVenda = vendedor.getEmVenda();
        historicoVendidos = vendedor.getHistoricoVendidos();
    }

    //Métodos de instância
    public Map<String, Imovel> getEmVenda (){
        return emVenda.values()
                      .stream()
                      .collect (Collectors.toMap(i -> i.getId(), i -> i.clone()));
    }
    
    public Map<String, Imovel> getHistoricoVendidos (){
        return historicoVendidos.values()
                                .stream()
                                .collect (Collectors.toMap(i -> i.getId(), i -> i.clone()));
    }
    
    public void setEmVenda (Map<String, Imovel> emVenda){
        for (Map.Entry<String, Imovel> i : emVenda.entrySet()) {
            String k = i.getKey();
            Imovel v = i.getValue().clone();
            this.emVenda.put(k, v);
        }
    }
    
    public void setHistoricoVendidos (Map<String, Imovel> historicoVendidos){
        for (Map.Entry<String, Imovel> i : historicoVendidos.entrySet()) {
            String k = i.getKey();
            Imovel v = i.getValue().clone();
            this.historicoVendidos.put(k, v);
        }
    }

    public Vendedor clone (){
        return new Vendedor (this);
    }
    
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Vendedor o = (Vendedor) obj;
        return super.equals(o) && o.getEmVenda().equals(emVenda) && 
               o.getHistoricoVendidos().equals(historicoVendidos);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Em Venda: ").append(emVenda.toString()).append("\n")
          .append("Histórico Vendidos: ").append(historicoVendidos.toString()).append("\n");
        return sb.toString();
    }

    public void registaImovel (Imovel im){
        String estado = im.getEstado();
        if (estado.equals("em venda") || estado.equals("reservado")){
            emVenda.put(im.getId(), im);
        }
        if (estado.equals("vendido")){
            historicoVendidos.put(im.getId(), im);
        }
    }

    public void setEstado (String idImovel, String estado) throws SemAutorizacaoException{
        if (emVenda.containsKey(idImovel)){
            if (estado.equals("vendido")){
                Imovel i = emVenda.remove(idImovel);
                i.setEstado (estado);
                historicoVendidos.put (idImovel, i);
            }
            if (estado.equals("em venda") || estado.equals("reservado")){
                emVenda.get(idImovel).setEstado(estado);
            }
        }
        else if (historicoVendidos.containsKey(idImovel)){
            if (estado.equals("vendido")){
                historicoVendidos.get(idImovel).setEstado(estado);
            }
            if (estado.equals("em venda") || estado.equals("reservado")){
                Imovel i = historicoVendidos.remove(idImovel);
                i.setEstado(estado);
                emVenda.put(idImovel, i);
            }
        }
        else throw new SemAutorizacaoException ("Não pode alterar o estado deste imóvel");
    }
    //Falta o comparator
    public List<Consulta> getConsultas(){
        List<Consulta> list = new ArrayList<>();
        for (Imovel i: emVenda.values()){
            List<Consulta> l = i.getConsultas();
            for (Consulta c : l){
                list.add(c.clone());
            }
        }
        for (Imovel i: historicoVendidos.values()){
            List<Consulta> l = i.getConsultas();
            for (Consulta c : l){
                list.add(c.clone());
            }
        }
        Collections.sort(list, new ComparadorConsultas());
        int end;
        if (list.size() < 10)
            end = list.size();
        else
            end = 10;
        return list.subList(0, end);
    }

    public Set<String> getTopImoveis(int n){
        Set<String> s = new HashSet<>();
        for (Imovel i: emVenda.values()){
            if (i.getConsultas().size() > n)
                s.add(i.getId());
        }
        for (Imovel i: historicoVendidos.values()){
            if (i.getConsultas().size() > n)
                s.add(i.getId());
        }
        return s;
    }

    public List<Imovel> getImoveis (String email){
        List<Imovel> l = new ArrayList<>();
        for (Imovel i: emVenda.values()){
            i.addConsulta(email);
            l.add(i.clone());
        }
        for (Imovel i: historicoVendidos.values()){
            i.addConsulta(email);
            l.add(i.clone());
        }
        return l;
    }
    
    public void iniciaLeilao (Imovel im, int horas) throws SemAutorizacaoException{
        if (!emVenda.containsKey (im.getId())){
            throw new SemAutorizacaoException ("Não pode leiloar este imóvel");
        }
    }
}
