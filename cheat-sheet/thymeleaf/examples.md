Шаблон страницы:
```html
<!DOCTYPE html>
<html
        lang="ru"
        xmlns="http://www.w3.org/1999/xhtml"
        xmlns:th="http://www.thymeleaf.org" >
<head>
    <meta charset="UTF-8">
    <title th:text="#{application.name}">Title</title>
</head>
<body>
    <h1 th:text="#{addcontact.title}">Header</h1>
</body>
</html>
```

Циклы на основе `th:each`:
```html
<!--  простой цикл  -->
<tr th:each="student: ${students}">
    <td th:text="${student.id}" />
    <td th:text="${student.name}" />
</tr>

<!--  цикл, где вместе с объектом предоставляется объект статуса цикла  -->
<!--  iStat - объект статуса цикла, где  -->
<!--  * index - индекс текущей итерации, начиная с 0  -->
<!--  * count - индекс текущей итерации, начиная с 1  -->
<!--  * size - количество элементов в коллекции       -->
<!--  * even/odd - чётная/нечётная итерация           -->
<!--  * first - является ли текущая операция первой   -->
<!--  * last - является ли текущая операция последней -->
<tr
th:each="student, iStat : ${students}"
th:style="${iStat.odd}? 'font-weight: bold;'"
th:alt-title="${iStat.even}? 'even' : 'odd'">
    <td th:text="${student.id}" />
    <td th:text="${student.name}" />
</tr>
```

Формы отправки данных:
```html
<!--  th:object - содержит POJO объект, в котором      -->
<!--  есть все ассоциированные с формой поля, которые  -->
<!--  нужно передать при отправке данных.              -->
<!--  Внутри "${...}" допускается только прямая ссылка -->
<!--  на объект "${fooObj}"                            -->
<!--  ссылки вида "${fooObj.foo}" недопустимы          -->
<form th:action="@{/foo}" th:object="${fooObj}" method="post">
    <!-- th:field="*{username} осуществляет биндинг поля fooObj.foo  -->
    <!-- к конкретному полю ввода                                    -->
    <input type="text" th:field="*{username}" />
    <input type="text" th:field="*{dateField}" placeholder="yyyy-MM-dd" />
    <input type="number" th:field="*{numberField}" placeholder="integer" />
    <input type="text" th:field="*{doubleField}" placeholder="double" />
    <input type="text" th:field="*{textField}" placeholder="string" />
    <input type="color" th:field="*{colorField}" placeholder="color" />
    <input type="datetime-local" th:field="*{dateTimeField}" placeholder="date" />
    
    <!-- пример со списком checkbox                                  -->
    <ul>
        <li th:each="color : ${allSampleColors}">
            <input type="checkbox" th:field="*{colors}" th:value="${color}"/>
            <label th:for="${#ids.prev('colors')}" th:text="${color}">RED</label>
        </li>
    </ul>
    
    <input type="submit" value="Submit"/>
</form>
```