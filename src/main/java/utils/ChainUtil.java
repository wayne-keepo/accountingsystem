package utils;

import domain.Balance;
import domain.DetailElectrod;
import entities.AccoutingHistory;
import entities.Detail;
import entities.DetailElectrodePrimitive;
import entities.PrimitivityBalance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.AccoutingHistoryService;
import utils.enums.Types;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
// это было так давно...я забыл что тут говнокодил
public class ChainUtil {
    private static final Logger logger = LogManager.getLogger(ChainUtil.class);


    public static List<DetailElectrod> createChainDetailElectrode(List<Detail> details, List<DetailElectrodePrimitive> primitivs) {
        List<DetailElectrod> des = new ArrayList<>();

        Map<String, List<DetailElectrodePrimitive>> depByType = new HashMap<>();

        depByType.put(
                Types.ESMG.eng(),
                primitivs.stream().filter(p -> p.getElectrodeType().equals(Types.ESMG.eng())).collect(Collectors.toList())
        );
        depByType.put(
                Types.ESMG_M.eng(),
                primitivs.stream().filter(p -> p.getElectrodeType().equals(Types.ESMG_M.eng())).collect(Collectors.toList())
        );

        depByType.forEach((k, v) -> {
            if (!v.isEmpty()) {
                Map<Detail, Map<Double,BigDecimal>> dd = new HashMap<>();
                List<Integer> ids = new ArrayList<>();
                // review ids:List
                v.forEach(p -> ids.add(p.getId()));
                v.forEach(p -> {
                    details.forEach(d -> {
                        if (d.getId() == p.getIdDetail()) {
                            Map<Double, BigDecimal> doubig = new HashMap<>();
                            doubig.put(p.getCount(),p.getCost());
                            dd.put(d, doubig);
                        }
                    });
                });
                DetailElectrod de = new DetailElectrod();
                de.setElectrodeType(k);
                de.setDetails(dd);
                de.setIds(ids);
                des.add(de);
            }
        });
        return des;
    }

    public static void createAccHistoryChain(List<Detail> details, List<AccoutingHistory> histories) {
        details.forEach(detail -> {
            histories.stream().filter(h -> h.getIdDetail() == detail.getId()).forEach(history -> history.setDetail(detail));
        });
    }

    public static Map<Detail, Map<PrimitivityBalance, List<AccoutingHistory>>> associateDetailWithPrimitiveAndHistory(List<Detail> details, List<PrimitivityBalance> pBalances, List<AccoutingHistory> histories) {
        logger.info("[ChainUtilLogic.Balance.Associating] START associating detail, primitive balance, accounting history...");
        logger.info("   create associate between detail and accounting history");
        createAccHistoryChain(details, histories);

        Map<Detail, Map<PrimitivityBalance, List<AccoutingHistory>>> associations = new HashMap<>();
        logger.info("   create association between primitive balance and accounting history and add under detail");
        details.forEach(detail -> {
            Map<PrimitivityBalance, List<AccoutingHistory>> pBalanceAccHist = new HashMap<>();
            // filter by detail
            List<PrimitivityBalance> personalPrimBalance = pBalances.stream().filter(p -> p.getIdDetail() == detail.getId()).collect(Collectors.toList());
            List<AccoutingHistory> personalHistory = histories.stream().filter(h -> h.getIdDetail() == detail.getId()).collect(Collectors.toList());
            // reference primitiv balance with history by month
            personalPrimBalance.forEach(p -> {
                pBalanceAccHist.put(
                        p,
                        personalHistory.stream().filter(h -> h.getMonth().equals(p.getMonth())).collect(Collectors.toList())
                );
            });
            if (!personalPrimBalance.isEmpty())
                associations.put(detail, pBalanceAccHist);
        });
        logger.info("   calculating incoming and outcoming for each primitive balance");
        associations.forEach((d, pa) -> {
            pa.forEach((p, h) -> {
                double[] sums = AccoutingHistoryService.calculate(h);
                p.setIncoming(sums[0]);
                p.setOutcoming(sums[1]);
            });
        });
        logger.info("[ChainUtilLogic.Balance.Associating] END associating detail, primitive balance, accounting history...");
        return associations;
    }

    public static List<Balance> createBalanceChain(List<Detail> details, List<PrimitivityBalance> pBalances, List<AccoutingHistory> histories) {
        logger.info("[ChainUtilLogic.Balance.Chaining] START creating balance...");
        List<Balance> balances = new ArrayList<>();
        logger.info("   create associating between detail, primitive balances and accounting histories for each primitive [detail -> primitive -> history]");
        Map<Detail, Map<PrimitivityBalance, List<AccoutingHistory>>> associations = associateDetailWithPrimitiveAndHistory(details, pBalances, histories);
        logger.info("   create balance from associated items");
        associations.forEach((d, pa) -> {
            Balance balance = new Balance();
            balance.setId(d.getId());
            balance.setDetail(d);

            AtomicReference<Double> incTotal = new AtomicReference<>(0.0);
            AtomicReference<Double> outTotal = new AtomicReference<>(0.0);
            AtomicReference<Double> begYear = new AtomicReference<>(0.0);

            Map<Month, Double> inc = new HashMap<>();
            Map<Month, Double> out = new HashMap<>();

            pa.forEach((p, h) -> {
                inc.put(p.getMonth(), p.getIncoming());
                out.put(p.getMonth(), p.getOutcoming());
                incTotal.updateAndGet(v -> v + p.getIncoming());
                outTotal.updateAndGet(v -> v + p.getOutcoming());
//                begYear.set(p.getBalanceAtBeginningYear());

            });
            // round to 3 symbols after dot
            double endYear = 0.0;
            if (begYear.get()!=0)
                begYear.set(new BigDecimal(begYear.get()).setScale(3, RoundingMode.UP).doubleValue());
            else
                begYear.set(d.getCount());
            endYear = new BigDecimal(incTotal.get() - outTotal.get()).setScale(2, RoundingMode.UP).doubleValue();

            balance.setBalanceAtBeginningYear(begYear.get());
            balance.setBalanceAtEndOfYear(endYear);
            balance.setInTotal(incTotal.get());
            balance.setOutTotal(outTotal.get());
            balance.setIncoming(inc);
            balance.setOutcoming(out);

            balances.add(balance);
        });
        logger.info("[ChainUtilLogic.Balance.Chaining] END creating balance...");
        return balances;
    }

    public static void associateDetailWithHistory(Detail detail, List<AccoutingHistory> histories) {
        histories.forEach(history -> history.setDetail(detail));
    }
}
