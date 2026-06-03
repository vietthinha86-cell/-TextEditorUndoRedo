/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package texteditorundoredo;

/**
 *
 * @author ADMIN
 */

public class UndoRedoEngine {

    private BoundedStack<DocumentState> undoStack;
    private BoundedStack<DocumentState> redoStack;
    private int capacity;

    public UndoRedoEngine(int capacity) {

        this.capacity = capacity;

        undoStack = new BoundedStack<>(capacity);
        redoStack = new BoundedStack<>(capacity);
    }

    public void saveState(DocumentState state) {

        undoStack.push(state);
        clearRedoStack();
    }

    public DocumentState undo(DocumentState currentState) {

        if (undoStack.isEmpty()) {
            return currentState;
        }

        redoStack.push(currentState);

        return undoStack.pop();
    }

    public DocumentState redo(DocumentState currentState) {

        if (redoStack.isEmpty()) {
            return currentState;
        }

        undoStack.push(currentState);

        return redoStack.pop();
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

    public BoundedStack<DocumentState> getUndoStack() {
        return undoStack;
    }

    public BoundedStack<DocumentState> getRedoStack() {
        return redoStack;
    }
}
