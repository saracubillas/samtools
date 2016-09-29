package app;

import Infrastructure.Balana;
import application.service.Command;
import application.service.CommandFactory;
import application.service.XACMLparser;

public class Console {

    private Command action;

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: <request_XACML>");
            System.exit(1);
        }
        String request_path = args[0];

        String decision = evaluateRequest(request_path);
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

    private static String evaluateRequest(String request_path) {
        Balana balana = new Balana();
        String response = balana.evaluateRequest(request_path);
        return XACMLparser.getDecision(response);
    }

    private static void executeAction(Command action, String resource) {
        action.execute(resource);
    }


}
