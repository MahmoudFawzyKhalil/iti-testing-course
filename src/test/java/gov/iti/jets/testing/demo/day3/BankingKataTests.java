package gov.iti.jets.testing.demo.day3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BankingKataTests {
    // Depositing increases balance
    // Negative amount not allowed
    // Withdrawing decreases balance
    // Date of transaction is correct
    // Deposit printed with +
    // Withdrawing printed with -
    // Extra:
    // Balance not allowed to become negative

    @Test
    void Deposit_increases_balance_by_deposit_amount() {
        // Arrange
        Clock fixedClock = createFixedClock();
        Account account = new Account( fixedClock );
        int depositAmount = 500;

        // Act
        account.deposit( depositAmount );

        // Assert
        String statement = account.printStatement();
        int actualBalance = extractFirstBalance( statement );

        Assertions.assertThat( actualBalance )
                .isEqualTo( depositAmount );
    }

    private static Clock createFixedClock() {
        Clock fixedClock = Clock.fixed( Instant.now(), ZoneId.systemDefault() );
        return fixedClock;
    }

    private static int extractFirstBalance( String statement ) {
        String firstTransaction = getFirstTransaction( statement );

        String balance = getBalance( firstTransaction );

        return Integer.parseInt( balance );
    }

    private static String getBalance( String transaction ) {
        return Arrays.stream( transaction.split( "\t" ) )
                .skip( 2 )
                .findAny()
                .orElseThrow();
    }

    private static String getFirstTransaction( String statement ) {
        return Arrays.stream( statement.split( "\n" ) )
                .skip( 1 )
                .findAny()
                .orElseThrow();
    }

}

class Account {

    private int balance;

    private final Clock clock;

    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
        clock = Clock.systemDefaultZone();
    }

    Account( Clock clock ) {
        this.clock = clock;
    }

    public void deposit( int amount ) {
        balance += amount;
        transactions.add( new Transaction( LocalDate.now( clock ), amount ) );
    }

    public String printStatement() {
        String headerRow = "Date\tAmount\tBalance\n";
        String transactionRows = formatTransactions();
        return headerRow + transactionRows;
    }

    private String formatTransactions() {
        return this.transactions
                .stream()
                .map( this::formatTransaction )
                .collect( Collectors.joining( "\n" ) );
    }

    private String formatTransaction( Transaction t ) {
        return "%s\t%s\t%s".formatted( t.date(), t.amount(), balance );
    }

    record Transaction(LocalDate date, int amount) {
    }

}

