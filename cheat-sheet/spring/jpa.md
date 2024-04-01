#### JPA

* `@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "personNumber", "isActive" }) })` -
* `@UniqueConstraint` -
* `@Entity(name = "orders)` - jpa-аннотация показывает что данный класс является моделью, которая представляет собой
  таблицу в базе данных
* `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)` - данная комбинация над полем класса показывает, что поле
  является идентификатором таблицы (`@Id`), а также, что это поле должно генерироваться самой базой данных со
  стратегией `GenerationType.IDENTITY` (`@GeneratedValue(strategy = GenerationType.IDENTITY)`)
* `@ManyToOne` - над полем типа `@Entity B` внутри `@Entity A` показывает, что множество сущностей `@Entity A` имеют
  ссылку (иметь связь) с одним экземпляром (одной записью в таблице) `@Entity B`.
* `@JoinColumn(name = "foo_id")` - над полем типа `@Entity B` внутри `@Entity A` показывает, что связь между `@Entity A`
  и `@Entity B` осуществляется посредством записи значения идентификатора (поля с аннотацией `@Id`) `@Entity B` в
  колонку `foo_id` `@Entity A`.
* `@OneToMany` - над полем типа(коллекции) `@Entity B` внутри `@Entity A` показывает, что одна сущность `@Entity A`
  имеет ссылку на множество сущностей `@Entity B`. Это может достигаться за счёт того, что `@Entity B` имеет столбец с
  указанием на `@Entity A`, в этом случае выделяется поле по которому можно определить `@Entity A` через `@Entity B`,
  это делается за счёт указания `@OneToMany(mappedBy = "someA")`, где `someA` - это поле в `@Entity B`. Может ли это
  достигаться за счёт создания отдельной таблицы?
* `@OneToMany(cascade = CascadeType.ALL)` - `cascade` - определяет, должны ли удаляться/сохраняться/обновляться
  связанные сущности при удалении текущей.
* `@CreatedTimestamp` - показывает, что поле должно содержать время создания сущности.
* `@UpdateTimestamp` - показывает, что поле должно содержать время обновления записи сущности.
* `JpsRepository<EntityType, KeyType>` - базовый интерфейс для репозитория, его основная реализация генерируется
  фреймворком jpa, имеет несколько встроенных методов. В нём можно создавать методы, через название которых можно задать
  семантическое составление запроса;
* `Pageable` - объект, содержащий информацию о limit информации в запросе + номер `page`
    - `PageRequest.of()` - утилитный класс для создания объекта `Pageable`
* `Page<Foo>` - объект, содержащий пачку запрошенных объектов с помощью Pageable + доп. информацию о кол-ве суммарной
  информации, количестве страниц и т.д.
    - `Page<Foo>.getContent()` - метод, который возвращает `List<Foo>` объектов
* `JpaSpecificationExecutor<Foo>` - класс, позволяющий использовать `Pageable.java` и `Specification.java` для поиска,
  фильтрации и выборки данных в `JapRepository<Foo>`. Как и для `JapRepository<Foo>` реализация методов будет
  сгенерирована автоматически. Наследоваться нужно одновременно от `JpaSpecificationExecutor<Foo>`
  и `JapRepository<Foo>`.
* `Specification<Foo>` - класс, содержащий спецификацию (критерии) фильтрации (выборки) данных из базы
    - `Specification.where(Specification<Foo>)` - статический метод позволяет начать цепочку спецификаций
    - `Specification<Foo>.and()` - позволяет добавить в цепочку условий новую спецификацию (предикат) со значением
* `JPQL` - язык запросов в `jpa`, который позволяет обращаться не к столбцам таблицы бд, а к полям `@Entity`
* `@Query` - аннотация над методом в классе-наследнике `JapRepository<>` позволяет написать кастомный запрос на
  языке `JPQL` или `SQL`
* `Example<>` - класс, с помощью которого можно запрашивать данные в `JpaRepository<>`.

* `ACID`
    - `Атомарность (Atomicity)` - транзакция считается атомарной если все её операции выполняются как единое целое. Если
      хотя бы одна операция транзакции не может быть выполнена, то все изменения, сделанные предыдущими операциями,
      откатываются.
    - `Согласованность (Consistency)` - транзакция должна обеспечивать целостность данных. Это означает, что база данных
      должна оставаться в согласованном состоянии до и после выполнения транзакции.
    - `Изолированность (Isolation)` - транзакции должны быть изолированы друг от друга. Это означает, что результаты
      одной транзакции не должны влиять на результаты другой транзакции. Это предотвращает конфликты и сбои в случае с
      параллельным выполнением транзакций.
    - `Долговечность (Durability)` - после успешного завершения транзакции её изменения должны быть сохранены в базе
      данных и остаться действительными даже в случае сбоев системы или отключения питания.
* `@Transactional` - аннотация над `методом` позволяет выполнять внешние вызовы `этого` метода в режиме транзакции
* `@Transactional` - аннотация над `классом` позволяет выполнять внешние вызовы `всех` методов класса в режиме
  транзакции
* `@Transactional` - позволяет устанавливать режимы `read-only` и `read-write`
* `@Transactional` - позволяет устанавливать уровень изоляции от других транзакций
* `@Transactional` - позволяет управлять точкам сохранения `savepoints` - они позволяют разделить транзакцию на
  несколько логических частей
* `@Transactional` - позволяет настроить поведение транзакции в случае возникновения исключения
* `@Transactional` - на самом деле сам метод класса не выполняется в режиме транзакции, `Spring` создаёт proxy-объект
  для всех мест, куда был затребован объект данного типа и уже методы этого proxy-объекта выполняются в режиме
  транзакции. Это ведёт к следующим особенностям:
    - Пусть класс `A` имеет метод `foo()`, который помечен `@Transactional`, класс `B` запросил у `spring` объект
      класса `A aObject`. Тогда вызовы `aObject.foo()` изнутри `B` будут проходить в режиме транзакции.
    - Пусть класс `A` имеет методы `foo()` с пометкой `@Transactional` и `foo2()` без пометки `@Transactional`, пусть
      метод `foo2()` вызывает метод `foo()`, пусть класс `B` запросил у `spring` объект класса `A aObject`. Тогда при
      вызове методов объекта `A aObject`изнутри `B` имеются две ситуации:
        + если вызывается метод `aObject.foo()`, то работа метода будет проходить в режиме транзакции
        + если вызывается метод `aObject.foo2()`, то работа метода `foo2()` и вызванного изнутри метода `foo2()`
          метода `foo()` будут выполняться `вне` режима транзакции, т.к. метод `foo2()` вызвал метод `foo()` у
          оригинального, а не proxy-объекта.
* `@OneToMany(fetch = LAZY)` - `fetch = LAZY` - этот параметр говорит, что связанная с полем `@Entity` не будет
  вычитываться сразу при запросе исходной сущности, а только при обращении к полю. Если исходная сущность запрашивается
  в контексте транзакции, то попытка запроса связанной сущности вне транзакции может привести к выбрасыванию
  исключения `LazyInitializationException.java`, если свойство `spring.jpa.open-in-view` будет стоять в `false` (
  по-умолчанию `true`)
* `spring.jpa.open-in-view` - если значение `true` (по-умолчанию `true`) - автоматически открывает соединение при
  попытке вычитывания связанной сущности, даже вне транзакции. Если значение `false` автоматически соединение не
  открывается и тогда будет выкидываться ошибка `LazyInitializationException.java`
* `@EntityGraph` - позволяет указать, какие связанные сущности должны быть запрошены при запросе родительской сущности.
* `@Entity` - объект entity полученный внутри `@Transactional` сохранит своё состояние по окончании транзакции
  автоматически, это нужно учитывать в тех случаях, когда изменяемые поля имеют модификатор отношений со
  свойством `cascade`.

#### Примеры

* Пример составления семантического запроса для наследников `JpaRepository<>`:

```java
 public interface FooRepository extends JpaRepository<Foo, Long> {
    List<Foo> findAllByProduct(String product);

    List<Foo> findAllByClientId(Long clientId);

    List<Foo> findAllByClientName(String clientName);

    @Data
    @Builder
    @Entity
    class Foo {
        @Id
        private Long id;
        private String product;
        private Client client;
    }

    @Data
    @Builder
    @Entity
    class Client {
        @Id
        private Long id;
        private String name;
    }
} 
 ```

* Примеры `Specification<>`:

```java
   private static Specification<Post> categorySpecificationOf(@Nonnull PostListRequest request) {
    return (root, query, cb) -> {
        List<Long> categoryIds = request.getCategories();
        if (CollectionUtils.isEmpty(categoryIds)) {
            return null;
        }
        return root
                .<Post, Category>join(Post.Fields.categories) // Post.Fields.categories - коллекция List<Category>
                .get(Category.Fields.id)
                .in(categoryIds);
    };
}
  ```

```java

@Data
@Builder
@Entity
class Foo {
    @Id
    private Long id;
    private String product;
    private Client client;
    private BigDecimal cost;
}

@Data
@Builder
@Entity
class Client {
    @Id
    private Long id;
    private String name;
}
```

```java
   // фильтрация по имени продукта
@RequireArgConstructor
class MySpecification implements Specification<Foo> {
    private final String productName;

    @Override
    public Predicate toPredicate(
            Root<Foo> root,                 // 
            CriteriaQuery<?> query,         // 
            CriteriaBuilder criteriaBuilder // 
    ) {
        if (productName == null) {
            return null;
        }

        return cb.equal(root.get("product"), productName);
    }
}
   ```

```java
    // фильтрация по range цены товара
private final BigDecimal minCost;
private final BigDecimal maxCost;

@Override
public Predicate toPredicate(
        Root<Foo> root,                 // 
        CriteriaQuery<?> query,         // 
        CriteriaBuilder criteriaBuilder // 
) {
    if (minCost == null && maxCost == null) {
        return null;
    }
    if (minCost == null) {
        return cb.lessThanOrEqualTo(root.get("cost"), maxCost);
    }
    if (maxCost == null) {
        return cb.greaterThanOrEqualTo(root.get("cost"), minCost);
    }

    return cb.between(root.get("cost"), minCost, maxCost);
}
  ``` 

```java
      // фильтрация по полю id связанной сущности (Client)
private final Long clientId;

@Override
public Predicate toPredicate(
        Root<Foo> root,                 // 
        CriteriaQuery<?> query,         // 
        CriteriaBuilder criteriaBuilder // 
) {
    if (clientId == null) {
        return null;
    }

    return cb.equal(root.get("client").get("id"), clientId); // получение значения у связанной сущности
}
  ``` 

* Пример `JPQL` и `SQL` в `@Query`

```java
// JPQL
@Query("SELECT foo FROM com.example.foo.Foo foo WHERE foo.product = :productName")
private List<Foo> getByProduct(String productName);

// SQL
@Query(value = "SELECT * FROM foos foo WHERE foo.product = :productName", nativeQuery = true)
private List<Foo> getByProduct(String productName);
```
* Пример `Example<>`
```java

@Service
@RequiredArgConstructor
public class Service {
  private final FooRepository repository;

  public FooEntity findByName(String name) {
    FooEntity probe = new Entity();
    probe.setName(name);

    ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withIgnorePaths("id", "date");
    Example<FooEntity> example = Example.of(probe, matcher);
    return repository.findOne(probe);
  }

}
```
