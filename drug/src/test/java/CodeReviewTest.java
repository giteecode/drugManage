import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



public class CodeReviewTest {

    volatile Integer totalAge = 0;

    CodeReviewTest(PersonDatabase<Person> personPersonDatabase) {
        Person[] persons = null;
        try {
            persons = personPersonDatabase.getAllPersons();
        } catch (IOException e) {
            // 此处建议对异常处理或者抛出，对于问题查找比较方便
        }

        List<Person> personsList = new LinkedList();

        // 数组从0开始，此处会越界异常，应该改为。i < persons.length
        // 也可以直接用 Arrays.asList(persons); 替代
        for (int i = 0; i <= persons.length; i++) {
            personsList.add(persons[i]);
        }

        //  personsList.parallelStream() 并行化导致 totalAge出现一定概率的错误，应该去掉parallelStream 改成串行化，保证数据正确
        personsList.parallelStream().forEach(person -> {
            // 如果此处有Person对象为空，则会出现空指针。应该做判断 if(person != null) {totalAge += person.getAge();}
            totalAge += person.getAge();
        });

        List<Person> males = new LinkedList<>();

        /**
         *  personsList.remove(person); 会报错
         *  原因：正在循环的集合是不允许删除的。
         *  可以改为使用迭代器，通过迭代器进行删除。
         *         Iterator<Person> iter = personsList.iterator();
         *         while (iter.hasNext()){
         *             Person person = iter.next();
         *             // 此处person.gender应该判断空，因为switch 不能是空元素
         *             // person.gender 应该改为 person.getGender(), 否则由于属性会被覆盖得不到正确值
         *             switch (person.getGender()) {
         *                 // 此处应该增加 break ，否则即使匹配也会继续执行 case
         *                 case "Male"  : males.add(person); break;
         *                 case "Female": iter.remove();  break;
         *             }
         *         }
         */
        for (Person person : personsList) {
            // 此处person.gender应该判断空，因为switch 不能是空元素
            // person.gender 应该改为 person.getGender(), 否则由于属性会被覆盖得不到正确值
            switch (person.gender) {
                // 此处应该增加 break ，否则即使匹配也会继续执行 case
                case "Female": personsList.remove(person);
                case "Male"  : males.add(person);
            }
        }

        System.out.println("Total age =" + totalAge);
        // 此处数据取值逻辑错误， females取值应为personsList集合的 移除数量，而非剩余数量。此处应该改为总数减去males数量
        // "Total number of females =" + (persons.length - males.size())
        System.out.println("Total number of females =" + personsList.size());
        System.out.println("Total number of males =" + males.size());
    }

}

// 建议不要作为内部类，新建Person类文件方便重用
class Person {

    // 应该提供set方法  并且建议改为 封装类型 Integer
    private int age;
    // 应该提供set get方法
    private String firstName;
    // 应该提供set get方法
    private String lastName;
    // 此处应该加上 private 修饰, 并且增加 set get方法
    String gender;

    // 构造方法应该加上 gender
    public Person(int age, String firstName, String lastName) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        // 此处字符串等于比较应该改为 equals 因为 == 是比较的内存地址
        // 且此处不应强转，应该 判断 obj instance of NewPerson
        return this.lastName == ((Person)obj).lastName;
    }

}

// 建议不要作为内部接口，新建接口文件，方便重用
interface PersonDatabase<E> {

    Person[] getAllPersons() throws IOException;

}