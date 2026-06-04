package texteditorundoredo;

public class DocumentState {

    private int stateId;
    private String content;
    private String actionDescription;
    private String createdTime;

    public DocumentState(int stateId, String content,
            String actionDescription, String createdTime) {

        this.stateId = stateId;
        this.content = content;
        this.actionDescription = actionDescription;
        this.createdTime = createdTime;
    }

    public int getStateId() {
        return stateId;
    }

    public String getContent() {
        return content;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void displayState() {
        System.out.println("State ID: " + stateId);
        System.out.println("Content: " + content);
        System.out.println("Action: " + actionDescription);
        System.out.println("Time: " + createdTime);
    }
}