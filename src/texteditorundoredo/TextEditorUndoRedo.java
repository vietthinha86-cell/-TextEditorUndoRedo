package texteditorundoredo;

import java.util.Scanner;

public class TextEditorUndoRedo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TextEditorManager editor = new TextEditorManager(10);
        StateVisualizer visualizer = new StateVisualizer(editor);

        int choice;

        do {
            System.out.println("\n========== TEXT EDITOR UNDO/REDO ==========");
            System.out.println("Current Text:ditmemaykhoa \"" + editor.getCurrentText() + "\"");
            System.out.println("-------------------------------------------");
            System.out.println("1. Type text at end");
            System.out.println("2. Insert text at position");
            System.out.println("3. Delete text by range");
            System.out.println("4. Undo");
            System.out.println("5. Redo");
            System.out.println("6. Display full state");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // clear Enter

            switch (choice) {

                case 1:
                    System.out.print("Enter text to type: ");
                    String text = scanner.nextLine();

                    editor.typeText(text);

                    System.out.println("Text typed successfully.");
                    break;

                case 2:
                    System.out.print("Enter position: ");
                    int insertPosition = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter text to insert: ");
                    String insertText = scanner.nextLine();

                    editor.insertText(insertPosition, insertText);

                    System.out.println("Insert operation finished.");
                    break;

                case 3:
                    System.out.print("Enter start position: ");
                    int start = scanner.nextInt();

                    System.out.print("Enter end position: ");
                    int end = scanner.nextInt();
                    scanner.nextLine();

                    editor.deleteText(start, end);

                    System.out.println("Delete operation finished.");
                    break;

                case 4:
                    editor.performUndo();
                    System.out.println("Undo operation finished.");
                    break;

                case 5:
                    editor.performRedo();
                    System.out.println("Redo operation finished.");
                    break;

                case 6:
                    visualizer.displayFullState();
                    break;

                case 0:
                    System.out.println("Program ended.");
                    break;

                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }

        } while (choice != 0);

        scanner.close();
    }
}