package jon.android.WAM;


public class Patient {

    private String pId;

    private int Session;

    private Patient() {}

    public Patient(String pId,int session) {

        this.pId = pId;
        this.Session = session;
    }

    public String getpId() {
        return pId;
    }
    public int getSession() {
        return Session;
    }
}
