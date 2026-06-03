/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package texteditorundoredo;

/**
 *
 * @author ADMIN
 */

public class DocumentState {

    private int stateId;
    private String content;
    private String actionDescription;
    private String createdTime;

    public DocumentState(int stateId, String content,
            String actionDescription, String createdTime) {
        this.stateId = stateId;
        this.content = content;
        this.actionDescription = actionDescription;
        this.createdTime = createdTime;
    }

    public String getContent() {
        return content;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void deleteText(int start, int end) {
        if (start >= 0 && end <= content.length() && start < end) {
            content = content.substring(0, start)
                    + content.substring(end);
        }
    }

    public void displayState() {
        System.out.println("State ID: " + stateId);
        System.out.println("Content: " + content);
        System.out.println("Action: " + actionDescription);
        System.out.println("Time: " + createdTime);
    }
}