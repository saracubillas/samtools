package app;

import Infrastructure.Balana;
import application.service.Command;
import application.service.CommandFactory;
import application.service.XACMLparser;

import java.io.Console;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import static java.lang.System.exit;


public class GenomeAccessControl {

    private static Map<String,String> idMap = new HashMap<String, String>();

    private static String action = "[1] View - Convert SAM to BAM\t[2] View Chromosome - extract the data for specific region";
    private static String roles = "researcher\tphysician";

    public static void main(String[] args){

        Console console;
        String userName = null;
        String actionName = null;
        String role = null;
        String filePath = null;
        String chromosomeRef = null;
        String resource = null;
        String fileName = null;

        printDescription();

        initData();


        String request=null;
        if ((console = System.console()) != null){
            userName = console.readLine("Enter User name : ");
            if(userName == null || userName.trim().length() < 1 ){
                System.err.println("\nUser name can not be empty\n");
                return;
            }

            role = console.readLine("Enter role : ");
            if(role == null || role.trim().length() < 1 ){
                System.err.println("\nRole can not be empty\n");
                System.out.println("\nYou can enter one of following roles: \n");
                System.out.println(roles);
                return;
            }

            System.out.println("\nYou can select one of following action: \n");
            System.out.println(action);

            String actionId = console.readLine("Enter action Id : ");
            if(actionId == null || actionId.trim().length() < 1 ){
                System.err.println("\nAction Id can not be empty\n");
                return;
            } else {
                actionName = idMap.get(actionId);
                if(actionName == null){
                    System.err.println("\nEnter valid action Id\n");
                    return;
                }
            }

            filePath = console.readLine("Enter file path : ");
            if(filePath == null || filePath.trim().length() < 1 ){
                System.err.println("\nFile path name can not be empty\n");
                return;
            }
            if (actionId.equals("2"))//refactor
            {
                chromosomeRef = console.readLine("Enter chromosome reference : ");
                if(chromosomeRef == null || chromosomeRef.trim().length() < 1 ){
                    System.err.println("\nChromosome ref can not be empty\n");
                    return;
                }
            }

            resource = filePath;
            if (chromosomeRef != null) {
                resource = resource + "#" + chromosomeRef;
            }

            if (resource != null) {
                fileName = resource.substring(resource.lastIndexOf('/')+1, resource.length());
            }
            request = createXACMLRequest(userName, role, actionName, fileName);
        }else{
            /*if (args.length!=1){
                System.err.println("Filepath and only filepath needed as program arguments");
                exit(-1);
            }
            try {
                request = FileUtils.readFileToString(new File(args[0]));

            }catch (java.io.IOException e){
                System.err.println("error while attempting to read request file");
                exit(-1);
            }*/
            actionName ="VIEW";
            resource="./Id05.geniff";
            fileName = resource;
            userName="John Doe";
            role="physician";
            request = createXACMLRequest(userName, role, actionName, fileName);

            /*actionName ="VIEWCHROMOSOME";
            resource="./Id05.geniff#2";
            fileName = resource;
            userName="JaneDoe";
            role="researcher";*/
            request = createXACMLRequest(userName, role, actionName, fileName);

        }

        String XACML_policy_filename = null; //null is for default
        String filename_without_ref;
        if (fileName.contains("#")) {
            filename_without_ref = fileName.substring(0, fileName.lastIndexOf('#'));
        }else{
            filename_without_ref = fileName;
        }
        if(FilenameUtils.getExtension(filename_without_ref).equalsIgnoreCase("geniff")){
            executeAction(fileName,"EXTRACT_XACML");
            String filename_dir = FilenameUtils.getPath(fileName);
            if (filename_dir.isEmpty()) filename_dir=".";
            File directory= new File(filename_dir);
            if (directory.isDirectory()){
                boolean xacml_found = false;
                for(File file:directory.listFiles()){
                    if(FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xacml")){
                        if (xacml_found){
                            System.err.println("too many XACML in the directory");
                            exit(-1);
                        }else{
                            XACML_policy_filename = file.getAbsolutePath();
                            xacml_found = true;
                        }
                    }
                }
            }else{
                System.err.println("Error in the geniff file location provided: file location is \""+filename_dir+"\"");
                exit(-1);
            }
        }





        System.out.println("\n======================== XACML Request ====================");
        System.out.println(request);
        System.out.println("===========================================================");

        String decision = evaluateRequest(request, XACML_policy_filename);

        if (decision.equals("Permit")){
            System.out.println("\n" + userName + " is authorized to perform this action\n\n");
            executeAction(resource, actionName);
        } else {
            System.out.println("\n" + userName + " is NOT authorized to perform this action\n");
        }

    }

    private static String evaluateRequest(String request, String XACML_policy){
        Balana balana = new Balana();
        String response = balana.evaluateRequest(request,XACML_policy);
        return XACMLparser.getDecision(response);
    }

    private static String evaluateRequest(String request) {
        return evaluateRequest(request,null);
    }


    private static void executeAction(String resource, String actionType ) {
        CommandFactory commandFactory = new CommandFactory();//inyectar esta dependencia
        Command action = commandFactory.getCommand(actionType);
        action.execute(resource);
    }

    private static void initData(){
        idMap.put("1" , "VIEW");
        idMap.put("2" , "VIEWCHROMOSOME");
    }


    public static void printDescription(){

        System.out.println("\n This is an application that have implemented some access " +
                "control over genomic information using XACML policies. Users has  been separated " +
                "in to two groups and it has been put a limit on accesing genomic information.\n");

    }

    public static String createXACMLRequest(String userName,String role, String actionName, String resource){


        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\"\n" +
                "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "  xsi:schemaLocation=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17 http://docs.oasis-open.org/xacml/3.0/xacml-core-v3-schema-wd-17.xsd\"\n" +
                "  ReturnPolicyIdList=\"true\" CombinedDecision=\"true\">\n" +
                " \n" +
                "  <Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n" +
                "    <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" IncludeInResult=\"false\">\n" +
                "      <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">"+ userName +"</AttributeValue>\n" +
                "    </Attribute>\n" +
                "  </Attributes>\n" +
                "  \n" +
                "  <Attributes Category=\"urn:oasis:names:tc:xacml:3.0:role\">\n" +
                "    <Attribute AttributeId=\"role\" IncludeInResult=\"true\">\n" +
                "      <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + role + "</AttributeValue>\n" +
                "    </Attribute>\n" +
                "  </Attributes>\n" +
                "  \n" +
                "  <Attributes Category=\"urn:oasis:names:tc:xacml:3.0:count\">\n" +
                "    <Attribute AttributeId=\"countView\" IncludeInResult=\"true\">\n" +
                "      <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#integer\">1</AttributeValue>\n" +
                "    </Attribute>\n" +
                "  </Attributes>\n" +
                "  \n" +
                "  <Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n" +
                "    <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\"\n" +
                "      IncludeInResult=\"false\">\n" +
                "      <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + resource +
                "</AttributeValue>\n" +
                "    </Attribute>\n" +
                "  </Attributes>\n" +
                "  \n" +
                "  <Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
                "    <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n" +
                "      <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + actionName + "</AttributeValue>\n" +
                "    </Attribute>\n" +
                "  </Attributes>\n" +
                "</Request>\n";
    }
}
