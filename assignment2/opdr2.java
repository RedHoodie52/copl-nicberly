import java.util.Scanner;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Node
{
    private String content;
    private Node left;
    private Node right;

    public Node (String newContent){
        this.content = newContent;
        this.left = null;
        this.right = null;
    }

    public String getContent(){
        return this.content;
    }

    public Node getLeft(){
        return this.left;
    }

    public Node getRight(){
        return this.right;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }

    public void setLeft(Node newLeft){
        this.left = newLeft;
    }

    public void setRight(Node newRight){
        this.right = newRight;
    }
}

class SyntaxTree
{
    public Node root;
    public String expr;
    public int index;

    public SyntaxTree (String newExpr){
        this.expr = newExpr;
        this.index = 0;
        this.root = parseExpression(newExpr);

        while (expr.length() > 0){
            this.root = parseExpression(expr);
        }
        //System.out.println(newExpr);
    }

    public Node parseVariable (){
        StringBuilder var = new StringBuilder();
       

        while(index < expr.length() && Character.isLetterOrDigit(expr.charAt(index))){
            var.append(expr.charAt(index));
            index++;
        }
        
        if (index < expr.length()){       // CHECK ABC DEF GHI
            expr = expr.substring(index); //
           
            
        }
        
        index = 0;
        Node varNode = new Node(var.toString());
        
        return varNode;
    }

    public Node parseLambda (){
        Node lambdaNode = new Node("\\");

        Node varNode =  parseVariable();
        if (varNode != null){
            lambdaNode.setLeft(varNode);
        }
        
        while (expr.charAt(index) == ' '){
            index++;
        }

        expr = expr.substring(index);
        index = 0;

        Node exprNode = parseExpression(expr);
        if (exprNode != null){
            lambdaNode.setRight(exprNode);
        }

        return lambdaNode;
    }
    
    public Node parseExpression (String newExpr){
        Node newRoot = new Node("");
        Character huidigeKar = expr.charAt(index);

        //System.out.println(huidigeKar + " - " + expr + " - " + index);

        if (huidigeKar == '\\'){
            index++;
            expr = newExpr.substring(index); // verwerk \

            index = 0;
            newRoot = parseLambda();
            
            return newRoot;
        } else if (Character.isLetter(huidigeKar)) {
            newRoot = parseVariable();

            return newRoot;
        } else if (huidigeKar == '(') {
            index++;
            expr = newExpr.substring(index);

            index = 0;
            newRoot = parseExpression(expr);

            index++;
            expr = expr.substring(index);

            return newRoot;
        } else if (huidigeKar == ' '){
            Node connectorNode = new Node("+");
            Node leftNode = root;

            index++;
            expr = newExpr.substring(index);      

            
            Node rightNode = parseExpression(expr);
            
            if (leftNode != null){
                connectorNode.setLeft(leftNode);
            }

            if (rightNode != null){
                connectorNode.setRight(rightNode);
            }

            root = connectorNode;

            System.out.println(expr + " " + index);

            return root;
        }


        

        //expr == var || (expr) || expr expr || \var expr

        // if(huidigeKar == '\\'){
        //     if (volgendeKar != )
        //     // newRoot.left = expr.charAt(tel + 1);
        //     // newRoot.left = expr.charAt(tel + 2);
        //     // \abc (\x y)
        //     //  (<expr> <expr)
        // }
        // else if(huidigeKar = ){
            
        // }

        // abc \x abc def
            //      +
            // abc      +
            //      ()     def
            
            //      +
            // abc    

            // ________________
            //|                | 
            //|    ebe         | 
            //|                | 
            //|________________| <-- keuken

            // wat s het probleeem ook alweer
            // abc def 

            // 


        return newRoot;
    }

    public String getRootContent(){
        // System.out.println("Root content printed");
        return root.getContent();
    }
}



class Main 
{
    public static void main(String args[])
    {
        // inlezen
        String input = "abc def ghi";

        SyntaxTree boom = new SyntaxTree(input);
        System.out.println(boom.root.getContent());
        System.out.println(boom.root.getLeft().getContent());
        System.out.println(boom.root.getLeft().getLeft().getContent());
        System.out.println(boom.root.getLeft().getRight().getContent());
        System.out.println(boom.root.getRight().getContent());


        // String fileName = "input.txt";
        
        // BufferedReader reader = new BufferedReader(new FileReader(fileName));
        // String line;
        // if (line != null){
        //     System.out.println(line);
        // }
        // reader.close();

        // ----------------------------------------------------------------------------------------------------

        // if(input.parse()){
        //     System.out.println("Accepted!"); 
        //     // System.out.println(input.getParsed());
        // } 
        // else {
        //     System.out.println("Rejected!");   
        // }
    }
}
