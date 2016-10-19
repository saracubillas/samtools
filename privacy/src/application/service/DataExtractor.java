package application.service;

/**
 * Created by gencom on 19/10/16.
 */
public class DataExtractor extends Command {
    @Override
    public void execute(String file) {
        System.out.println("extracting data...");
        String fileNameWithoutExtn = file.substring(0, file.lastIndexOf('.'));
        String command = "./writtingGenISOBMFF decode " + fileNameWithoutExtn + ".geniff " + "DATA";
        System.out.println(command);

        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }
}
