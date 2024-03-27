* `@Valid` - аннотация в методе контроллера обозначает, что тело запроса `@RequestBody @Valid Foo foo` должно быть
  провалидировано, т.е. при поступлении запроса валидации, связанные и объявленные в `Foo.class` выполняться, а при
  несоответствии полей условием будет выброшено исключение `MethodArgumentNotValidException.class`.
* `@Validated` - примерно то же самое, что и `@Valid`, но принадлежит не `jakarta`, а `spring`. В `@Validated` -
  расширенные возможности, в том числе указывать группу валидации
* `Кастомная валидация` - возможность создавать свои валидаторы для проверки правильности заполнения полей. Для этого
  нужно:
    - аннотация, маркирующая необходимость валидации поля(всего объекта):
    - ```java
      @Documented
      @Constraint(validatedBy = OrderFilterValidator.class) // `OrderFilterValidator` - класс, где происходит 
                                                            // проверка (валидация) условий
      @Target(ElementType.TYPE)
      @Retention(RetentionPolicy.RUNTIME)
      public @interface FooFilterValid { // аннотация, маркирующая необходимость валидации поля
        String message() default "Поля пагинации minCost и maxCost должны быть указаны!";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
      }
      ```
    - реализация валидации:
    - ```java
      public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>

        @Override
        public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
            if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
                return false;
            }
            return (value.getMinCost() != null || value.getMaxCost() == null)
                && (value.getMinCost() == null || value.getMaxCoxt() != null);
         }
      }
      ```
    - аннотировать модель, которую нужно проверить
    - ```java
       @Data
       @OrderFilterValid
       public class OrderFilter { 
          private Integer pageNumber;
          private Integer pageSize;
          private BigDecimal minCost;
          private BigDecimal maxCost;
       }
      ```
