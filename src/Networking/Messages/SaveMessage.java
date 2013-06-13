/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Messages;

import Database.PlayerData;

/**
 *
 * @author Shiyang
 * The save message encapsulates a player data to be shipped off to
 * the Server to be saved into the database
 */
public class SaveMessage extends Message{
    private PlayerData saveData;
    public SaveMessage(PlayerData saveData) {
        this.saveData = saveData;
    }

    public PlayerData getSaveData() {
        return saveData;
    }
}
