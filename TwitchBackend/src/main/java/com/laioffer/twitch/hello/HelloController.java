package com.laioffer.twitch.hello;


import net.datafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;


@RestController
public class HelloController {
    //接受请求 名字多为controller


//    @GetMapping("/hello")
//    public String sayHello() {
////        System.out.println("Nigger World!");
//        return "Nigger World!";
//    }
    //http://localhost:8080/hello -> local host 本机的意思, 8080是端口 默认的
    //找hello annotation在哪 然后call里面的function


//    @GetMapping("/hello")
//    public String sayHello() {
//        Faker faker = new Faker();
//        String name = faker.name().fullName();
//        String company = faker.company().name();
//        String street = faker.address().streetAddress();
//        String city = faker.address().city();
//        String state = faker.address().state();
//        String bookTitle = faker.book().title();
//        String bookAuthor = faker.book().author();
//
//        String template = "This is %s. I work at %s. I live at %s in %s %s. My favorite book is %s by %s";
//        return template.formatted(
//                name,
//                company,
//                street,
//                city,
//                state,
//                bookTitle,
//                bookAuthor
//        );
//    }
    @GetMapping("/hello")
    public Person sayHello(@RequestParam(required = false) String locale) {
        if (locale == null) {
            locale = "en_US";
        }//传一个语言 改语言
        //required = false 一开始可以不用设置 一开始默认的是en_US
        //改true的话必须设置
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        String company = faker.company().name();
        String street = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String bookTitle = faker.book().title();
        String bookAuthor = faker.book().author();


        return new Person(name, company, new Address(street, city, state, null), new Book(bookTitle, bookAuthor));
    }

}