import java.util.Scanner;

class SyntaxChecker
{
    private int positie;
    private String invoer;

    public SyntaxChecker(String invoer){
        this.invoer = invoer;
        this.positie = 0;
    }

    private boolean isEinde(){
        return positie >= invoer.length();
    }

    private char verwerk(){
        if(!isEinde()){
            return invoer.charAt(positie++);
        }
        throw new RuntimeException("Unexpected end of invoer");
    }

    private String variabele(){
        StringBuilder varName = new StringBuilder();
        while (!isEinde() && Character.isLetterOrDigit(invoer.charAt(positie))) {
            varName.append(verwerk());
        }

        if (varName.length() > 0){
            if (!Character.isLetter(varName.charAt(0))){
                throw new RuntimeException("Eerste karakter van variabele is geen letter.");
            }
        }
        
        return varName.toString();
    }
    
    private boolean expr(){
        if (!isEinde()){
            char huidigeKar = invoer.charAt(positie);
            if (huidigeKar == '('){
                verwerk(); // verwerk '('
                if (expr()){
                    if (!isEinde() && invoer.charAt(positie) == ')'){
                        verwerk(); // verwerk ')'
                        return true;
                    }
                    else{
                        throw new RuntimeException("Expected ')'");
                    }
                } else {
                    throw new RuntimeException("Expected expression after '('");
                }
            }
            else if (huidigeKar == '\\'){
                verwerk(); // verwerk '\'
                String varNaam = variabele();
                if (!varNaam.isEmpty() && expr()){
                    return true;
                } 
            }
            else if (huidigeKar == ' '){
                //System.out.println("Space found");
                verwerk();
                if (!isEinde()){
                    return expr(); // return dit 
                } else { 
                    throw new RuntimeException("Trailing space found");
                }
            } else {
                //System.out.println("Variable read");
                String varNaam = "";
                varNaam = variabele();

                if (!isEinde()){
                    huidigeKar = invoer.charAt(positie);
                }

                if (huidigeKar == ' '){
                    verwerk();
                    expr(); // hier ook 
                }

                if (!varNaam.isEmpty()){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean parse() {
        // System.out.println(expr());
        // System.out.println(isEinde());
        return expr() && isEinde();
    }

}


class Main 
{
    public static void main(String args[])
    {
        // Scanner obj = new Scanner(System.in);
        // System.out.println("Wat is de string invoer: ");
        // String invoer = obj.nextLine();
        // public static int waardeHaakjes = 0;

        Scanner inv = new Scanner(System.in);
        System.out.println("Wat is de invoer: ");
        String invoer = inv.nextLine();
        SyntaxChecker input = new SyntaxChecker(invoer);

        if(input.parse()){
            System.out.println("Accepted!"); 
        } 
        else {
            System.out.println("Rejected!");
            
        }
    
    }

}


