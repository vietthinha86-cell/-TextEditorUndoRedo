package texteditorundoredo;

import java.time.LocalDateTime;

public class TextEditorManager {

    private StringBuilder currentText;
    private UndoRedoEngine engine;

    private int batchCounter = 0;
    private int stateCounter = 0;

    public TextEditorManager(int capacity) {

        currentText = new StringBuilder();
        engine = new UndoRedoEngine(capacity);
    }

    public void typeText(String text) {

        if (text == null || text.isEmpty()) {
            return;
        }

        int position = currentText.length();

        currentText.append(text);

        batchCounter++;

        ActionBatch action = new ActionBatch(
                batchCounter,
                ActionType.INSERT,
                text,
                position
        );

        engine.saveAction(action);
    }

    public void insertText(int position, String text) {

        if (text == null || text.isEmpty()) {
            return;
        }

        if (position < 0 || position > currentText.length()) {
            System.out.println("Invalid insert position!");
            return;
        }

        currentText.insert(position, text);

        batchCounter++;

        ActionBatch action = new ActionBatch(
                batchCounter,
                ActionType.INSERT,
                text,
                position
        );

        engine.saveAction(action);
    }

    public void deleteText(int start, int end) {

        if (start < 0 || end > currentText.length() || start >= end) {
            System.out.println("Invalid delete range!");
            return;
        }

        String deletedText = currentText.substring(start, end);

        currentText.delete(start, end);

        batchCounter++;

        ActionBatch action = new ActionBatch(
                batchCounter,
                ActionType.DELETE,
                deletedText,
                start
        );

        engine.saveAction(action);
    }

    public void performUndo() {

        if (!engine.canUndo()) {
            System.out.println("Nothing to undo.");
            return;
        }

        ActionBatch action = engine.undo();

        applyReverse(action);
    }

    public void performRedo() {

        if (!engine.canRedo()) {
            System.out.println("Nothing to redo.");
            return;
        }

        ActionBatch action = engine.redo();

        applyAction(action);
    }

    private void applyAction(ActionBatch action) {

        if (action.getActionType() == ActionType.INSERT) {

            currentText.insert(
                    action.getStartPosition(),
                    action.getBatchedText()
            );

        } else if (action.getActionType() == ActionType.DELETE) {

            int start = action.getStartPosition();
            int end = start + action.length();

            if (start >= 0 && end <= currentText.length()) {
                currentText.delete(start, end);
            }
        }
    }

    private void applyReverse(ActionBatch action) {

        if (action.getActionType() == ActionType.INSERT) {

            int start = action.getStartPosition();
            int end = start + action.length();

            if (start >= 0 && end <= currentText.length()) {
                currentText.delete(start, end);
            }

        } else if (action.getActionType() == ActionType.DELETE) {

            currentText.insert(
                    action.getStartPosition(),
                    action.getBatchedText()
            );
        }
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

    public UndoRedoEngine getEngine() {
        return engine;
    }
}