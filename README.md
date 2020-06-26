# SpaceShooter

Простая игра, где вам нужно уничтожать вражеские корабли и не дать врагу уничтожить ваш корабль. У игры нет конца (кроме проигрыша) и нет ограничений по уровню.

A simple game where you need to destroy enemy ships and not let enemy destroy your ship. The game does not have a end (except for a loss) and not level restrictions.


## Описание
  
  - Переход на следующий уровень осуществляется после набора **20** фрагов.  
  - Изначально дается **3** жизни. За каждые **200** фрагов добавляется 1 жизнь.  
  - Каждые 3 уровня прочность корабля увеличивается в зависимости от выбранной сложности (легкая - на 20, нормальная - на 10, сложная - на 5).  
  - В игре 4 босса. Боссы появляются на **5, 15, 25 и 50** уровнях.  
  - В игре имеется возможность сохранить прогресс. Каждое следующее сохранение перезаписывает предыдущее.  
  
<h3><details><summary>Подробней</summary>  
  <br>
<ul>
<li><details>
  <summary>Корабль игрока</summary><br>
  <table><tr>
    <td><img src="readme/mainShip.png" height="200"></td>
    <td><b>Прочность</b>: 100<br>
    <b>Урон</b>: 1<br>
    <b>Скорострельность</b>: 4</td>
  </tr></table>
</details></li>
<li><details>
  <summary>Враги</summary><br>
  <table>
    <tr>
      <td><b>Малый скаут</b><br><img src="readme/enemy0.png" height="200"></td>
      <td><b>Прочность</b>: 3<br>
      <b>Урон</b>: 2<br>
      <b>Скорострельность</b>: 1</td>
    </tr>
    <tr>
      <td><b>Средний скаут</b><br><img src="readme/enemy0_1.png" height="200"></td>
      <td><b>Прочность</b>: 5<br>
      <b>Урон</b>: 3<br>
      <b>Скорострельность</b>: 1</td>
    </tr>
    <tr>
      <td><b>Истребитель</b><br><img src="readme/enemy1.png" height="200"></td>
      <td><b>Прочность</b>: 8<br>
      <b>Урон</b>: 5<br>
      <b>Скорострельность</b>: 0.5</td>
    </tr>
    <tr>
      <td><b>Средний истребитель</b><br><img src="readme/enemy1_1.png" height="200"></td>
      <td><b>Прочность</b>: 10<br>
      <b>Урон</b>: 7<br>
      <b>Скорострельность</b>: 0.5</td>
    </tr>
    <tr>
      <td><b>Тяжелый истребитель</b><br><img src="readme/enemy1_2.png" height="150"></td>
      <td><b>Прочность</b>: 15<br>
      <b>Урон</b>: 7<br>
      <b>Скорострельность</b>: 0.5</td>
    </tr>
     <tr>
      <td><b>Крейсер</b><br><img src="readme/enemy2.png" height="200"></td>
      <td><b>Прочность</b>: 15<br>
      <b>Урон</b>: 10<br>
      <b>Скорострельность</b>: 0.4</td>
    </tr>
    <tr>
      <td><b>Тяжелый крейсер</b><br><img src="readme/enemy2.png" height="200"></td>
      <td><b>Прочность</b>: 18<br>
      <b>Урон</b>: 12<br>
      <b>Скорострельность</b>: 0.4</td>
    </tr>
  </table>
</details></li>
<li><details>
  <summary>Боссы</summary><br>
  <table>
    <tr>
      <td><b>Линкор</b><br><img src="readme/boss0.png" height="200"></td>
      <td><b>Прочность</b>: 80<br>
      <b>Урон</b>: 9<br>
      <b>Скорострельность</b>: 1.3</td>
    </tr>
    <tr>
      <td><b>Ударный крейсер</b><br><img src="readme/boss1.png" height="200"></td>
      <td><b>Прочность</b>: 100<br>
      <b>Урон</b>: 10<br>
      <b>Скорострельность</b>: 1</td>
    </tr>
    <tr>
      <td><b>Тяжелый линкор</b><br><img src="readme/boss2.png" height="200"></td>
      <td><b>Прочность</b>: 180<br>
      <b>Урон</b>: 15<br>
      <b>Скорострельность</b>: 0.6</td>
    </tr>
    <tr>
      <td><b>Шип-матка</b><br><img src="readme/boss3.png" height="200"></td>
      <td><b>Прочность</b>: 250<br>
      <b>Урон</b>: 20<br>
      <b>Скорострельность</b>: 1</td>
    </tr>
  </table>
</details></li>
</details></h>

## Установка/Installation

### Android

Скачайте [APK](readme/SpaceShooter.apk?raw=true) файл и установите его.

Download [APK](readme/SpaceShooter.apk?raw=true) and install it.

### Windows/Linux/MacOS

Скачайте [JAR](readme/SpaceShooter.jar?raw=true) файл и запустите его. Для запуска необходима Java 1.8+. Скачать Java можно [здесь](https://www.java.com/ru/download/).

Download [JAR](readme/SpaceShooter.jar?raw=true) and run it. This content requires Java 1.8+. You can download Java [here](https://www.java.com/ru/download/).

## Управление/Control

**PC версия**: стрелки влево/вправо, нажатием мыши по экрану.  
**Android версия**: нажатие по экрану, акселерометр (по умолчанию выключен, включается в настройках).  

**PC version**: left / right arrows, mouse clicking on the screen.  
**Android version**: screen tap, accelerometer (by default off, turns on in properties).

<h2><details>
  <summary>Скриншоты/Screenshots</summary>

  <img src="readme/SH_mainMenu.jpg" height="600">
  <img src="readme/SH_gameplay.jpg" height="600">
  <img src="readme/SH_gameplay1.jpg" height="600">
</details></h2>

## Автор/Author

Ilya Mafov <i.mafov@gmail.com>

## Лицензия/License

GNU GPLv3 [LICENSE](LICENSE)
