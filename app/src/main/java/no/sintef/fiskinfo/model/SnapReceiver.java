package no.sintef.fiskinfo.model;

public class SnapReceiver {
    // ignore long snapMessageID
    // ignore long snapUserID

    public String receiverEmail;

    public SnapReceiver() {
    }

    public SnapReceiver(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

}
