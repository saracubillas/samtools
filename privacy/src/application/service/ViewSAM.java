package application.service;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

import static java.lang.System.exit;

public class ViewSAM extends Command {

    @Override
    public void execute(String file) {
        System.out.println("viewing samtools...");
        String fileNameWithoutExtn = file.substring(0, file.lastIndexOf('.'));

        String fileExtension = file.substring(file.lastIndexOf('.')+1);

        String command = null;
        if (fileExtension.equalsIgnoreCase("sam")) {
            String commandView = "samtools view -b -S -o ";
            command = commandView + fileNameWithoutExtn + ".bam " + file;
        }else if(fileExtension.equalsIgnoreCase("geniff")){
            getBAM(file);

            String filename_dir = FilenameUtils.getPath(file);
            String bamFileName = null;
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
                            bamFileName = file_in_directory.getAbsolutePath();
                            bam_found = true;
                        }
                    }
                }
                if(bam_found){
                    command = "samtools view -H " + bamFileName;
                }else{
                    System.err.println("BAM file not properly generated from geniff");
                    exit(-1);
                }
            }else{
                System.err.println("Error in the geniff file location provided");
                exit(-1);
            }
        }

        System.out.println(command);

        String output = executeCommand(command);
        System.out.println(output);
        System.out.println("\n========OUTPUT======");
    }

    private void getBAM(String filename){
        DATAextractor dataExtractor = new DATAextractor();
        dataExtractor.execute(filename);
    }
}
