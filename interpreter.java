/*
    variable assignment (ints only)
    operators
    print

    TODO: make recursive to handle nested ifs
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class interpreter {

    public static void main(String args[]) throws IOException
    {   
        StringTokenizer st = new StringTokenizer(Files.readString(Path.of("code.in"))); // break input into tokens
        vars = new TreeMap<>();

        handleBracket(st);
        
    }


    public static TreeMap<String, Integer> vars;


    public static void handleBracket(StringTokenizer tokens)
    {

        boolean prevCond = false;

        while (tokens.hasMoreTokens())
        {
            String token = tokens.nextToken();
            
            // the only multiline operation is an if, otherwise we can just handle the line seperately
            if (token.equals("if") || (prevCond && token.equals("else if")))
            {
                String parse = "";
                while (!token.equals(")"))
                {
                    token = tokens.nextToken();
                    parse += token + " ";
                }

                StringTokenizer newTokens = new StringTokenizer(parse);
                String newCurrToken = newTokens.nextToken();

                int valid = parser.parse(newTokens, newCurrToken, vars);
                System.out.println(valid);
                
                if (valid != 0)
                {
                    parse = "";
                    tokens.nextToken(); // throw away {
                    int brackets = 1;

                    while (brackets != 0)
                    {
                        token = tokens.nextToken();

                        if (token.equals("}"))
                        {
                            brackets -= 1;
                        }

                        if (brackets != 0)
                        {
                            parse += token + " ";
                        }

                        if (token.equals("{"))
                        {
                            brackets += 1;
                        }

                        
                    }

                    newTokens = new StringTokenizer(parse);
                    handleBracket(newTokens);
                }
                else
                {
                    int brackets = 1;
                    String curr = tokens.nextToken();

                    while (brackets != 0)
                    {
                        curr = tokens.nextToken();
                        if (curr.equals("{"))
                        {
                            brackets += 1;
                        }
                        if (curr.equals("}"))
                        {
                            brackets -= 1;
                        }
                    }
                    prevCond = true;
                }
                
            }
            else if (prevCond && token.equals("else"))
            {
                prevCond = false;
            }
            else 
            {
                String parse = token + " ";
                while (!token.equals(";"))
                {
                    token = tokens.nextToken();
                    parse += token + " ";
                }

                StringTokenizer newTokens = new StringTokenizer(parse);
                String currToken = newTokens.nextToken();

                handleLine(currToken, newTokens);

                prevCond = false;
            }
            
        }
    }
    
    public static void handleLine(String currToken, StringTokenizer tokens)
    {
        if (currToken.equals("print"))
        {
            String parse = "";
            while (!currToken.equals(";"))
            {
                currToken = tokens.nextToken();
                parse += currToken + " ";
            }

            StringTokenizer newTokens = new StringTokenizer(parse);
            String newCurrToken = newTokens.nextToken();
                    
            System.out.println(parser.parse(newTokens, newCurrToken, vars));

        }
        else
        {
            String name = currToken;

            if (parser.isNum(name.charAt(0) + ""))
            {
                System.out.println("Syntax error");
                System.exit(0);
            }

            currToken = tokens.nextToken();

            if (!currToken.equals("="))
            {
                System.out.println("Syntax error");
                System.exit(0);
            }
            else
            {
                String parse = "";
                while (!currToken.equals(";"))
                {
                    currToken = tokens.nextToken();
                    parse += currToken + " ";
                }

                StringTokenizer newTokens = new StringTokenizer(parse);
                String newCurrToken = newTokens.nextToken();

                int num = parser.parse(newTokens, newCurrToken, vars);
                vars.put(name, num);
            }
        }
    }

}