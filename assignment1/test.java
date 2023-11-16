import java.util.Scanner;

class SyntaxChecker
{
    private int positie;
    private String invoer;
    private int haakjes = 0;

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
    // ((a))
    private boolean expr(){
        if (!isEinde()){
            char huidigeKar = invoer.charAt(positie);
            if (huidigeKar == '('){
                haakjes++;
                verwerk(); // verwerk '('
                huidigeKar = invoer.charAt(positie);
                if (expr()){
                    if (!isEinde() && invoer.charAt(positie) == ')'){
                        haakjes--;
                        verwerk(); // verwerk ')'
                        if (isEinde()){
                            return true;
                        }
                        else { // als niet einde
                            huidigeKar = invoer.charAt(positie);    
                            // if (huidigeKar == ')' && haakjes > 0){
                            //     verwerk();
                            //     huidigeKar = invoer.charAt(positie);
                            // }

                            while (huidigeKar == ')' && haakjes != 0){
                                if (haakjes > 0){
                                    verwerk(); // verwerk huidige ')'
                                    haakjes--;

                                    if (isEinde() && haakjes != 0){
                                        throw new RuntimeException("Expected another ).");  
                                    }
                                }
                                if (!isEinde()){
                                    huidigeKar = invoer.charAt(positie);
                                }
                                    
                            }   

                            if (isEinde()){
                                return true;
                            }

                            return expr();
                        }
                        

                        // ((a))

                        // huidigeKar = invoer.charAt(positie)
                        
                        // if(huidigeKar == ')' && haakjes != 0 ){
                        //     verwerk();
                        
                        // } 
                        // (\x (a) (b))
                    }
                    else{
                        throw new RuntimeException("Expected ')'");
                    }
                } else {
                    throw new RuntimeException("Expected expression after '('");
                }
            }
            else if (huidigeKar == '\\'){
                verwerk();
                huidigeKar = invoer.charAt(positie);
        
                while(huidigeKar == ' '){
                    verwerk();
                    huidigeKar = invoer.charAt(positie);
                }
                String varNaam = variabele();
                if (!varNaam.isEmpty() && expr()){
                    return true;
                }
            }
            else if (huidigeKar == ' '){
                //System.out.println("Space found");
                verwerk();
                huidigeKar = invoer.charAt(positie);
                if (!isEinde()){
                    return expr(); // return dit 
                } else { 
                    throw new RuntimeException("Trailing space found");
                }
            } else if (huidigeKar == ')'){
                if (haakjes > 0){
                    haakjes--;
                    verwerk();
                    if (!isEinde()){
                        huidigeKar = invoer.charAt(positie);
                    }
                }
                
            } else {
                String varNaam = "";
                varNaam = variabele();

                System.out.println(huidigeKar);    

                if (!isEinde()){
                    huidigeKar = invoer.charAt(positie);
                }

                if (huidigeKar == ' '){
                    verwerk();
                    huidigeKar = invoer.charAt(positie);
                    expr(); // hier ook 
                }

                if (!varNaam.isEmpty()){
                    return true;
                }

                // return expr();
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


