Шаблон страницы:
```html
<!DOCTYPE html>
<html
        lang="ru"
        xmlns="http://www.w3.org/1999/xhtml"
        xmlns:th="http://www.thymeleaf.org" >
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body></body>
</html>
```

Форма отправки данных:
```html
<!--  action может быть как полным путём, так и относительным  -->
<!--  в случае пустого поля action запрос будет отправлен по   -->
<!--  текущему адресу страницы                                 -->
<form action="/foo" method="post">
    <!--  простое однострочное текстовое поле ввода  -->
    <input type="text" name="text_name">
    
    <!--  radio button, у кнопок, относящемся к одному выбору  -->
    <!--  должно быть одинаковое значение поля `name`          -->
    <!--  в value указывается отображаемое на экране имя кнопки  -->
    <input type="radio" name="response" value="yes">да<br>
    <input type="radio" name="response" value="no">нет

    <!--  простые checkbox с отображением на отдельной строке  -->
    <p><input type="checkbox" name="spice" value="Salt">Соль</p>
    <p><input type="checkbox" name="spice" value="Pepper">Перец</p>
    <p><input type="checkbox" name="spice" value="Garlic">Чеснок</p>
    
    <!--  многострочное поле ввода                             -->
    <textarea name="comments" rows="10" cols="48">Комментарии оставлять тут!</textarea>

    <!--  выпадающий список  -->
    <select name="character">
        <option value="Homer">Homer Simpson</option>
        <option value="Marge">Marge Simpson</option>
        <option value="Bart">Bart Simpson</option>
        <option value="Lisa">Lisa Simpson</option>
        <option value="Maggie">Maggie Simpson</option>
    </select>

    <!--  fieldset - для визуального выделения  -->
    <!--  legend - заголовок группы элементов  -->
    <fieldset>
        <legend>Специи</legend>
        <p><input type="checkbox" name="spice" value="Salt">Соль</p>
        <p><input type="checkbox" name="spice" value="Pepper">Перец</p>
        <p><input type="checkbox" name="spice" value="Garlic">Чеснок</p>
    </fieldset>
    
    <!--  label - (ярлык) - позволяет связать текст с элементом формы, создать подпись  -->
    <p><label><input type="radio" name="response" value="yes">да</label></p>
    
    <!--  кнопка отправить данные формы  -->
    <input  type="submit" value="Отправить">
</form>
```

Неупорядоченный список:
```html
<ul>
  <li>Milk</li>
  <li>
    Cheese
    <ul>
      <li>Blue cheese</li>
      <li>Feta</li>
    </ul>
  </li>
</ul>
```
аналогичен
* Milk
* Cheese:
    * Blue cheese
    * Feta