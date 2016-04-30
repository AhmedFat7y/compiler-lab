java -jar java-cup-11b.jar parser.cup
java JLex.Main Lexer
javac -cp java-cup-11b-runtime.jar Lexer.java sym.java parser.java AP.java
java -cp java-cup-11b-runtime.jar:. AP

