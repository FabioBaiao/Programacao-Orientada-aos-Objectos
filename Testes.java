import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

/**
 * The test class Testes.
 *
 * Ã‰ necessÃ¡rio completar os teste, colocando os parÃ¢metros nos construtores.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Testes
{
    private Imoobiliaria imo;
    private Vendedor v;
    private Comprador c;
    private Moradia m;
    private Apartamento a;
    private Loja l;
    private LojaHabitavel lh;
    private Terreno t;

    /**
     * Teste principal
     */
    @Test
    public void mainTest() {
        imo = new Imoobiliaria();
        //Testes a registar utilizador
        try {
            v = new Vendedor("vendedor1", "Ricardo", "12345", "Rua K", LocalDate.now());
            c = new Comprador("comprador1", "Maria", "abcde", "Rua J", LocalDate.now());
            imo.registarUtilizador (v);
            imo.registarUtilizador (v);
        }
        catch (Exception e){
            fail();
        }
        try {
            Vendedor v1 = v.clone();
            imo.registarUtilizador (v1);
            fail();
        }
        catch (UtilizadorExistenteException e){
        
        }
        catch (Exception e){
            fail();
        }
        //Testes a iniciar sessão e fechar sessão
        try{
            String email = v.getEmail();
            String password = v.getPassword();
            imo.iniciaSessao (email, password);
            imo.fechaSessao();
            email = c.getEmail();
            password = c.getPassword();
            imo.iniciaSessao(email, password);
            imo.fechaSessao();
        }
        catch (Exception e){
            fail();
        }
        try{
            imo.iniciaSessao ("","");
            fail();
        }
        catch (SemAutorizacaoException e){
            
        }
        catch (Exception e){
            fail();
        }
        //Testes a  resgistar imóvel
        try{
            t = new Terreno("Rua K", 42000, 38000, "em venda", "tr", "Habitação", 90, 900, true);
            imo.registaImovel (t);
            fail();
        }
        catch (SemAutorizacaoException e){
        
        }
        catch (Exception e){
            fail();
        }
        try{
            String email = c.getEmail();
            String password = c.getPassword();
            imo.iniciaSessao (email, password);
            imo.registaImovel (t);
            fail();
        }
        catch (SemAutorizacaoException e){
            imo.fechaSessao();
        }
        catch (Exception e){
            fail();
        }
        try {
            String email = v.getEmail();
            String password = v.getPassword();
            imo.iniciaSessao(email, password);
            imo.registaImovel (t);
            Imovel t1 = t.clone();
            imo.registaImovel (t1);
            fail();
        }
        catch (ImovelExisteException e){
            imo.fechaSessao();
        }
        catch (Exception e){
            fail();
        }
        //Testes a get consultas
        try{
            List<Consulta> l = imo.getConsultas();
            fail();
        }
        catch (SemAutorizacaoException e){
            
        }
        catch (Exception e){
            fail();
        }
        try {
            String email = c.getEmail();
            String password = c.getPassword();
            imo.iniciaSessao (email, password);
            List<Consulta> l = imo.getConsultas ();
            fail();
        }
        catch (SemAutorizacaoException e){
            imo.fechaSessao();
        }
        catch (Exception e){
            fail();
        }
        //Pôr mais para baixo
        try{
            String email = v.getEmail();
            String password = v.getPassword();
            imo.iniciaSessao (email, password);
            List<Consulta> l = imo.getConsultas ();
            assertTrue (l.size() <= 10);
            imo.fechaSessao();
        }
        catch (Exception e){
            fail();
        }
        //Testes a set estado
        try{
            String id = t.getId();
            imo.setEstado (id, "em venda");
            fail();
        }
        catch (SemAutorizacaoException e){
           
        }
        catch (Exception e){
            fail();
        }
        
        
        
        
        
        
        /*
        try {
            imo.iniciaSessao("",null);
			fail();
        } catch(SemAutorizacaoException e) {
            
        } catch(Exception e) {
            fail();
        }
        
        try {
            v = new Vendedor("vendedor1", "Ricardo", "12345", "Rua K", LocalDate.now());  // Preencher parÃ¢metros do construtor
            imo.registarUtilizador(v);
        } catch(Exception e) {
            fail();
        }
        
        String email = v.getEmail();
        String password = v.getPassword();
        
        try {
            imo.iniciaSessao(email, password);
        } catch(Exception e) {
            fail();
        }
        
        t = new Terreno("Rua K", 42000, 38000, "em venda", "tr", "Habitação", 90, 900, true);  // Preencher parÃ¢metros do construtor
        try {
            imo.registaImovel(t);
        } catch (Exception e) {
            fail();
        }
            
        int s = imo.getImovel("Terreno", Integer.MAX_VALUE).size();
        assertTrue(s>0);
        Set<String> ids = imo.getTopImoveis(0);
        assertTrue(ids.contains(t.getId()));
        assertTrue(imo.getMapeamentoImoveis().keySet().contains(t));
        try {
            assertTrue(imo.getConsultas().size()>0);
        } catch(Exception e) {
            fail();
        }
        
        imo.fechaSessao();
        Comprador c = new Comprador("comprador1", "Maria", "abcde", "Rua J", LocalDate.now());// Preencher parÃ¢metros do construtor
        try {
            imo.registarUtilizador(c);
        } catch(Exception e) {
            fail();
        }
        email = c.getEmail();
        password = c.getPassword();
        try {
            imo.iniciaSessao(email, password);
            imo.setFavorito(t.getId());
            assertTrue(imo.getFavoritos().contains(t));
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }
        imo.fechaSessao();
        Comprador c1 = c.clone();
        assertTrue (c1.equals(c));
        try{
            imo.registarUtilizador (c1);
            fail();
        }
        catch (UtilizadorExistenteException e){
            
        }
        try {
            imo.registaImovel (t);
            fail();
        }
        catch (SemAutorizacaoException | ImovelExisteException e){
            
        }
        */
        
    }
    
}