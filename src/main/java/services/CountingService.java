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
import java.util.stream.Collectors;

// TODO: подумать над тем чтобы вынести инициализацию RawElectrode в main
public class CountingService {

    public static List<Balance> countingForProduceRawElectrode(String type, int count, ObservableList<Balance> balances) {
        List<String> detailTitlesForErrorMsg = new ArrayList<>();
        List<Integer> detailIds = new ArrayList<>();
        Map<Detail, Map<Double, Double>> ddd = new HashMap<>();
        List<DetailElectrodePrimitive> detailElectrods = DetailElectrodeService.getAllByType(type);

        detailElectrods.forEach(deid -> detailIds.add(deid.getIdDetail()));
        List<Detail> details = DetailService.getDetailsByIDs(detailIds);
        // TODO: review for optimization
        details.forEach(detail -> {
            Double eqCount = detailElectrods.stream().filter(x -> x.getIdDetail().equals(detail.getId())).findFirst().get().getCount() * count;
// возможно сразу выбрасывать оповещение о нехватке деталей, ибо нахуя создавать лишнюю коллекцию когда можно не создавать ?
            if (!(Double.compare(detail.getCount(), eqCount) == -1)) {
                HashMap<Double, Double> tmp = new HashMap<>();
                tmp.put(detail.getCount(), eqCount);
                ddd.put(detail, tmp);
            } else
                detailTitlesForErrorMsg.add(detail.getTitle());
        });

        if (!detailTitlesForErrorMsg.isEmpty())
            throw new RuntimeException( // TODO ERROR: добавить обработку ошибки (всплывающее сообщение / кастомный класс ошибок )
                    String.format("Количество деталей %s на складе недостаточно для производства %d электрода %s типа.",
                            detailTitlesForErrorMsg.toString().replaceAll("[\\[\\]]", ""),
                            count,
                            type));

        RawElectrode rw = ElectrodeService.getRawElectrodeByType(type);
        if (rw == null)
            rw = initializeRawElectrode(type);

        int oldRawCount = rw.getCount();
        int newRawCount = oldRawCount + count;
        ElectrodeService.updateRawElectrodeCount(rw, newRawCount);

        List<RefreshBalanceData> updBalanceData = new ArrayList<>();
// TODO: review for optimization
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
            updBalanceData.add(new RefreshBalanceData(Month.of(month), detailId, delta));
        });

        details = new ArrayList<>(ddd.keySet());

        DetailService.bulkUpdate(details);

        if (!updBalanceData.isEmpty())
            return BalanceService.updBalanceWhenProduceRawElectrode(updBalanceData, balances);
        return null;
    }

    public static void countingForProduceSummaryFromRawElectrode(String from, String to, String type) {
        RawElectrode rw = ElectrodeService.getRawElectrodeByType(type);
        if (rw == null)
            rw = initializeRawElectrode(type);

        int numericFrom = Integer.valueOf(from);
        int numericTo = Integer.valueOf(to);
        int howProduce = numericTo - numericFrom;
        int rawElectrodeCount = rw.getCount();

        if (!(rawElectrodeCount >= howProduce))
            throw new RuntimeException(String.format("Недостаточно сырья для продажи %d электродов", howProduce)); // TODO ERROR: добавить обработку ошибки (всплывающее сообщение)

        ElectrodeService.updateRawElectrodeCount(rw, rawElectrodeCount - howProduce);
    }

    public static RawElectrode initializeRawElectrode(String type) {
        return ElectrodeService.initRawElectrode(type, 0);
    }

}
