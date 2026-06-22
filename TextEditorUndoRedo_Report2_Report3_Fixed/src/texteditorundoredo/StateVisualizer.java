package texteditorundoredo;

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

        for (ActionBatch action :
                manager.getEngine()
                        .getUndoStack()
                        .getItems()) {

            System.out.println(action);
        }
    }

    public void displayRedoStack() {
        System.out.println("\nREDO STACK:");

        for (ActionBatch action :
                manager.getEngine()
                        .getRedoStack()
                        .getItems()) {

            System.out.println(action);
        }
    }

    public void displayDocumentState() {
        System.out.println("\nDOCUMENT STATE:");

        DocumentState state =
                manager.getCurrentState("Display Current State");

        state.displayState();
    }

    public void displayFullState() {
        displayCurrentText();
        displayUndoStack();
        displayRedoStack();
        displayDocumentState();
    }
}