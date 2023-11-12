package dev.lucasmendes.simple_pdf;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class EmployeeService {
    private final List<Employee> employees;

    public EmployeeService(int qtd) {
        this.employees = createEmployees(qtd);
    }

    private List<Employee> createEmployees(int qtd) {
        var list = new ArrayList<Employee>();
        var random = new Random();

        var firstNames = List.of("João", "Maria", "Pedro", "Ana", "Paulo");
        var lastNames = List.of("Silva", "Santos", "Oliveira", "Souza", "Lima");
        var positions = List.of("Gerente", "Analista", "Desenvolvedor", "Testador", "Arquiteto");

        for (int i = 0; i < qtd; i++) {
            int id = i + 1;
            var firstName = firstNames.get(random.nextInt(firstNames.size()));
            var ultimoNome = lastNames.get(random.nextInt(lastNames.size()));
            var position = positions.get(random.nextInt(positions.size()));
            var active = random.nextBoolean();
            var salary = 1000.0 + random.nextDouble() * 9000.0;
            salary = Math.round(salary * 100.0) / 100.0;
            var employee = new Employee(id, firstName, ultimoNome, position, active, salary);
            list.add(employee);
        }

        return list;
    }
}
