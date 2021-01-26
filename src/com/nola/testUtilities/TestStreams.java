package com.nola.testUtilities;

import com.nola.databases.BundleDb;
import com.nola.databases.CheckoutDb;
import com.nola.parsers.FlatObjectParser;

import java.io.*;

public class TestStreams {
    public static InputStream GetCheckoutCsvStream(){
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        try {
            writer.write("\"Timestamp\",\"Username\",\"Book id\",\"User id\",\"Due Date\"\n");
            writer.write("\"2020/09/30 9:29:20 AM MDT\",\"someone@email.com\",\"7890788-(2)\",\"name.1\",\"2020-10-07\"\n");
            writer.write("\"2020/09/22 8:05:04 AM MDT\",\"someone@email.com\",\"678564-FIC-5-(1)\",\"name.2\",\"2020-10-07\"\n");
            writer.write("\"2020/09/22 8:09:24 AM MDT\",\"someone@email.com\",\"DOG99\",\"name.3\",\"2020-10-07\"\n");
            writer.write("\"2020/09/22 8:11:44 AM MDT\",\"someone@email.com\",\"PIG07\",\"name.4\",\"2020-10-07\"\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        var buffer = memStream.toByteArray();
        try {
            memStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(buffer);
    }

    public static InputStream GetBundleCheckoutCsvStream() {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        try {
            writer.write("\"Timestamp\",\"Username\",\"Book id\",\"User id\",\"Due Date\"\n");
            writer.write("\"2020/09/30 9:29:20 AM MDT\",\"someone@email.com\",\"BUN01\",\"name.1\",\"2020-10-07\"\n");
            writer.write("\"2020/09/22 8:05:04 AM MDT\",\"someone@email.com\",\"DUN02\",\"name.2\",\"2020-10-07\"\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        var buffer = memStream.toByteArray();
        try {
            memStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(buffer);
    }

    public static InputStream GetBundleCsvStream() {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        try {
            writer.write("\"Timestamp\",\"Id\",\"Description\",\"Book ids\"\n");
            writer.write("\"2021/01/23 10:08:42 PM PST\",\"KK-01\",\"Kokil Bundle 1\",\"CAT12, BAT12\"\n");
            writer.write("\"2021/01/23 10:09:21 PM PST\",\"KK-02\",\"Kakatua Bundle 2\",\"DOG99, PIG07\"");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        var buffer = memStream.toByteArray();
        try {
            memStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(buffer);
    }

    public static InputStream GetReturnCsvStream() {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        try {
            writer.write("\"Timestamp\",\"Username\",\"Book id\"\n");
            writer.write("\"2020/09/30 9:29:20 AM MDT\",\"someone@email.com\",\"7890788-(2)\"\n");
            writer.write("\"2020/09/22 8:05:04 AM MDT\",\"someone@email.com\",\"678564-SOC-3-(1)\"\n");
            writer.close();

            var buffer = memStream.toByteArray();
            memStream.close();
            return new ByteArrayInputStream(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream GetReturnCsvStream_shortId() {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        try {
            writer.write("\"Timestamp\",\"Username\",\"Book id\"\n");
            writer.write("\"2020/09/30 9:29:20 AM MDT\",\"someone@email.com\",\"CAT12\"\n");
            writer.write("\"2020/09/22 8:05:04 AM MDT\",\"someone@email.com\",\"BAT12\"\n");
            writer.close();

            var buffer = memStream.toByteArray();
            memStream.close();
            return new ByteArrayInputStream(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static InputStream GetNewUserCsvStream() throws IOException{
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        writer.write("\"Timestamp\",\"Name\",\"Role\",\"Email\",\"Phone\"\n");
        writer.write("\"2020/01/24 8:37:32 AM PST\",\"Tanni Chakraborty \",\"Teacher\",\"tanni.chakraborty@gmail.com\",\"732-668-7269\"\n");
        writer.write("\"2020/01/24 8:38:11 AM PST\",\"Totini Tonu\",\"Student\",\"rajatshuvro@gmail.com\",\"732-666-7242\"\n");
        writer.write("\"2020/01/24 8:39:31 AM PST\",\"Homayara Chowdhury\",\"Administrator\",\"sharmin@onkur.com\",\"(619) 204-5111\"\n");
        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }
    public static InputStream GetBookCsvStream() {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);
        byte[] buffer = null;
        try{
            writer.write("\"Timestamp\",\"ISBN\",\"Title\",\"Author\",\"Publisher\",\"Year\",\"Genre\",\"Reading level\",\"Page count\",\"Price\"\n");
            writer.write("\"2020/01/31 3:45:48 PM PST\",\"\",\"Golper Asor (4)\",\"Brac\",\"Brac\",\"2017\",\"General\",\"4\",\"10\",\"5\"\n");
            writer.write("\"2020/01/31 3:54:45 PM PST\",\"\",\"Golper Asor (2)\",\"Brac\",\"Brac\",\"2017\",\"General\",\"2\",\"10\",\"5\"\n");
            writer.write("\"2020/01/25 7:01:51 PM PST\",\"\",\"Pani\",\"Md.shah alam\",\"Sisemi workshop \",\"2013\",\"Science\",\"5\",\"10\",\"5\"\n");
            writer.close();

            buffer = memStream.toByteArray();
            memStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(buffer);
    }
    public static InputStream GetTransactionStreamReduced() throws IOException {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        writer.write("#Onkur library transactions\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t7890788-(2)\n");
        writer.write("User Id:\t234\n");
        writer.write("Date:\t\t2019-09-13 10:30:31\n");
        writer.write("Type:\t\tCheckout\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t678564-(1)\n");
        writer.write("User Id:\t123\n");
        writer.write("Date:\t\t2019-10-15 11:01:22\n");
        writer.write("Type:\t\tCheckout\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t456098-(1)\n");
        writer.write("User Id:\t345\n");
        writer.write("Date:\t\t2019-11-03 10:33:10\n");
        writer.write("Type:\t\tCheckout\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t7890788-(2)\n");
        writer.write("User Id:\t234\n");
        writer.write("Date:\t\t2019-11-13 10:30:25\n");
        writer.write("Type:\t\tReturn\n");
        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }
    public static InputStream GetTransactionsStream() throws IOException {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        writer.write("#Onkur library transactions\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t7890788-(2)\n");
        writer.write("User Id:\t564\n");
        writer.write("Date:\t\t2019-09-13 10:30:31\n");
        writer.write("Type:\t\tCheckout\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t678564-(1)\n");
        writer.write("User Id:\t897\n");
        writer.write("Date:\t\t2019-10-15 11:01:22\n");
        writer.write("Type:\t\tCheckout\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t456098-(1)\n");
        writer.write("User Id:\t897\n");
        writer.write("Date:\t\t2019-11-03 10:33:10\n");
        writer.write("Type:\t\tCheckout\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t7890788-(2)\n");
        writer.write("User Id:\t564\n");
        writer.write("Date:\t\t2019-11-13 10:30:25\n");
        writer.write("Type:\t\tReturn\n");
        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }

    public static InputStream GetIdMapStream() throws IOException {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        writer.write("#Onkur library short to long id mappings\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Long Id:\t7890788-(2)\n");
        writer.write("Short Id:\tAC564\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Long Id:\t678564-(1)\n");
        writer.write("Short Id:\tEU8C7\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Long Id:\t456098-(1)\n");
        writer.write("Short Id:\tUSA20\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Long Id:\t7890788-(1)\n");
        writer.write("Short Id:\tBA123\n");
        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }

    public static InputStream GetUsersStream() throws IOException {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        writer.write("#Onkur library users\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Name:\tSaber Nabil\n");
        writer.write("Id:\t\t234\n");
        writer.write("Role:\tTeacher\n");
        writer.write("Email:\tsaber.nabil@onkur.com\n");
        writer.write("Phone:\t346-892-9879\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Name:\tRajat Shuvro Roy\n");
        writer.write("Id:\t\t678\n");
        writer.write("Role:\tVolunteer\n");
        writer.write("Email:\trajat.shuvro@onkur.com\n");
        writer.write("Phone:\t346-892-9879\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Name:\tZohir Chowdhury\n");
        writer.write("Id:\t\t897\n");
        writer.write("Role:\tAdministrator\n");
        writer.write("Email:\tzohir.chowdhury@onkur.com\n");
        writer.write("Phone:\t858-892-9879\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Name:\tTotini Tonu\n");
        writer.write("Id:\t\t564\n");
        writer.write("Role:\tStudent\n");
        writer.write("Email:\ttotini.tonu@onkur.com\n");
        writer.write("Phone:\t858-892-9279\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Name:\tIshal Khan\n");
        writer.write("Id:\t\t157\n");
        writer.write("Role:\tStudent\n");
        writer.write("Email:\tishal.khan@onkur.com\n");
        writer.write("Phone:\t858-892-7279\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Name:\tAyrah Khan\n");
        writer.write("Id:\t\t167\n");
        writer.write("Role:\tStudent\n");
        writer.write("Email:\tayrah.khan@onkur.com\n");
        writer.write("Phone:\t858-892-3279\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Name:\tNoureen Chowdhury\n");
        writer.write("Id:\t\t169\n");
        writer.write("Role:\tStudent\n");
        writer.write("Email:\tnoureen.chowdhury@onkur.com\n");
        writer.write("Phone:\t858-892-9299\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Name:\tDarayush Ahmed\n");
        writer.write("Id:\t\t456\n");
        writer.write("Role:\tStudent\n");
        writer.write("Email:\tdarayush.ahmed@onkur.com\n");
        writer.write("Phone:\t858-892-9379\n");

        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }

    public static InputStream GetBooksStream() throws IOException {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        writer.write("#Onkur library books\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Title:\t\t\tAmar Baba\n");
        writer.write("Author:\t\t\tRajat Shuvro Roy\n");
        writer.write("ISBN:\t\t\t7890788\n");
        writer.write("Publisher:\t\tBonosree books and co\n");
        writer.write("Year:\t\t\t2015\n");
        writer.write("Genre:\t\t\tGeneral\n");
        writer.write("Copy number:\t1\n");
        writer.write("Page count:\t\t7\n");
        writer.write("Price:\t\t\t5\n");
        writer.write("Reading level:\t4\n");
        writer.write("Entry date:\t\t2018-05-17 11:35:06\n");
        writer.write("Expiry date:\t2018-11-10 11:35:34\n");
        writer.write("ShortId:\tLILDD\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Title:\t\t\tAmar Baba\n");
        writer.write("Author:\t\t\tRajat Shuvro Roy\n");
        writer.write("ISBN:\t\t\t7890788\n");
        writer.write("Publisher:\t\tBonosree books and co\n");
        writer.write("Year:\t\t\t2015\n");
        writer.write("Genre:\t\t\tGeneral\n");
        writer.write("Copy number:\t2\n");
        writer.write("Page count:\t\t7\n");
        writer.write("Price:\t\t\t5\n");
        writer.write("Reading level:\t4\n");
        writer.write("Entry date:\t\t2018-05-17 11:35:06\n");
        writer.write("Expiry date:\t\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Title:\t\t\tBhua Bhalobasha\n");
        writer.write("Author:\t\t\tSaber Nabil\n");
        writer.write("ISBN:\t\t\t678564\n");
        writer.write("Publisher:\t\tDakkhin Khan Publishers\n");
        writer.write("Year:\t\t\t2017\n");
        writer.write("Genre:\t\t\tFiction\n");
        writer.write("Copy number:\t1\n");
        writer.write("Page count:\t\t10\n");
        writer.write("Price:\t\t\t10\n");
        writer.write("Reading level:\t5\n");
        writer.write("Entry date:\t\t2018-11-27 11:49:55\n");
        writer.write("Expiry date:\t\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Title:\t\t\tRobindra Prem\n");
        writer.write("Author:\t\t\tNandana Mitra\n");
        writer.write("ISBN:\t\t\t456098\n");
        writer.write("Publisher:\t\tBakura Publishers\n");
        writer.write("Year:\t\t\t2018\n");
        writer.write("Genre:\t\t\tFiction\n");
        writer.write("Copy number:\t1\n");
        writer.write("Page count:\t\t15\n");
        writer.write("Price:\t\t\t10\n");
        writer.write("Reading level:\t6\n");
        writer.write("Entry date:\t\t2019-11-27 11:28:44\n");
        writer.write("Expiry date:\t\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");

        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }
    public static InputStream GetAntiqueBooksStream() throws IOException {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        writer.write("#Onkur library books\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Title:\t\t\tChotoder Ramayan\n");
        writer.write("Author:\t\t\tUpendrakishore Roy Choudhury\n");
        writer.write("ISBN:\t\t\t-1\n");
        writer.write("Publisher:\t\tKalighat prokashoni\n");
        writer.write("Year:\t\t\t1929\n");
        writer.write("Genre:\t\t\tFiction/Religion\n");
        writer.write("Page count:\t\t35\n");
        writer.write("Price:\t\t\t15\n");
        writer.write("Reading level:\t4\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Title:\t\t\tBorno porichoy\n");
        writer.write("Author:\t\t\tIshwarchandra Viddyasagar\n");
        writer.write("ISBN:\t\t\t-1\n");
        writer.write("Publisher:\t\tBengal publishers\n");
        writer.write("Year:\t\t\t1881\n");
        writer.write("Genre:\t\t\tGeneral\n");
        writer.write("Page count:\t\t20\n");
        writer.write("Price:\t\t\t10\n");
        writer.write("Reading level:\t3\n");

        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }

    public static InputStream  GetEmptyCheckoutStream() throws IOException {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        for (var line: CheckoutDb.HeaderLines
        ) {
            writer.write(line+'\n');
        }
        writer.write(FlatObjectParser.RecordSeparator+"\n");

        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }

    public static InputStream  GetCheckoutStream() throws IOException {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        for (var line: CheckoutDb.HeaderLines
        ) {
            writer.write(line+'\n');
        }
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t456098-(1)\n");
        writer.write("User Id:\t897\n");
        writer.write("Checkout Date:\t\t2019-11-03 10:33:10\n");
        writer.write("Due Date:\t\t2019-12-03 10:33:10\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Book Id:\t456097-(1)\n");
        writer.write("User Id:\t897\n");
        writer.write("Checkout Date:\t\t2019-11-03 10:33:10\n");
        writer.write("Due Date:\t\t2019-12-03 10:33:10\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }

    public static InputStream  GetBundleStream() throws IOException {
        var memStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(memStream);

        for (var line: BundleDb.HeaderLines
        ) {
            writer.write(line+'\n');
        }
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Id:\tUKI33\n");
        writer.write("Description:\tKokil Bundle 1\n");
        writer.write("BookIds:\t\t7890788-(1),678564-(1)\n");
        writer.write("Date:\t\t2021-01-03 10:33:10\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.write("Id:\tFIN83\n");
        writer.write("Description:\tKokil Bundle two\n");
        writer.write("BookIds:\t\t7890788-(2),456098-(1)\n");
        writer.write("Date:\t\t2021-01-03 10:33:10\n");
        writer.write(FlatObjectParser.RecordSeparator+"\n");
        writer.close();

        var buffer = memStream.toByteArray();
        memStream.close();
        return new ByteArrayInputStream(buffer);
    }


}
