package bean;

public class Content {
    String Content;
    String Type;
    Long Date = System.currentTimeMillis();
    String SenderUUID;
    String RecipientUUID;
    String Origin;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Content() {
    }


    public Content(String content, String type, String senderUUID, String recipientUUID, String origin) {
        Content = content;
        Type = type;
        SenderUUID = senderUUID;
        RecipientUUID = recipientUUID;
        Origin = origin;
    }

    public Long getDate() {
        return Date;
    }

    public String getSenderUUID() {
        return SenderUUID;
    }

    public void setSenderUUID(String senderUUID) {
        SenderUUID = senderUUID;
    }

    public String getRecipientUUID() {
        return RecipientUUID;
    }

    public void setRecipientUUID(String recipientUUID) {
        RecipientUUID = recipientUUID;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }
}

