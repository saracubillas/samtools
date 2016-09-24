package application.service;

public class ViewSAM extends Command {

    @Override
    public void execute(String file) {
        System.out.println("viewing samtools...");
        String command = "samtools view -bS " + file;
        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }
}
