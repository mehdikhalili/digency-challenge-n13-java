package com.mehdikhalili.digencychallenge.n13.configuration;

import com.mehdikhalili.digencychallenge.n13.dto.CardDto;
import com.mehdikhalili.digencychallenge.n13.service.CardService;
import org.dozer.DozerBeanMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

    /**
     * Generating random data
     */
    @Bean
    CommandLineRunner run(CardService service) {
        return args -> {

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Random random = new Random();
            Set<String> cins = new HashSet<>();

            service.add(new CardDto("Khalili", "خليلي", "Zakaria", "زكرياء",
                    "AE000000", "Front End Developer", formatter.parse("15/06/2006"), "A",
                    "https://www.bootdey.com/img/Content/avatar/avatar2.png"));

            service.add(new CardDto("El Hafidi", "الحافظي", "Omar", "عمر",
                    "AA505050", "Civil Engineer", formatter.parse("17/01/1999"), "A",
                    "https://www.bootdey.com/img/Content/avatar/avatar6.png"));

            service.add(new CardDto("Khalili", "خليلي", "Radad", "رداد",
                    "AE000001", "IT Talent Acquisition", formatter.parse("17/07/1992"), "B",
                    "https://www.bootdey.com/img/Content/avatar/avatar2.png"));

            for (int i = 0; i < 25; i++) {
                String nom = "Nom" + i;
                String nomAr = "النسب";
                String prenom = "Prenom" + i;
                String prenomAr = "الاسم";
                String cin;
                String profession = "Profession" + i;
                Date dateNaissance = formatter.parse("01/01/2000");
                String type = "A";
                String image = "https://www.bootdey.com/img/Content/avatar/avatar" + (random.nextInt(8) + 1) + ".png";

                do {
                    cin = "AE" + random.nextInt(1000000);
                } while (!cins.add(cin));

                service.add(new CardDto(nom, nomAr, prenom, prenomAr, cin, profession, dateNaissance, type, image));
            }

        };
    }

}
