package texteditorundoredo;

import java.time.LocalDateTime;

public class TextEditorManager {

    private final StringBuilder currentText;
    private final UndoRedoEngine engine;
    private ActionBatch currentBatch;

    private int batchCounter = 0;
    private int stateCounter = 0;

    public TextEditorManager(int capacity) {

        currentText = new StringBuilder();
        engine = new UndoRedoEngine(capacity);
        currentBatch = null;
    }

    public void typeText(String text) {

        if (text == null || text.isEmpty()) {
            return;
        }

        for (int i = 0; i < text.length(); i++) {
            String input = String.valueOf(text.charAt(i));
            int position = currentText.length();
            boolean isContinuous = isContinuousInsert(position);

            saveStateWithBatching(ActionType.INSERT, input, position, isContinuous);
            currentText.insert(position, input);
        }
    }

    public void insertText(int position, String text) {

        if (text == null || text.isEmpty()) {
            return;
        }

        if (position < 0 || position > currentText.length()) {
            System.out.println("Invalid insert position!");
            return;
        }

        for (int i = 0; i < text.length(); i++) {
            String input = String.valueOf(text.charAt(i));
            int insertPosition = position + i;
            boolean isContinuous = (i > 0) || isContinuousInsert(insertPosition);

            saveStateWithBatching(ActionType.INSERT, input, insertPosition, isContinuous);
            currentText.insert(insertPosition, input);
        }
    }

    public void deleteText(int start, int end) {

        if (start < 0 || end > currentText.length() || start >= end) {
            System.out.println("Invalid delete range!");
            return;
        }

        String deletedText = currentText.substring(start, end);
        boolean isContinuous = isContinuousDelete(start, deletedText.length());

        saveStateWithBatching(ActionType.DELETE, deletedText, start, isContinuous);
        currentText.delete(start, end);
    }

    public void saveStateWithBatching(String actionType, String newInput,
            boolean isContinuous) {

        ActionType type = parseActionType(actionType);

        if (type == ActionType.INSERT) {
            int position = currentText.length();
            saveStateWithBatching(type, newInput, position, isContinuous);
            currentText.insert(position, newInput);
            return;
        }

        if (type == ActionType.DELETE) {
            int start = Math.max(0, currentText.length() - newInput.length());
            saveStateWithBatching(type, newInput, start, isContinuous);
            currentText.delete(start, currentText.length());
        }
    }

    private void saveStateWithBatching(ActionType actionType, String newInput,
            int position, boolean isContinuous) {

        if (newInput == null || newInput.isEmpty()) {
            return;
        }

        boolean canMerge = currentBatch != null
                && currentBatch.canMerge(actionType, isContinuous,
                        position, newInput.length());

        if (canMerge) {
            currentBatch.merge(newInput, position);
        } else {
            batchCounter++;

            currentBatch = new ActionBatch(
                    batchCounter,
                    actionType,
                    newInput,
                    position
            );

            engine.saveAction(currentBatch);
        }

        engine.clearRedoStack();
    }

    public void performUndo() {

        if (!engine.canUndo()) {
            System.out.println("Nothing to undo.");
            return;
        }

        ActionBatch action = engine.undo();
        applyReverse(action);
        currentBatch = null;
    }

    public void performRedo() {

        if (!engine.canRedo()) {
            System.out.println("Nothing to redo.");
            return;
        }

        ActionBatch action = engine.redo();
        applyAction(action);
        currentBatch = null;
    }

    private void applyAction(ActionBatch action) {

        if (action == null) {
            return;
        }

        if (action.getActionType() == ActionType.INSERT) {

            int position = Math.min(action.getStartPosition(), currentText.length());
            currentText.insert(position, action.getBatchedText());

        } else if (action.getActionType() == ActionType.DELETE) {

            int start = action.getStartPosition();
            int end = start + action.length();

            if (start >= 0 && end <= currentText.length()) {
                currentText.delete(start, end);
            }
        }
    }

    private void applyReverse(ActionBatch action) {

        if (action == null) {
            return;
        }

        if (action.getActionType() == ActionType.INSERT) {

            int start = action.getStartPosition();
            int end = start + action.length();

            if (start >= 0 && end <= currentText.length()) {
                currentText.delete(start, end);
            }

        } else if (action.getActionType() == ActionType.DELETE) {

            int position = Math.min(action.getStartPosition(), currentText.length());
            currentText.insert(position, action.getBatchedText());
        }
    }

    private boolean isContinuousInsert(int position) {

        return currentBatch != null
                && currentBatch.getActionType() == ActionType.INSERT
                && position == currentBatch.getEndPosition();
    }

    private boolean isContinuousDelete(int start, int length) {

        return currentBatch != null
                && currentBatch.getActionType() == ActionType.DELETE
                && (start == currentBatch.getStartPosition()
                || start + length == currentBatch.getStartPosition());
    }

    private ActionType parseActionType(String actionType) {

        if (actionType == null) {
            throw new IllegalArgumentException("Action type must not be null");
        }

        String normalized = actionType.trim().toUpperCase();

        if (normalized.equals("TYPE") || normalized.equals("INSERT")) {
            return ActionType.INSERT;
        }

        if (normalized.equals("DELETE")) {
            return ActionType.DELETE;
        }

        throw new IllegalArgumentException("Unsupported action type: " + actionType);
    }

    public String getCurrentText() {
        return currentText.toString();
    }

    public DocumentState getCurrentState(String actionDescription) {

        stateCounter++;

        return new DocumentState(
                stateCounter,
                currentText.toString(),
                actionDescription,
                LocalDateTime.now().toString()
        );
    }

    public ActionBatch getCurrentBatch() {
        return currentBatch;
    }

    public UndoRedoEngine getEngine() {
        return engine;
    }
}
