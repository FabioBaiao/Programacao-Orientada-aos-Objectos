import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.io.*;

public class Imoobiliaria implements Serializable
{
    //Variáveis de instância
    private Map<String, Utilizador> utilizadores;
    private Map<String, Imovel> imoveis;
    private Utilizador online;
    
    //Construtores
    public Imoobiliaria (){
        utilizadores = new HashMap<>();
        imoveis = new HashMap<>();
        online = null;
    }
    
    public Imoobiliaria (Map<String, Utilizador> utilizadores, Map<String, Imovel> imoveis, Utilizador online){
        this.utilizadores = new HashMap<>();
        this.imoveis = new HashMap<>();
        setUtilizadores(utilizadores);
        setImoveis(imoveis);
        this.online = online;
    }

    public Imoobiliaria (Imoobiliaria i){
        this (i.getUtilizadores(), i.getImoveis(), i.getOnline());
    }

    //Métodos de Instância
    public Map<String, Utilizador> getUtilizadores(){
        Map<String, Utilizador> ut = new HashMap<>();
        for (Map.Entry<String, Utilizador> p : utilizadores.entrySet()){
            String k = p.getKey();
            Utilizador u = p.getValue().clone();
            ut.put(k,u);
        }
        return ut;
    }
    
    public Map<String, Imovel> getImoveis(){
        Map<String, Imovel> im = new HashMap<>();
        for (Map.Entry<String, Imovel> p : imoveis.entrySet()){
            String k = p.getKey();
            Imovel i = p.getValue().clone();
            im.put(k,i);
        }
        return im;
    }

    public Utilizador getOnline (){
        if (online == null){
            return null;
        }
        return online.clone();
    }
    
    public void setUtilizadores (Map<String, Utilizador> utilizadores){
        for (Map.Entry<String, Utilizador> u : utilizadores.entrySet()) {
            String k = u.getKey();
            Utilizador v = u.getValue().clone();
            this.utilizadores.put(k, v);
        }
    }
    
    public void setImoveis (Map<String, Imovel> imoveis){
        for (Map.Entry<String, Imovel> i : imoveis.entrySet()) {
            String k = i.getKey();
            Imovel v = i.getValue().clone();
            this.imoveis.put(k, v);
        }
    }

    public void setOnline (Utilizador online){
        this.online = online.clone();
    }

    public Imoobiliaria clone(){
        return new Imoobiliaria (this);
    }
    
    public boolean equals (Object obj){
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Imoobiliaria i = (Imoobiliaria) obj;
        return i.getUtilizadores().equals(utilizadores) && 
               i.getImoveis().equals(imoveis) && i.getOnline().equals(online);
    }
    
    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("Utilizadores:\n").append(utilizadores.toString()).append("\n").append(imoveis.toString())
                .append("\n");
        return sb.toString();
    }

    public static Imoobiliaria initApp() throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream("estado.txt"));
        Imoobiliaria i = (Imoobiliaria) ois.readObject();
        ois.close();
        return i;
    }   
 
    /**
    * Registar um utilizador, quer vendedor, quer comprador.
    * @param utilizador
    */
    public void registarUtilizador (Utilizador utilizador) throws UtilizadorExistenteException{
        if (utilizadores.containsKey(utilizador.getEmail()))
            throw new UtilizadorExistenteException("Utilizador já se encontra registado");
        
        utilizadores.put(utilizador.getEmail(), utilizador.clone());
    }

    /**
    * Validar o acesso à aplicação utilizando as credenciais (email e password).
    * @param email
    * @param password
    */
    public void iniciaSessao (String email, String password) throws SemAutorizacaoException{
        Utilizador u = utilizadores.get(email); 
        if (u == null) 
            throw new SemAutorizacaoException("Utilizador não registado");

        if (!u.getPassword().equals(password)) 
            throw new SemAutorizacaoException("Password incorreta");
        
        online = u;
    }
    
     public void fechaSessao (){
        online = null;
    }
    //Vendedores
    /**
    * Colocar um imóvel à venda.
    * @param im
    */
    public void registaImovel (Imovel im) throws ImovelExisteException, SemAutorizacaoException{
        if (online == null || !(online instanceof Vendedor)){
            throw new SemAutorizacaoException ("Utilizador sem autorização");
        }
        if (imoveis.containsKey(im.getId())){
            throw new ImovelExisteException ("Imóvel já existe");
        }
        Imovel i = im.clone();
        imoveis.put(im.getId(), i);
        ((Vendedor) online).registaImovel(i);
    }
    /**
    * Visualizar uma lista com as datas (e emails, caso exista essa informação) das 10 últimas consultas aos imóveis que tem para venda
    * @return
    */
    //Falta o comparator
    public List<Consulta> getConsultas () throws SemAutorizacaoException{
        return ((Vendedor) online).getConsultas();
    }
    /**
    * Alterar o estado de um imóvel, de acordo com as acções feitas sobre ele
    * @param idImovel
    * @param estado
    */
    public void setEstado (String idImovel, String estado) throws ImovelInexistenteException, SemAutorizacaoException, EstadoInvalidoException{
        if (!imoveis.containsKey(idImovel)){
            throw new ImovelInexistenteException ("Imovel não existe");
        }
        if (online == null || !(online instanceof Vendedor)){
            throw new SemAutorizacaoException ("Utilizador sem autorização");
        }
        if (!estado.equals("vendido") || !estado.equals("reservado") || !estado.equals("em venda")){
            throw new EstadoInvalidoException ("Estado inválido");
        }

        ((Vendedor) online).setEstado (idImovel, estado);        
    }
    /**
    * Obter um conjunto com os códigos dos seus imóveis mais consultados (ou seja, com mais de N consultas).
    * @param n
    * @return
    */
    //Não sei se é preciso exceção
    public Set<String> getTopImoveis(int n){
        if (online != null && online instanceof Vendedor){
            return ((Vendedor) online).getTopImoveis(n);
        }
        return null;
    }    
    //Todos os utilizadores
    /**
    * Consultar a lista de todos os imóveis de um dado tipo (Terreno, Moradia, etc.) e até um certo preço.
    * @param classe
    * @param preco
    * @return
    */
    public List<Imovel> getImovel (String classe, int preco){
        List<Imovel> l = new ArrayList<>();
        String email = null;
        if (online != null){
            email = online.getEmail();
        }
        for (Imovel i: imoveis.values()){
            if (i.getClass().getName().equals(classe) && i.getPrecoPedido() < preco){
                i.addConsulta (email);
                l.add (i.clone());
            }
        }
        return l;
    }
    /**
    * Consultar a lista de todos os imóveis habitáveis (até um certo preço).
    * @param preco
    * @return
    */
    public List<Habitavel> getHabitaveis (int preco){
        List<Habitavel> l = new ArrayList<>();
        String email = null;
        if (online != null){
            email = online.getEmail();
        }
        for (Imovel i: imoveis.values()){
            if (i instanceof Habitavel && i.getPrecoPedido() < preco){
                i.addConsulta (email);
                l.add((Habitavel) i.clone());
            }
        }
        return l;
    }   
    /**
    * Obter um mapeamento entre todos os imóveis e respectivos vendedores.
    * @return
    */
    public Map<Imovel, Vendedor> getMapeamentoImoveis (){
        Map<Imovel, Vendedor> m = new HashMap<>();
        String email = null;
        if (online != null){
            email = online.getEmail();
        }
        for (Utilizador u: utilizadores.values()){
            if (u instanceof Vendedor){
                List<Imovel> l = ((Vendedor) u).getImoveis(email);
                for (Imovel i: l){
                    m.put(i, (Vendedor) u.clone());
                }
            }
        }
        return m;
    }
    //Compradores registados
    /**
    * Marcar um imóvel como favorito.
    * @param idImovel
    */
    public void setFavorito (String idImovel) throws ImovelInexistenteException, SemAutorizacaoException{
        if (!imoveis.containsKey(idImovel)){
            throw new ImovelInexistenteException ("Imóvel não existe");
        }
        if (online == null || !(online instanceof Comprador)){
            throw new SemAutorizacaoException ("Utilizador sem autorização");
        }
        Imovel i = imoveis.get(idImovel);
        ((Comprador) online).setFavorito(i);
    }
    /**
    * Consultar imóveis favoritos ordenados por preço.
    * @return
    */
    
    public TreeSet<Imovel> getFavoritos () throws SemAutorizacaoException{
        if (online == null || !(online instanceof Comprador)){
            throw new SemAutorizacaoException ("Utilizador sem autorização");
        }
        String email = online.getEmail();
        List<Imovel> l = ((Comprador) online).getFavoritosConsulta(email);
        TreeSet<Imovel> s = new TreeSet<>(new ComparadorPreco());
        for (Imovel i: l){
            s.add(i);
        }
        return s;
    }

    public void saveApp (String nomeFicheiro) throws FileNotFoundException, IOException{
        ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(nomeFicheiro));
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
    
    public void log(String f, boolean ap) throws IOException{
        FileWriter fw = new FileWriter(f, ap);
        fw.write("\n---- LOG - LOG ---- \n");
        fw.write(this.toString());
        fw.write("\n---- LOG - LOG ---- \n");
        fw.flush();
        fw.close();
    }
    
    private Leilao leilao;

    /**
    * Abertura de leilão.
    * @param im
    * @param horas
    */
    public void iniciaLeilao (Imovel im, int horas) throws SemAutorizacaoException{
        if (online == null || !(online instanceof Vendedor)){
            throw new SemAutorizacaoException ("Utilizador sem autorização");
        }
        ((Vendedor) online).iniciaLeilao(im, horas); //Verifica se o imóvel pertence ao vendedor

        leilao = new Leilao (im, horas);
    }
    /**
    * Adicionar comprador ao leilão.
    * @param idComprador
    * @param limite
    * @param incrementos
    * @param minutos
    */

    public void adicionaComprador (String idComprador, double limite, double incrementos, double minutos) throws LeilaoTerminadoException{
        if (leilao == null){
            throw new LeilaoTerminadoException ("Leilão terminado");
        }
        Comprador c = (Comprador) utilizadores.get(idComprador).clone();
        c.adicionaInfoLeilao (limite, incrementos, minutos);
        leilao.adicionaComprador (c);
    }
    /**
    * Encerrar um leilão
    * @return
    */
    public Comprador encerraLeilao (){
        if (leilao == null || !(online instanceof Vendedor))
            return null;
            
        Comprador c = leilao.encerraLeilao();
        leilao = null;
        return c;
    }
}
