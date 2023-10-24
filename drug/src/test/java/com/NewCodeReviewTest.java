
package  com;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class NewCodeReviewTest {

    volatile Integer totalAge = 0;

    NewCodeReviewTest(NewPersonDatabase<NewPerson> personPersonDatabase) {
        NewPerson[] persons = null;
        try {
            persons = personPersonDatabase.getAllPersons();
        } catch (IOException e) {

        }

        List<NewPerson> personsList = Collections.synchronizedList(new LinkedList<NewPerson>());


        // 数组从0开始，此处会越界异常，应该改为。i < persons.length
        for (int i = 0; i < persons.length; i++) {
            personsList.add(persons[i]);
        }
        System.out.println("------>" + personsList.size());

        personsList.forEach(person -> {

            if(person != null) {
                totalAge += person.getAge();
            }
        });

        List<NewPerson> males = new LinkedList<>();

        Iterator<NewPerson> iter = personsList.iterator();
        while (iter.hasNext()){
            NewPerson person = iter.next();
            // 此处person.gender应该判断空，因为switch 不能是空元素
            // person.gender 应该改为 person.getGender(), 否则由于属性会被覆盖得不到正确值
            switch (person.getGender()) {
                // 此处应该增加 break ，否则即使匹配也会继续执行 case

                case "Male"  : males.add(person); break;
                case "Female": iter.remove(); System.out.println("移除--------"); break;
            }
        }
//        for (Person person : personsList) {
//            switch (person.gender) {
//                case "Female": personsList.iterator().remove();
//                case "Male"  : males.add(person);
//            }
//        }

        System.out.println("Total age =" + totalAge);
        // 此处数据取值逻辑错误， females取值应为personsList集合的 移除数量，而非剩余数量。此处应该改为总数减去males数量
        // "Total number of females =" + (persons.length - males.size())
        System.out.println("Total number of females =" + (persons.length - males.size()));
        System.out.println("Total number of males =" + males.size());
    }

    public static void main(String[] args) {
        new NewCodeReviewTest(new NewPersonDatabase() {
            @Override
            public NewPerson[] getAllPersons() throws IOException {
                NewPerson[] a = new NewPerson[5];

                a[0] = new NewPerson(2, "aaa", "vvvv", "Male");
                a[1] = new NewPerson(5, "bbb", "ccc", "Female");

                a[2] = new NewPerson(5, "ccc", "bbb", "Female");

                a[3] = new NewPerson(5, "666", "888", "Male");

                a[4] = new NewPerson(5, "0", "9", "Female");
                return a;
            }
        });
    }

}


class NewPerson {

    // 应该提供set方法  并且建议改为 封装类型 Integer
    private int age;
    // 应该提供set get方法
    private String firstName;
    // 应该提供set get方法
    private String lastName;
    // 此处应该加上 private 修饰, 并且增加 set get方法
    private String gender;

    // 构造方法应该加上 gender
    public NewPerson(int age, String firstName, String lastName, String gender) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        // 此处字符串等于比较应该改为 equals 因为 == 是比较的内存地址
        // 且此处不应强转，应该 判断 obj instance of NewPerson
        return this.lastName == ((NewPerson)obj).lastName;
    }

}


interface NewPersonDatabase<E> {

    // 此处理论上不是抛IOException，可以改为 Exception或去掉
    NewPerson[] getAllPersons() throws IOException;

}

