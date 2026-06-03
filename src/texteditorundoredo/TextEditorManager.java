/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package texteditorundoredo;

/**
 *
 * @author ADMIN
 */

import java.time.LocalDateTime;

public class TextEditorManager {

    private StringBuilder currentText;
    private UndoRedoEngine engine;
    private ActionBatch currentBatch;

    private int stateCounter = 0;

    public TextEditorManager(int capacity) {

        currentText = new StringBuilder();
        engine = new UndoRedoEngine(capacity);
    }

    public void typeText(String text) {

        saveCurrentState("Before Typing");

        currentText.append(text);
    }

    public void deleteText(int start, int end) {

        if (start < 0 || end > currentText.length() || start >= end) {
            System.out.println("Invalid delete range!");
            return;
        }

        saveCurrentState("Before Delete");

        currentText.delete(start, end);
    }

    public void performUndo() {

        DocumentState currentState =
                createState("Current State");

        DocumentState previousState =
                engine.undo(currentState);

        currentText = new StringBuilder(previousState.getContent());
    }

    public void performRedo() {

        DocumentState currentState =
                createState("Current State");

        DocumentState nextState =
                engine.redo(currentState);

        currentText = new StringBuilder(nextState.getContent());
    }

    public String getCurrentText() {
        return currentText.toString();
    }

    public void commitCurrentBatch() {

        if (currentBatch != null) {
            System.out.println("Batch committed.");
        }
    }

    private void saveCurrentState(String action) {

        DocumentState state = createState(action);

        engine.saveState(state);
    }

    private DocumentState createState(String action) {

        stateCounter++;

        return new DocumentState(
                stateCounter,
                currentText.toString(),
                action,
                LocalDateTime.now().toString()
        );
    }

    public UndoRedoEngine getEngine() {
        return engine;
    }
}