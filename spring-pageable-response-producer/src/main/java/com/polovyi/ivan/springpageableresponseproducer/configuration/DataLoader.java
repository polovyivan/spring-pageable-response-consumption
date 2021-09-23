package com.polovyi.ivan.springpageableresponseproducer.configuration;

import com.polovyi.ivan.springpageableresponseproducer.entity.CustomerEntity;
import com.polovyi.ivan.springpageableresponseproducer.repository.CustomerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Component
public record DataLoader(CustomerRepository customerRepository) {

    @Bean
    private InitializingBean sendDatabase() {
        return () -> {
            customerRepository.saveAll(generateCustomerList());
        };
    }

    private List<CustomerEntity> generateCustomerList() {
        return IntStream.range(0, 1000)
                .mapToObj(i -> CustomerEntity.builder().createdAt(
                        LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 10)))))
                        .gender(randomString("WM"))
                        .phoneNumber(RandomStringUtils.randomNumeric(15))
                        .build())
                .collect(toList());
    }

    private static String randomString(String fromCharacters) {
        Random random = new Random();
        return String.valueOf(fromCharacters.charAt(random.nextInt(fromCharacters.length())));
    }
}
