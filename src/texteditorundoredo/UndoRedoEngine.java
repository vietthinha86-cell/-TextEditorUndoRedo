package texteditorundoredo;

public class UndoRedoEngine {

    private BoundedStack<ActionBatch> undoStack;
    private BoundedStack<ActionBatch> redoStack;
    private final int capacity;

    public UndoRedoEngine(int capacity) {

        this.capacity = capacity;
        undoStack = new BoundedStack<>(capacity);
        redoStack = new BoundedStack<>(capacity);
    }

    public void saveAction(ActionBatch action) {

        undoStack.push(action);
        clearRedoStack();
    }

    public ActionBatch undo() {

        if (undoStack.isEmpty()) {
            return null;
        }

        ActionBatch latestAction = undoStack.pop();
        redoStack.push(latestAction);

        return latestAction;
    }

    public ActionBatch redo() {

        if (redoStack.isEmpty()) {
            return null;
        }

        ActionBatch latestAction = redoStack.pop();
        undoStack.push(latestAction);

        return latestAction;
    }

    public void clearRedoStack() {
        redoStack.clear();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public BoundedStack<ActionBatch> getUndoStack() {
        return undoStack;
    }

    public BoundedStack<ActionBatch> getRedoStack() {
        return redoStack;
    }

    public int getCapacity() {
        return capacity;
    }
}
