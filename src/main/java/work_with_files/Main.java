package work_with_files;

import work_with_files.model.City;
import work_with_files.service.FileService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        FileService fileService = new FileService();
        List<City> cityList = new ArrayList<>();
        try {
            cityList = fileService.loadFileToList();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

        fileService.printList(cityList);
        System.out.println();
        System.out.println("==========Sort by name============");
        fileService.sortByName(cityList);
        fileService.printList(cityList);
        System.out.println();
        System.out.println("==========Sort by district and name============");
        fileService.sortByDistrictAndName(cityList);
        fileService.printList(cityList);
        System.out.println();
        System.out.println("==========Max population============");
        fileService.getMaxPopulation(cityList);
        System.out.println();
        System.out.println("==========Count cities by region============");
        fileService.getCountCitiesByRegion(cityList);

    }
}
