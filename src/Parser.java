/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *
 * Author       : Prateek Chandra
 * Email        : pbc5080@psu.edu
 * PSUID        : 983272256
 * Date         : 4th February 2018
 * Java         : 1.8.0_161
 * Description  : It is a syntactic analyzer which parses the tokens given by Lexer using the recursive descent technique.
 * OS           : Windows 10
 *
 */
public class Parser {
    Lexer lexer;
    Token token;
    int textIndentation = 0;
    
    public Parser(String s){
        lexer = new Lexer(s + "$");
        token = lexer.nextToken();
    }

    public void run () {
        webpage();
    }
    public void webpage () 
    {
        //the first token has to be a keyword and it has to be a <body> according to the grammar
        if (token.getTokenType() == Token.TokenType.KEYWORD && (token.getTokenValue().equals("<body>"))) 
        {
            System.out.println(token.getTokenValue()); //prints out <body>
            token = lexer.nextToken();
            textIndentation++;
            
            //since text can be 0 times,(which means : "<body></body>" is valid), check if the next token is not a "body"
            if(!token.getTokenValue().equals("</body>"))
            {
                do 
                {
                    text();
                } while (!(token.getTokenValue().equals("</body>"))&& token.getTokenType() != Token.TokenType.EOI);
            }
            
            //testing for repitition of the TEXT as it can be repeated multiple times
            
            if (token.getTokenValue().equals("</body>"))
            {
                System.out.println(token.getTokenValue());
                token = lexer.nextToken();
            }
        }
        else if (!(token.getTokenValue().equals("<body>")))
        {
            System.err.println("Syntax error: expecting an <body>" + "; saw:" + token.getTokenValue());
            System.exit(1);
        }
        else
        {
            System.err.println("Syntax error: expecting a Keyword" + "; saw:" + Token.typeToString(token.getTokenType()));
            System.exit(1);
        }
        
        //End Of Input
        if (token.getTokenType() == Token.TokenType.EOI)
            token = lexer.nextToken();
        else 
        {
            //error(Token.TokenType.EOI);
            System.err.println("Syntax error: expecting: " + Token.typeToString(Token.TokenType.EOI) + "; saw: " + Token.typeToString(token.getTokenType()));
            System.exit(1);
        }
        return;
        //System.out.println("</body>");
    }
    public void text ()
    {
        String keywordBegin = ""; //to keep track of the begining and the end of the keyword
        //opening of the keyword does not have a back slash
        
        if (!(token.getTokenValue().equals("<li>")) &&token.getTokenType() == Token.TokenType.KEYWORD &&(!(token.getTokenValue().equals("<body>"))) && (token.getTokenValue().charAt(1) != '/'))
        {  
            for (int i = 0; i < textIndentation;i ++)
            {
                System.out.print("  ");
            }
            textIndentation++;
            keywordBegin = token.getTokenValue();
            System.out.println(keywordBegin);
            token = lexer.nextToken();
            //do not get out of the recursion because the next token could be a string

        }
        else if (token.getTokenValue().charAt(1) == '/')
        {
            System.err.println("Syntax error: expecting a keyword without slash" + "; saw:" + token.getTokenValue());
            System.exit(1);
        }
        else if (token.getTokenValue().equals("<body>"))
        {
            System.err.println("Syntax error: cannot have another <body> " + "; saw:" + token.getTokenValue());
            System.exit(1);
        }
        else if (token.getTokenValue().equals("<li>"))
        {
            System.err.println("Syntax error: cannot have <li> without <ul>" + "; saw:" + token.getTokenValue());
            System.exit(1);
        }
        
                   
        //this section is for checking if the token is a string or a underline <ul> or if it is another text
        if (keywordBegin.equals("<ul>") && token.getTokenValue().equals("<li>")) //since <li> Text </li> can be repeated 0 or more times, we check both the current and the next token 
        {
            do
            {
                listItem();
            }while (token.getTokenValue().equals("</li>"));
        }
        else if (token.getTokenType() == Token.TokenType.STRING && keywordBegin.equals("<ul>")) //<ul> STRING </ul> is not possible
        {
            System.err.println("Syntax error: cannot have a string after a <ul>" + "; saw:" + token.getTokenValue());
            System.exit(1);
        }
        else if (token.getTokenType() == Token.TokenType.STRING && !keywordBegin.equals("<ul>"))
        {
            for (int i = 0; i < textIndentation;i ++)
            {
                System.out.print("  ");
            }
            System.out.println(token.getTokenValue());
            token = lexer.nextToken();
            
            if (keywordBegin.equals ("")) //if the token is not a keyword but a string, it will print out a string as keywordBegin only changes when its an open keyword
            {
                return;
            }
        }
        else if (!token.getTokenValue().equals("</ul>"))
        {
            text();
        }
        
        
        //checking for the end keyword "</...>"

        //if the close keyword matches the opening keyword then only it will print it, otherwise its an error
        if ( token.getTokenType() == Token.TokenType.KEYWORD && token.getTokenValue().charAt(1)== '/' && !(token.getTokenValue().equals("</li>")) && (keywordBegin.substring(1).equals(token.getTokenValue().substring(2))))
        {
            //if its a closing keyword, indentation reduces to the original indentation of the opening keyword by recursion
            textIndentation--;
            for (int i = 0; i < textIndentation;i ++)
            {
                System.out.print("  ");
            }
            System.out.println(token.getTokenValue());
            token = lexer.nextToken();
        }
        else if (token.getTokenType() != Token.TokenType.KEYWORD)
        {
            System.err.println("Syntax error: expecting a keyword"+ "; saw:"+ token.getTokenType());
            System.exit(1);
        }
        else if (token.getTokenValue().charAt(1)!= '/')
        {
            System.err.println("Syntax error: expecting a keyword with slash"+ "; saw:"+ token.getTokenValue());
            System.exit(1);
        }
        else if (token.getTokenValue().equals("</li>"))
        {
            System.err.println("Syntax error: Cannot have </li> without its begining"+ "; saw:"+ token.getTokenValue());
            System.exit(1);
        }
        else if (!(keywordBegin.substring(1).equals(token.getTokenValue().substring(2))))
        {
            System.err.println("Syntax error: Expecting a <"+keywordBegin.substring(1)+ "; saw:</"+ token.getTokenValue().substring(2));
            System.exit(1);
        }
        //since string does not include space, string followed by a string is incorrect as defined by the grammar
         
    }
    public void listItem()
    {
        String keywordBegin = ""; //storing the open keyword so that we can compare with the close keyword 
        //open keyword (without backslash)
        
        if (token.getTokenValue().equals("<li>") && token.getTokenType() == Token.TokenType.KEYWORD)
        {
            //token = lexer.nextToken();
            //prints out the indentation
            for (int i = 0; i < textIndentation;i ++)
            {
                System.out.print("  ");
            }

            textIndentation++; //incrementation of the indentation

            keywordBegin = token.getTokenValue();
            System.out.println(token.getTokenValue());
            token = lexer.nextToken();

        }
        else if(token.getTokenType() != Token.TokenType.KEYWORD)
        {
            System.err.println("Syntax error: expecting a keyword"+ "; saw:"+ token.getTokenType());
            System.exit(1);
        }
        else if (!token.getTokenValue().equals("<li>"))
        {
            System.err.println("Syntax error: expecting an <li>"
                               + "; saw:"
                               + token.getTokenValue());
            System.exit(1);
        }
        
        //going back to text as defined by the grammar
        text();
        
        //clsoe keyword (with bachslash) and it should match with the opening keyword 
        if (token.getTokenType() == Token.TokenType.KEYWORD) //need to check if it is not a string
        {
            if ( token.getTokenValue().charAt(1)== '/' && (keywordBegin.substring(1).equals(token.getTokenValue().substring(2))))
            {
                //token = lexer.nextToken();
                textIndentation--;
                for (int i = 0; i < textIndentation;i ++)
                {
                    System.out.print("  ");
                }
                System.out.println(token.getTokenValue());
                token = lexer.nextToken();

            }
            else
            {
                System.err.println("Syntax error: expecting a </li>" + "; saw:" + token.getTokenValue());
                System.exit(1);
            }
        }
        else
        {
            System.err.println("Syntax error: expecting a keyword"+ "; saw:"+ token.getTokenType());
            System.exit(1);
        }
        
        //listitem can be repeated many times as defined by the grammar
        if (token.getTokenValue().equals("<li>") && token.getTokenType() == Token.TokenType.KEYWORD)
        {
            listItem();
        }
        
    }
/*
    Code provided by the professor:
    
    public void statement () {
        System.out.println("<Statement>");
        assignmentStmt();
        while (token.getTokenType() == Token.TokenType.SEMICOLON) {
            System.out.println("\t<Semicolon>;</Semicolon>");
            token = lexer.nextToken();
            assignmentStmt();
        }
        match(Token.TokenType.EOI);
        System.out.println("</Statement>");
    }

    public void assignmentStmt () {
        System.out.println("\t<Assignment>");
        String val = match(Token.TokenType.ID);
        System.out.println("\t\t<Identifier>" + val + "</Identifier>");
        match(Token.TokenType.ASSIGNMENTOP);
        System.out.println("\t\t<AssignmentOp>:=</AssignmentOp>");
        expression();
        System.out.println("\t</Assignment>");
    }

    public void expression () {
        if (token.getTokenType() == Token.TokenType.ID) {
            System.out.println("\t\t<Identifier>" + token.getTokenValue()
                               + "</Identifier>");
        } else if (token.getTokenType() == Token.TokenType.INT) {
            System.out.println("\t\t<Int>" + token.getTokenValue()
                               + "</Int>");
        } else if (token.getTokenType() == Token.TokenType.FLOAT) {
            System.out.println("\t\t<Float>" + token.getTokenValue()
                               + "</Float>");
        } else {
            System.err.println("Syntax error: expecting an ID, an int, or a float"
                               + "; saw:"
                               + Token.typeToString(token.getTokenType()));
            System.exit(1);
        }
        token = lexer.nextToken();
    }

    private String match (Token.TokenType tp) {
        String value = token.getTokenValue();
        if (token.getTokenType() == tp)
            token = lexer.nextToken();
        else error(tp);
        return value;
    }

    private void error (Token.TokenType tp) {
        System.err.println("Syntax error: expecting: " + Token.typeToString(tp)
                           + "; saw: "
                           + Token.typeToString(token.getTokenType()));
        System.exit(1);
    }
*/
}
