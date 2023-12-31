import model.City;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Реализация сортировки по имени и по имени и федеральному округу:
        Comparator<City> byName = (City c1, City c2) -> c1.getName().compareTo(c2.getName());
        Comparator<City> byNameAndDistrict = Comparator.comparing(City::getDistrict).thenComparing((City::getName));

        String filePath = "src/main/resources/data.csv";
        Path path = Paths.get(filePath);
        ArrayList<City> cities = new ArrayList<>();
        int citiesSize = 0;
        try {
            Scanner scanner = new Scanner(path);
            scanner.useDelimiter("\r\n");
            while(scanner.hasNext()){
                String[] cityInfo = scanner.next().split(";");
                if(cityInfo.length !=6){
                    continue;
                }
                String name = cityInfo[1];
                String region = cityInfo[2];
                String district = cityInfo[3];
                int population = Integer.parseInt(cityInfo[4]);
                String foundation = cityInfo[5];
                City city = new City(name, region, district, population, foundation);
                cities.add(city);
                citiesSize++;
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.print("problematic line: ");
            System.out.println(citiesSize - 1);
        }
        HashMap<String, Integer> sortedByRegion = sortedByRegion(cities);
        Set<String> regions = sortedByRegion.keySet();
        regions.forEach(r -> System.out.println(r + " - " + sortedByRegion.get(r)));
    }

    public static String mostPopulousCity(ArrayList<City> cities){
        Comparator<City> byPopulation = Comparator.comparing(City::getPopulation);
        cities.sort(byPopulation);
        int population = cities.get(cities.size() - 1).getPopulation();
        City[] citiesArray = cities.toArray(new City[0]);
        return "[" + (citiesArray.length - 1) + "] = " + population;
    }

    public static HashMap<String, Integer> sortedByRegion(ArrayList<City> cities){
        HashMap<String, Integer> sortedCities = new HashMap<>();
        cities.forEach(c -> {
            if(sortedCities.containsKey(c.getRegion())){
                sortedCities.put(c.getRegion(), sortedCities.get(c.getRegion()) + 1);
            } else {
                sortedCities.put(c.getRegion(), 1);
            }
        });
        return sortedCities;
    }

}
