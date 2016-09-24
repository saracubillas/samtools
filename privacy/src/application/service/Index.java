package application.service;

public class Index extends Command {

    @Override
    public void execute(String file) {
        System.out.println("index samtools...");

        String command = "samtools index " + file;
        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }
}
