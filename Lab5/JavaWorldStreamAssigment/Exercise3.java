

import java.util.Optional;

import java.util.Comparator;

public class Exercise3 {

    public static void main(String[] args) {
        CountryDao countryDao = InMemoryWorldDao.getInstance();

        // EX3: Find the highest populated capital city
       Optional<City> highestPopulatedCapital = countryDao.findAllCountries().stream()
        .map(country ->
                country.getCities().stream()
                        .filter(city -> city.getId() == country.getCapital())
                        .findFirst()       
        )
        .flatMap(Optional::stream)         
        .max(Comparator.comparingInt(City::getPopulation)); 

highestPopulatedCapital.ifPresent(capital ->
        System.out.println("Highest populated capital: " +
                capital.getName() + " (" + capital.getPopulation() + ")")
);
    }


}
