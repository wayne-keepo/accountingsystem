package databaselogic.utils;

import domain.Balance;
import domain.DetailElectrod;
import domain.Electrod;
import domain.ElectrodeSummary;
import entities.*;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChainUtil {

    public static List<DetailElectrod> createChainElectrodeDetail(List<Detail> details, List<Electrod> electrods, List<PrimitiveElectrodeDetail> primitivs){
        List<DetailElectrod> detailElectrods = new ArrayList<>();
        for (PrimitiveElectrodeDetail primitive: primitivs){
            DetailElectrod de = new DetailElectrod();
            Detail detail = details.stream().findFirst().filter(e-> e.getId()==primitive.getIdDetail()).get();
            Electrod electrod = electrods.stream().filter(e->e.getId()==primitive.getIdElectrode()).findFirst().get();
            de.setElectrod(electrod);
            de.setDetail(detail);
            de.setCountDetailForElectrode(primitive.getCount());
            detailElectrods.add(de);
        }
        if (!detailElectrods.isEmpty())
            return detailElectrods;
        return null;
    }

    public static List<Balance> createBalanceChain(List<Detail> details, List<PrimitivityBalance> pBalances){
        List<Balance> balances = new ArrayList<>();

        for (Detail detail: details){
            if (pBalances.stream().anyMatch(p->p.getIdDetail()==detail.getId())) {
                Balance balance = new Balance();
                Double balanceAtBeginningYear = 0.0;
                Double balanceAtEndOfYear = 0.0;
                Double inTotal = 0.0;
                Double outTotal = 0.0;
                Year year = null;
                Map<Month, Double> incoming = new HashMap<>();
                Map<Month, Double> outcoming = new HashMap<>();
                for (PrimitivityBalance primitiv : pBalances) {
                    if (primitiv.getIdDetail() == detail.getId()) {
                        year = primitiv.getYear();
//                        id = primitiv.getId();
                        inTotal += primitiv.getIncoming();
                        outTotal += primitiv.getOutcoming();
                        balanceAtBeginningYear = primitiv.getBalanceAtBeginningYear();
                        incoming.put(primitiv.getMonth(), primitiv.getIncoming());
                        outcoming.put(primitiv.getMonth(), primitiv.getOutcoming());
                    }

                }
                balanceAtEndOfYear = inTotal - outTotal;
                balance.setDetail(detail);
                balance.setId(detail.getId());
                balance.setYear(year);
                balance.setInTotal(inTotal);
                balance.setOutTotal(outTotal);
                balance.setBalanceAtBeginningYear(balanceAtBeginningYear);
                balance.setBalanceAtEndOfYear(balanceAtEndOfYear);
                balance.setIncoming(incoming);
                balance.setOutcoming(outcoming);
                balances.add(balance);
            }
        }
        if (!balances.isEmpty())
            return balances;
        return null;
    }

    public static List<ElectrodeSummary> createElectrodeSummaryChain(List<Electrod> electrods, List<Summary> summaries){
        List<ElectrodeSummary> es = new ArrayList<>();

        for (Electrod electrode: electrods){
            ElectrodeSummary elEs = new ElectrodeSummary();
            Summary summary = summaries.stream().filter(s-> s.getIdElectrode().equals(electrode.getId())).findFirst().get();
            elEs.setElectrod(electrode);
            elEs.setSummary(summary);
            es.add(elEs);
        }
        if (!es.isEmpty())
            return es;
        return null;
    }

    public static void associateDetailWithHistory(Detail detail, List<AccoutingHistory> histories){
        histories.forEach(history->history.setDetail(detail));
    }
}
