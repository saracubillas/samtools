package application.service;


import java.text.Normalizer;

public class ViewSAM extends Command {

    @Override
    public void execute(String file) {
        System.out.println("viewing samtools...");
        String fileNameWithoutExtn = file.substring(0, file.lastIndexOf('.'));
        String commandView = "samtools view -b -S -o ";
        String command = commandView + fileNameWithoutExtn + ".bam " + file;
        System.out.println(command);

        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }
}
