import java.util.Scanner;

class Main 
{
    public static void main(String args[])
    {
        Scanner obj = new Scanner(System.in);
        System.out.println("Wat is de string invoer: ");
        String invoer = obj.nextLine();

        System.out.println("je hebt " + invoer + " ingevoerd.");
        
        int aantal = 0;
        for (int i = 0; i < invoer.length(); i++)
        {
            if (invoer.charAt(i) = '(')
            {
                
            }
            
            else if (invoer.charAt(i) = '(')
            {

            }
            else if (invoer.charAt(i) = '')
            {
                
            }
            else if (invoer.charAt(i) = ' ')
            {
                
            }
            else if (invoer.charAt(i) = ' ')
            {
                
            }
        } 
    }

    public static boolean isAlphabetical(char c)
    {
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9'){
            return true;
        }
        return false;
    }
}
//invoer checken per karakter
//



// lexical analyzer
class Lexer
{

}