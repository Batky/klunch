/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.me73.lunch.inout;

import eu.me73.lunch.classes.*;

import java.text.Collator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbacigal
 */
public abstract class Import implements ImportInterface{
    
    private static final Logger log = LoggerFactory.getLogger(Import.class);
    
    String personFile;
    String lunchesFile;
    String ordersFile;
    String pricesFile;
    ArrayList<Person> users;
    ArrayList<Day> days;
    ArrayList<Order> orders;
    Map<PersonType, Double> prices;
    boolean reload;

    public Import(String personFile, String lunchesFile, String ordersFile, String pricesFile) {
        this.personFile = personFile;
        this.lunchesFile = lunchesFile;
        this.ordersFile = ordersFile;
        this.pricesFile = pricesFile;
        this.orders = null;
        this.users = null;
        this.days = null;
        this.prices = null;
        reload = false;
    }

    public Import(String path, String fileExtension) {
        this.personFile = path + "//zam." + fileExtension;
        this.lunchesFile = path + "//obed." + fileExtension;
        this.ordersFile = path + "//objednavky." + fileExtension;
        this.pricesFile = path + "//ceny.ini";
        this.orders = null;
        this.users = null;
        this.days = null;
        this.prices = null;
        reload = false;
    }

    public boolean isSetReload() {
        return reload;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }
    
    protected CharSequence remakeOldDateString(String s) {    
        log.debug("Remaking string {}", s);
        String newString = s.substring(0, s.length() - 7).replaceAll("\\s", "");
        log.trace("Phase 1 (replacing): {}", newString);
        String [] tmpString = newString.split("\\.");
        log.trace("Phase 2 (splitting): {}", Arrays.toString(tmpString));
        if (tmpString.length < 3) {
            String [] skuska = newString.split("/");
            log.trace("Phase 3 (splitting date): {}", Arrays.toString(skuska));
            if (skuska.length < 3) {
                log.debug("Bad date ! Creating date 1.1.1990");
                newString = "1.1.1990";
            } else {
                newString = skuska[0]+"."+skuska[1]+".";
                if (skuska[2].length() > 4) {                    
                    newString += skuska[2].substring(0, 4);
                    log.trace("Year has more than four figures");
                } else {
                    newString += skuska[2];
                    log.trace("Year has four figures");
                }
            }
        } else {
            log.trace("Phase 3 (creating date)");
            newString = tmpString[0]+"."+tmpString[1]+".";
            if (tmpString[2].length() == 2) {
                //TODO:Working only for 21 century, make it independent
                log.trace("Year without century, adding 21th century");
                newString += "20" + tmpString[2];
            } else {
                if (tmpString[2].length() > 4) {
                    newString += tmpString[2].substring(0, 4);
                    log.trace("Year has more than four figures");
                } else {
                    newString += tmpString[2];
                    log.trace("Year has four figures");
                }
            }
        }
        return newString;
    }

    @Override
    public Person getPersonByID(String id) {
        if (users == null) {
            this.getAllPersons();
        }
        Person per = null;
        try {
            per = users.stream().filter((u)->u.getId().equals(id)).findFirst().get();
        } catch (Exception e) {
            System.err.println(e);
        }
        return per;
    }

    @Override
    public Person getPersonByBarCode(String barCode) {
        if (users == null) {
            this.getAllPersons();
        }
        return users.stream().filter((u)->u.getBarcode().equals(barCode)).findFirst().get();
    }

    @Override
    abstract public ArrayList<Person> getAllPersons();

    @Override
    public Day getDay(LocalDate aDay) {
        if (days == null) {
            this.getAllDays();
        }
        if (days.stream().noneMatch((d)->d.getDate().equals(aDay))) {
            return null;
        }
        return days.stream().filter((d)->d.getDate().equals(aDay)).findFirst().get();
    }
    
    @Override
    public ArrayList<Person> getAllPersonsSorted(Collator collator){
        log.trace("Sorting all persons by locale: {}", collator.toString());
        return (ArrayList) this.getAllPersons().stream()
                .sorted((p1, p2)-> collator.compare(p1.getLongName(), p2.getLongName()))
                .collect(Collectors.toList());
    }
    
    public ArrayList<Person> getAllPersonsSortedSK() {
        log.trace("Sorting all persons by locale: sk");
        return this.getAllPersonsSorted(Collator.getInstance(Locale.forLanguageTag("sk")));
    }

    @Override
    abstract public ArrayList<Day> getAllDays();

    @Override
    abstract public ArrayList<Order> getAllOrders();

    @Override
    public ArrayList<Order> getOrdersByDate(LocalDate day) {
        if (this.orders == null) {
            this.getAllOrders();
        }
        Collator collator = Collator.getInstance(Locale.forLanguageTag("sk"));
        return new ArrayList<>(
                this.orders.stream()
                        .filter(
                                (o)->o.getDay().equals(day)
                        )
                        .sorted(
                                (o1, o2)-> collator.compare(o1.getPerson().toString(), o2.getPerson().toString())
                        )
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Order getOrderByPersonIDAndDate(String id, LocalDate day) {
        if (this.orders == null) {
            this.getAllOrders();
        }
        Order newOrder = null;
        try {
            newOrder = orders.stream().filter((o)->o.getDay().equals(day) && o.getPerson().getId().equals(id)).findFirst().get();
        }
        catch(Exception e) {
            System.err.println(e);
        }
        return newOrder; 
    }

    @Override
    public ArrayList<Order> getOrdersByMonth(LocalDate month) {
        if (this.orders == null) {
            this.getAllOrders();
        }
        Collator collator = Collator.getInstance(Locale.forLanguageTag("sk"));
        return new ArrayList<>(
                this.orders.stream()
                        .filter(
                                (o)->o.getDay().getMonth().equals(month.getMonth())
                        )
                        .sorted(
                                (o1, o2)-> collator.compare(o1.getPerson().toString(), o2.getPerson().toString())
                        )
                        .sorted(
                                (o1, o2)-> o1.getDay().compareTo(o2.getDay())
                        )
                        .collect(Collectors.toList()));        
    }

    @Override
    public ArrayList<Order> getOrdersByYear(LocalDate year) {
        if (this.orders == null) {
            this.getAllOrders();
        }
        Collator collator = Collator.getInstance(Locale.forLanguageTag("sk"));
        return new ArrayList<>(
                this.orders.stream()
                        .filter(
                                (o)->o.getDay().getYear() == year.getYear()
                        )
                        .sorted(
                                (o1, o2)-> collator.compare(o1.getPerson().toString(), o2.getPerson().toString())
                        )
                        .sorted(
                                (o1, o2)-> o1.getDay().compareTo(o2.getDay())
                        )
                        .collect(Collectors.toList()));        
    }

    @Override
    public ArrayList<DailyMealSummary> getDailySummary(LocalDate day) {
       ArrayList<DailyMealSummary> summary = new ArrayList<>();
       if (this.getDay(day) != null) {
           this.getDay(day).getMealOfTheDay().stream().forEach((meal)->{
               summary.add(new DailyMealSummary(meal));
           });
           int iSoup = 1; int iMeal = 1;
           for (DailyMealSummary s : summary) {
               if (s.getMeal().isSoup()) {
                   s.setOrderNumber(iSoup++);
               } else {
                   s.setOrderNumber(iMeal++);
               }
           }
           summary.stream().forEach((dm)->{
               if (dm.getMeal().isSoup()) {
                   dm.setCount((int)this.getOrdersByDate(day).stream().filter((o)->o.getSelectedSoup() == dm.getOrderNumber()).count());
               } else {
                   dm.setCount((int)this.getOrdersByDate(day).stream().filter((o)->o.getSelectedMainMeal() == dm.getOrderNumber()).count());
               }
           });
       }
       return summary;
    }
    
    private Stream<Person> getDistinctPersonsFromMonthOrders(LocalDate month){
        log.trace("Getting distinct persons from month orders");
        ArrayList<Person> personList = new ArrayList<>();
        this.getOrdersByMonth(month).stream()
                .forEach((o)->personList.add(
                        new Person(
                                o.getPerson().getId(), 
                                o.getPerson().getName(), 
                                o.getPerson().getLastName(), 
                                o.getPerson().getBarcode(), 
                                o.getPerson().getType())
                ));
//        Stream<Person> personStream = personList.stream().distinct();
        log.debug("All persons list, number of objects {}", personList.size());        
        log.debug("Distincts persons list, number of objects {}", personList.stream().distinct().count());
        return personList.stream().distinct();
    }

    @Override
    public ArrayList<MonthSummary> getMonthSummary(LocalDate month) {
        log.trace("Returning month summary");
        ArrayList<MonthSummary> summary = new ArrayList<>();
        this.getDistinctPersonsFromMonthOrders(month)
                .forEach((p)->summary.add(new MonthSummary(p, (int)this.getOrdersByMonth(month).stream().filter((o)->o.getPerson().equals(p)).count())));
        Collator collator = Collator.getInstance(Locale.forLanguageTag("sk"));
        return (ArrayList<MonthSummary>)
                summary.stream()
                        .filter((ms)->ms.getMealCount()!=0)
                        .sorted((ms1, ms2)->collator.compare(ms1.getPerson().toString(), ms2.getPerson().toString()))
                        .sorted((ms1, ms2)->ms1.getPerson().getType().compareTo(ms2.getPerson().getType()))
                        .collect(Collectors.toList());
    }
    
    abstract public Map<PersonType, Double> getPrices();

    /**
     * Path to the persons data file
     * @return String
     */
    public String getPersonFile() {
        return personFile;
    }

    public String getLunchesFile() {
        return lunchesFile;
    }

    public String getOrdersFile() {
        return ordersFile;
    }

    public String getPricesFile() {
        return pricesFile;
    }
    
    

}
