import databaselogic.controllers.DBBalanceController;
import databaselogic.controllers.DBDetailController;
import databaselogic.controllers.DBElectrodeController;
import databaselogic.controllers.DBSummaryController;
import domain.Balance;
import domain.InitializerForTest;
import entities.Detail;
import services.BalanceService;

import java.math.BigDecimal;
import java.time.Year;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TEST {

    public static void main(String[] args) {

        Map<Map<Integer,Integer>,String> asd = new HashMap<>();
        for (int i=0;i<10;i++){
            Map<Integer,Integer> keys = new HashMap<>();
            keys.put(i,i);
            asd.put(keys,String.valueOf(i+1));
        }
        System.out.println(asd.keySet().toString());
    }

}

