/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package texteditorundoredo;

/**
 *
 * @author ADMIN
 */

public class ActionBatch {

    private int batchId;
    private String actionType;
    private String batchedText;
    private int startPosition;
    private int endPosition;

    public ActionBatch(int batchId,
            String actionType,
            int startPosition,
            int endPosition) {

        this.batchId = batchId;
        this.actionType = actionType;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.batchedText = "";
    }

    public void addCharacter(char c) {
        batchedText += c;
    }

    public void mergeAction(String text) {
        batchedText += text;
    }

    public boolean isSameActionType(String type) {
        return actionType.equals(type);
    }

    public String getBatchedText() {
        return batchedText;
    }
}
