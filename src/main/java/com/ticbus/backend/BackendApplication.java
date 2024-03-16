package com.ticbus.backend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticbus.backend.model.City;
import com.ticbus.backend.repository.CityRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
        exclude = {RedisRepositoriesAutoConfiguration.class}
)
@Log4j2
@EnableTransactionManagement
@EnableJpaRepositories("com.ticbus.backend.repository")
@EntityScan(basePackages = "com.ticbus.backend.model")
@EnableCaching
public class BackendApplication {

    private static final String CITY_JSON = "/city.json";

    @Autowired
    private CityRepository cityRepository;

    @Value(value = "${spring.datasource.url}")
    private String url;

    @Value(value = "${spring.datasource.username}")
    private String username;

    @Value(value = "${spring.datasource.password}")
    private String password;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BackendApplication.class, args);

    }

    public static boolean tableExist(Connection conn, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void migrateData() throws SQLException, ClassNotFoundException {
        initCityDataTable();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }

    private void initCityDataTable() {
        try {
            Connection con = getConnection();
            if (!tableExist(con, "tb_city")) {
                InputStream stream = BackendApplication.class.getResourceAsStream(CITY_JSON);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
                List<City> cities = mapper.readValue(stream, new TypeReference<List<City>>() {
                });
                if (CollectionUtils.isNotEmpty(cities)) {
                    cityRepository.deleteAll();
                    cityRepository.saveAll(cities);
                }
            }
        } catch (Exception e) {
            log.error("Exception while init City data table: ", e);
        }
    }


}
