package texteditorundoredo;

public class TextEditorUndoRedo {

    public static void main(String[] args) {

        TextEditorManager editor = new TextEditorManager(10);

        StateVisualizer visualizer = new StateVisualizer(editor);

        System.out.println("===== TYPE TEXT =====");

        editor.typeText("Hello");
        editor.typeText(" World");

        visualizer.displayFullState();

        System.out.println("\n===== DELETE =====");

        editor.deleteText(5, 11);

        visualizer.displayFullState();

        System.out.println("\n===== UNDO DELETE =====");

        editor.performUndo();

        visualizer.displayFullState();

        System.out.println("\n===== UNDO TYPE WORLD =====");

        editor.performUndo();

        visualizer.displayFullState();

        System.out.println("\n===== REDO TYPE WORLD =====");

        editor.performRedo();

        visualizer.displayFullState();

        System.out.println("\n===== REDO DELETE =====");

        editor.performRedo();

        visualizer.displayFullState();
    }
}