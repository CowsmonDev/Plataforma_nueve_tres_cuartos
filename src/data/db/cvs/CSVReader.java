package data.db.cvs;

import data.ListaDeViajes;
import data.db.cvs.CSVTransfrom;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CSVReader<T extends CSVTransfrom<T>> {
    private String line = "";
    private final String delimiter = ";";
    private String path = "";

    public CSVReader(String path){
        this.path = path;
    }

    public void FullData(ListaDeViajes viajes, T object){
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.path));
            if((line = br.readLine()) != null){
                String[] headers = line.split(delimiter);
                while((line = br.readLine()) != null)
                    this.addData(viajes, object ,line.split(delimiter));
                    //viajes.add(object.transformFromCSV(line.split(delimiter)));
            }
        }catch (FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void addData(ListaDeViajes viajes, T object, String[] line);
}
