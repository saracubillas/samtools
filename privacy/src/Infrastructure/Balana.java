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


    public static String evaluateRequest(String request_path) {
        initBalana();
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(request_path));
            String request = new String(encoded, StandardCharsets.UTF_8);
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

    private static void initBalana() {
        // using file based policy repository. so set the policy location as system property
        String policyLocation = null;
        String configPath = null;
        try {
            policyLocation = (new File(".")).getCanonicalPath() + File.separator + "policy";
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
