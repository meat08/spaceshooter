# SpaceShooter

Простая игра, где вам нужно уничтожать вражеские корабли и не дать врагу уничтожить ваш корабль. У игры нет конца (кроме проигрыша) и нет ограничений по уровню.

A simple game where you need to destroy enemy ships and not let enemy destroy your ship. The game does not have a end (except for a loss) and not level restrictions.


## Описание
  
  - Переход на следующий уровень осуществляется после набора **20** фрагов.  
  - Изначально дается **3** жизни. За каждые **200** фрагов добавляется 1 жизнь.  
  - Каждые 3 уровня прочность корабля увеличивается в зависимости от выбранной сложности (легкая - на 20, нормальная - на 10, сложная - на 5).  
  - В игре 4 босса. Боссы появляются на **5, 15, 25 и 50** уровнях.  
  - В игре имеется возможность сохранить прогресс. Каждое следующее сохранение перезаписывает предыдущее. 
  - Победа над боссом улучшает корабль. 1 босс - +50 прочности, 2 босс - двойные пушки, 3 босс - тройные пушки, 4 босс - +250 к прочности.
  
  <details>
  <summary>EN</summary>
    
   - The transition to the next level occurs after **20** frags.
   - Initially, the ship has **3** lives. For every **200** frags, 1 life is added.
   - Each 3 levels of ship durability increase depending on the chosen complexity (easy - by 20, normal - by 10, hard - by 5).
   - The game has 4 bosses. Bosses appear at **5, 15, 25 and 50** levels.
   - The game has the ability to save progress. Each subsequent save overwrites the previous one.
   - Victory over the boss improves the ship. 1 boss - +50 durability, 2 boss - double guns, 3 boss - triple guns, 4 boss - +250 durability.
  </details>
  
<h3><details><summary>Подробней</summary>  
  <br>
<ul>
<li><details>
  <summary>Корабль игрока</summary><br>
  <table><tr>
    <td><img src="readme/mainShip.png" width="200"></td>
    <td><b>Прочность</b>: 100<br>
    <b>Урон</b>: 1<br>
    <b>Скорострельность</b>: 4</td>
  </tr></table>
</details></li>
<li><details>
  <summary>Враги</summary><br>
  <table>
    <tr>
      <td><b>Малый скаут</b><br><img src="readme/enemy0.png" width="100"></td>
      <td><b>Прочность</b>: 3<br>
      <b>Урон</b>: 2<br>
      <b>Скорострельность</b>: 1</td>
    </tr>
    <tr>
      <td><b>Средний скаут</b><br><img src="readme/enemy0_1.png" width="100"></td>
      <td><b>Прочность</b>: 5<br>
      <b>Урон</b>: 3<br>
      <b>Скорострельность</b>: 1</td>
    </tr>
    <tr>
      <td><b>Истребитель</b><br><img src="readme/enemy1.png" width="100"></td>
      <td><b>Прочность</b>: 8<br>
      <b>Урон</b>: 5<br>
      <b>Скорострельность</b>: 0.5</td>
    </tr>
    <tr>
      <td><b>Средний истребитель</b><br><img src="readme/enemy1_1.png" width="100"></td>
      <td><b>Прочность</b>: 10<br>
      <b>Урон</b>: 7<br>
      <b>Скорострельность</b>: 0.5</td>
    </tr>
    <tr>
      <td><b>Тяжелый истребитель</b><br><img src="readme/enemy1_2.png" width="150"></td>
      <td><b>Прочность</b>: 15<br>
      <b>Урон</b>: 7<br>
      <b>Скорострельность</b>: 0.5</td>
    </tr>
     <tr>
      <td><b>Крейсер</b><br><img src="readme/enemy2.png" width="100"></td>
      <td><b>Прочность</b>: 15<br>
      <b>Урон</b>: 10<br>
      <b>Скорострельность</b>: 0.4</td>
    </tr>
    <tr>
      <td><b>Тяжелый крейсер</b><br><img src="readme/enemy2_1.png" width="100"></td>
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
      <td><b>Линкор</b><br><img src="readme/boss0.png" width="150"></td>
      <td><b>Прочность</b>: 80<br>
      <b>Урон</b>: 9<br>
      <b>Скорострельность</b>: 1.3</td>
    </tr>
    <tr>
      <td><b>Ударный крейсер</b><br><img src="readme/boss1.png" width="150"></td>
      <td><b>Прочность</b>: 100<br>
      <b>Урон</b>: 10<br>
      <b>Скорострельность</b>: 1</td>
    </tr>
    <tr>
      <td><b>Тяжелый линкор</b><br><img src="readme/boss2.png" width="150"></td>
      <td><b>Прочность</b>: 180<br>
      <b>Урон</b>: 15<br>
      <b>Скорострельность</b>: 0.6</td>
    </tr>
    <tr>
      <td><b>Шип-матка</b><br><img src="readme/boss3.png" width="150"></td>
      <td><b>Прочность</b>: 250<br>
      <b>Урон</b>: 20<br>
      <b>Скорострельность</b>: 1</td>
    </tr>
  </table>
</details></li>
<li><details>
  <summary>Бонусы</summary><br>
  <table>
    <tr>
      <td><b>Усиление орудий</b><br><img src="readme/bonus0.png" width="80"></td>
      <td>Увеличивает скорость стрельбы и выпускает по 3 пули.</td>
    </tr>
    <tr>
      <td><b>Лечение</b><br><img src="readme/bonus1.png" width="80"></td>
      <td>Восстанавливает прочность. Объем восстановления зависит от уровня.</td>
    </tr>
    <tr>
      <td><b>Щит</b><br><img src="readme/bonus2.png" width="80"></td>
      <td>Окружает корабль щитом на несколько секунд. Щит блокирует весь урон.</td>
    </tr>
    <tr>
      <td><b>Ядерный удар</b><br><img src="readme/bonus3.png" width="80"></td>
      <td>Взрывает ядерный заряд, который уничтожает все объекты на экране. На боссов не действует.</td>
    </tr>
  </table>
</details></li>
</details></h>
  
<h3><details><summary>More details</summary>  
  <br>
<ul>
<li><details>
  <summary>Player ship</summary><br>
  <table><tr>
    <td><img src="readme/mainShip.png" width="200"></td>
    <td><b>Durability</b>: 100<br>
    <b>Damage</b>: 1<br>
    <b>Fire rate</b>: 4</td>
  </tr></table>
</details></li>
<li><details>
  <summary>Enemyes</summary><br>
  <table>
    <tr>
      <td><b>Small scout</b><br><img src="readme/enemy0.png" width="100"></td>
      <td><b>Durability</b>: 3<br>
      <b>Damage</b>: 2<br>
      <b>Fire rate</b>: 1</td>
    </tr>
    <tr>
      <td><b>Middle scout</b><br><img src="readme/enemy0_1.png" width="100"></td>
      <td><b>Durability</b>: 5<br>
      <b>Damage</b>: 3<br>
      <b>Fire rate</b>: 1</td>
    </tr>
    <tr>
      <td><b>Fighter</b><br><img src="readme/enemy1.png" width="100"></td>
      <td><b>Durability</b>: 8<br>
      <b>Damage</b>: 5<br>
      <b>Fire rate</b>: 0.5</td>
    </tr>
    <tr>
      <td><b>Middle fighter</b><br><img src="readme/enemy1_1.png" width="100"></td>
      <td><b>Durability</b>: 10<br>
      <b>Damage</b>: 7<br>
      <b>Fire rate</b>: 0.5</td>
    </tr>
    <tr>
      <td><b>Heavy fighter</b><br><img src="readme/enemy1_2.png" width="150"></td>
      <td><b>Durability</b>: 15<br>
      <b>Damage</b>: 7<br>
      <b>Fire rate</b>: 0.5</td>
    </tr>
     <tr>
      <td><b>Cruiser</b><br><img src="readme/enemy2.png" width="100"></td>
      <td><b>Durability</b>: 15<br>
      <b>Damage</b>: 10<br>
      <b>Fire rate</b>: 0.4</td>
    </tr>
    <tr>
      <td><b>Heavy cruiser</b><br><img src="readme/enemy2_1.png" width="100"></td>
      <td><b>Durability</b>: 18<br>
      <b>Damage</b>: 12<br>
      <b>Fire rate</b>: 0.4</td>
    </tr>
  </table>
</details></li>
<li><details>
  <summary>Bosses</summary><br>
  <table>
    <tr>
      <td><b>Battleship</b><br><img src="readme/boss0.png" width="150"></td>
      <td><b>Durability</b>: 80<br>
      <b>Damage</b>: 9<br>
      <b>Fire rate</b>: 1.3</td>
    </tr>
    <tr>
      <td><b>Strike cruiser</b><br><img src="readme/boss1.png" width="150"></td>
      <td><b>Durability</b>: 100<br>
      <b>Damage</b>: 10<br>
      <b>Fire rate</b>: 1</td>
    </tr>
    <tr>
      <td><b>Heavy battleship</b><br><img src="readme/boss2.png" width="150"></td>
      <td><b>Durability</b>: 180<br>
      <b>Damage</b>: 15<br>
      <b>Fire rate</b>: 0.6</td>
    </tr>
    <tr>
      <td><b>Mothership</b><br><img src="readme/boss3.png" width="150"></td>
      <td><b>Durability</b>: 250<br>
      <b>Damage</b>: 20<br>
      <b>Fire rate</b>: 1</td>
    </tr>
  </table>
</details></li>
<li><details>
  <summary>Bonuses</summary><br>
  <table>
    <tr>
      <td><b>Gun reinforcement</b><br><img src="readme/bonus0.png" width="80"></td>
      <td>Increases rate of fire and fires 3 bullets each shot.</td>
    </tr>
    <tr>
      <td><b>Healing</b><br><img src="readme/bonus1.png" width="80"></td>
      <td>Restores durability. The amount of recovery depends on the level.</td>
    </tr>
    <tr>
      <td><b>Shield</b><br><img src="readme/bonus2.png" width="80"></td>
      <td>Surrounds the ship with a shield for a few seconds. Shield blocks all damage.</td>
    </tr>
    <tr>
      <td><b>Nuclear strike</b><br><img src="readme/bonus3.png" width="80"></td>
      <td>Explodes a nuclear charge that destroys all the objects on the screen. Doesn’t affect bosses.</td>
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
