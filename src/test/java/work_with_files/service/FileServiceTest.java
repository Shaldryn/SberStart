package work_with_files.service;

import work_with_files.model.City;
import org.junit.*;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FileServiceTest {
    private static FileService fileService;
    private List<City> sortedCityList = new ArrayList<>();
    private List<City> cityList;

    @BeforeClass
    public static void initFileService() {
        fileService = new FileService();
    }

    @Before
    public void beforeEachTest() {
        System.out.println("Start test");
        System.out.println("Sort list");
        try {
            sortedCityList = fileService.loadFileToList();
            cityList = new ArrayList<>(sortedCityList);
            sortedCityList.sort(Comparator.comparing(City::getName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean sorted(List<City> cityList) {
        if (cityList.isEmpty()){
            return false;
        }
        for (int i = 0; i < cityList.size(); i++) {
            if (!cityList.get(i).getName().equals(sortedCityList.get(i).getName())) {
                return false;
            }
        }
        return true;
    }

    @After
    public void afterEachTest() {
        System.out.println("End test");
        cityList.clear();
        sortedCityList.clear();
    }

    @Test
    public void testLoadFileToList() {
        try {
            List<City> cityList = fileService.loadFileToList();
            assertFalse(cityList.isEmpty());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortByName() {
        fileService.sortByName(cityList);
        assertTrue(sorted(cityList));
    }

//    @Test
//    public void testGetMaxPopulation(){
//        String expected = "[882]=11514330";
//
//    }
}
