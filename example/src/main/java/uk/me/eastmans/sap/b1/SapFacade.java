package uk.me.eastmans.sap.b1;

/**
 * Created by markeastman on 26/07/2017.
 */
public interface SapFacade {
    public SapUser authenticate(SapUserCredentials credentials ) throws InvalidUserCredentials;
}
