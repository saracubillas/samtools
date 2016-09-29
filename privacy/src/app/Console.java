package app;

import Infrastructure.Balana;
import application.service.Command;
import application.service.CommandFactory;
import application.service.XACMLparser;

public class Console {

    private Command action;

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: <request_XACML> <config_file> ");
            System.exit(1);
        }

        String request_path = args[0];

        String config_file = args[1];


        String decision = evaluateRequest(request_path, config_file);
        if (decision.equals("Permit")){
            System.out.println("\n=== OK");
            executeAction(request_path);
        } else {
            System.out.println("Permision denied");
        }
    }

    private static void executeAction(String request_path) {
        String resource = XACMLparser.getResource(request_path);
        String actionType = XACMLparser.getActionType(request_path);

        CommandFactory commandFactory = new CommandFactory();//inyectar esta dependencia
        Command action = commandFactory.getCommand(actionType);
        executeAction(action, resource);
    }

    private static String evaluateRequest(String request_path, String config_file) {
        Balana balana = new Balana();
        String response = balana.evaluateRequest(request_path, config_file);
        return XACMLparser.getDecision(response);
    }

    private static void executeAction(Command action, String resource) {
        action.execute(resource);
    }


}
