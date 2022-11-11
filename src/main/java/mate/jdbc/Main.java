package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer audi = new Manufacturer();
        audi.setName("Audi");
        audi.setCountry("Germany");

        Manufacturer honda = new Manufacturer();
        honda.setName("Honda");
        honda.setCountry("Japanese");

        Manufacturer mcLaren  = new Manufacturer();
        mcLaren.setName("mcLaren");
        mcLaren.setCountry("UK");

        Manufacturer ferrari = new Manufacturer();
        ferrari.setName("Ferrari");
        ferrari.setCountry("Italy");

        manufacturerDao.create(audi);
        manufacturerDao.create(honda);
        manufacturerDao.create(mcLaren);
        manufacturerDao.create(ferrari);

        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.getAll());
    }
}
