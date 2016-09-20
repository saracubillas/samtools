package app.Domain;


public class XACMLrequest {
    private String subject;
    private String resource;
    private String action;

    public XACMLrequest(String subject, String resource, String action) {
        this.subject = subject;
        this.resource = resource;
        this.action = action;
    }
}
