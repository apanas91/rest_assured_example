package model;

public class MessageReq {
    private String text;
    private int recipient;

    public MessageReq(String text, int recipient) {
        this.text = text;
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }
}
