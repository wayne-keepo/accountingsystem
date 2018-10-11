package services;

import databaselogic.controllers.DBDetailController;
import entities.Detail;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class DetailService {
    private static final DBDetailController controller = new DBDetailController();

    public static List<Detail> getAll() {
        return controller.getAll();
    }

    // TODO; Дописать
    public static boolean findCandidatesOnUpdating(List<Detail> details, List<Integer> ids) {
        List<Detail> candidates = new ArrayList<>(ids.size());
        ids.forEach(id -> details.stream().filter(detail -> detail.getId() == id).findFirst().ifPresent(candidates::add));
//        Map<Integer, Detail> conv = details.stream().collect(Collectors.toMap(Detail::getId, detail -> detail));
        System.out.println(candidates.toString());
//        ids.forEach(id -> candidates.add(conv.get(id)));

        if (candidates.isEmpty())
            return false;
//        bulkUpdate(candidates);
        return true;
    }

    public static void bulkUpdate(List<Detail> details) {
        controller.bulkUpdate(details);
    }

    public static void sendOnUpdate(List<Detail> details, List<Integer> ids) {
        if (!ids.isEmpty()) {
            List<Detail> candidates = new ArrayList<>(ids.size());
            ids.forEach(id -> details.stream().filter(d -> d.getId() == id).findFirst().ifPresent(candidates::add));
            bulkUpdate(candidates);
        }
    }

    public static List<Detail> getDetailsByIDs(List<Integer> ids) {
        return controller.getDetailsByIDs(ids);
    }
    //TODO[Future]: in future upd count will be as user write +value where  value - its value which he wants to add to the current
    public static void notifyUpdCountInAccHis(Detail detail) {
        AccoutingHistoryService.updateHistoryForDay(
                Year.now().getValue(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth(),
                1,
                detail.getId(),
                detail.getCount(),
                // when we release this future task we change false on true and will be take newValue from table or no if we will send count from detail because its value already summarized and changed
                false
        );
    }
}
