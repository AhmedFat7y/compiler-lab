import java_cup.runtime.Symbol;
import java.lang.System;
import java.util.*;
import java.io.*;


class Lexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

  String errorToken = "";
  int nItems = 0;
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
  public void exitState(int nExtraStatestoExit) {
    if (previousStates.isEmpty()) {
      //throw new Exception("what are you trying to do, man?");
    }
    for(int i=0; i < nExtraStatestoExit; i++) {
      previousStates.removeLast();
    }
    yybegin(previousStates.removeLast());
  }
  public Symbol getSym(int symVal, String token) {
    //System.out.println("Token: " + token + " == " + symVal);
    return new Symbol(symVal, token);
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

	Lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer () {
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
	private final int with = 16;
	private final int equation = 19;
	private final int math = 16;
	private final int non = 12;
	private final int commands = 16;
	private final int ones = 23;
	private final int MATH_MODE_STATE = 4;
	private final int generic = 22;
	private final int BACK_SLASH_STATE = 1;
	private final int printable = 16;
	private final int stuff = 22;
	private final int without = 15;
	private final int mode = 17;
	private final int slash = 8;
	private final int back = 7;
	private final int content = 16;
	private final int EQUATION_MODE_STATE = 5;
	private final int list = 21;
	private final int COMMAND_TEXT_STATE = 6;
	private final int item = 20;
	private final int comands = 9;
	private final int YYINITIAL = 0;
	private final int DOCUMENT_STATE = 2;
	private final int ITEM_LIST_STATE = 3;
	private final int yy_state_dtrans[] = {
		0,
		33,
		101,
		102,
		108,
		110,
		115,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101,
		101
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
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_START,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NOT_ACCEPT,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NOT_ACCEPT,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NOT_ACCEPT,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NOT_ACCEPT,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NOT_ACCEPT,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NOT_ACCEPT,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NOT_ACCEPT,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NOT_ACCEPT,
		/* 53 */ YY_NO_ANCHOR,
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
		/* 99 */ YY_NOT_ACCEPT,
		/* 100 */ YY_NOT_ACCEPT,
		/* 101 */ YY_NOT_ACCEPT,
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NOT_ACCEPT,
		/* 104 */ YY_NOT_ACCEPT,
		/* 105 */ YY_NOT_ACCEPT,
		/* 106 */ YY_NOT_ACCEPT,
		/* 107 */ YY_NOT_ACCEPT,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NOT_ACCEPT,
		/* 110 */ YY_NOT_ACCEPT,
		/* 111 */ YY_NOT_ACCEPT,
		/* 112 */ YY_NOT_ACCEPT,
		/* 113 */ YY_NOT_ACCEPT,
		/* 114 */ YY_NOT_ACCEPT,
		/* 115 */ YY_NOT_ACCEPT,
		/* 116 */ YY_NOT_ACCEPT,
		/* 117 */ YY_NOT_ACCEPT,
		/* 118 */ YY_NOT_ACCEPT,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NOT_ACCEPT,
		/* 121 */ YY_NOT_ACCEPT,
		/* 122 */ YY_NOT_ACCEPT,
		/* 123 */ YY_NOT_ACCEPT,
		/* 124 */ YY_NOT_ACCEPT,
		/* 125 */ YY_NOT_ACCEPT,
		/* 126 */ YY_NOT_ACCEPT,
		/* 127 */ YY_NOT_ACCEPT,
		/* 128 */ YY_NOT_ACCEPT,
		/* 129 */ YY_NOT_ACCEPT,
		/* 130 */ YY_NOT_ACCEPT,
		/* 131 */ YY_NOT_ACCEPT,
		/* 132 */ YY_NOT_ACCEPT,
		/* 133 */ YY_NOT_ACCEPT,
		/* 134 */ YY_NOT_ACCEPT,
		/* 135 */ YY_NOT_ACCEPT,
		/* 136 */ YY_NOT_ACCEPT,
		/* 137 */ YY_NOT_ACCEPT,
		/* 138 */ YY_NOT_ACCEPT,
		/* 139 */ YY_NOT_ACCEPT,
		/* 140 */ YY_NOT_ACCEPT,
		/* 141 */ YY_NOT_ACCEPT,
		/* 142 */ YY_NOT_ACCEPT,
		/* 143 */ YY_NOT_ACCEPT,
		/* 144 */ YY_NOT_ACCEPT,
		/* 145 */ YY_NOT_ACCEPT,
		/* 146 */ YY_NOT_ACCEPT,
		/* 147 */ YY_NOT_ACCEPT,
		/* 148 */ YY_NOT_ACCEPT,
		/* 149 */ YY_NOT_ACCEPT,
		/* 150 */ YY_NOT_ACCEPT,
		/* 151 */ YY_NOT_ACCEPT,
		/* 152 */ YY_NOT_ACCEPT,
		/* 153 */ YY_NOT_ACCEPT,
		/* 154 */ YY_NOT_ACCEPT,
		/* 155 */ YY_NOT_ACCEPT
	};
	private int yy_cmap[] = unpackFromString(1,130,
"5:9,30,31,5,30,6,5:18,32,39,5:2,2,3,39,5,36,38,39:2,37,39,5,39,35:10,5:3,39" +
",5:3,34:26,23,1,4,39,34,5,16,24,9,7,12,29,22,34,25,34,21,15,11,13,8,20,26,3" +
"4,17,14,10,34:2,28,34,27,18,39,19,5:2,33,0")[0];

	private int yy_rmap[] = unpackFromString(1,156,
"0,1:3,2,1,3,1:5,4,1:2,5,1,6,1:2,4,6:2,7,8,9,1:3,10,11,1,12,13,14,15,16,17,1" +
"8,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,4" +
"3,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,5,59,60,61,62,63,64,65,66,67" +
",68,69,70,71,72,73,74,75,76,77,7,8,78,79,80,81,82,83,84,85,17,86,87,88,89,8" +
"7,90,12,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109," +
"110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,4,6,127")[0];

	private int yy_nxt[][] = unpackFromString(128,40,
"1,2,3,4,5,6,36,6:11,5:2,6:3,5,6:7,7,6,1,6:6,-1:41,4:5,-1,4:24,-1,4,-1,4:6,-" +
"1:5,6:13,-1:2,6:3,-1,6:7,-1,6,-1,6:6,-1,153:5,-1,153:12,12,153:11,-1,153,-1" +
",153:6,-1,79:5,-1,79:12,15,79:11,-1,79,-1,79:6,-1,154:5,-1,154:12,17,154:11" +
",-1,154,-1,154:6,-1,99:5,-1,99:12,23,99:11,-1,99,-1,99:6,-1,100:5,-1,100:12" +
",24,100:11,-1,100,-1,100:6,-1:3,25:28,-1,25,-1,25:6,-1:7,29:11,-1:2,29:3,-1" +
",29:6,-1:4,29:2,-1:39,30,-1:5,116:5,-1,116:12,32,116:11,-1,116,-1,116:6,1,8" +
",9:4,37,41,9:2,43,45,47,9,49,119,9,51,9:6,53,9:5,10:3,1,9:6,-1,103,-1:39,4:" +
"2,35:3,25,35:24,-1,35,-1,35:6,-1:5,6:13,-1:2,6:3,-1,6:7,7,6,-1,6:6,-1:31,7," +
"-1:11,25:28,7,25,-1,25:6,-1:7,39:11,-1:2,39:3,-1,39:6,-1:4,39:2,111,-1:12,5" +
"5,-1:38,40,-1:7,42,-1:37,56,-1:42,44,-1:34,57,-1:43,46,-1:44,58,-1:31,48,-1" +
":33,59,-1:44,50,-1:12,118,-1:42,60,-1:21,120,-1,117,-1:51,121,-1:27,54,-1:4" +
"9,62,-1:27,63,-1:41,11,-1:47,64,-1:31,130,-1:45,65,-1:35,66,-1:40,67,-1:49," +
"69,-1:25,70,-1:44,71,-1:24,153:5,-1,153:12,12,153:5,150,153:5,-1,153,-1,153" +
":6,-1:24,73,-1:27,13,-1:42,74,-1:37,125,-1:38,77,-1:36,78,-1:55,135,-1:43,1" +
"4,-1:28,79,-1:35,80,-1:33,81,-1:44,83,-1:47,84,-1:33,85,-1:37,16,-1:27,154:" +
"5,-1,154:5,155,154:6,17,154:5,151,154:5,-1,154,-1,154:6,-1:14,86,-1:41,87,-" +
"1:35,18,-1:36,89,-1:52,90,-1:29,19,-1:42,91,-1:36,92,-1:43,93,-1:46,94,-1:3" +
"3,128,-1:23,142:5,-1,142:24,-1,142,-1,142:6,-1,153:5,-1,153:12,20,153:11,-1" +
",153,-1,153:6,-1:18,129,-1:22,154:5,-1,154:12,21,154:11,-1,154,-1,154:6,-1," +
"154:5,-1,154:12,22,154:11,-1,154,-1,154:6,1,-1:32,1,-1:6,1,2,3,35,25:2,38,2" +
"5:24,7,25,34,25:6,-1:25,104,-1:28,105,-1:37,106,-1:38,107,-1:60,26,-1:7,1,-" +
"1,27,28,-1:2,109,29:11,-1:2,29:3,-1,29:6,-1,7,-1,1,29,30,-1:3,28,1,2,3,4,-1" +
":2,109,39:11,-1:2,39:3,-1,39:6,-1,7,-1,1,39,30,-1:3,28,-1:7,112:11,-1:2,112" +
":3,-1,112:6,-1:4,112,-1:12,112:11,-1:2,112:3,-1,112:6,-1:4,112:2,-1,113,31," +
"-1:33,114,-1:7,1,-1:17,116,-1:14,1,-1:15,138,-1:44,61,-1:41,52,-1:47,122,-1" +
":27,68,-1:41,123,-1:50,75,-1:15,153:5,-1,153:5,95,153:6,12,153:11,-1,153,-1" +
",153:6,-1:18,82,-1:36,88,-1:25,154:5,-1,154:5,97,154:6,17,154:11,-1,154,-1," +
"154:6,-1:17,96,-1:23,99:5,-1,99:24,-1,99,-1,99:6,-1:14,72,-1:50,76,-1:15,15" +
"4:5,-1,154:6,98,154:5,17,154:11,-1,154,-1,154:6,-1,100:5,-1,100:24,-1,100,-" +
"1,100:6,-1,153:5,-1,153:12,12,153:7,124,153:3,-1,153,-1,153:6,-1:14,126,-1:" +
"26,154:5,-1,154:12,17,154:7,127,154:3,-1,154,-1,154:6,-1,142:3,137,142,-1,1" +
"42:11,133,142:12,-1,142,-1,142:6,-1:14,131,-1:26,154:5,-1,154,132,154:10,17" +
",154:11,-1,154,-1,154:6,-1,153:5,-1,153:12,12,153:5,134,153:5,-1,153,-1,153" +
":6,-1,154:5,-1,154:12,17,154:5,136,154:5,-1,154,-1,154:6,-1,142:3,137,142,-" +
"1,142:24,-1,142,-1,142:6,-1,154:5,-1,154:12,17,154:5,139,154:5,-1,154,-1,15" +
"4:6,-1,153:5,-1,153:4,140,153:7,12,153:11,-1,153,-1,153:6,-1,154:5,-1,154:4" +
",141,154:7,17,154:11,-1,154,-1,154:6,-1,154:5,-1,154:7,143,154:4,17,154:11," +
"-1,154,-1,154:6,-1,153:5,-1,153:5,144,153:6,12,153:11,-1,153,-1,153:6,-1,15" +
"4:5,-1,154:5,145,154:6,17,154:11,-1,154,-1,154:6,-1,154:5,-1,154:9,146,154:" +
"2,17,154:11,-1,154,-1,154:6,-1,153:5,-1,153:7,147,153:4,12,153:11,-1,153,-1" +
",153:6,-1,154:5,-1,154:7,148,154:4,17,154:11,-1,154,-1,154:6,-1,154:5,-1,15" +
"4:3,149,154:8,17,154:11,-1,154,-1,154:6,-1,154:5,-1,154:12,17,154:6,152,154" +
":4,-1,154,-1,154:6");

	public java_cup.runtime.Symbol next_token ()
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

  return getSym(sym.EOF, null);
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
  gotoState(MATH_MODE_STATE);
  return getSym(sym.MATHMODE, yytext());
}
					case -4:
						break;
					case 4:
						{
}
					case -5:
						break;
					case 5:
						{
}
					case -6:
						break;
					case 6:
						{
  return getSym(sym.BODY, yytext());
}
					case -7:
						break;
					case 7:
						{
  //System.out.println("new line");
}
					case -8:
						break;
					case 8:
						{
  exitState();
  return getSym(sym.NEWLINE, '\\' + yytext());
}
					case -9:
						break;
					case 9:
						{
  errorToken += yytext();
}
					case -10:
						break;
					case 10:
						{
  exitState();
  Symbol s = getSym(sym.ERROR,
  "Line: " + (yyline + 1)
  + " Undefined control sequence: "
  + '\\' + errorToken);
  errorToken = "";
  return s;
}
					case -11:
						break;
					case 11:
						{
  gotoState(COMMAND_TEXT_STATE);
  return getSym(sym.DATE, '\\' + yytext());
}
					case -12:
						break;
					case 12:
						{
  exitState(2);
  return getSym(sym.END, '\\' + yytext());
}
					case -13:
						break;
					case 13:
						{
  gotoState(COMMAND_TEXT_STATE);
  return getSym(sym.TITLE, '\\' + yytext());
}
					case -14:
						break;
					case 14:
						{
  gotoState(COMMAND_TEXT_STATE);
  return getSym(sym.BF, '\\' + yytext());
}
					case -15:
						break;
					case 15:
						{
  exitState();
  return getSym(sym.LABEL, '\\' + yytext());
}
					case -16:
						break;
					case 16:
						{
  gotoState(COMMAND_TEXT_STATE);
  return getSym(sym.SECTION, '\\' + yytext());
}
					case -17:
						break;
					case 17:
						{
  //gotoState(DOCUMENT_STATE);
  gotoState(YYINITIAL);
  return getSym(sym.BEGIN, '\\' + yytext());
}
					case -18:
						break;
					case 18:
						{
  gotoState(COMMAND_TEXT_STATE);
  return getSym(sym.SUB_TITLE, '\\' + yytext());
}
					case -19:
						break;
					case 19:
						{
  exitState();
  return getSym(sym.MAKE, '\\' + yytext());
}
					case -20:
						break;
					case 20:
						{
  exitState(2);
  if(nItems != 0) {
    return getSym(sym.END, '\\' + yytext());
  } else {
    return getSym(sym.ERROR, "Line: " + yyline + " Item list with missing \\item");
  }
}
					case -21:
						break;
					case 21:
						{
  nItems = 0;
  gotoState(ITEM_LIST_STATE);
  return getSym(sym.BEGIN, '\\' + yytext());
}
					case -22:
						break;
					case 22:
						{
  gotoState(EQUATION_MODE_STATE);
  return getSym(sym.BEGIN, '\\' + yytext());
}
					case -23:
						break;
					case 23:
						{
  exitState();
  return getSym(sym.DOC_CLASS, '\\' + yytext());
}
					case -24:
						break;
					case 24:
						{
  exitState();
  return getSym(sym.PACKAGE, '\\' + yytext());
}
					case -25:
						break;
					case 25:
						{
  return getSym(sym.BODY, yytext());
}
					case -26:
						break;
					case 26:
						{
  nItems++;
  return getSym(sym.ITEM, yytext());
}
					case -27:
						break;
					case 27:
						{
  exitState();
  return getSym(sym.MATHMODE, yytext());
}
					case -28:
						break;
					case 28:
						{
  return getSym(sym.OPERATOR, yytext());
}
					case -29:
						break;
					case 29:
						{
  return getSym(sym.VAR, yytext());
}
					case -30:
						break;
					case 30:
						{
  return getSym(sym.NM, yytext());
}
					case -31:
						break;
					case 31:
						{
  return getSym(sym.FUNC, yytext());
}
					case -32:
						break;
					case 32:
						{
  exitState(1);
  return getSym(sym.TEXT, yytext());
}
					case -33:
						break;
					case 34:
						
					case -34:
						break;
					case 35:
						{
}
					case -35:
						break;
					case 36:
						{
  return getSym(sym.BODY, yytext());
}
					case -36:
						break;
					case 37:
						{
  errorToken += yytext();
}
					case -37:
						break;
					case 38:
						{
  return getSym(sym.BODY, yytext());
}
					case -38:
						break;
					case 39:
						{
  return getSym(sym.VAR, yytext());
}
					case -39:
						break;
					case 41:
						{
  errorToken += yytext();
}
					case -40:
						break;
					case 43:
						{
  errorToken += yytext();
}
					case -41:
						break;
					case 45:
						{
  errorToken += yytext();
}
					case -42:
						break;
					case 47:
						{
  errorToken += yytext();
}
					case -43:
						break;
					case 49:
						{
  errorToken += yytext();
}
					case -44:
						break;
					case 51:
						{
  errorToken += yytext();
}
					case -45:
						break;
					case 53:
						{
  errorToken += yytext();
}
					case -46:
						break;
					case 119:
						{
  errorToken += yytext();
}
					case -47:
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
