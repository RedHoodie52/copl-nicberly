import java.util.Scanner;
import java.io.*;

class SyntaxChecker
{
    private int positie;
    private String invoer;
    private String parsed;
    private int haakjes;

    public SyntaxChecker(String invoer){
        this.invoer = invoer;
        this.parsed = "";
        this.positie = 0;
        this.haakjes = 0;
    }

    public String getParsed(){
        return this.parsed;
    }

    private boolean isEinde(){
        return positie >= invoer.length();
    }

    private char verwerk(boolean valid){
        if(!isEinde()){
            if (valid){
                // parsed += invoer.charAt(positie); // TODO: geparsede invoer returnen
            }
                
            return invoer.charAt(positie++);
        }
        throw new RuntimeException("Unexpected end of invoer");
    }

    private String variabele(){
        StringBuilder var = new StringBuilder();
        while (!isEinde() && Character.isLetterOrDigit(invoer.charAt(positie))) {
            var.append(verwerk(true));
        }

        if (var.length() > 0){
            if (!Character.isLetter(var.charAt(0))){
                throw new RuntimeException("Eerste karakter van variabele is geen letter.");
            }
        }
        
        return var.toString();
    }

    private boolean expr(){
        if (isEinde()){
            return false;
        }

        char huidigeKar = invoer.charAt(positie);

        if (huidigeKar == '('){
            haakjes++;
            huidigeKar = verwerk(true);
            if (isEinde()){
                return false; // geen karakters na (
            }

            if (expr()){
                if (isEinde()){
                    throw new RuntimeException(" Expected ')' "); 
                }
                
                if (invoer.charAt(positie) == ')'){
                    haakjes--;
                    verwerk(true);
                    // VVV  haakjes > 0 &&  
                    if ( !isEinde() && invoer.charAt(positie) != ')' ){
                        return expr(); 
                    }

                    return true;    
                }

            } else {
                throw new RuntimeException(" Expected expression after '(' "); 
            }
        } else if (huidigeKar == '\\'){
            huidigeKar = verwerk(true); // verwerk de '\'
    
            while(huidigeKar == ' '){
                huidigeKar = verwerk(true);
            }

            String varNaam = variabele();
            if (!varNaam.isEmpty() && expr()){
                return true;
            }
        } else if (huidigeKar == ' '){
            huidigeKar =  verwerk(true);
            if (!isEinde()){
                return expr();
            } else { 
                throw new RuntimeException("Trailing space found");
            }
        } else if (huidigeKar == ')'){
            haakjes--;
            verwerk(true);       // verwerk de ')' 
            if (isEinde()){
                return true;
            }else{
                if (expr()){
                    return true;
                }
            }
        } else {
            String varNaam = "";
            varNaam = variabele();

            if (!isEinde()){
                huidigeKar = invoer.charAt(positie);
            }

            if (huidigeKar == ' '){
                huidigeKar = verwerk(false);
                expr(); 
            }

            if (!varNaam.isEmpty()){
                // System.out.println(varNaam);    
                return true;
            }
        }

        return false;
    }

    public boolean parse() {
        invoer = invoer.trim(); // Removes trailing and leading whitespace
        return expr() && isEinde();
    }
}


class Main 
{
    public static void main(String args[])
    {
        Scanner inv = new Scanner(System.in);
        System.out.println("Wat is de invoer: ");
        String invoer = inv.nextLine();
        SyntaxChecker input = new SyntaxChecker(invoer);

        if(input.parse()){
            System.out.println("Accepted!"); 
            // System.out.println(input.getParsed());
        } 
        else {
            System.out.println("Rejected!");   
        }
    }
}


