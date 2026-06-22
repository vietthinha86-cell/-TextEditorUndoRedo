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

        // Khi user gõ/xóa hành động mới,
        // redo cũ không còn hợp lệ nữa.
        clearRedoStack();
    }

    public ActionBatch undo() {

        if (undoStack.isEmpty()) {
            return null;
        }

        ActionBatch action = undoStack.pop();
        redoStack.push(action);

        return action;
    }

    public ActionBatch redo() {

        if (redoStack.isEmpty()) {
            return null;
        }

        ActionBatch action = redoStack.pop();
        undoStack.push(action);

        return action;
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
}