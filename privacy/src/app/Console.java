package app;

import Infrastructure.Balana;

public class Console {
    public static void main(String[] args) {
    /*    String request_path = "examples/request/XACMLRequest5.xml";
        String policy_path = "examples/policy";*/

        if (args.length < 2) {
            System.out.println("Usage: <request_XACML> <policy_path>");
            System.exit(1);
        }

        String request_path = args[0];
        String policy_path = args[1];

        evaluateRequest(request_path, policy_path);
    }

    private static void evaluateRequest(String request_path, String policy_path) {
        Balana balana = new Balana();
        balana.evaluateRequest(request_path, policy_path);
    }
}
