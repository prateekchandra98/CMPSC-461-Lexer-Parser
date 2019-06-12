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
 * Description  : Tests different cases for the lexer and the parser
 * OS           : Windows 10
 *
 */
public class Test {
    public static void main (String args[]) {
        
        System.out.println("\nTest 1: \n");
        // testing the lexer 
        Lexer lex = new Lexer ("<body></body>$");
        Token tk = lex.nextToken();
        while (tk.getTokenType() != Token.TokenType.EOI) {
            tk.print();
            System.out.print(" ");
            tk = lex.nextToken();
        }
        System.out.println("\n");
        
        //Testing the parser
        Parser parser = new Parser ("<body> </body>");
        parser.run(); 
        ////////////////////////////////////////////////////////////////////
        System.out.println("\nTest 2: \n");
        // testing the lexer
        lex = new Lexer ("<body> google <b><i><b> yahoo</b></i></b></body>$");
        tk = lex.nextToken();
        while (tk.getTokenType() != Token.TokenType.EOI) {
            tk.print();
            System.out.print(" ");
            tk = lex.nextToken();
        }
        System.out.println("\n");
        
        parser = new Parser ("<body> google <b><i><b> yahoo</b></i></b></body>");
        parser.run();
        ////////////////////////////////////////////////////////////////////
        System.out.println("\nTest 3: \n");
        // testing the lexer
        lex = new Lexer ("<body> google hello<b><i><b><ul><li>yahoo</li><li>yahoo</li></ul></b></i></b><ul></ul><b><i><b> yahoo</b></i></b></body>$");
        tk = lex.nextToken();
        while (tk.getTokenType() != Token.TokenType.EOI) {
            tk.print();
            System.out.print(" ");
            tk = lex.nextToken();
        }
        System.out.println("\n");
        
        parser = new Parser ("<body> google hello <b><i><b><ul><li>yahoo</li><li>yahoo</li></ul></b></i></b><ul></ul><b><i><b> yahoo</b></i></b></body>");
        parser.run();
    }
}
/*
Output that i got in Windows 10 from the above test case:

Test 1: 

<body> </body> 

<body>
</body>

Test 2: 

<body> google <b> <i> <b> yahoo </b> </i> </b> </body> 

<body>
  google
  <b>
    <i>
      <b>
        yahoo
      </b>
    </i>
  </b>
</body>

Test 3: 

<body> google <b> <i> <b> <ul> <li> yahoo </li> <li> yahoo </li> </ul> </b> </i> </b> <ul> </ul> <b> <i> <b> yahoo </b> </i> </b> </body> 

<body>
  google
  <b>
    <i>
      <b>
        <ul>
          <li>
            yahoo
          </li>
          <li>
            yahoo
          </li>
        </ul>
      </b>
    </i>
  </b>
  <ul>
  </ul>
  <b>
    <i>
      <b>
        yahoo
      </b>
    </i>
  </b>
</body>
BUILD SUCCESSFUL (total time: 0 seconds)



*/
