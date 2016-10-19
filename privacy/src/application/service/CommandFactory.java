package application.service;

public class CommandFactory {

        public Command getCommand(String commandType){
            if(commandType == null){
                return null;
            }
            if(commandType.equalsIgnoreCase("VIEW")){
                return new ViewSAM();
            } else if(commandType.equalsIgnoreCase("INDEX")){
                return new Index();
            } else if(commandType.equalsIgnoreCase("VIEWCHROMOSOME")){
                return new ViewChromosome();
            } else if(commandType.equalsIgnoreCase("SORT")){
                return new Sort();
            } else if (commandType.equalsIgnoreCase("EXTRACT_XACML")){
                return new XACMLextractor();
            } else if (commandType.equalsIgnoreCase("EXTRACT_DATA")){
                return new DataExtractor();
            }

            return null;
        }

}
