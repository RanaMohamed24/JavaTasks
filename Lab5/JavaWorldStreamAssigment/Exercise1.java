
import java.util.Comparator;
import java.util.Optional;

public class Exercise1 {

   public static void main(String[] args) {
      CountryDao countryDao = InMemoryWorldDao.getInstance();

      // EX1: Find the highest populated city of each country
     /// Option1
     System.out.println("Using Option 1:");

       countryDao.findAllCountries().stream().forEach(country->{Optional<City> highestPopulCity=
         country.getCities().stream().max(Comparator.comparingInt(City::getPopulation));

         highestPopulCity.ifPresent(city -> System.out
               .println(country.getName() + " -> " + city.getName() + " (" + city.getPopulation() + ")"));

       });
       System.out.println("=========================================================");
System.out.println("Using Option 2:");

System.out.println("=========================================================");
       countryDao.findAllCountries()
                .forEach(country ->
        country.getCities().stream()
            .max(Comparator.comparingInt(City::getPopulation))
            .ifPresent(city ->
                System.out.println(country.getName() + " -> " +
                        city.getName() + " (" + city.getPopulation() + ")")
            )
    );

      





    
   }
}