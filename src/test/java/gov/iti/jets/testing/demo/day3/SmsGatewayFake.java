package gov.iti.jets.testing.demo.day3;

import gov.iti.jets.testing.infrastructure.gateway.SmsGateway;
import org.mockito.Mockito;

public class SmsGatewayFake extends SmsGateway {

    private final SmsGateway internalMock = Mockito.mock();

    public SmsGatewayFake() {
        super( "doesn't matter" );
    }

    @Override
    public void sendSms( String phoneNumber, String message ) {
        internalMock.sendSms( phoneNumber, message );
    }

    public void verifySmsSent( String phoneNumber, String message ) {
        Mockito.verify( internalMock )
                .sendSms( phoneNumber, message );
        Mockito.verifyNoMoreInteractions( internalMock );
    }
    public void verifyNoSmsSent() {
        Mockito.verifyNoInteractions( internalMock );
    }

}
