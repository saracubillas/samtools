package application.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ViewSAM implements Command {

    @Override
    public void execute() {
        System.out.println("viewing samtools...");
        String command = "samtools view -bS /Users/sara/Documents/MIRI/thesis/samtools-1.3.1/examples/toy.sam";
        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }

    private String executeCommand(String command) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            System.out.println("\n========executing======");
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                System.out.println("\n========while======");
                output.append(line).append("\n");
            }

        } catch (Exception e) {

            //if samtools is not installed show a personalized message
            e.printStackTrace();
            System.out.println("\n========error======");
        }

        return output.toString();

    }
}
