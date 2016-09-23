package application.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ViewSAM extends Command {

    @Override
    public void execute() {
        System.out.println("viewing samtools...");
        String command = "samtools view -bS /Users/sara/Documents/MIRI/thesis/samtools-1.3.1/examples/toy.sam";
        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }


}
