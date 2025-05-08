import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class passOneFileGenerator {
    private static int _count ; //To count How many Files processed;
    private String _InFileName = "intermediate.txt";
    private String _OutFileName = "out_pass1.txt";
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
    public passOneFileGenerator(String FileName) throws FileNotFoundException {
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
    public InputFileLine Split (String SicLine)
    {
        InputFileLine line = new InputFileLine();
//        line.LineNumber = SicLine.substring(0,3);
        line.Label = SicLine.substring(0,7);
        line.Delimeter = SicLine.substring(7,15);
        line.menmonic = SicLine.substring(13,20);
        line.Operand= SicLine.substring(26,33);
//        line.Comment = SicLine.substring(47);
        return line;
    }

    //Method to check vector contents (Just to debug)
    public void PrintFileLinesVectorContents()
    {
        for(int i = 0 ;i<FileLines.size();i++)
            System.out.println(FileLines.get(i));
    }

    //Method to Remove Line number and comments , deal with InputFileLien Opject
    public InputFileLine RemoveLineNumbersAndComment(int index)
    {
        InputFileLine line = new InputFileLine();


        line = Split(FileLines.elementAt(index));
        FileLines.remove(index);
        FileLines.add(index, line.Label + line.Delimeter + line.menmonic + line.Delimeter
                + line.Operand);
        return line;

    }
    //To save the data in the Out.txt
    public String ConvertFromInputFileLineToOutputString(InputFileLine line)
    {
//        String locFormated = String.format("%04X\t%s", LocationHandler.LocationCounter(line.menmonic), line);
        return  String.format("%04X\t%-8s%-8s%-8s",
                LocationHandler.LocationCounter(line.menmonic),
                line.Label,
                line.menmonic,
                line.Operand);
//        return  locFormated + line.Label + line.Delimeter + line.menmonic + line.Delimeter + line.Operand ;
    }
    //This method Generates the output file
    public void GenerateOutFile()
    {
        InputFileLine line = new InputFileLine();
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
