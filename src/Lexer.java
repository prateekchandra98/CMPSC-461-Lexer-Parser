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
 * Description  : It is a lexical analyzer. The class Lexer ensures that it has a public method nextToken() which returns a Token.
 * OS           : Windows 10
 *
 */
public class Lexer {
    private final String letters = "abcdefghijklmnopqrstuvmxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String digits = "0123456789";
    private final String [] keywords = {"<body>", "</body>", "<b>", "</b>", "<i>", "</i>","<ul>", "</ul>", "<li>", "</li>"};
    
    String stmt;
    int index = 0;
    char ch;
    
    public Lexer(String s){
        stmt = s; index=0; ch = nextChar();
    }

    public Token nextToken(){
        do {
            //ch = nextChar();
            if (ch == '<')
            {
                StringBuffer keyword =  new StringBuffer("");
                do{
                    keyword.append(ch);
                    ch = nextChar();
                }while ((ch != '$')&& (ch != '>'));
                
                boolean keyWordCheck = false;
                if (ch == '>'){
                    keyword.append(ch);
                    ch = nextChar();
                }
                for (int i = 0; i < keywords.length; i++)
                {
                    if (keyword.toString().equals(keywords[i]))
                        keyWordCheck = true;
                }
                if (keyWordCheck == true)
                {
                    //ch = nextChar();
                    return new Token(Token.TokenType.KEYWORD, keyword.toString());
                }
                else
                {
                    return new Token(Token.TokenType.INVALID, keyword.toString());
                }
            }
            else if (Character.isLetter(ch)||Character.isDigit(ch)) 
            {
                String str = concat (letters + digits);
                
                //ch = nextChar();
                
                if ( !Character.isLetter(ch) && !Character.isDigit(ch) && (ch != '<') && (ch != ' '))
                {
                    return new Token(Token.TokenType.INVALID, str);
                }
                else
                {
                    return new Token (Token.TokenType.STRING, str);
                }
            }
            else if (ch == ' ')//if it is a space, ignore and go to the next character
            {
                ch = nextChar();
            }
            else if (ch == '$') 
            {
                return new Token(Token.TokenType.EOI, "");
            }
            else
            {
                return new Token(Token.TokenType.INVALID, Character.toString(ch));
            }
            
    /*        
            if (Character.isLetter(ch)) 
            {
                String id = concat (letters + digits);
                return new Token(Token.TokenType.ID, id);
            } 
            else if (Character.isDigit(ch)) 
            {
                String num = concat(digits);
                if (ch != '.')
                    return new Token(Token.TokenType.INT, num);
                num += ch; ch = nextChar();
                if (Character.isDigit(ch)) {
                    num += concat(digits);
                    return new Token(Token.TokenType.FLOAT, num);
                } else 
                    return new Token(Token.TokenType.INVALID, num);
            }
            else switch (ch) 
            {
                case ' ': 
                    ch = nextChar(); break;
                case ';': 
                    ch = nextChar();
                    return new Token(Token.TokenType.SEMICOLON, "");
                case ':':
                    if (check('=')) 
                        return new Token(Token.TokenType.ASSIGNMENTOP, "");
                    else return new Token(Token.TokenType.INVALID, ":");
                case '$':
                    return new Token(Token.TokenType.EOI, "");
                default:
                    ch = nextChar();
                    return new Token(Token.TokenType.INVALID, 
                                     Character.toString(ch));
            } 
    */
        } while (true);
    }
    
    private char nextChar() {
        char ch = stmt.charAt(index); index = index+1;
        return ch;
    }
    /*
    private String isKeyword (String set) {
        StringBuffer r = new StringBuffer("");
        do {
            r.append(ch); 
            ch = nextChar();
        } while (set.indexOf(ch) >= 0 && ch != '<');
        
        return r.toString();
    }
    */
    private String concat (String set) {
        StringBuffer r = new StringBuffer("");
        do { r.append(ch); ch = nextChar();
        } while (set.indexOf(ch) >= 0);
        return r.toString();
    }

    private boolean check(char c) {
        ch = nextChar();
        if (ch == c) {ch = nextChar(); return true;}
        else return false;
    }

    public void error (String msg) {
        System.err.println("\nError: location " + index + " " + msg);
        System.exit(1);
    }
}
