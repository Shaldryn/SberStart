package work_with_files.service;

import work_with_files.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileService {

    public List<City> loadFileToList() throws FileNotFoundException{

        File file = new File(Objects.requireNonNull(FileService.class.getClassLoader().getResource("cities.txt")).getFile());

        List<City> listCities = new ArrayList<>();
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String[] arrLines = sc.nextLine().split(";");
            listCities.add(new City(Long.parseLong(arrLines[0]),
                    arrLines[1],
                    arrLines[2],
                    arrLines[3],
                    Integer.parseInt(arrLines[4]),
                    Integer.parseInt(arrLines[5])));
        }
        sc.close();

        return listCities;
    }

//    private void validCheckLine(String[] arrLine){
//
//    }

    public void printList(List<City> cityList) {
        for (City city :
                cityList) {
            System.out.println(city);
        }
    }

    public void sortByName(List<City> cityList) {
        cityList.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
    }

    public void sortByDistrictAndName(List<City> cityList) {
        cityList.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName));
    }

    public void getMaxPopulation(List<City> cityList) {
        City[] arrCities = cityList.toArray(new City[0]);
        int max = 0;
        int index = 0;
        for (int i = 0; i < arrCities.length; i++) {
            if (arrCities[i].getPopulation() > max) {
                max = arrCities[i].getPopulation();
                index = i;
            }
        }
        System.out.printf("[%d]=%d%n", index, max);
    }

    public void getCountCitiesByRegion(List<City> cityList) {
        Map<String, Integer> result = cityList.stream().collect(HashMap::new, (map, value) -> {
            map.merge(value.getRegion(), 1, Integer::sum);
        }, HashMap::putAll);

        for (Map.Entry<String, Integer> entity :
                result.entrySet()) {
            System.out.printf("%s - %d%n", entity.getKey(), entity.getValue());
        }
    }
}
