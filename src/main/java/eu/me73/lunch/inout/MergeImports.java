package eu.me73.lunch.inout;

import eu.me73.lunch.classes.Day;
import eu.me73.lunch.classes.Order;
import eu.me73.lunch.classes.Person;
import eu.me73.lunch.classes.PersonType;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by batky on 18.7.2015.
 */
public class MergeImports {

    private final ImportFromOldVersion importFromOldVersion;
    private final ImportFromFile importFromFile;
    private final ExportToFile exportToFile;

    public MergeImports(ImportFromOldVersion importFromOldVersion, ImportFromFile importFromFile, ExportToFile exportToFile) {
        this.importFromOldVersion = importFromOldVersion;
        this.importFromFile = importFromFile;
        this.exportToFile = exportToFile;
    }

    public void mergeAndSave() {
        ArrayList<Day> daysOld = importFromOldVersion.getAllDays();
        ArrayList<Person> personOld = importFromOldVersion.getAllPersons();
        ArrayList<Order> ordersOld = importFromOldVersion.getAllOrders();
        Map<PersonType, Double> pricesOld = importFromOldVersion.getPrices();

        ArrayList<Day> days = importFromFile.getAllDays();
        ArrayList<Person> person = importFromFile.getAllPersons();
        ArrayList<Order> orders = importFromFile.getAllOrders();
        Map<PersonType, Double> prices = importFromFile.getPrices();

        ArrayList<Person> personNew = (ArrayList<Person>) Stream.concat(person.stream(), personOld.stream()).distinct().collect(Collectors.toList());
        ArrayList<Day> daysNew = (ArrayList<Day>) Stream.concat(days.stream(), daysOld.stream()).distinct().collect(Collectors.toList());
        ArrayList<Order> ordersNew = (ArrayList<Order>) Stream.concat(orders.stream(), ordersOld.stream()).distinct().collect(Collectors.toList());
        Map<PersonType, Double> pricesNew;

        if (prices.isEmpty()) {
            pricesNew = pricesOld;
        } else {
            pricesNew = prices;
        }

        exportToFile.saveDays(daysNew);
        exportToFile.saveOrders(ordersNew);
        exportToFile.savePersons(personNew);
        exportToFile.savePrices(pricesNew);

    }

}