package app;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;


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

        printDescription();

        initData();


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
        }


       /* String request = createXACMLRequest(userName, role, actionName, chromosomeRef);
        //String request = createXACMLRequest("JohnDoe", "physician", "VIEW", 40);
        PDP pdp = getPDPNewInstance();

        System.out.println("\n======================== XACML Request ====================");
        System.out.println(request);
        System.out.println("===========================================================");

        String response = pdp.evaluate(request);

        System.out.println("\n======================== XACML Response ===================");
        System.out.println(response);
        System.out.println("===========================================================");

        try {
            ResponseCtx responseCtx = ResponseCtx.getInstance(getXacmlResponse(response));
            AbstractResult result  = responseCtx.getResults().iterator().next();
            if(AbstractResult.DECISION_PERMIT == result.getDecision()){
                System.out.println("\n" + userName + " is authorized to perform this purchase\n\n");
            } else {
                System.out.println("\n" + userName + " is NOT authorized to perform this purchase\n");
                List<Advice> advices = result.getAdvices();
                for(Advice advice : advices){
                    List<AttributeAssignment> assignments = advice.getAssignments();
                    for(AttributeAssignment assignment : assignments){
                        System.out.println("Advice :  " + assignment.getContent() +"\n\n");
                    }
                }
            }
        } catch (ParsingException e) {
            e.printStackTrace();
        }
*/
    }
    private static void initData(){
        idMap.put("1" , "VIEW");
        idMap.put("2" , "VIEWCHROMOSOME");
    }

   /* private static void initBalana(){

        try{
            // using file based policy repository. so set the policy location as system property
            String policyLocation = (new File(".")).getCanonicalPath() + File.separator + "resources";
            System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, policyLocation);
        } catch (IOException e) {
            System.err.println("Can not locate policy repository");
        }
        // create default instance of Balana
        balana = Balana.getInstance();
    }

*
     * Returns a new PDP instance with new XACML policies
     *
     * @return a  PDP instance


    private static PDP getPDPNewInstance(){

        PDPConfig pdpConfig = balana.getPdpConfig();

        // registering new attribute finder. so default PDPConfig is needed to change
        AttributeFinder attributeFinder = pdpConfig.getAttributeFinder();
        List<AttributeFinderModule> finderModules = attributeFinder.getModules();
        finderModules.add(new SampleAttributeFinderModule());
        attributeFinder.setModules(finderModules);

        return new PDP(new PDPConfig(attributeFinder, pdpConfig.getPolicyFinder(), null, true));
    }

    public static int calculateTotal(String productName, int amount){

        String priceString = priceMap.get(productName);
        return Integer.parseInt(priceString)*amount;

    }

*
     * Creates DOM representation of the XACML request
     *
     * @param response  XACML request as a String object
     * @return XACML request as a DOM element


    public static Element getXacmlResponse(String response) {

        ByteArrayInputStream inputStream;
        DocumentBuilderFactory dbf;
        Document doc;

        inputStream = new ByteArrayInputStream(response.getBytes());
        dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        try {
            doc = dbf.newDocumentBuilder().parse(inputStream);
        } catch (Exception e) {
            System.err.println("DOM of request element can not be created from String");
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                System.err.println("Error in closing input stream of XACML response");
            }
        }
        return doc.getDocumentElement();
    }
*/
    public static void printDescription(){

        System.out.println("\n This is an application that have implemented some access " +
                "control over genomic information using XACML policies. Users has  been separated " +
                "in to two groups and it has been put a limit on accesing genomic information.\n");

    }

    /*public static String createXACMLRequest(String userName,String role, String actionName, String chromosomeRef){

        return "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n" +
                "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
                "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">buy</AttributeValue>\n" +
                "</Attribute>\n" +
                "</Attributes>\n" +
                "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n" +
                "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + userName +"</AttributeValue>\n" +
                "</Attribute>\n" +
                "</Attributes>\n" +
                "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n" +
                "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + resource + "</AttributeValue>\n" +
                "</Attribute>\n" +
                "</Attributes>\n" +
                "<Attributes Category=\"http://kmarket.com/category\">\n" +
                "<Attribute AttributeId=\"http://kmarket.com/id/amount\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#integer\">" + amount + "</AttributeValue>\n" +
                "</Attribute>\n" +
                "<Attribute AttributeId=\"http://kmarket.com/id/totalAmount\" IncludeInResult=\"false\">\n" +
                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#integer\">" + totalAmount + "</AttributeValue>\n" +
                "</Attribute>\n" +
                "</Attributes>\n" +
                "</Request>";

    }*/
}
