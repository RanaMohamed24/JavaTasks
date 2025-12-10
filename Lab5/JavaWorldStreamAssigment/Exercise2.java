
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Exercise2 {

    public static void main(String[] args) {
        CountryDao countryDao = InMemoryWorldDao.getInstance();
// EX2: Find the most populated city of each continent


     Map<String, Optional<City>> result =
        countryDao.findAllCountries().stream()
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.flatMapping(
                                country -> country.getCities().stream(),
                                Collectors.maxBy(Comparator.comparingInt(City::getPopulation))
                        )
                ));

result.forEach((continent, cityOpt) ->
        cityOpt.ifPresent(c ->
                System.out.println(continent + " -> " + c.getName() + " (" + c.getPopulation() + ")")
        )
);

    }

}
