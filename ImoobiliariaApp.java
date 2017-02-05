import java.io.*;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;

public class ImoobiliariaApp
{
     // Construtor privado (não queremos instâncias!...)
    private ImoobiliariaApp() {}

    private static Imoobiliaria tab;
    // Menus da aplicação
    private static Menu menumain, menucomprador, menuvendedor;

    // Método principal
    public static void main(String[] args) {
        carregarMenus();
        tab = new Imoobiliaria();
        do {
            menumain.executa();
            switch (menumain.getOpcao()) {
                case 1: iniciarSessao();
                        break;
                case 2: fazerRegisto();
                        break;
                case 3: porTipo();
                        break;  
                case 4: porHabitaveis();
                        break;
                case 5: porMapeamento();
                        break;
                case 6: carregarDados();
                        break;
                case 7: guardarDados();
                        break;
            }
        } while (menumain.getOpcao()!=0);
        System.out.println("Até breve!...");     
    }
    
    // Métodos auxiliares
    
    private static void carregarMenus() {
        String[] ops = {"Sair",
                        "Iniciar Sessão",
                        "Fazer Registo",
        /*Sem a loja habitável*/"Consultar imovéis por tipo",
                        "Consultar imóveis habitáveis",
                        "Consultar imóveis e respetivos vendedores",
                        "Carregar Dados",
                        "Guardar Dados"
                        };

        String [] opscom = {"Terminar Sessão", "Consultar lista de imóveis favoritos",
                            "Adicionar imóvel aos favoritos",
        /*Sem a loja habitável*/"Consultar imovéis por tipo",
                           "Consultar imóveis habitáveis",
                           "Consultar imóveis e respetivos vendedores",
                           "Juntar-se ao leilão"};
                           
        String [] opsven = {"Terminar Sessão", "Registar imóvel para venda",
                            "Alterar estado de um imóvel",
                            "Ver as 10 últimas consultas a imóveis para venda",
                            "Lista dos imóveis mais consultados",
        /*Sem a loja habitável*/"Consultar imovéis por tipo",
                           "Consultar imóveis habitáveis",
                           "Consultar imóveis e respetivos vendedores",
                           "Gerir leilão"};

        menumain = new Menu(ops);
        menucomprador = new Menu(opscom);
        menuvendedor = new Menu(opsven);
    }
    
    public static final boolean DEVELOPMENT = true;
    
    private static void carregarDados() {
        System.out.print('\u000C');
        System.out.println ("--- Carregar Dados ---\n");
        Scanner scin = new Scanner(System.in);
        try {
            tab = Imoobiliaria.initApp();
        }
        catch (IOException e) {
            System.out.println("Não consegui ler os dados!\nErro de leitura.");
            scin.nextLine();
            scin.close();
            return;
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Não consegui ler os dados!\nFicheiro com formato desconhecido.");
            scin.nextLine();
            scin.close();
            return;
        }
        catch (ClassCastException e) {
            System.out.println("Não consegui ler os dados!\nErro de formato.");
            scin.nextLine();
            scin.close();
            return;
        }

        System.out.println ("Dados carregados com sucesso");
        scin.nextLine();
        scin.close();
    }
    
    private static void guardarDados(){
        System.out.print('\u000C');
        System.out.println ("--- Guardar Dados ---\n");
        Scanner scin = new Scanner(System.in);
        try {
            tab.saveApp("estado.txt");
            tab.log("log.txt", true);
        }
        catch (IOException e) {
            System.out.println("Não consegui gravar os dados!");
            scin.nextLine();
            scin.close();
            return;
        }
        
        System.out.println ("Dados guardados com sucesso");
        scin.nextLine();
        scin.close();
        return;
    }
    
    private static void iniciarSessao() {
        System.out.print('\u000C');
        System.out.println("--- Iniciar Sessão ---\n");
        Scanner scin = new Scanner(System.in);
        System.out.print("Introduza a sua conta de utilizador (email): ");
        String email = scin.nextLine();
        System.out.print("Introduza a password: ");
        String password = scin.nextLine();
        
        try {
            tab.iniciaSessao(email, password);
        } catch (SemAutorizacaoException e){
            System.out.println(e.getMessage());
            scin.nextLine();
            scin.close();
            return;
        }
        Utilizador online = tab.getOnline();
        if (online instanceof Comprador)
            do {
                menucomprador.executa();
                switch (menucomprador.getOpcao()) {
                    case 1: determinarFavoritos();
                            break;
                    case 2: adicionarFavoritos();
                            break;
                    case 3: porTipo();
                            break;
                    case 4: porHabitaveis();
                            break;
                    case 5: porMapeamento();
                            break;
                    case 6: leilaoComp();
                            break;
                    case 0: tab.fechaSessao(); 
                            break;
                    default: System.out.println("Opção Inválida!!!");
                             break;
                }
            } while (menucomprador.getOpcao()!=0);
           
        else 
            do {
                menuvendedor.executa();
                switch (menuvendedor.getOpcao()) {
                    case 1: registarImovel();
                            break;
                    case 2: alterarEstado();
                            break;
                    case 3: ultimasConsultas();
                            break;
                    case 4: maisConsultados();
                            break;
                    case 5: porTipo();
                            break;
                    case 6: porHabitaveis();
                            break;
                    case 7: porMapeamento();
                            break;
                    case 8: leilaoVend();
                            break;
                    case 0: tab.fechaSessao(); 
                            break;
                    default: System.out.println("Opção Inválida!!!");
                             break;
                }
            } while (menuvendedor.getOpcao()!=0);
    }
    
    private static void fazerRegisto() {
        System.out.print('\u000C');
        System.out.println("--- Registo ---\n");
        Scanner scin = new Scanner(System.in);
        System.out.print("Conta de utilizador (email): ");
        String email = scin.nextLine();
        System.out.print("Password: ");
        String password = scin.nextLine();
        System.out.print("Nome: ");
        String nome = scin.nextLine();
        System.out.print("Morada: ");
        String morada = scin.nextLine();
        System.out.print("Data de nascimento (aaaa-mm-dd): ");
        String aux = scin.nextLine();
        String[] elems = aux.split("-");
        LocalDate data;
        if (elems.length < 3){
            data = LocalDate.now();
        }
        else {
            data = LocalDate.of(Integer.parseInt(elems[0]), Integer.parseInt(elems[1]), Integer.parseInt(elems[2]));
        }
        
        System.out.print("Deseja registar-se como (1) vendedor ou (2) comprador? ");
        int escolha = 0;
        try{
            escolha = Integer.parseInt(scin.nextLine());
        }
        catch (NumberFormatException e){}

        if (escolha == 1) {
            try {
                tab.registarUtilizador((Utilizador) new Vendedor(email, nome, password, morada, data));}
            catch (UtilizadorExistenteException e) {
                System.out.println(e.getMessage());
                scin.nextLine();
                scin.close();
                return;}
        }
        else if (escolha == 2) {
                 try {
                     tab.registarUtilizador((Utilizador) new Comprador(email, nome, password, morada, data));}
                 catch (UtilizadorExistenteException e) {
                     System.out.println(e.getMessage());
                     scin.nextLine();
                     scin.close();
                     return;}
        }
        else {System.out.println("Opção Inválida!!");
              scin.nextLine();
              scin.close();
              return;}

        System.out.println ("Utilizador registado com sucesso");
        scin.nextLine();
        scin.close();
    }

    private static void porTipo(){
        System.out.print('\u000C');
        Scanner scin = new Scanner(System.in);
        System.out.println ("--- Tipo de imóvel ---\n");
        System.out.println(" 1. Moradia\n 2. Terreno\n 3. Apartamento\n 4. Loja\n 5. LojaHabitavel");
        System.out.print("Opção: ");
        int op;
        try {
            op = Integer.parseInt(scin.nextLine());
        }
        catch (NumberFormatException e){
            op = 0;
        }
        if (op > 5 || op < 1) {
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        String tipo = "";
        switch (op) {
            case 1: tipo = "Moradia";
                    break;
            case 2: tipo = "Terreno";
                    break;
            case 3: tipo = "Apartamento";
                    break;
            case 4: tipo = "Loja";
                    break;
            case 5: tipo = "LojaHabitavel";
                    break;
            }
        System.out.print("Preço Máximo: ");
        int preco;
        try{
            preco = Integer.parseInt(scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;            
        }
        List<Imovel> l = new ArrayList<>();
        l = tab.getImovel(tipo, preco);
        for (Imovel im : l)
            System.out.println("\nImóvel: " + im.getId() + "\nLocalização: " + im.getRua() + "\nPreço: " + im.getPrecoPedido());
            
        scin.nextLine();
        scin.close();
    }

    private static void porHabitaveis(){
        System.out.print('\u000C');
        Scanner scin = new Scanner(System.in);
        System.out.println("--- Imóveis Habitáveis ---\n");
        System.out.print("Preço Máximo: ");
        int price;
        try{
            price = Integer.parseInt(scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        List<Habitavel> imoveis = new ArrayList<>();
        imoveis = tab.getHabitaveis(price);
        for(Habitavel im : imoveis){
            Imovel i = (Imovel) im;
            System.out.println("\nImóvel: " + i.getId() + "\n Localização: " + i.getRua() + "\n Preço: " + i.getPrecoPedido());
        }
        scin.nextLine();
        scin.close();
    }

    private static void porMapeamento(){
        System.out.print('\u000C');
        System.out.println ("---- Mapeamento Imóveis-Vendedor ---\n");
        Scanner scin = new Scanner(System.in);
        Map<Imovel, Vendedor> m = tab.getMapeamentoImoveis();
        for(Imovel im : m.keySet()){
            System.out.println("Imóvel: " + im.getId() + "\n Localização: " + im.getRua() + "\n Preço: " + im.getPrecoPedido() + "\n Vendedor: " 
                + m.get(im).getNome()+"\n");
        }
        scin.nextLine();
        scin.close();
    }
    
    private static void determinarFavoritos(){
        System.out.print('\u000C');
        System.out.println ("--- Imóveis Favoritos ---\n");
        Scanner scin = new Scanner(System.in);
        TreeSet<Imovel> favoritos = new TreeSet<>();
        try {
            favoritos = tab.getFavoritos();}
        catch (SemAutorizacaoException e) {
            System.out.println(e.getMessage());
            scin.nextLine();
            scin.close();
            return;
        }
        for (Imovel im : favoritos)
            System.out.println("Imóvel: " + im.getId() + "\nLocalização: " + im.getRua() + "\nPreço: " + im.getPrecoPedido()+"\n");

        scin.nextLine();
        scin.close();
    }
    
    private static void adicionarFavoritos(){
        System.out.print('\u000C');
        System.out.println ("--- Marcar Imóvel como Favorito ---\n");
        Scanner scin = new Scanner(System.in);
        System.out.print("Identifique o imóvel a adicionar aos favoritos: ");
        String idImovel = scin.nextLine();
        try {
            tab.setFavorito(idImovel);}
        catch (SemAutorizacaoException e1) {
            System.out.println(e1.getMessage());
            scin.nextLine();
            scin.close();
            return;
        }
        catch (ImovelInexistenteException e2) {
            System.out.println(e2.getMessage());
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.println("Adicionado!!");
        scin.nextLine();
        scin.close();
    }
    
    private static void registarImovel(){
        System.out.print('\u000C');
        System.out.println ("--- Registar Imóvel ---\n");
        Scanner scin = new Scanner(System.in);
        System.out.print("Rua: ");
        String rua = scin.nextLine();
        double precoPedido;
        double precoMinimo;
        try{ 
            System.out.print("Preco pedido: ");
            precoPedido = Double.parseDouble(scin.nextLine());
            System.out.print("Preco minimo: ");
            precoMinimo = Double.parseDouble(scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.print("Estado (em venda/reservado/vendido): ");
        String estado = scin.nextLine();
        if (!estado.equals("em venda") && !estado.equals("reservado") && !estado.equals("vendido")){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.print("ID: ");
        String id = scin.nextLine();
        System.out.println(" 1. Moradia\n 2. Terreno\n 3. Apartamento\n 4. Loja\n 5. Loja Habitável");
        System.out.print("Opção: ");
        int op;
        try {
            op = Integer.parseInt(scin.nextLine());
        }
        catch (NumberFormatException e){
            op = 0;
        }
        if (op > 5 || op < 1) {
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        switch (op) {
            case 1: registaMoradia(rua, precoPedido, precoMinimo, estado, id);
                    break;
            case 2: registaTerreno(rua, precoPedido, precoMinimo, estado, id);
                    break;
            case 3: registaApartamento(rua, precoPedido, precoMinimo, estado, id);
                    break;
            case 4: registaLoja(rua, precoPedido, precoMinimo, estado, id);
                    break;
            case 5: registaLojaHabitavel(rua, precoPedido, precoMinimo, estado, id);
                    break;
            }
        scin.nextLine();
        scin.close();
    }
    
    private static void registaMoradia(String rua, double precoPedido, double precoMinimo, String estado, String id){
        Scanner scin = new Scanner(System.in);
        System.out.print ("Tipo (isolada/geminada/banda/gaveto): ");
        String tipo = scin.nextLine();
        int areaI, areaTotal, areaTerrenoI, nQuartos, nWCs, nPorta;
        try {
            System.out.print ("Area de implantação: ");
            areaI = Integer.parseInt (scin.nextLine());
            System.out.print ("Area total: ");
            areaTotal = Integer.parseInt (scin.nextLine());
            System.out.print ("Area de terreno envolvente: ");
            areaTerrenoI = Integer.parseInt (scin.nextLine());
            System.out.print ("Número de quartos: ");
            nQuartos = Integer.parseInt (scin.nextLine());
            System.out.print ("Número de WCs: ");
            nWCs = Integer.parseInt (scin.nextLine());
            System.out.print ("Número da porta: ");
            nPorta = Integer.parseInt (scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        try{
            tab.registaImovel (new Moradia (rua, precoPedido, precoMinimo, estado, id, tipo, areaI, areaTotal, areaTerrenoI, nQuartos, nWCs, nPorta));
        }
        catch (SemAutorizacaoException | ImovelExisteException e){
            System.out.println (e.getMessage());
            scin.close();
            return;
        }
        System.out.println ("Moradia criada com sucesso");
        scin.close();
    }
    
    private static void registaTerreno(String rua, double precoPedido, double precoMinimo, String estado, String id){
        Scanner scin = new Scanner(System.in);
        System.out.print ("Tipo de construção (Habitação/Armazém): ");
        String tipoConstrucao = scin.nextLine();
        int diametro;
        double maxKWH;
        try {
            System.out.print ("Diametro das canalizações (mm): ");
            diametro = Integer.parseInt (scin.nextLine());
            System.out.print ("kWh máximos suportados pela rede elétrica: ");
            maxKWH = Double.parseDouble (scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.print ("Com acesso à rede de esgotos (true/false): ");
        boolean esgotos = Boolean.parseBoolean (scin.nextLine());
        try{
            tab.registaImovel (new Terreno (rua, precoPedido, precoMinimo, estado, id, tipoConstrucao, diametro, maxKWH, esgotos));
        }
        catch (SemAutorizacaoException | ImovelExisteException e){
            System.out.println (e.getMessage());
            scin.close();
            return;
        }
        System.out.println ("Terreno criado com sucesso");
        scin.close();
    }
    
    private static void registaApartamento(String rua, double precoPedido, double precoMinimo, String estado, String id){
        Scanner scin = new Scanner(System.in);
        System.out.print ("Tipo (Simplex/Duplex/Triplex): ");
        String tipo = scin.nextLine();
        int areaTotal, nQuartos, nWCs, nPorta, andar;
        try {
            System.out.print ("Area total: ");
            areaTotal = Integer.parseInt (scin.nextLine());
            System.out.print ("Número de quartos: ");
            nQuartos = Integer.parseInt (scin.nextLine());
            System.out.print ("Número de WCs: ");
            nWCs = Integer.parseInt (scin.nextLine());
            System.out.print ("Número da porta: ");
            nPorta = Integer.parseInt (scin.nextLine());
            System.out.print ("Andar: ");
            andar = Integer.parseInt (scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.print ("Tem garagem (true/false): ");
        boolean temGaragem = Boolean.parseBoolean (scin.nextLine());
        try{
            tab.registaImovel (new Apartamento (rua, precoPedido, precoMinimo, estado, id, tipo, areaTotal, nQuartos, nWCs, nPorta, andar, temGaragem));
        }
        catch (SemAutorizacaoException | ImovelExisteException e){
            System.out.println (e.getMessage());
            scin.close();
            return;
        }
        System.out.println ("Apartamento criado com sucesso");
        scin.close();
    }
    
    private static void registaLoja(String rua, double precoPedido, double precoMinimo, String estado, String id){
        Scanner scin = new Scanner(System.in);
        System.out.print ("Tipo de negócio: ");
        String tipo = scin.nextLine();
        int areaTotal, nPorta;
        try {
            System.out.print ("Area total: ");
            areaTotal = Integer.parseInt (scin.nextLine());
            System.out.print ("Número da porta: ");
            nPorta = Integer.parseInt (scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.print ("Tem WC (true/false): ");
        boolean wc = Boolean.parseBoolean (scin.nextLine());
        try{
            tab.registaImovel (new Loja (rua, precoPedido, precoMinimo, estado, id, areaTotal, wc, tipo, nPorta));
        }
        catch (SemAutorizacaoException | ImovelExisteException e){
            System.out.println (e.getMessage());
            scin.close();
            return;
        }
        System.out.println ("Loja criada com sucesso");
        scin.close();
    }
    
    private static void registaLojaHabitavel(String rua, double precoPedido, double precoMinimo, String estado, String id){
        Scanner scin = new Scanner(System.in);
        System.out.print ("Tipo (Simplex/Duplex/Triplex): ");
        String tipo = scin.nextLine();
        int areaTotal, nQuartos, nWCs, nPorta, andar;
        try {
            System.out.print ("Area total (apartamento): ");
            areaTotal = Integer.parseInt (scin.nextLine());
            System.out.print ("Número de quartos (apartamento): ");
            nQuartos = Integer.parseInt (scin.nextLine());
            System.out.print ("Número de WCs (apartamento): ");
            nWCs = Integer.parseInt (scin.nextLine());
            System.out.print ("Número da porta (apartamento): ");
            nPorta = Integer.parseInt (scin.nextLine());
            System.out.print ("Andar (apartamento): ");
            andar = Integer.parseInt (scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.print ("Tem garagem (apartamento)(true/false): ");
        boolean temGaragem = Boolean.parseBoolean (scin.nextLine());
        Apartamento a = new Apartamento (rua, precoPedido, precoMinimo, estado, id, tipo, areaTotal, nQuartos, nWCs, nPorta, andar, temGaragem);
        try {
            System.out.print ("Area total (Loja): ");
            areaTotal = Integer.parseInt (scin.nextLine());
            System.out.print ("Número da porta (Loja): ");
            nPorta = Integer.parseInt (scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.print ("Tem WC (Loja) (true/false): ");
        boolean wc = Boolean.parseBoolean (scin.nextLine());
        try{
            tab.registaImovel (new LojaHabitavel (rua, precoPedido, precoMinimo, estado, id, areaTotal, wc, tipo, nPorta, a));
        }
        catch (SemAutorizacaoException | ImovelExisteException e){
            System.out.println (e.getMessage());
            scin.close();
            return;
        }
        System.out.println ("Loja Habitável criada com sucesso");
        scin.close();
    }

    private static void alterarEstado(){
        System.out.print('\u000C');
        System.out.println ("--- Alterar Estado de Imóvel ---\n");
        Scanner scin = new Scanner(System.in);
        System.out.print("Identifique o imóvel cujo estado será alterado: ");
        String id = scin.nextLine();
        System.out.print("Novo estado: ");
        String estado = scin.nextLine();
        try {
            tab.setEstado(id, estado);}
        catch (SemAutorizacaoException | ImovelInexistenteException | EstadoInvalidoException e1) {
            System.out.println(e1.getMessage());
        }
        scin.nextLine();
        scin.close();
    }
    
    private static void ultimasConsultas(){
        System.out.print('\u000C');
        System.out.println ("--- Últimas 10 Consultas ---\n");
        Scanner scin = new Scanner(System.in);
        List<Consulta> ultimas = new ArrayList<>();
        try {
            ultimas = tab.getConsultas();}
        catch (SemAutorizacaoException e1) {
            System.out.println(e1.getMessage());
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.println("Últimas consultas:");
        for (Consulta c : ultimas)
            System.out.println("Data: " + c.getData() + "\n Email: " + c.getEmail()+"\n");
            
        scin.nextLine();
        scin.close();
    }
    
    private static void maisConsultados(){
        System.out.print('\u000C');
        System.out.println ("--- Imóveis mais Consultados ---\n");
        Scanner scin = new Scanner(System.in);
        System.out.print("Introduza o número minimo de consultas: ");
        int n = Integer.parseInt(scin.nextLine());
        Set<String> top = new HashSet<>();
        top = tab.getTopImoveis(n);
        System.out.println("\nImóveis mais consultados:");
        for (String s : top)
            System.out.println("Imóvel: " + s); 
            
        scin.nextLine();
        scin.close();
    }
    
    private static void leilaoVend(){
        System.out.print('\u000C');
        System.out.println ("--- Gerir Leilões ---\n");
        Scanner scin = new Scanner(System.in);
        System.out.print("Criar(1) ou encerrar(2) leilão? ");
        int op;
        try{
            op = Integer.parseInt (scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        if (op == 1){
            System.out.print ("Indique o id do imóvel que pretende leiloar: ");
            String id = scin.nextLine();
            System.out.print ("Quantas horas pretende deixar o leilão aberto? ");
            int horas;
            try{
                horas = Integer.parseInt (scin.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Opção Inválida!!\n");
                scin.nextLine();
                scin.close();
                return;
            }
            try{
                Imovel i = tab.getImoveis().get(id);
                tab.iniciaLeilao(i, horas);
            }
            catch (SemAutorizacaoException e){
                System.out.println (e.getMessage());
                scin.nextLine();
                scin.close();
                return;
            }
            System.out.println ("Leilão iniciado (à espera de compradores)");
        }
        else if (op == 2){
            Comprador c = tab.encerraLeilao();
            System.out.println ("Leilão encerrado");
            if (c != null){
                System.out.println ("Vencedor: " + c.getNome() + "\n Preço: " + c.getPrecoFinal());
            }
        }
        else {
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        scin.nextLine();
        scin.close();
    }
    
    private static void leilaoComp(){
        System.out.print('\u000C');
        System.out.println ("--- Entrar no Leilão ---\n");
        Scanner scin = new Scanner(System.in);
        double limite, incremento, minutos;
        try{
            System.out.print("Defina um limite de licitação: ");
            limite = Double.parseDouble (scin.nextLine());
            System.out.print("Quanto está disposto a incrementar? ");
            incremento = Double.parseDouble (scin.nextLine());
            System.out.print("Defina um intervalo entre licitações: ");
            minutos = Double.parseDouble (scin.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Opção Inválida!!\n");
            scin.nextLine();
            scin.close();
            return;
        }
        Comprador online = (Comprador) tab.getOnline();
        try{
            tab.adicionaComprador(online.getEmail(), limite, incremento, minutos);
        }
        catch (LeilaoTerminadoException e){
            System.out.println(e.getMessage());
            scin.nextLine();
            scin.close();
            return;
        }
        System.out.println("Adicionado ao Leilão");
        scin.nextLine();
        scin.close();
        return;
    }
}
