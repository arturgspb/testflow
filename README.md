Автоматическое тестирование

## Технологии
Java (Kotlin/Scala) + Библиотека [Selenide](http://ru.selenide.org/) + JUnit

### Мотивация

JVM языки обусловлены в первую очередь отличной либой selenide равной которой пока я не встречал.
Во вторую очередь, так как я пропагандирую паттерн PageObject жесткая типизация позволит писать и рефакторить тесты гораздо быстрее.

Все это будет запускаться из под среду JUnit, которй много много лет уже и к ней есть куча всяких отчетов.
Кроме того она выгружает xml c результатами тестов и лбый CI этот формат знают и любят.

### Page Object
Использовать патент PageObject для описания частей интерфейса чтоб не дублировать селекторы

Пример с селекторами длямо в коде теста:
https://github.com/arturgspb/testflow/blob/master/src/test/java/testapp/WikiMediaDownloadSpec.java#L16

В принципе неплох, однако часто селекторы будут повторятся в проекте и при изменении селектора нужно будет менять много мест в тестах.
Так же селекторы бывают довольно сложные или вообще нужно сделать несколько кликов для открытия окна выбора чего-то.
Для того, чтобы убрать мусор из самих тестов используют паттерн [PageObject](https://kreisfahrer.gitbooks.io/selenium-webdriver/content/page_object_pattern_arhitektura_testovogo_proekta/ispolzovanie_patterna_page_object.html).

Создается отдельный файл с методами для работы со страницей или ее частью:
https://github.com/arturgspb/testflow/blob/master/src/test/java/pageobject/WikiPage.java

В тестах вы начинаете использоваться осмысленные понятные имена функций объекта 
https://github.com/arturgspb/testflow/blob/master/src/test/java/testapp/WikiMediaDownloadSpec.java#L24

Такой подход по моему мнению окупается почти сразу, так как естов в проекте редко меньше 10-20

## Как запускать

### Ручной запуск
Я запускаю локально, так как UI тесты мало у кого работают стабильно на 100%, бывает нужно перезапускать частями.
Этого сейчас вполне достаточно, так ка вообще для всех задач запускать долго (в мете тесты идут около 25 мин в один поток).
Для многопоточности и тесты должны быть готовы и железо нужно.

### Автоматический
Автозапуски можно настроить на Bamboo (или любом другом CI). 
Unit (не интерфейсные) тесты меты именно так и запускаются.

Важно, знать, что для автозапусков у вас либо это должно стартовать в докере 
в Google Chrome или этот докер или живая тачка где-то должны быть. При каких-то неведомых случаях браузер залипает и его надо рестартовать.
Локально или в докере, понятно, это удобнее делать.



## FAQ

> ну то что ты прислал - это в принципе не отличается от моего скрипта

Крайне разительно отличается как раз тем, что в моем варианте:
 - есть стандартная для тестов системность распределения и оформления папок, файлов и аннотаций внетри самих тестов в файлах
 - стандартные xml отчеты после выполнения
 - стандартный запуск через IDE всех тестов, отдельных папок, отдельных файлов, отдельных тестов внутри файлов

>Речь скорее была в описании всей инфраструктууры. В каком проекте/репозитории разрабатывать тесты?

В любом отдельном репозитории, это неважно

> откуда тесты будут брать креденшиалы?

Конечно же зашиты в констатнте прямо в репозитории

> куда их деплоить?

Никуда не деплоить, просто запускать локально или через CI или когда нить через мету

> версионность проектов, тестов, ос и браузеров для тестов

Тесты стоит писать только на состоявшийся функционал, который уже в продакшене и никогда не нужнописать на то, что: еще в разработке, вылили меньше недели назад
Так как код и оформление еще кучу раз может поменяться. Тесты нужно чтобы закреплять функциональность, а не проверять ее на этапе первичной разработки.
Тесты защищают от регрессионных багов

> куда они будут писать логи ?

XML отчет можно использовать как угодно. Например брать его с мастер сборок bamboo
Если имеются в виду логи как в проекте, то это вообще не нужно для тестов

> как будет настраиваться мониторинг?

CI (Bamboo) пришлет email если тесты упали

> по какому принципу будем отбирать фукционал для тестировани и какие тесты можно удалять?

Это у тестировщиков обычно спрашивают - это их работа.
По моему опыту тестить надо то, что при поломке взрывает важные вещи. Обычно это не 100% проекта
