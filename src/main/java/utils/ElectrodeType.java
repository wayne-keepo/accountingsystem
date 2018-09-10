package utils;

import entities.Type;

import java.util.HashMap;
// TODO: возможно пригодится..
public class ElectrodeType {
    private HashMap<Type,String> types;
    private static ElectrodeType instance;

    private ElectrodeType(){types=new HashMap<>();}

    public static ElectrodeType getInstance(){
        if (instance==null)
            instance = new ElectrodeType();
        return instance;
    }

    public HashMap<Type, String> getTypes() {
        return types;
    }

    public void addType(Type type){

    }
}
