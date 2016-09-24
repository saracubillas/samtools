package app;

import Infrastructure.Balana;
import application.service.Command;
import application.service.CommandFactory;
import application.service.XACMLparser;

public class Console {

    private Command action;

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: <request_XACML> <policy_path> ");
            System.exit(1);
        }

        String request_path = args[0];
        String policy_path = args[1];


        String decision = evaluateRequest(request_path, policy_path);
        if (decision.equals("Permit")){
            System.out.println("\n=== OK");
            executeAction(request_path);
        } else {
            System.out.println("Permision denied");
        }
    }

    private static void executeAction(String request_path) {
        String resource = XACMLparser.getResource(request_path);
        //pillar la accion de la request
        String actionType = "VIEW";

        CommandFactory commandFactory = new CommandFactory();//inyectar esta dependencia
        Command action = commandFactory.getCommand(actionType);
        executeAction(action, resource);
    }

    private static String evaluateRequest(String request_path, String policy_path) {
        Balana balana = new Balana();
        String response = balana.evaluateRequest(request_path, policy_path);
        return XACMLparser.getDecision(response);
    }

    private static void executeAction(Command action, String resource) {
        action.execute(resource);
    }


}
