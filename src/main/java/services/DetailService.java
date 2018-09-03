package services;

import databaselogic.controllers.DBDetailController;
import entities.Detail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DetailService {
    private static final DBDetailController controller = new DBDetailController();

    public static List<Detail> getAll() {
        return controller.getAll();
    }

    public static boolean findCandidatesOnUpdating(List<Detail> details, List<Integer> ids) {
        List<Detail> candidates = new ArrayList<>();

        Map<Integer, Detail> conv = details.stream().collect(Collectors.toMap(Detail::getId, detail -> detail));

        ids.forEach(id -> candidates.add(conv.get(id)));

        if (candidates.isEmpty())
            return false;

        bulkUpdate(candidates);
        return true;
    }

    public static void bulkUpdate(List<Detail> details) {
        controller.bulkUpdate(details);
    }

    public static List<Detail> getDetailsByIDs(List<Integer> ids) {
        return controller.getDetailsByIDs(ids);
    }

}
