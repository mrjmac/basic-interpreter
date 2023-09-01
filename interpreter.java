/*
    variable assignment (ints only)
    operators
    print
    conditionals
    while loops
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

        int elseLevel = 0;  // 0 == if not found, throw error on elif or else
                            // 1 == at some point conditional was true, ignore any elif or else
                            // 2 == conditional has been entered but is not true yet, keep checking elif or else

        while (tokens.hasMoreTokens())
        {
            String token = tokens.nextToken();
            
            // the only multiline operation is an if, otherwise we can just handle the line seperately
            if (token.equals("if"))
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

                    elseLevel = 1;
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

                    elseLevel = 2;
                }                
            }
            else if ((token.equals("else") || token.equals("elif")) && elseLevel != 0)
            {
                if (elseLevel == 1)
                {

                    if (token.equals("elif"))
                    {
                        while (!token.equals(")"))
                        {
                            token = tokens.nextToken();
                        }
                    }

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

                }
                else if (elseLevel == 2)
                {
                    if (token.equals("elif"))
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

                            elseLevel = 1;
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
                        }
                    }
                    else
                    {
                        String parse = "";
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

                        StringTokenizer newTokens = new StringTokenizer(parse);
                        handleBracket(newTokens);

                        // after an else it's not expected to have anything else
                        elseLevel = 0;
                    }
                }
            }
            else if (token.equals("while"))
            {
                String condition = "";

                while (!token.equals(")"))
                {
                    token = tokens.nextToken();
                    condition += token + " ";
                }

                StringTokenizer conditionTokens = new StringTokenizer(condition);
                String conditionCurrToken = conditionTokens.nextToken();
                
                String parse = "";
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

                while (parser.parse(new StringTokenizer(condition), conditionCurrToken, vars) != 0)
                {
                    handleBracket(new StringTokenizer(parse));
                }
                
                elseLevel = 0;
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

                // we have done something that isn't related to a conditional so the conditional state has reset
                elseLevel = 0;
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
