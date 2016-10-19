package application.service;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

import static java.lang.System.exit;

public class ViewChromosome extends Command {

    @Override
    public void execute(String file) {
        String fileWithoutRef = file.substring(0, file.lastIndexOf('#'));
        String fileWithoutExtn = fileWithoutRef.substring(0, fileWithoutRef.lastIndexOf('.'));
        String chromosomeRef = file.substring(file.lastIndexOf("#") + 1);

        String fileExtension = fileWithoutRef.substring(fileWithoutRef.lastIndexOf('.')+1);

        if (fileExtension.equalsIgnoreCase("sam")) {
            viewSAM(fileWithoutRef);
            IndexBam(fileWithoutExtn);
        }else if(fileExtension.equalsIgnoreCase("geniff")){
            getBAM(file);
            System.out.println("BAM obtained");
            getBAI(file);
            System.out.println("BAI obtained");

            String filename_dir = FilenameUtils.getPath(fileWithoutRef);
            if (filename_dir.isEmpty()) filename_dir=".";
            File directory= new File(filename_dir);
            if (directory.isDirectory()){
                boolean bam_found = false;
                for(File file_in_directory:directory.listFiles()){
                    String file_in_directory_name = file_in_directory.getName();
                    String file_in_directory_extension = file_in_directory_name.substring(file_in_directory_name.lastIndexOf('.')+1);
                    if(file_in_directory_extension.equalsIgnoreCase("bam")){
                        if (bam_found){
                            System.err.println("too many BAM files in the directory");
                            exit(-1);
                        }else{
                            String bamFileName = file_in_directory.getAbsolutePath();
                            fileWithoutExtn = filename_dir+bamFileName.substring(bamFileName.lastIndexOf('/')+1, bamFileName.lastIndexOf('.'));
                            bam_found = true;
                        }
                    }
                }
            }else{
                System.err.println("Error in the geniff file location provided");
                exit(-1);
            }
        }
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

    private void getBAM(String filename){
        DATAextractor dataExtractor = new DATAextractor();
        dataExtractor.execute(filename);
    }

    private void getBAI(String filename){
        IndexExtractor extractor = new IndexExtractor();
        extractor.execute(filename);
    }
}
