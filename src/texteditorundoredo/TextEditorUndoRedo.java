/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package texteditorundoredo;

/**
 *
 * @author ADMIN
 */
public class TextEditorUndoRedo {
   public static void main(String[] args) {

        TextEditorManager editor =
                new TextEditorManager(10);

        StateVisualizer visualizer =
                new StateVisualizer(editor);

        System.out.println("===== TYPE TEXT =====");

        editor.typeText("Hello");
        editor.typeText(" World");

        visualizer.displayCurrentText();

        System.out.println("\n===== DELETE =====");

        editor.deleteText(5, 11);

        visualizer.displayCurrentText();

        System.out.println("\n===== UNDO =====");

        editor.performUndo();

        visualizer.displayCurrentText();

        System.out.println("\n===== REDO =====");

        editor.performRedo();

        visualizer.displayCurrentText();

        System.out.println("\n===== SYSTEM STATE =====");

        visualizer.displayFullState();
    }
}  
    

