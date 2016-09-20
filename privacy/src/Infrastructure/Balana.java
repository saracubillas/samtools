package Infrastructure;


import org.wso2.balana.ConfigurationStore;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Balana {

    private static org.wso2.balana.Balana balana;
    private static String config_path = "../../../config/config_rbac.xml";


    public static String evaluateRequest(String request_path, String policy_path) {

        initBalana(config_path, policy_path);


        try {

            byte[] encoded = Files.readAllBytes(Paths.get(request_path));
            String request = new String(encoded, StandardCharsets.UTF_8);

            PDP pdp = getPDPNewInstance();

            String response = pdp.evaluate(request);


            System.out.println("\n======================== XACML Response ===================");
            System.out.println(response);
            System.out.println("===========================================================");


            return response;
           /* if (readXML(response).equals("Permit")){
                System.out.println("\n=== OK");
                viewSAM(sam_file);

            } else {
                System.out.println("Permision denied");
            }*/

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());

        }
        return "";
    }

    private static void initBalana(String config_path, String policy_path) {

        System.setProperty(ConfigurationStore.PDP_CONFIG_PROPERTY, config_path);

        System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, policy_path);

        balana = org.wso2.balana.Balana.getInstance();
    }

    private static PDP getPDPNewInstance() {

        PDPConfig pdpConfig = balana.getPdpConfig();

        return new PDP(pdpConfig);
    }
}
