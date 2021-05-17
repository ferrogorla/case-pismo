package com.pismo;

import com.pismo.model.OperationType;
import com.pismo.repository.OperationTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CasePismoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasePismoApplication.class, args);
    }

    @Bean
    public CommandLineRunner defaultInserts(OperationTypeRepository operationTypeRepository) {
        return (args) -> {
            operationTypeRepository.save(new OperationType(1l, "COMPRA A VISTA", true));
            operationTypeRepository.save(new OperationType(2l, "COMPRA PARCELADA", true));
            operationTypeRepository.save(new OperationType(3l, "SAQUE", true));
            operationTypeRepository.save(new OperationType(4l, "PAGAMENTO", false));
        };
    }

}
