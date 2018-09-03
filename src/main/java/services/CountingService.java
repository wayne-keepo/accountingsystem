package services;

import domain.Balance;
import domain.RefreshBalanceData;
import entities.Detail;
import entities.DetailElectrodePrimitive;
import entities.RawElectrode;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.*;

// TODO: подумать над тем чтобы вынести инициализацию RawElectrode в main
public class CountingService {

    public static ObservableList<Balance> countingForProduceRawElectrode(String type, int count, ObservableList<Balance> balances) {
        ElectrodeService.initRawElectrode();

        List<String> detailTitlesForErrorMsg = new ArrayList<>();
        List<DetailElectrodePrimitive> detailElectrods = DetailElectrodeService.getAllByType(type);
//        System.out.println("--------------------- begins counting the number of parts for the production of the " + count + " electrodes ---------------------------------");
//        System.out.println("DetailElectrode by Type: \n" + detailElectrods.toString());
        List<Integer> detailIds = new ArrayList<>();
        Map<Detail, Map<Double, Double>> ddd = new HashMap<>();

        detailElectrods.forEach(deid -> detailIds.add(deid.getIdDetail()));
//        System.out.println("Extract detail IDs: " + detailIds.toString());

        List<Detail> details = DetailService.getDetailsByIDs(detailIds);
//        System.out.println("Get details by IDs: " + details.toString());

        details.forEach(detail -> {
            Double eqCount = detailElectrods.stream().filter(x -> x.getIdDetail().equals(detail.getId())).findFirst().get().getCount() * count;
            if (!(Double.compare(detail.getCount(), eqCount) == -1)) {
                HashMap<Double, Double> tmp = new HashMap<>();
                tmp.put(detail.getCount(), eqCount);
                ddd.put(detail, tmp);
            } else
                detailTitlesForErrorMsg.add(detail.getTitle());
        });
//        System.out.println("Error details: " + detailTitlesForErrorMsg.toString());
        if (!detailTitlesForErrorMsg.isEmpty())
            throw new RuntimeException( // TODO ERROR: добавить обработку ошибки (всплывающее сообщение/ кастомный класс ошибко )
                    String.format("Количество деталей %s на складе недостаточно для производства %d электрода %S типа.",
                            detailTitlesForErrorMsg.toString().replaceAll("[\\[\\]]", ""),
                            count,
                            type));

        int oldRawCount = RawElectrode.getInstance().getCount();
        int newRawCount = oldRawCount + count;
        ElectrodeService.updateRawElectrodeCount(newRawCount);

        List<RefreshBalanceData> updBalanceData = new ArrayList<>();

        ddd.forEach((detail, map) -> {
            Double oldCount = detail.getCount();
            Double delta = map.get(oldCount);
            Double newCount = oldCount - delta;
            detail.setCount(newCount);

            int year = Year.now().getValue();
            int month = LocalDate.now().getMonthValue();
            int day = MonthDay.now().getDayOfMonth();
            int detailId = detail.getId();

            AccoutingHistoryService.updateHistoryForDay(year, month, day, 0, detailId, delta);
            updBalanceData.add(new RefreshBalanceData(Month.of(month),detailId, delta));
        });

        details = Arrays.asList(ddd.keySet().toArray(new Detail[ddd.keySet().size()]));
        DetailService.bulkUpdate(details);

        if (!updBalanceData.isEmpty())
           return BalanceService.updBalanceWhenProduceRawElectrode(updBalanceData, balances);
        return null;
    }

    public static void countingForProduceSummaryFromRawElectrode(String from, String to, String type) {
        ElectrodeService.initRawElectrode();

        int numericFrom = Integer.valueOf(from);
        int numericTo = Integer.valueOf(to);
        int howProduce = numericTo - numericFrom;
        int rawElectrodeCount = RawElectrode.getInstance().getCount();

        if (!(rawElectrodeCount >= howProduce))
            throw new RuntimeException(String.format("Недостаточно сырья для продажи %d электродов", howProduce)); // TODO ERROR: добавить обработку ошибки (всплывающее сообщение)

        ElectrodeService.updateRawElectrodeCount(rawElectrodeCount - howProduce);
    }

}
