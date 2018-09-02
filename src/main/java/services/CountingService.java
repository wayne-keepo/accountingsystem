package services;

import entities.Detail;
import entities.DetailElectrodePrimitive;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.*;

public class CountingService {
//TableView<> details,TableView<> detailElectrode,



    public static void countingForProduceRawElectrode(String type, int count){
        List<String> detailTitlesForErrorMsg =new ArrayList<>();

        List<DetailElectrodePrimitive> detailElectrods = DetailElectrodeService.getAllByType(type);
        System.out.println("--------------------- begins counting the number of parts for the production of the "+count+" electrodes ---------------------------------");
        System.out.println("DetailElectrode by Type: \n"+detailElectrods.toString());
        List<Integer> detailIds = new ArrayList<>();
        Map<Detail,Map<Double,Double>> ddd = new HashMap<>();

        detailElectrods.forEach(deid->detailIds.add(deid.getIdDetail()));
        System.out.println("Extract detail IDs: "+detailIds.toString());

        List<Detail> details = DetailService.getDetailsByIDs(detailIds);
        System.out.println("Get details by IDs: "+details.toString());

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
        System.out.println("Error details: "+ detailTitlesForErrorMsg.toString());
        if (!detailTitlesForErrorMsg.isEmpty())
            throw new RuntimeException(
                    String.format("Количество деталей %s на складе недостаточно для производства электрода %S типа.",
                            detailTitlesForErrorMsg.toString().replaceAll("[\\[\\]]",""),
                            type));
        System.out.println("Connected map map => detail -> detCount | neededCount: \n"+ddd.toString());
        ddd.forEach((detail,map)->{
            Double oldCount = detail.getCount();
            Double delta =  map.get(oldCount);
            Double newCount = oldCount-delta;
            detail.setCount(newCount);
            System.out.println(String.format("Into foreach; \nOld count: %s | Delta: %s| New count: %s | Current count from detail: %s \n Start upd acc his for detailID: %d",String.valueOf(oldCount),String.valueOf(delta),String.valueOf(newCount),String.valueOf(detail.getCount()),detail.getId()));
            System.out.println(String.format("Year: %d Month: %d Day: %d",Year.now().getValue(), LocalDate.now().getMonthValue(), MonthDay.now().getDayOfMonth()));
            AccoutingHistoryService.updateHistoryForDay(Year.now().getValue(), LocalDate.now().getMonthValue(), MonthDay.now().getDayOfMonth(), 0, detail.getId(), delta);
        });

        details = null;
        details = Arrays.asList(ddd.keySet().toArray(new Detail[ddd.keySet().size()]));
        System.out.println("Details for update: "+details.toString()+" \n Start upd details...");
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
