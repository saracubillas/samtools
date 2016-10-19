package application.service;

/**
 * Created by gencom on 19/10/16.
 */
public class XACMLextractor extends Command{
    @Override
    public void execute(String file) {
        System.out.println("extracting xacml...");
        String fileNameWithoutExtn = file.substring(0, file.lastIndexOf('.'));
        String command = "./writtingGenISOBMFF decode " + fileNameWithoutExtn + ".geniff " + "XACML";
        System.out.println(command);

        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }
}
