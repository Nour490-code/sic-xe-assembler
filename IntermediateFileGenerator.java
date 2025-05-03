import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class IntermediateFileGenerator {
    private static int _count ; //To count How many Files processed;
    private String _InFileName = "In.txt" ;
    private String _OutFileName = "Out.txt";
    private File In = new File(_InFileName);
    private File Out = new File(_OutFileName);


    Scanner input = new Scanner(In); //To Read From InFile
    PrintWriter Output = new PrintWriter(_OutFileName); // To Write In OutputFile
    public Vector <String> FileLines = new Vector<>(); //To Manepulate File data

    //Setters and Getters
    public Vector<String> GetFileLines()
    {
        return FileLines;
    }

    //Constructor
    public IntermediateFileGenerator(String FileName) throws FileNotFoundException {
        _count ++;
       // _FileName = FileName;
    }

    // Methods

    //Method to Save File Data in a vector
    public Vector<String> ReadFile()
    {
        if(In.exists())
        {
            while (input.hasNextLine())
            {
                FileLines.addElement(input.nextLine());
            }
            System.out.println("First Line : ");
            System.out.println(FileLines.get(0));
        }
        else
            System.out.println("File not found!");
        return FileLines;
    }

    //Method To Split Each Part in the line to deal with it
    public InputFileLien Split (String SicLine)
    {
        InputFileLien line = new InputFileLien();
        line.LineNumber = SicLine.substring(0,3);
        line.Delimeter = SicLine.substring(3,9);
        line.Label = SicLine.substring(9,16);
        line.menmonic = SicLine.substring(22,29);
        line.Operand= SicLine.substring(35,42);
        line.Comment = SicLine.substring(47);
        return line;
    }

    //Method to check vector contents (Just to debug)
    public void PrintFileLinesVectorContents()
    {
        for(int i = 0 ;i<FileLines.size();i++)
            System.out.println(FileLines.get(i));
    }

    //Method to Remove Line number and comments , deal with InputFileLien Opject
    public InputFileLien RemoveLineNumbersAndComment(int index)
    {
        InputFileLien line = new InputFileLien();


        line = Split(FileLines.elementAt(index));
        FileLines.remove(index);
        FileLines.add(index, line.Label + line.Delimeter + line.menmonic + line.Delimeter
                + line.Operand);
        return line;

    }
    //To save the data in the Out.txt
    public String ConvertFromInputFileLineToOutputString(InputFileLien line)
    {
        return line.Label + line.Delimeter + line.menmonic + line.Delimeter + line.Operand ;
    }
    //This method Generates the output file
    public void GenerateOutFile()
    {
        InputFileLien line = new InputFileLien();
        String OutputLine ;
        for(int i = 0 ;i<FileLines.size();i++)
        {
            line = RemoveLineNumbersAndComment(i);
            OutputLine = ConvertFromInputFileLineToOutputString(line);
            Output.println(OutputLine);
        }
        Output.close();
    }

}
