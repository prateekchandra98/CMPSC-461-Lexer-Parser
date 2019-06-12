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
 * Description  : class Token that can store the value and category of any token.
 * OS           : Windows 10
 *
 */
public class Token {
    public enum TokenType {STRING, KEYWORD, EOI, INVALID}

    private TokenType type;
    private String val;

    Token (TokenType t, String v) {
        type = t; val = v;
    }

    TokenType getTokenType() {return type;}
    String getTokenValue() {return val;}

    void print () {
        String s = "";
        switch (type) {
        case STRING: case KEYWORD: s = val; break;
        //case INT: case FLOAT: case ID: s = val; break;
        //case SEMICOLON: s = ";"; break;
        //case ASSIGNMENTOP: s = ":="; break;
        case EOI: s = "";
        case INVALID: s = "invalid"; break;
        }
        System.out.print(s);
    }

    public static String typeToString (TokenType tp) {
        String s = "";
        switch (tp) {
        //case LETTER: s = "Letter"; break;
        //case DIGIT: s = "Digit"; break;
        //case INT: s = "Int"; break;
        //case FLOAT: s = "Float"; break;
        //case ID: s = "ID"; break;
        //case SEMICOLON: s = "Semicolon"; break;
        //case ASSIGNMENTOP: s = "AssignmentOP"; break;
        case STRING: s = "String";break;
        case KEYWORD: s = "Keyword";break;    
        case INVALID: s="Invalid"; break;
        }
        return s;
    }

}
