package uk.me.eastmans.sap.b1;

/**
 * Created by markeastman on 26/07/2017.
 */
public class SapException extends Exception {
    public SapException( String reason )
    {
        super( reason );
    }
}
