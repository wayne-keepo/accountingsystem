package services;

import entities.Detail;
import entities.DetailElectrodePrimitive;
import entities.RawElectrode;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.*;
// TODO: подумать над тем чтобы вынести инициализацию RawElectrode в main
public class CountingService {

    public static void countingForProduceRawElectrode(String type, int count) {
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
                    String.format("Количество деталей %s на складе недостаточно для производства электрода %S типа.",
                            detailTitlesForErrorMsg.toString().replaceAll("[\\[\\]]", ""),
                            type));

        int oldRawCount = RawElectrode.getInstance().getCount();
        int newRawCount = oldRawCount+count;
        ElectrodeService.updateRawElectrodeCount(newRawCount);

//        System.out.println("Connected map map => detail -> detCount | neededCount: \n" + ddd.toString());
        ddd.forEach((detail, map) -> {
            Double oldCount = detail.getCount();
            Double delta = map.get(oldCount);
            Double newCount = oldCount - delta;
            detail.setCount(newCount);
//            System.out.println(String.format("Into foreach; \nOld count: %s | Delta: %s| New count: %s | Current count from detail: %s \n Start upd acc his for detailID: %d", String.valueOf(oldCount), String.valueOf(delta), String.valueOf(newCount), String.valueOf(detail.getCount()), detail.getId()));
//            System.out.println(String.format("Year: %d Month: %d Day: %d", Year.now().getValue(), LocalDate.now().getMonthValue(), MonthDay.now().getDayOfMonth()));
            AccoutingHistoryService.updateHistoryForDay(Year.now().getValue(), LocalDate.now().getMonthValue(), MonthDay.now().getDayOfMonth(), 0, detail.getId(), delta);
        });

        details = Arrays.asList(ddd.keySet().toArray(new Detail[ddd.keySet().size()]));
//        System.out.println("Details for update: " + details.toString() + " \n Start upd details...");
        DetailService.bulkUpdate(details);
    }

    public static void countingForProduceSummaryFromRawElectrode(String from, String to, String type) {
        ElectrodeService.initRawElectrode();

        int numericFrom = Integer.valueOf(from);
        int numericTo = Integer.valueOf(to);
        int howProduce = numericTo - numericFrom;
        int rawElectrodeCount = RawElectrode.getInstance().getCount();

        if (!(rawElectrodeCount >= howProduce))
            throw new RuntimeException(String.format("Недостаточно сырья для продажи %d електродов", howProduce)); // TODO ERROR: добавить обработку ошибки (всплывающее сообщение)

        ElectrodeService.updateRawElectrodeCount(rawElectrodeCount - howProduce);
    }

}
