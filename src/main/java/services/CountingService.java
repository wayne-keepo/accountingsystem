package services;

import entities.Detail;
import entities.DetailElectrodePrimitive;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class CountingService {
//TableView<> details,TableView<> detailElectrode,
    public static void countingForProduceRawElectrode(String type, int count){
        List<String> detailTitlesForErrorMsg =new ArrayList<>();

        List<DetailElectrodePrimitive> detailElectrods = DetailElectrodeService.getAllByType(type);
        List<Integer> detailIds = new ArrayList<>();
        Map<Detail,Map<Double,Double>> ddd = new HashMap<>();

        detailElectrods.forEach(deid->detailIds.add(deid.getIdDetail()));

        List<Detail> details = DetailService.getDetailsByIDs(detailIds);

        details.forEach(detail -> {
            Double eqCount = detailElectrods.stream().filter(x->x.getIdDetail().equals(detail.getId())).findFirst().get().getCount()*count;
            if (!(Double.compare(detail.getCount(),eqCount)==-1)){
                HashMap<Double,Double> tmp = new HashMap<>();
                tmp.put(detail.getCount(),eqCount);
                ddd.put(detail,tmp);
            }
            else
                detailTitlesForErrorMsg.add(detail.getTitle());
        });

        if (!detailTitlesForErrorMsg.isEmpty())
            throw new RuntimeException(
                    String.format("Количество деталей %s на складе недостаточно для производства электрода %S типа.",
                            detailTitlesForErrorMsg.toString().replaceAll("[\\[\\]]",""),
                            type));
        ddd.forEach((detail,map)->{
            Double oldCount = detail.getCount();
            Double newCount = oldCount-map.get(oldCount);
            detail.setCount(newCount);
            AccoutingHistoryService.updateHistoryForDay(Year.now().getValue(), LocalDate.now().getMonthValue(), MonthDay.now().getDayOfMonth(), 1, detail.getId(), newCount);

        });

        details = Arrays.asList(ddd.keySet().toArray(new Detail[ddd.keySet().size()]));

        DetailService.bulkUpdate(details);
    }
}
/*
1. проверить тип электрода
2. найти сколько деталей используются для производства 1 электрода такого типа
3. проверить хватает ли количества деталей для производства n количества электродов
4. умножить N(1) на N(x)
5. вычесть N(x) из общего количества деталей на складе
7. обновить историю учета дял детали
6. проделать алгоритм для всех деталей учавствующих в производстве электрода
 */
