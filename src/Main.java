import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
    IntermediateFileGenerator test1 = new IntermediateFileGenerator("In.txt");
    //test1.ReadFile();
    //test1.Split(test1.GetFileLines().elementAt(0));
    test1.GenerateOutFile();
    //test1.PrintFileLinesVectorContents();
      //  System.out.println(test1.FileLines.size());
    ObjectCodeGenerator test2 = new ObjectCodeGenerator("In.txt");
    test2.ReadFile(test2.GetFileLines(),test2.GetScanner(),test2.GetInputFile());

        System.out.println(test2.CalculateTA("10AC","0033","1009"));
        test2.ReadFromSymbolTableFile();
        test2.GenerateObjectCode();
       // test2.PrintVector();
        test2.GenerateOutputFile();
        test2.PrintVector();

    }
}