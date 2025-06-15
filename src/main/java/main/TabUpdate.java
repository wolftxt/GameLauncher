package main;

/**
 * Simple interface to update the UI when state changes.
 *
 * @author davidwolf
 */
public interface TabUpdate {

    public void addCard(int index);

    public void setMessage(int index, String message);
}
