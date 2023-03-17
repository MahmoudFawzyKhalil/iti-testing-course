package gov.iti.jets.testing.day2.shopping.infrastructure.gateway;

public class SmsGateway {

    private static final SmsGateway INSTANCE = new SmsGateway();

    private SmsGateway() {
    }

    public static SmsGateway getInstance() {
        return INSTANCE;
    }

    public void sendSms(String phoneNumber, String message) {
        // Takes a long time
        // Sends an SMS to the actual phoneNumber
        // Costs you money to send the SMS
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Message {%s} sent to phone number %s".formatted(message, phoneNumber));
    }
}
