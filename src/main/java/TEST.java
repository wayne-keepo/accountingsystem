import databaselogic.controllers.DBDetailController;
import domain.Electrod;
import entities.Summary;
import projectConstants.CustomConstants;
import projectConstants.DBConstants;
import services.ElectrodeService;
import services.SummaryService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TEST {
    public static void main(String[] args) {

        //Detail tests
        DBDetailController dbDetailController = new DBDetailController();
//        Detail detail = dbDetailController.get(1);

        //Accounting History tests
//        DBAccountingHistoryController dbachc = new DBAccountingHistoryController();
//        AccoutingHistory achis = dbachc.getByDetail(1).get(0);
//        achis.setDetail(detail);
//        List<AccoutingHistory> ahList = dbachc.getByDetail(1);
//        Map<RussianMonths, List<AccoutingHistory>> histories = AccoutingHistoryService.historyToMapForAccoutingWindow(ahList);
//        Double d = 10.0512;
//        String s = String.valueOf(d);
//        String s2 = String.format("test %s",s);
//        System.out.println(s2);
//
//        StringBuilder st = new StringBuilder();
//        st.append(123).append(321).append("qweqwe");
//        System.out.println(st+" "+st.length());
//        st.setLength(0);
//        System.out.println(st);
//        st.append(1).append(2).append("zxc");
//        System.out.println(st+" "+st.length());
//
//        double templateDouble = 12.1354678578862;
//
//        System.out.println("Template double: " + templateDouble);
//
//        double newDouble = new BigDecimal(templateDouble).setScale(3, RoundingMode.UP).doubleValue();
//
//        System.out.println("New double: " + newDouble);
//        List<Map<String,Object>> test = dbDetailController.test();
//        Map<String,Object> tm = test.get(0);
//        System.out.println(DetailElectrodeService.getAllDEPrimitivs().toString());
//        List<DetailElectrod> de = ChainUtil.createChainDetailElectrode(
//                dbDetailController.getAll(),
//                DetailElectrodeService.getAllDEPrimitivs()
//        );
//        System.out.println(de.toString());
//        System.out.println(c.getSimpleName());
//        System.out.println(tm.toString());
//        dbDetailController.test().forEach(m->{
//            m.forEach((key, value) -> {
//                System.out.println(
//                        " Key: "+key+" Key Class: "+key.getClass().getSimpleName()+
//                        " Value: "+value+" Value Class: "+value.getClass().getName()+"\n");
//            });
//            System.out.println(m.keySet()+"\n"+m.values().toString());
//        });
//        System.out.println(dbDetailController.test().toString());
//        System.out.println(TT.ONE);
//        System.out.println(TT.ONE.getNum());
//        System.out.println(TT.ONE.getSt("st1"));
//        System.out.println(TT.ONE.getSt("st2"));
//        System.out.println(TT.ONE.ordinal());
//        List<Electrod> electrods = ElectrodeService.bulkCreateElectrodeFromRange("000001","100000", CustomConstants.ESMG);
//        System.out.println(electrods.isEmpty());
//        electrods.forEach(e->System.out.println(e.getElectrodNumber()));
//        List<String> numbers = Arrays.asList("000111","000098","000052","000021","000071","000089","000051","000014","000013","000011","000012");
//        StringBuilder st = new StringBuilder();
//        for (int i = 0;i<numbers.size();i++){
//            if (numbers.size()-i==1)
//                st.append(numbers.get(i));
//            else
//                st.append(numbers.get(i)).append(",");
//        }
//        String sql = String.format("%s ( %s )", DBConstants.SELECT_ELECTRODS_BY_NUMBERS,st.toString());
//        System.out.println(sql);
//        SummaryService.bulkCreateElectrodeSummaryFromRange("000001","000005",CustomConstants.ESMG, LocalDate.now(),LocalDate.now(),"Test Customer","Data Test");

        StringBuilder ids = new StringBuilder();
        ids.append("1").append(",").append("2").append(",").append("3").append(",").append("4").append(",").append("5").append(",").append("6").append(",").append("7").append(",").append("8").append(",").append("9").append(",");
        System.out.println(ids);
        ids.delete(ids.lastIndexOf(","),ids.lastIndexOf(",")+1);
        System.out.println(ids);


    }

    static class Simple {
        private int value;

        public Simple(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Simple: " + value;
        }
    }

    enum TT{
        ONE(1,"Один","One");

        private final int num;
        private final String st1;
        private final String st2;
        private TT(int i, String ruOne, String one) {
            num=i;st1=ruOne;st2=one;
        }

        public int getNum() {
            return num;
        }
        public String getSt(String fl){
            switch (fl){
                case "st1":return st1;
                case "st2":return st2;
            }
            throw new  AssertionError("Unknown op: " + this);
        }
    }
}

