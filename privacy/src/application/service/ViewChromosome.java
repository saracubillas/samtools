package application.service;

public class ViewChromosome extends Command {

    @Override
    public void execute(String file) {
        System.out.println("viewing chromosome ");

        String fileWithoutRef = file.substring(0, file.lastIndexOf('#'));
        String fileName = fileWithoutRef.substring( fileWithoutRef.lastIndexOf('/')+1, fileWithoutRef.length() );//toy.sam
        String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
        String chromosomeRef = file.substring(file.lastIndexOf("#") + 1);

        viewSAM(fileWithoutRef);
        Command Index = new Index();
        System.out.println(fileNameWithoutExtn + ".bam");
        Index.execute(fileNameWithoutExtn + ".bam");

        String command = "samtools view -h" + fileNameWithoutExtn + ".bam " + chromosomeRef + " > " +
                fileNameWithoutExtn + "." + chromosomeRef + ".sam ";

        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }

    private void viewSAM(String file) {
        System.out.println(file);
        Command ViewSAM = new ViewSAM();
        ViewSAM.execute(file);
    }
}
