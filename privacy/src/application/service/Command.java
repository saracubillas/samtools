package application.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class Command {
    public abstract void execute();

    public String executeCommand(String command) {

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

