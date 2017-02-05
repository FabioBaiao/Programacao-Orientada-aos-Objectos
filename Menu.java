/**
 * Esta classe implementa um menu em modo texto.
 * 
 * @author Josá Creissac Campos 
 * @version v2.0 (20150601)
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    // variáveis de instância
    private List<String> opcoes;
    private int op;
    
    /**
     * Constructor for objects of class Menu
     */
    public Menu(String[] opcoes) {
        this.opcoes = new ArrayList<String>();
        for (String op : opcoes) 
            this.opcoes.add(op);
        this.op = 0;
    }

    /**
     * Método para apresentar o menu e ler uma opção.
     * 
     */
    public void executa() {
        do {
         	System.out.print('\u000C');
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
    }
    
    /** Apresentar o menu */
    private void showMenu() {
        System.out.println("\n *** IMOOBILIÁRIA *** \n");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
    }
    
    /** Ler uma opção válida */
    private int lerOpcao() {
        int op; 
        Scanner is = new Scanner(System.in);
        
        System.out.print("\nOpção: ");
        try {
            op = Integer.parseInt(is.nextLine());
        }
        catch (InputMismatchException | NumberFormatException e) { // Não foi inscrito um int
            op = -1;
        }
        return op;
    }
    
    /**
     * Método para obter a última opção lida
     */
    public int getOpcao() {
        return this.op;
    }
}
