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

        while (st.hasMoreTokens())
        {
            String token = st.nextToken();
            
            // the only multiline operation is an if, otherwise we can just handle the line seperately
            if (token.equals("if"))
            {
                String parse = "";
                while (!token.equals(")"))
                {
                    token = st.nextToken();
                    parse += token + " ";
                }

                StringTokenizer newTokens = new StringTokenizer(parse);
                String newCurrToken = newTokens.nextToken();

                int valid = parser.parse(newTokens, newCurrToken, vars);
                
                if (valid != 0)
                {
                    while (!token.equals("}"))
                    {

                    }
                    
                }
                else
                {
                    while (!token.equals("}"))
                    {

                    }
                }
                
                
            }
            else
            {
                String parse = token + " ";
                while (!token.equals(";"))
                {
                    token = st.nextToken();
                    parse += token + " ";
                }

                tokens = new StringTokenizer(parse);
                currToken = tokens.nextToken();

                handleLine();
            }
            
        }
    }

    

    // turn into args
    public static String currToken;
    public static TreeMap<String, Integer> vars;
    public static StringTokenizer tokens;

    public static void handleBracket()
    {

    }
    
    public static void handleLine()
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