/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package texteditorundoredo;

/**
 *
 * @author ADMIN
 */

public class StateVisualizer {

    private TextEditorManager manager;

    public StateVisualizer(TextEditorManager manager) {
        this.manager = manager;
    }

    public void displayCurrentText() {

        System.out.println("Current Text:");
        System.out.println(manager.getCurrentText());
    }

    public void displayUndoStack() {

        System.out.println("\nUNDO STACK:");

        for (DocumentState state :
                manager.getEngine()
                        .getUndoStack()
                        .getItems()) {

            System.out.println(
                    state.getActionDescription()
            );
        }
    }

    public void displayRedoStack() {

        System.out.println("\nREDO STACK:");

        for (DocumentState state :
                manager.getEngine()
                        .getRedoStack()
                        .getItems()) {

            System.out.println(
                    state.getActionDescription()
            );
        }
    }

    public void displayFullState() {

        displayCurrentText();
        displayUndoStack();
        displayRedoStack();
    }
}