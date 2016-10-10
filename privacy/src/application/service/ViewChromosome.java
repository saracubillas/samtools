package application.service;

public class ViewChromosome extends Command {

    @Override
    public void execute(String file) {
        String fileWithoutRef = file.substring(0, file.lastIndexOf('#'));
        String fileWithoutExtn = fileWithoutRef.substring(0, fileWithoutRef.lastIndexOf('.'));
        String chromosomeRef = file.substring(file.lastIndexOf("#") + 1);

        viewSAM(fileWithoutRef);
        IndexBam(fileWithoutExtn);
        String command = "samtools view -h " + fileWithoutExtn + ".bam " + chromosomeRef;
        
        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }

    private void IndexBam(String fileWithoutExtn) {
        Command Index = new Index();
        Index.execute(fileWithoutExtn + ".bam");
    }

    private void viewSAM(String file) {
        Command ViewSAM = new ViewSAM();
        ViewSAM.execute(file);
    }
}
