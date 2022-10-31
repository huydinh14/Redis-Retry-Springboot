package com.example.redisspringboot.example;

import com.example.redisspringboot.dao.IEmployeeDao;
import com.example.redisspringboot.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RedisExample implements CommandLineRunner {
    @Autowired
    private RedisTemplate template;

    @Autowired
    private IEmployeeDao empDao;

    @Override
    public void run(String... args) throws Exception {
        //valueExample();
        //listExample();

        //saving one employee
        empDao.saveEmployee(new Employee(500, "Emp0", 2150.0));

        //saving multiple employees
        empDao.saveAllEmployees(
                Map.of( 501, new Employee(501, "Emp1", 2396.0),
                        502, new Employee(502, "Emp2", 2499.5),
                        503, new Employee(503, "Emp4", 2324.75)
                )
        );

        //modifying employee with empId 503
        empDao.updateEmployee(new Employee(503, "Emp3", 2325.25));

        //deleting employee with empID 500
        empDao.deleteEmployee(500);

        //retrieving all employees
        empDao.getAllEmployees().forEach((k,v)-> System.out.println(k +" : "+v));

        //retrieving employee with empID 501
        System.out.println("Emp details for 501 : "+empDao.getOneEmployee(501));


    }
    public void valueExample(){
        // Set giá trị của key "loda" là "hello redis"
        template.opsForValue().set("loda","hello world");

        // In ra màn hình Giá trị của key "loda" trong Redis
        System.out.println("Value of key loda: "+template.opsForValue().get("loda"));
    }

    public void listExample(){
        // Tạo ra một list gồm 2 phần tử
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("redis");

        // Set gia trị có key loda_list
        template.opsForList().rightPushAll("loda_list", list);
//        template.opsForList().rightPushAll("loda_list", "hello", "world");

        System.out.println("Size of key loda: "+template.opsForList().size("loda_list"));
    }
}
