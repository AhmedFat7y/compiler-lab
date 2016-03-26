
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Class MLA
 *
 * Reads a specified input file, tokenize it, and writes
 * the output to a specified output file;
 *
 * Input and output files can be given as command line
 * arguments. If no arguments are given, hard coded file
 * names will be used.
 *
 * Output file will be automatically overwritten if exists.
 *
 */
public class MLA {
  public static void main(String[] args) {
    String[] inFiles = new String[] {"Template1/Latex1.tex", "Template2/Latex2.tex", "Template3/Latex3.tex"};
    String[] outFiles= new String[] {"Template1/Sample.out", "Template2/Sample.out", "Template3/Sample.out"};
    for (int i=0; i < inFiles.length; i++) {

      String inFile = inFiles[i];
      String outFile = outFiles[i];

      System.out.println("Start tokenizing file: " + inFile);

      Lexer lexer = new Lexer(inFile);

      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

        Token t;

        while ((t = lexer.nextToken()) != null) {
          //System.out.println(t.toString());
          writer.write(t.toString());
          writer.newLine();
        }

        writer.close();

        System.out.println("Done tokenizing file: " + inFile);
        System.out.println("Output written in file: " + outFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
