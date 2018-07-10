import views.tables.utils.RussianMonths;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;

public class TEST {
    public static void main(String[] args) {

        Integer year = Year.now().getValue();
        System.out.println(year);

        System.out.println(Arrays.toString(Month.values()));

        System.out.println(Month.of(7));

        System.out.println(LocalDate.now().getMonth().getValue());

        System.out.println(Arrays.toString(RussianMonths.values()));

        System.out.println(RussianMonths.of(8)+ " "+ RussianMonths.Август.getValue());

    }

}

