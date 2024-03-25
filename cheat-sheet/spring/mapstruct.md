#### MapStruct

* `Mapstruct` - библиотека преобразования одних сущностей в другие без лишнего шаблонного кода.
* `@Mapper(componentModel = "spring", unmappedTargetPlicy = ReportingPlicy.IGNORE)`
    - `@Mapper` - обозначает, что `interface` является маппером и должен быть использован для генерации кода маппинга
      исходного типа в целевой
    - `componentModel = "spring"` - означает, что сгенерированный маппер должен быть автоматически зарегистрирован, как
      Spring Bean
    - `unmappedTargetPlicy = ReportingPlicy.IGNORE` - определят, что делать (игнорировать или выбрасывать исключения)
      если обнаружились немаппингованые целевые свойства, т.е. когда после маппинга у целевого объекта есть
      незаполненные поля
* `@Mapping(source = "orderId", target = "id")` - используется для указания маппинга между исходным объектом и целевым
* `@DecorateWith(OrderMapperDelegate.class)` - указывает, что `OrderMapperDelegate.class` должен использоваться для
  делегирования части функционала при маппинге
* `@Mapper(uses = { OrderMapperV2.class })` - `uses` - показывает, что нужно использовать уже созданный
  маппер `OrderMapperV2.class` для генерации текущего маппера
