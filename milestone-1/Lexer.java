import java.lang.System;
import java.util.*;
import java.io.*;
public class Lexer {
  Yylex tokenizer;
  public  Lexer(String fileName)
  {
    try
    {
    tokenizer=new Yylex(new BufferedReader(new FileReader(fileName)));
    }
    catch(Exception e)
    {
    }
  }
  public Token nextToken()
  {
    Token next=null;
    try
    {
     next=  tokenizer.getToken();
    }
    catch(Exception e)
    {
    }
    return next;
  }
  }


class Yylex {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

  //initialize  variables to be used by class
  LinkedList<Integer> previousStates;
  public void gotoState(int state) {
    previousStates.addLast(yy_lexical_state);
    yybegin(state);
  }
  public void exitState() {
    if (previousStates.isEmpty()) {
      //throw new Exception("what are you trying to do, man?");
    }
    //System.out.println(previousStates);
    yybegin(previousStates.removeLast());
  }
  public void exitState(int nStatestoExit) {
    if (previousStates.isEmpty()) {
      //throw new Exception("what are you trying to do, man?");
    }
    for(int i=0; i < nStatestoExit; i++) {
      previousStates.removeLast();
    }
    yybegin(previousStates.removeLast());
  }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

//Add code to be executed on initialization of the lexer
  previousStates = new LinkedList<>();
	}

	private boolean yy_eof_done = false;
	private final int with = 13;
	private final int equation = 16;
	private final int math = 13;
	private final int non = 10;
	private final int commands = 19;
	private final int MATH_MODE_STATE = 3;
	private final int BACK_SLASH_STATE = 1;
	private final int printable = 13;
	private final int without = 19;
	private final int stuff = 19;
	private final int mode = 14;
	private final int slash = 6;
	private final int back = 5;
	private final int content = 20;
	private final int EQUATION_MODE_STATE = 4;
	private final int list = 18;
	private final int item = 17;
	private final int comands = 7;
	private final int YYINITIAL = 0;
	private final int ITEM_LIST_STATE = 2;
	private final int yy_state_dtrans[] = {
		0,
		20,
		73,
		73,
		73,
		74,
		74,
		74,
		74,
		74,
		74,
		74,
		74,
		74,
		74,
		74,
		74,
		74,
		74,
		74
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NOT_ACCEPT,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NOT_ACCEPT,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NOT_ACCEPT,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NOT_ACCEPT,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NOT_ACCEPT,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NOT_ACCEPT,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NOT_ACCEPT,
		/* 31 */ YY_NOT_ACCEPT,
		/* 32 */ YY_NOT_ACCEPT,
		/* 33 */ YY_NOT_ACCEPT,
		/* 34 */ YY_NOT_ACCEPT,
		/* 35 */ YY_NOT_ACCEPT,
		/* 36 */ YY_NOT_ACCEPT,
		/* 37 */ YY_NOT_ACCEPT,
		/* 38 */ YY_NOT_ACCEPT,
		/* 39 */ YY_NOT_ACCEPT,
		/* 40 */ YY_NOT_ACCEPT,
		/* 41 */ YY_NOT_ACCEPT,
		/* 42 */ YY_NOT_ACCEPT,
		/* 43 */ YY_NOT_ACCEPT,
		/* 44 */ YY_NOT_ACCEPT,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NOT_ACCEPT,
		/* 47 */ YY_NOT_ACCEPT,
		/* 48 */ YY_NOT_ACCEPT,
		/* 49 */ YY_NOT_ACCEPT,
		/* 50 */ YY_NOT_ACCEPT,
		/* 51 */ YY_NOT_ACCEPT,
		/* 52 */ YY_NOT_ACCEPT,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NOT_ACCEPT,
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NOT_ACCEPT,
		/* 58 */ YY_NOT_ACCEPT,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NOT_ACCEPT,
		/* 62 */ YY_NOT_ACCEPT,
		/* 63 */ YY_NOT_ACCEPT,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NOT_ACCEPT,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NOT_ACCEPT,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NOT_ACCEPT,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NOT_ACCEPT,
		/* 75 */ YY_NOT_ACCEPT,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NOT_ACCEPT,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NOT_ACCEPT,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NOT_ACCEPT,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NOT_ACCEPT,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NOT_ACCEPT,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NOT_ACCEPT,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NOT_ACCEPT,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NOT_ACCEPT,
		/* 98 */ YY_NOT_ACCEPT,
		/* 99 */ YY_NOT_ACCEPT
	};
	private int yy_cmap[] = unpackFromString(1,130,
"14:10,28,14:2,27,14:77,19,1,20,14:3,11,21,4,2,7,26,18,14,22,14,17,10,6,8,3," +
"16,23,14,12,9,5,14:2,25,14,24,13,14,15,14:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,100,
"0,1:5,2,1,3,1:3,4,1,4:2,5,6,7,1,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22," +
"23,24,25,26,27,28,29,3,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,4" +
"7,48,49,50,51,52,53,54,55,56,57,5,6,58,59,60,61,62,63,64,65,66,67,68,69,70," +
"71,72,73,74,75,76,77,78,79,80,81,82,4,83")[0];

	private int yy_nxt[][] = unpackFromString(84,29,
"1,2,3:25,18,4,-1:32,22,-1:7,24,-1:18,42:14,8,42:11,-1:3,98:14,12,98:11,-1:3" +
",71:14,16,71:11,-1:3,72:14,17,72:11,-1:30,4,1,5,6,19:2,21,19,23,19,25,19:2," +
"27,19:8,29,19:5,18,4,-1:12,26,-1:20,33,-1:32,28,-1:29,34,-1:26,30,-1:14,76," +
"-1:13,35,-1:26,31,-1,75,-1:23,36,-1:33,32,-1:46,37,-1:24,77,-1:25,39,-1:15," +
"40,-1:30,7,-1:37,41,-1:25,42,-1:24,43,-1:29,44,-1:40,45,-1:12,46,-1:33,47,-" +
"1:38,48,-1:14,9,-1:29,51,-1:27,52,-1:25,53,-1:50,10,-1:11,54,-1:22,55,-1:38" +
",56,-1:23,57,-1:37,58,-1:21,59,-1:26,11,-1:21,98:6,99,98:7,12,98:6,96,98:4," +
"-1:11,60,-1:30,61,-1:24,13,-1:25,62,-1:42,63,-1:20,64,-1:25,65,-1:32,66,-1:" +
"36,67,-1:21,80,-1:17,90:26,-1:15,81,-1:16,98:14,14,98:11,-1:3,98:14,15,98:1" +
"1,-1:2,1,-1:26,18,4,1,-1:32,87,-1:33,38,-1:28,78,-1:41,49,-1:7,98:6,69,98:7" +
",12,98:11,-1:14,68,-1:17,71:26,-1:24,50,-1:7,98:7,70,98:6,12,98:11,-1:3,72:" +
"26,-1:3,98:14,12,98:8,79,98:2,-1:3,90:12,84,90:6,86,90:6,-1:11,82,-1:20,98:" +
"2,83,98:11,12,98:11,-1:3,98:14,12,98:6,85,98:4,-1:3,90:19,86,90:6,-1:3,98:1" +
"4,12,98:6,88,98:4,-1:3,98:5,89,98:8,12,98:11,-1:3,98:8,91,98:5,12,98:11,-1:" +
"3,98:6,92,98:7,12,98:11,-1:3,98:10,93,98:3,12,98:11,-1:3,98:8,94,98:5,12,98" +
":11,-1:3,98:4,95,98:9,12,98:11,-1:3,98:14,12,98:7,97,98:3,-1:2");

	public Token getToken ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {
				return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
  gotoState(BACK_SLASH_STATE);
}
					case -3:
						break;
					case 3:
						{
}
					case -4:
						break;
					case 4:
						{
  System.out.println("new line");
}
					case -5:
						break;
					case 5:
						{
  exitState();
  return new Token(Token.NEWLINE, '\\' + yytext());
}
					case -6:
						break;
					case 6:
						{
}
					case -7:
						break;
					case 7:
						{}
					case -8:
						break;
					case 8:
						{exitState(2);}
					case -9:
						break;
					case 9:
						{}
					case -10:
						break;
					case 10:
						{}
					case -11:
						break;
					case 11:
						{}
					case -12:
						break;
					case 12:
						{}
					case -13:
						break;
					case 13:
						{}
					case -14:
						break;
					case 14:
						{
  gotoState(ITEM_LIST_STATE);
  return new Token(Token.BEGIN, '\\' + yytext());
}
					case -15:
						break;
					case 15:
						{
  gotoState(EQUATION_MODE_STATE);
  return new Token(Token.BEGIN, '\\' + yytext());
}
					case -16:
						break;
					case 16:
						{
  exitState();
  return new Token(Token.DOC_CLASS, '\\' + yytext());
}
					case -17:
						break;
					case 17:
						{
  exitState();
  return new Token(Token.PACKAGE, '\\' + yytext());
}
					case -18:
						break;
					case 19:
						{
}
					case -19:
						break;
					case 21:
						{
}
					case -20:
						break;
					case 23:
						{
}
					case -21:
						break;
					case 25:
						{
}
					case -22:
						break;
					case 27:
						{
}
					case -23:
						break;
					case 29:
						{
}
					case -24:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
