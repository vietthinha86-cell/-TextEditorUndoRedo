package texteditorundoredo;

public class ActionBatch {

    private int batchId;
    private ActionType actionType;
    private String batchedText;
    private int startPosition;
    private int endPosition;

    public ActionBatch(int batchId, ActionType actionType,
                       String batchedText, int startPosition) {

        this.batchId = batchId;
        this.actionType = actionType;
        this.batchedText = batchedText;
        this.startPosition = startPosition;
        this.endPosition = startPosition + batchedText.length();
    }

    public int getBatchId() {
        return batchId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public String getBatchedText() {
        return batchedText;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public int length() {
        return batchedText.length();
    }

    public boolean isSameActionType(ActionType type) {
        return this.actionType == type;
    }

    public String getActionDescription() {
        return actionType
                + " text=\"" + batchedText + "\""
                + " at position " + startPosition;
    }

    @Override
    public String toString() {
        return "Batch #" + batchId
                + " [" + actionType
                + ", text=\"" + batchedText
                + "\", start=" + startPosition
                + ", end=" + endPosition + "]";
    }
}