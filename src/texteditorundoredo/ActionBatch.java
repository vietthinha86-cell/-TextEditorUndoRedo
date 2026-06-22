package texteditorundoredo;

public class ActionBatch {

    private int batchId;
    private ActionType actionType;
    private StringBuilder batchedText;
    private int startPosition;
    private int endPosition;

    public ActionBatch(int batchId, ActionType actionType,
            String batchedText, int startPosition) {

        if (actionType == null) {
            throw new IllegalArgumentException("Action type must not be null");
        }

        if (batchedText == null) {
            batchedText = "";
        }

        this.batchId = batchId;
        this.actionType = actionType;
        this.batchedText = new StringBuilder(batchedText);
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
        return batchedText.toString();
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

    public boolean canMerge(ActionType type, boolean isContinuous,
            int inputPosition, int inputLength) {

        if (type == null || !isContinuous || !isSameActionType(type)) {
            return false;
        }

        if (inputLength <= 0) {
            return false;
        }

        if (actionType == ActionType.INSERT) {
            return inputPosition == endPosition;
        }

        if (actionType == ActionType.DELETE) {
            boolean deleteForward = inputPosition == startPosition;
            boolean backspaceStyle = inputPosition + inputLength == startPosition;
            return deleteForward || backspaceStyle;
        }

        return false;
    }

    public void merge(String newInput, int inputPosition) {

        if (newInput == null || newInput.isEmpty()) {
            return;
        }

        if (actionType == ActionType.INSERT) {
            batchedText.append(newInput);
            endPosition = startPosition + batchedText.length();
            return;
        }

        if (actionType == ActionType.DELETE) {
            if (inputPosition + newInput.length() == startPosition) {
                batchedText.insert(0, newInput);
                startPosition = inputPosition;
            } else {
                batchedText.append(newInput);
            }

            endPosition = startPosition + batchedText.length();
        }
    }

    public String getActionDescription() {
        return actionType
                + " text=\"" + getBatchedText() + "\""
                + " at position " + startPosition;
    }

    @Override
    public String toString() {
        return "Batch #" + batchId
                + " [" + actionType
                + ", text=\"" + getBatchedText()
                + "\", start=" + startPosition
                + ", end=" + endPosition + "]";
    }
}
