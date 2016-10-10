package application.service;

public class ViewChromosome extends Command {

    @Override
    public void execute(String file) {
        System.out.println("viewing chromosome ");

        String fileWithoutRef = file.substring(0, file.lastIndexOf('#'));
        String fileName = fileWithoutRef.substring( fileWithoutRef.lastIndexOf('/')+1, fileWithoutRef.length() );//toy.sam
        String fileWithoutExtn = fileWithoutRef.substring(0, fileWithoutRef.lastIndexOf('.'));
        String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
        String chromosomeRef = file.substring(file.lastIndexOf("#") + 1);

        viewSAM(fileWithoutRef);
        Command Index = new Index();
        Index.execute(fileWithoutExtn + ".bam");
/*
        String command = "samtools view -h " + fileWithoutExtn + ".bam " + chromosomeRef + " > " +
                fileWithoutExtn + "." + chromosomeRef + ".sam "; */

        String command = "samtools view -h " + fileWithoutExtn + ".bam " + chromosomeRef;
        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }

    private void viewSAM(String file) {
        Command ViewSAM = new ViewSAM();
        ViewSAM.execute(file);
    }
}
