package integration;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;

public class CommonOperations {
    
    //Contains DbSetup builder functions for all necessary operations to be used in populating and cleaning test database

    public static final Operation DELETE_ALL =
            Operations.deleteAllFrom("investigators", "attorneys", "clients", "judges", "cases", "charges", "charged_counts");
    
    public static final Operation INSERT_INVESTIGATOR_DATA =
            Operations.insertInto("investigators")
                .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1L))
                .columns("name", "employment_status")
                .values("Tim Loodle", 0)
                .values("Sandra Sanderson", 0)
                .values("Debbie Downer", 1)
                .build();
    
    public static final Operation INSERT_ATTORNEY_DATA =
            Operations.insertInto("attorneys")
                .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1L))
                .columns("name", "employment_status", "investigator")
                .values("Matt Quillian", 0, 1L)
                .values("John Doe", 0, 2L)
                .values("Jane Smith", 1, 1L)
                .values("Jason Schneider", 1, 3L)
                .build();
    
    public static final Operation INSERT_CLIENT_DATA =
            Operations.insertInto("clients")
                .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1L))
                .columns("name", "custody_status")
                .values("Eric Hoefle", 0)
                .values("Jason Baddorf", 0)
                .values("Jamie Jameson", 1)
                .values("Marky Mark", 1)
                .values("Phteven McButton", 0)
                .values("Atticus Finch", 1)
                .values("Erica Erickson", 0)
                .values("Fblthp NLN", 0)
                .values("Jimmy Fallon", 0)
                .values("Hamilton Holmes", 1)
                .build();
    
    public static final Operation INSERT_JUDGE_DATA =
            Operations.insertInto("judges")
                .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1L))
                .columns("name")
                .values("Horace Johnson")
                .values("John Mott")
                .build();
    
    public static final Operation INSERT_CASE_DATA =
            Operations.insertInto("cases")
                .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1L))
                .columns("case_number", "client_id", "judge_id", "attorney_id")
                .values("18J161450", 1L, 1L, 1L)
                .values("18J141738", 2L, 1L, 2L)
                .values("18J171429", 3L, 1L, 1L)
                .values("17J283757", 4L, 1L, 2L)
                .values("17J172365", 5L, 1L, 2L)
                .values("18J179254", 6L, 2L, 1L)
                .values("18J172979", 6L, 2L, 1L)
                .values("17J281737", 7L, 2L, 2L)
                .values("18J218436", 7L, 2L, 2L)
                .values("18J476421", 8L, 1L, 1L)
                .values("17J742874", 9L, 1L, 2L)
                .values("18J851936", 10L, 1L, 1L)
                .build();
    
    public static final Operation INSERT_CHARGE_DATA =
            Operations.insertInto("charges")
                .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1L))
                .columns("name", "statute")
                .values("Simple battery", "16-5-23.1")
                .values("Battery", "16-5-23.1")
                .values("Theft by taking", "16-8-2")
                .values("Driving while license suspended", "40-5-21")
                .values("Possession of controlled substance", "16-13-30")
                .build();
    
    public static final Operation INSERT_CHARGED_COUNTS_DATA =
            Operations.insertInto("charged_counts")
                .columns("case", "charge")
                .values(1L, 1L)
                .values(2L, 3L)
                .values(3L, 4L)
                .values(3L, 5L)
                .values(4L, 3L)
                .values(5L, 2L)
                .values(6L, 4L)
                .values(7L, 1L)
                .values(8L, 5L)
                .values(9L, 2L)
                .values(10L, 1L)
                .values(11L, 3L)
                .values(12L, 5L)
                .build();
}
