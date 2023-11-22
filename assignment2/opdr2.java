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
        this.root = parseExpression(expr);
        this.expr = newExpr;
        this.index = 0;

        //System.out.println(newExpr);
    }

    public Node parseVariable (){
        StringBuilder var = new StringBuilder();

        while(Character.isLetterOrDigit(expr.charAt(index)) ){
            var.append(expr.charAt(index));
            index++;
        }

        expr = expr.substring(index);
        index = 0;

        Node varNode = new Node(var.toString());
        
        return varNode;
    }

    public Node parseLambda (){
        Node lambdaNode = new Node("\\");

        Node varNode =  parseVariable(); // parameter meegeven
        if (varNode != null){
            lambdaNode.setLeft(varNode);
        }

        Node exprNode = parseExpression(expr); // parameter meegeven
        if (exprNode != null){
            lambdaNode.setRight(exprNode);
        }

        return lambdaNode;
    }

    public Node parseExpression (String expr){
        Node newRoot = new Node("");
        //Character huidigeKar = expr.charAt(index);

        // if (huidigeKar == '\\'){
        //     //index++;
        //     //expr = expr.substring(index);
        //     newRoot = parseLambda();
            
        //     return newRoot;
        // }

        // if(huidigeKar == '\\'){
        //     if (volgendeKar != )
        //     // newRoot.left = expr.charAt(tel + 1);
        //     // newRoot.left = expr.charAt(tel + 2);
        //     // \abc (\x y)
        //     //  (<expr> <expr)
        // }
        // else if(huidigeKar = ){
            
        // }




        return newRoot;
    }

    public String getRootContent(){
        return root.getContent();
    }
}



class Main 
{
    public static void main(String args[])
    {
        // inlezen

        String input = "\\x abc";

        SyntaxTree boom = new SyntaxTree(input);
        System.out.println(boom.getRootContent());


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
