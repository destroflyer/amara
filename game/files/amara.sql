-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 11. Jul 2020 um 02:51
-- Server-Version: 10.4.8-MariaDB
-- PHP-Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `amara`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `characters`
--

CREATE TABLE `characters` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `title` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `lore` text COLLATE utf8_unicode_ci NOT NULL,
  `is_public` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `characters`
--

INSERT INTO `characters` (`id`, `name`, `title`, `lore`, `is_public`) VALUES
(1, 'minion', 'Minion', '', 0),
(2, 'wizard', 'Wizard', '', 0),
(3, 'robot', 'Robot', '', 0),
(4, 'jaime', 'Jaime', '', 0),
(5, 'soldier', 'Soldier', '', 0),
(6, 'steve', 'Steve', '', 0),
(7, 'daydream', 'Daydream', '', 0),
(8, 'oz', 'Oz', '', 0),
(9, 'varus', 'Varus', '', 0),
(10, 'nathalya', 'Nathalya', '', 0),
(11, 'ganfaul', 'Ganfaul', 'Deep in the Ioborin Forest, there once was an ancient tribe called Otaki. Although they didn’t interact much with the world outside their territories, the Otaki were highly feared due to their warlike rituals and dark magic. While the wizards in the nearby kingdom of Eastcliff studied the magic of light and experimented to enhance their magic with new artificial tools, the tribe prayed to the nature and the night. This lead to a great despise against the „barbaric tribe of the forest“ - The disgust and fear inside the kingdom grew and grew… until the young Andrion Farstride ascended Eastcliff’s throne. Driven by greed and conservative advisors, he eventually decided to attack the primitive tribe. But little did he and his army knew about the true power of its shamans: A giant war started and lasted for three nights and three days. The Otaki had no choice but to use their tribe’s forbidden techniques, granting them huge power… at the cost of losing a part of their humanity. They grew body parts of animals, lost their voice and became one with nature and some of its deepest forces. Using those spells, being outnumbered by a large amount, the Otaki took out most of King Andrions army before finally getting encircled and defeated. It became clear, that with only a few dozen of soldiers left, Eastcliff could not win another fight against such bestial creatures in the future - In fear of losing his authority, the king decided to annihilate the whole Otaki tribe: Prisoners, soldiers, the elders, women and their kids. On this day, only one tribal survived - A little baby, rescued by Trasimond, an old wizard among the royal army. He has been serving the throne since many years and was a close friend to Andrions father, King Bradley. Knowing, that the old king wouldn’t have approved this genocide, he decided to rescue the little child with gray hair, hide his origin and to teach him in the wizard academy of Eastcliff, Avaetora.\n\nWhile growing up, the child named Ganfaul learned the usage and ethics of Eastcliff’s white magic and to live in peace. Trasimond taught him everything he knew and the young boy absorbed every bit of arcane knowledge he could. At the age of 28, he already surpassed his mentor and became an official wizard serving the royal family. But something inside of Ganfaul was still seeking for power - Power in a form and shape he just couldn’t see in all those bright lectures and rulings of the wizards. He searched and searched… until the day that he discovered the hidden part of the academies library, guarded by two soldiers he had never seen before. Using a difficult and long-planned illusion spell, he could deceive the guards and secretly slip in the library. After hours of studying the chronicles of Eastcliff and all of its secrets, Ganfaul found what his soul was seeking for all those years: The history of the mighty Otaki tribe… a tribe, so powerful they bonded with the nature, obtaining unbelievable powers… HIS tribe! Memories he believed to be dreams, flashed before his eyes and after remembering what happened years ago, Ganfaul could not longer control himself. Screaming and igniting the library, it only took a short amount of time for someone to show up: It was Trasimond, the man who saved him and became his mentor. He noticed the tribal books in the hands of Ganfaul and tried to convince him to calm down, but without success. Being old and surpassed by his student since a long time, the wizard did not stand a chance against the raging Otaki, who slaughtered him and the guards in seconds. After opening his eyes again and realizing, what he had done, Ganfaul took the kept books and scripts of his tribe and left the academy in a trail of blood. He returned to the Ioborin Forest, swearing death to King Andrion and preparing the revival of his tribe. No one has ever seen him again or knows which of the old bestial powers Ganfaul was able to obtain - But the day that the last Otaki returns… there will be war.', 1),
(12, 'kachujin', 'Kachujin', '', 1),
(13, 'maw', 'Maw', '', 1),
(14, 'erika', 'Erika', 'When talking about Eastcliff, people usually think of the majestic city, its great walls, bright streets and wealth. But where there is light, there is also shadow: The cities slums, far in the south. Gloomy streets, filled with dirt, sewage and criminals. A place, where only the toughest people could make a living… or those who already gave up. Only looking from day to day, life in Eastcliff’s dark corners isn’t easy. This was revealed to Erika Pinefall in a young age - Her parents abandoned her after the birth, knowing they could not afford to raise a child or offer her anything else besides hunger and pain. Growing up in an orphanage, Erika had no one to connect with… and no one to trust - The house’s rules were clear: The more orphans, the less to eat for everyone. It wasn’t uncommon for the children to expel newcomers. But the young girl managed to fight, survive and grow - Knowing she could never physically compete with the older boys or lurking thieves in the streets, she resorted to cunning, intelligent maneuvers and outranging her opponents. She mastered to defeat aggressors with arrow and bow before they could even reach her.\n\nAfter leaving the orphanage at the age of 19, Erika tried to escape her old life and to find work in the upper part of Eastcliff, but wherever she went, she was rejected harshly. No one wanted to deal with a dirty hiker from the slums. Although she never wanted to harm people again, Erika’s rough childhood and the steady rejection since her birth formed her into a criminal. In order to survive, she had no choice but to rob other thieves and finally even passers, mostly wealthy citizens at the borders of Eastcliff. Becoming known as the „Orphan Bandit“, Erika single-handedly managed to double the cities street patrols, but never got caught. Remaining in the shadows, she’s aiming her arrow to any nearby aggressor and doing everything necessary to survive.', 1),
(15, 'maria', 'Maria', '', 1),
(16, 'scarlet', 'Scarlet', '', 0),
(17, 'dwarf_warrior', 'Dwarf Warrior', '', 0),
(18, 'garmon', 'Garmon', '', 0),
(19, 'dosaz', 'Dosaz', '', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `characters_skins`
--

CREATE TABLE `characters_skins` (
  `id` int(10) UNSIGNED NOT NULL,
  `character_id` int(11) NOT NULL,
  `name` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `title` varchar(1000) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `characters_skins`
--

INSERT INTO `characters_skins` (`id`, `character_id`, `name`, `title`) VALUES
(1, 7, 'fire', 'Firedream'),
(2, 7, 'nightmare', 'Nightmare'),
(3, 7, 'envy', 'Envy'),
(4, 3, 'definitely_not', 'Definitely Not Robot'),
(5, 1, 'gentleman', 'Gentleman Minion'),
(6, 8, 'oz_junior', 'Oz Junior'),
(7, 7, 'inuyasha', 'Inuyasha'),
(8, 7, 'amazoness', 'Amazoness Daydream');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `mmo_players`
--

CREATE TABLE `mmo_players` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `map_name` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `data` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users_characters_active_skins`
--

CREATE TABLE `users_characters_active_skins` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `character_id` int(10) UNSIGNED NOT NULL,
  `skin_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `users_characters_active_skins`
--

INSERT INTO `users_characters_active_skins` (`user_id`, `character_id`, `skin_id`) VALUES
(1, 1, 5),
(1, 3, 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users_characters_skins`
--

CREATE TABLE `users_characters_skins` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `skin_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `users_characters_skins`
--

INSERT INTO `users_characters_skins` (`user_id`, `skin_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users_meta`
--

CREATE TABLE `users_meta` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `value` varchar(1000) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `users_meta`
--

INSERT INTO `users_meta` (`user_id`, `name`, `value`) VALUES
(2, 'avatar', 'ether_amara'),
(1, 'profile_text', 'Woof.'),
(1, 'avatar', 'ether_anna');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users_meta_defaults`
--

CREATE TABLE `users_meta_defaults` (
  `name` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `value` varchar(1000) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `users_meta_defaults`
--

INSERT INTO `users_meta_defaults` (`name`, `value`) VALUES
('avatar', 'jmonkeyengine'),
('profile_text', '');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `website_sessions`
--

CREATE TABLE `website_sessions` (
  `id` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `data` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `last_modification_date` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `characters`
--
ALTER TABLE `characters`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `characters_skins`
--
ALTER TABLE `characters_skins`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `website_sessions`
--
ALTER TABLE `website_sessions`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `characters`
--
ALTER TABLE `characters`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT für Tabelle `characters_skins`
--
ALTER TABLE `characters_skins`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
