package pl.sudokusolver.app;

public class Singleton {
    private static Singleton instance = null;

    private boolean blocked = false;

    private Singleton(){}

    /**
     * Function to check whether input is blocked or not
     * @return true if it is, false otherwise
     */
    public boolean isBlocked(){
        return blocked;
    }

    /**
     * Function to block interaction with app
     */
    public void block(){
        blocked = true;
    }

    /**
     * Function to unblock interaction with app
     */
    public void unblock(){
        blocked = false;
    }

    /**
     * static method to create singleton class
     * @return created singleton
     */
    public static Singleton getInstance(){
        if (instance == null)
            instance = new Singleton();

        return instance;
    }
}
