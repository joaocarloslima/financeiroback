package br.com.fiap.gestanca.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.gestanca.models.Conta;
import br.com.fiap.gestanca.models.Despesa;
import br.com.fiap.gestanca.models.Usuario;
import br.com.fiap.gestanca.repositorys.ContaRepository;
import br.com.fiap.gestanca.repositorys.DespesaRepository;
import br.com.fiap.gestanca.repositorys.UsuarioRepository;

import java.util.List;

@Configuration
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    DespesaRepository despesaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        Conta c1 = new Conta(1L, "itau", new BigDecimal(100), "money");
        Conta c2 = new Conta(2L, "bradesco", new BigDecimal(50), "money");
        Conta c3 = new Conta(3L, "carteira", new BigDecimal(50), "coin");

        contaRepository.saveAll(List.of(c1,c2,c3));

        despesaRepository.saveAll(List.of(
            Despesa.builder().valor(new BigDecimal(100)).data(LocalDate.now()).descricao("aluguel").conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(50)).data(LocalDate.now()).descricao("cinema").conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(34)).data(LocalDate.now()).descricao("luz").conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(67)).data(LocalDate.now()).descricao("netflix").conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(87)).data(LocalDate.now()).descricao("água").conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(50)).data(LocalDate.now()).descricao("gás").conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(45)).data(LocalDate.now()).descricao("trem").conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(9)).data(LocalDate.now()).descricao("cinema").conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(123)).data(LocalDate.now()).descricao("restaurante").conta(c1).build(),
            Despesa.builder().valor(new BigDecimal(65)).data(LocalDate.now()).descricao("ifood").conta(c1).build()

        ));

        usuarioRepository.save(Usuario
                .builder()
                .nome("João Carlos")
                .email("joao@fiap.com.br")
                .senha(encoder.encode("123456"))
                .build());
    }

}
