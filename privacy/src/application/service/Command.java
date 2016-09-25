package application.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class Command {
    public abstract void execute(String file);

    public String executeCommand(String command) {

        StringBuilder output = new StringBuilder();

        Process p;
        String[] elements = command.split(" ");
        try {
            p = Runtime.getRuntime().exec(elements);

            System.out.println("\n========executing======");
            p.waitFor();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader errorReader =
                    new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                System.out.println("\n========while======");
                output.append(line).append("\n");
            }

            String lineError = "";
            while ((lineError = errorReader.readLine())!= null) {
                System.out.println("\n========error======");
                output.append(lineError).append("\n");
            }

        } catch (Exception e) {

            //if samtools is not installed show a personalized message
            e.printStackTrace();
            System.out.println("\n========error======");
        }

        return output.toString();

    }

}

