-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 15, 2018 at 10:59 AM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `psmsdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `stuff`
--

CREATE TABLE `stuff` (
  `stuff_id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `description` text,
  `quantity` int(11) DEFAULT NULL,
  `location` varchar(145) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `stuff`
--

INSERT INTO `stuff` (`stuff_id`, `name`, `description`, `quantity`, `location`) VALUES
(2, 'Pencil', 'Blue Color', 1, 'small cupboard'),
(3, 'Eyeglasses', 'Black Color', 1, 'Red Box');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `stuff`
--
ALTER TABLE `stuff`
  ADD PRIMARY KEY (`stuff_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `stuff`
--
ALTER TABLE `stuff`
  MODIFY `stuff_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
