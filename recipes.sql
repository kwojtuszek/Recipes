-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 08 Lut 2022, 08:47
-- Wersja serwera: 10.4.17-MariaDB
-- Wersja PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `recipes`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ingridients`
--

CREATE TABLE `ingridients` (
  `idIngridients` int(11) NOT NULL,
  `Name` varchar(45) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `ingridients`
--

INSERT INTO `ingridients` (`idIngridients`, `Name`) VALUES
(10, 'Jajka'),
(9, 'Kebsik'),
(2, 'Masło'),
(5, 'Mąka'),
(1, 'Mleko'),
(4, 'Olej'),
(7, 'Sałata'),
(8, 'Wino'),
(6, 'Woda'),
(3, 'Ziemniaki');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `recipes`
--

CREATE TABLE `recipes` (
  `idRecipes` int(11) NOT NULL,
  `Name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Type` varchar(45) COLLATE utf8_polish_ci NOT NULL DEFAULT 'Nie sprecyzowano',
  `Rate` int(11) NOT NULL,
  `Difficulty` int(11) NOT NULL,
  `Steps` mediumtext COLLATE utf8_polish_ci NOT NULL,
  `Price` double NOT NULL,
  `Accepted` tinyint(4) NOT NULL DEFAULT 0,
  `Users_idUsers` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `recipes`
--

INSERT INTO `recipes` (`idRecipes`, `Name`, `Type`, `Rate`, `Difficulty`, `Steps`, `Price`, `Accepted`, `Users_idUsers`) VALUES
(1, 'Frytki Kręcone', 'Obiad', 0, 3, 'Obierz ziemniaki i pokruj je w paski. Następnie wrzuć je do frytkownicy i smaż. Gotowe posul i wcinaj', 5, 1, 1),
(29, 'Gacowianka Perlage', 'Przekąska', 0, 10, 'Woda potrzebna jest i Gacak i sie robi gacowana woda', 2137, 1, 9),
(30, 'Ciastka Kruche', 'Deser', 0, 4, 'Kruche ciasteczka. wyrob ciasto .....', 15, 1, 10),
(31, 'dsa', 'dsa', 0, 2, 'dsa', 2, 1, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `recipes_has_ingridients`
--

CREATE TABLE `recipes_has_ingridients` (
  `idRecipesIngridients` int(11) NOT NULL,
  `Type` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Amount` int(11) NOT NULL,
  `Ingridients_idIngridients` int(11) NOT NULL,
  `Recipes_idRecipes` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `recipes_has_ingridients`
--

INSERT INTO `recipes_has_ingridients` (`idRecipesIngridients`, `Type`, `Amount`, `Ingridients_idIngridients`, `Recipes_idRecipes`) VALUES
(24, 'Kilogramów', 12, 3, 1),
(25, 'Mililitrów', 20000, 6, 29),
(26, 'Gram', 100, 9, 29),
(28, 'Sztuk', 5, 10, 30),
(29, 'Opakowań', 2, 1, 30);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `role`
--

CREATE TABLE `role` (
  `idRole` int(11) NOT NULL,
  `Role_Name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Aktywna` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `role`
--

INSERT INTO `role` (`idRole`, `Role_Name`, `Aktywna`) VALUES
(1, 'Admin', 1),
(2, 'Użytkownik', 1),
(3, 'Moderator', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `idUsers` int(11) NOT NULL,
  `Login` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Pass` varchar(45) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`idUsers`, `Login`, `Pass`) VALUES
(1, 'klauders', '123456'),
(2, 'szymon', '123456'),
(3, 'Robert', 'dsa'),
(5, 'test', 'test'),
(7, 'test2', 'test'),
(8, 'test3', 'rest'),
(9, 'Gacak', '123'),
(10, 'Przemek', '123');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_has_role`
--

CREATE TABLE `user_has_role` (
  `idUserRole` int(11) NOT NULL,
  `Date` datetime NOT NULL,
  `Assigned` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Users_idUsers` int(11) NOT NULL,
  `Role_idRole` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `user_has_role`
--

INSERT INTO `user_has_role` (`idUserRole`, `Date`, `Assigned`, `Users_idUsers`, `Role_idRole`) VALUES
(1, '2022-02-01 11:47:10', 'System', 1, 1),
(2, '2022-02-07 10:24:39', 'klauders', 2, 1),
(3, '2022-02-07 11:00:06', 'szymon', 3, 2),
(5, '2022-02-02 11:58:01', 'System', 5, 2),
(7, '2022-02-07 10:21:12', 'klauders', 7, 2),
(8, '2022-02-02 12:45:48', 'System', 8, 2),
(9, '2022-02-07 18:53:07', 'klauders', 9, 1),
(10, '2022-02-08 08:06:00', 'klauders', 10, 3);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `ingridients`
--
ALTER TABLE `ingridients`
  ADD PRIMARY KEY (`idIngridients`),
  ADD KEY `idx_Material` (`Name`);

--
-- Indeksy dla tabeli `recipes`
--
ALTER TABLE `recipes`
  ADD PRIMARY KEY (`idRecipes`),
  ADD KEY `fk_Recipes_Users1_idx` (`Users_idUsers`);

--
-- Indeksy dla tabeli `recipes_has_ingridients`
--
ALTER TABLE `recipes_has_ingridients`
  ADD PRIMARY KEY (`idRecipesIngridients`),
  ADD KEY `fk_Recipes_has_Ingridients_Ingridients1_idx` (`Ingridients_idIngridients`),
  ADD KEY `fk_Recipes_has_Ingridients_Recipes1_idx` (`Recipes_idRecipes`);

--
-- Indeksy dla tabeli `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`idRole`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idUsers`);

--
-- Indeksy dla tabeli `user_has_role`
--
ALTER TABLE `user_has_role`
  ADD PRIMARY KEY (`idUserRole`),
  ADD KEY `fk_User_has_Role_Users1_idx` (`Users_idUsers`),
  ADD KEY `fk_User_has_Role_Role1_idx` (`Role_idRole`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `ingridients`
--
ALTER TABLE `ingridients`
  MODIFY `idIngridients` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `recipes`
--
ALTER TABLE `recipes`
  MODIFY `idRecipes` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT dla tabeli `recipes_has_ingridients`
--
ALTER TABLE `recipes_has_ingridients`
  MODIFY `idRecipesIngridients` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT dla tabeli `role`
--
ALTER TABLE `role`
  MODIFY `idRole` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `idUsers` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `user_has_role`
--
ALTER TABLE `user_has_role`
  MODIFY `idUserRole` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `recipes`
--
ALTER TABLE `recipes`
  ADD CONSTRAINT `fk_Recipes_Users1` FOREIGN KEY (`Users_idUsers`) REFERENCES `users` (`idUsers`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `recipes_has_ingridients`
--
ALTER TABLE `recipes_has_ingridients`
  ADD CONSTRAINT `fk_Recipes_has_Ingridients_Ingridients1` FOREIGN KEY (`Ingridients_idIngridients`) REFERENCES `ingridients` (`idIngridients`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Recipes_has_Ingridients_Recipes1` FOREIGN KEY (`Recipes_idRecipes`) REFERENCES `recipes` (`idRecipes`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `user_has_role`
--
ALTER TABLE `user_has_role`
  ADD CONSTRAINT `fk_User_has_Role_Role1` FOREIGN KEY (`Role_idRole`) REFERENCES `role` (`idRole`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_User_has_Role_Users1` FOREIGN KEY (`Users_idUsers`) REFERENCES `users` (`idUsers`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
