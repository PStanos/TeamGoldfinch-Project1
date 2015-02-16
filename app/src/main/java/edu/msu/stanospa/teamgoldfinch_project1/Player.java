package edu.msu.stanospa.teamgoldfinch_project1;

public class Player {
    /**
     * The player's name
     */
    private String name;

    /**
     * Set the selected bird
     * @param selectedBird the selected bird
     */
    public void setSelectedBird(Bird selectedBird) {
        this.selectedBird = selectedBird;
    }

    /**
     * Get the selected bird
     * @return the selected bird
     */
    public Bird getSelectedBird() {
        return selectedBird;
    }

    /**
     * The selected bird
     */
    private Bird selectedBird;

    public Player() {

    }
}
