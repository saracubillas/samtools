package Infrastructure;


import org.wso2.balana.ConfigurationStore;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Balana {

    private static org.wso2.balana.Balana balana;


    public static String evaluateRequest(String request){
        return evaluateRequest(request, null);
    }

    public static String evaluateRequest(String request, String policyFileName) {
        initBalana(policyFileName);
        try {
            PDP pdp = getPDPNewInstance();
            String response = pdp.evaluate(request);

            System.out.println("\n======================== XACML Response ===================");
            System.out.println(response);
            System.out.println("===========================================================");

            return response;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());

        }
        return "";
    }

    private static void initBalana(){
        initBalana(null);
    }

    private static void initBalana(String policyFilename) {
        // using file based policy repository. so set the policy location as system property
        String policyLocation = null;
        String configPath = null;
        try {
            if (policyFilename==null) {
                policyLocation = (new File(".")).getCanonicalPath() + File.separator + "policy/XACMLPolicy.xml";
            }else{
                policyLocation = policyFilename;
            }
            System.out.println("policyLocation: "+policyLocation);
            System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, policyLocation);
        } catch (IOException e) {
            System.err.println("Can not locate policy repository");
        }

        try {
            configPath = (new File(".")).getCanonicalPath() + File.separator + "config/config_rbac.xml";
            System.setProperty(ConfigurationStore.PDP_CONFIG_PROPERTY, configPath);
        } catch (IOException e) {
            System.err.println("Can not locate configuration repository");
        }


        balana = org.wso2.balana.Balana.getInstance();
    }

    private static PDP getPDPNewInstance() {

        PDPConfig pdpConfig = balana.getPdpConfig();

        return new PDP(pdpConfig);
    }
}
