#### Аннотации

* `@FieldNameConstants`
* `@Getter`
* `@Setter`
* `@NoArgsConstructor`
* `@AllArgsConstructor`
* `@RequiredArgsConstructor`
* `@ToString`
* `@EqualsAndHashCode`
* `@Builder`
* `@Builder.Defaul`
* `@With`
* `@Singular`
* `@SneakyThrows`

#### Примеры

Пример для:

* `@FieldNameConstants`
* `@Getter`
* `@Setter`
* `@NoArgsConstructor`
* `@AllArgsConstructor`
* `@RequiredArgsConstructor`
* `@ToString`
* `@EqualsAndHashCode`
* `@Builder`
* `@Builder`.Defaul
* `@With`
* `@Singular`
* `@SneakyThrows`

```java
    @FieldNameConstants // создаёт класс FirstDataClass.Fields.class с именами-константами каждого из полей
    @Getter // создаёт getters для каждого из полей
    @Setter // создаёт setters для каждого из полей
    @NoArgsConstructor // создаёт конструктор без аргументов
    @AllArgsConstructor // создаёт конструктор со всеми аргументами
    @RequiredArgsConstructor // создаёт конструктор для final-аргументов
    @ToString // переопределяет Object.toString()
    @EqualsAndHashCode // переопределяет Object.equals() и Object.hashCode()
    // @Data // эквивалент @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
             // нужно быть осторожным с @Data, @ToString, @EqualsAndHashCode
             // если в сущности есть кольцевые зависимости, то операции сравнения могут войти
             // в бесконечный цикл
    @Builder // создаёт класс FirstDataClass.Builder.class, с методами для каждого из полей
    public class FirstDataClass {
        @Builder.Default // позволяет задавать дефолтное значение поля в билдере
        private final Long id = -1;
        @With // создаёт метод копирования, позволяющий создать такой же объект с одним изменённым значением
        private SecondDataClass secondData;
        @Singular // позволяет создать метод для добавления одного элемента коллекции
        private List<SecondDataClass> secondDataClasses;

        @SneakyThrows   // позволяет убрать ошибку необработанного исключения в методе, под капотом
                        // exception помещается в потомка RuntimeException
        public void foo() {
            throw new IOException();
        }
    }
   
```
