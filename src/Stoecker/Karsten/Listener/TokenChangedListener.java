package Stoecker.Karsten.Listener;


public interface TokenChangedListener
{
    /**
     * Method which should be called, if a token changed. This could be used to inform listener about required actions (e.g. show actualized token at gui).
     */
    public void tokenChanged();
}
