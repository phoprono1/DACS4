-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2024 at 04:38 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dacs4`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookmarks`
--

CREATE TABLE `bookmarks` (
  `id_bookmarks` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `url` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bookmarks`
--

INSERT INTO `bookmarks` (`id_bookmarks`, `id_user`, `url`, `title`, `timestamp`) VALUES
(48, 10, 'https://baomoi.com/', NULL, '2023-12-22 06:29:53');

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `sender_id` int(11) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `search_history`
--

CREATE TABLE `search_history` (
  `id_history` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `searchTerm` text NOT NULL,
  `titleTerm` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `search_history`
--

INSERT INTO `search_history` (`id_history`, `id_user`, `searchTerm`, `titleTerm`, `timestamp`) VALUES
(51, 0, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 10:46:45'),
(52, 0, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 10:46:55'),
(53, 0, 'https://www.google.com/', 'Google', '2023-12-06 10:46:57'),
(54, 0, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 10:54:42'),
(55, 0, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:02:43'),
(56, 0, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:05:11'),
(57, 0, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:06:59'),
(58, 0, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:09:18'),
(59, 1, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:14:06'),
(60, 1, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:18:06'),
(61, 1, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:18:13'),
(62, -1, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:19:26'),
(63, 1, 'https://www.google.com/search?q=b', 'b - Tìm trên Google', '2023-12-06 11:21:25'),
(64, 1, 'https://www.google.com/search?q=h%C3%B4m%20nay%20l%C3%A0%20ng%C3%A0y%20m%E1%BA%A5y?', 'hôm nay là ngày mấy? - Tìm trên Google', '2023-12-06 11:24:27'),
(65, 1, 'https://www.google.com/search?q=h%C3%B4m%20nay%20l%C3%A0%20ng%C3%A0y%20m%E1%BA%A5y?', 'hôm nay là ngày mấy? - Tìm trên Google', '2023-12-06 11:24:35'),
(66, 1, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:24:46'),
(67, 1, 'https://vi.wikipedia.org/wiki/A', 'A – Wikipedia tiếng Việt', '2023-12-06 11:24:56'),
(68, 1, 'https://vi.wikipedia.org/wiki/Trang_Ch%C3%ADnh', 'Wikipedia, bách khoa toàn thư mở', '2023-12-06 11:25:10'),
(69, 1, 'https://vi.wikipedia.org/wiki/Trang_Ch%C3%ADnh', 'Wikipedia, bách khoa toàn thư mở', '2023-12-06 11:25:15'),
(70, 1, 'https://vi.wikipedia.org/wiki/Trang_Ch%C3%ADnh', 'Wikipedia, bách khoa toàn thư mở', '2023-12-06 11:28:06'),
(71, 1, 'https://vi.wikipedia.org/wiki/A', 'A – Wikipedia tiếng Việt', '2023-12-06 11:28:07'),
(72, 1, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:28:13'),
(73, 1, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-06 11:28:15'),
(74, 1, 'https://vi.wikipedia.org/wiki/A', 'A – Wikipedia tiếng Việt', '2023-12-06 11:28:20'),
(75, 1, 'https://m.facebook.com/?_rdr', 'Facebook – log in or sign up', '2023-12-06 12:25:50'),
(76, 1, 'https://www.google.com/', 'Google', '2023-12-06 12:26:08'),
(77, 1, 'https://www.google.com/search?q=h%C3%B4m+nay+%C4%83n+g%C3%AC&sca_esv=588364199&source=hp&ei=YGhwZaCYI8eI-QathZ_ABg&iflsig=AO6bgOgAAAAAZXB2cDoN8XT5cIXhHHUtCKlXdaqm17_3&ved=0ahUKEwigyK6l5_qCAxVHRN4KHa3CB2gQ4dUDCAk&uact=5&oq=h%C3%B4m+nay+%C4%83n+g%C3%AC&gs_lp=Egdnd3Mtd2l6IhBow7RtIG5heSDEg24gZ8OsMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABEj4F1CnBFiZFnAFeACQAQGYAdUBoAGHDKoBBjE1LjEuMbgBA8gBAPgBAagCCsICHRAAGIAEGIoFGOUCGOUCGOoCGLQCGIoDGLcDGNQDwgIKEAAYAxiPARjqAsICChAuGAMYjwEY6gLCAg4QABiABBiKBRixAxiDAcICCxAAGIAEGLEDGIMBwgIFEC4YgATCAg4QLhiABBiKBRixAxiDAcICChAAGIAEGIoFGEPCAggQABiABBixA8ICBBAAGAPCAg8QABiABBiKBRhDGEYYgALCAggQLhiABBjUAsICBhAAGBYYHg&sclient=gws-wiz', 'hôm nay ăn gì - Tìm trên Google', '2023-12-06 12:26:17'),
(78, 1, 'https://m.facebook.com/?_rdr', 'Facebook – log in or sign up', '2023-12-06 12:26:25'),
(79, 1, 'https://www.google.com', 'Google', '2023-12-06 12:26:40'),
(80, 1, 'https://m.facebook.com/?_rdr', 'Facebook – log in or sign up', '2023-12-12 08:30:46'),
(81, 1, 'https://www.google.com/', 'Google', '2023-12-12 08:30:48'),
(82, 1, 'https://vi.wikipedia.org/wiki/Trang_Ch%C3%ADnh', 'Wikipedia, bách khoa toàn thư mở', '2023-12-12 08:30:52'),
(83, 1, 'https://www.google.com', 'Google', '2023-12-12 08:30:54'),
(84, 1, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-12 08:30:59'),
(85, 1, 'https://vi.wikipedia.org/wiki/Trang_Ch%C3%ADnh', 'Wikipedia, bách khoa toàn thư mở', '2023-12-12 08:52:33'),
(86, 1, 'https://m.facebook.com/?_rdr', 'Facebook – log in or sign up', '2023-12-12 08:52:38'),
(87, 1, 'https://vi.wikipedia.org/wiki/Trang_Ch%C3%ADnh', 'Wikipedia, bách khoa toàn thư mở', '2023-12-12 08:54:17'),
(88, 1, 'https://www.google.com', 'Google', '2023-12-12 08:54:21'),
(89, 1, 'https://m.facebook.com/?_rdr', 'Facebook – log in or sign up', '2023-12-12 08:54:25'),
(90, 1, 'https://www.google.com', 'Google', '2023-12-12 08:54:29'),
(91, 1, 'https://www.youtube.com/', 'YouTube', '2023-12-12 08:54:38'),
(92, 1, 'https://www.google.com', 'Google', '2023-12-12 08:55:07'),
(93, 1, 'https://www.youtube.com/', 'YouTube', '2023-12-12 09:02:47'),
(94, 1, 'https://m.facebook.com/?_rdr', 'Facebook – log in or sign up', '2023-12-12 09:03:07'),
(95, 1, 'https://www.youtube.com/', 'YouTube', '2023-12-12 09:03:55'),
(96, 1, 'https://www.google.com/search?q=a', 'a - Tìm trên Google', '2023-12-15 05:31:29'),
(97, 2, 'https://www.google.com', 'Google', '2023-12-16 14:23:49'),
(98, 1, 'https://www.google.com', 'Google', '2023-12-16 15:17:31'),
(99, 1, 'https://www.google.com', 'Google', '2023-12-16 16:28:51'),
(100, 1, 'https://www.google.com', 'Google', '2023-12-20 11:55:20'),
(101, 1, 'https://www.google.com', 'Google', '2023-12-20 12:03:27'),
(102, 1, 'https://www.google.com', 'Google', '2023-12-20 12:10:34'),
(103, 1, 'https://kenh14.vn/', 'Kênh tin tức giải trí - Xã hội - Kenh14.vn', '2023-12-20 12:10:43'),
(104, 1, 'https://www.google.com', 'Google', '2023-12-20 12:17:19'),
(105, 1, 'https://www.google.com', 'Google', '2023-12-20 12:17:22'),
(106, 1, 'https://www.google.com', 'Google', '2023-12-20 12:17:48'),
(107, 1, 'https://www.google.com', 'Google', '2023-12-20 12:18:26'),
(108, 1, 'https://www.google.com', 'Google', '2023-12-20 12:33:41'),
(109, 1, 'https://www.google.com', 'Google', '2023-12-20 12:34:59'),
(110, 1, 'https://www.google.com', 'Google', '2023-12-20 12:37:32'),
(111, 1, 'https://kenh14.vn/', 'Kênh tin tức giải trí - Xã hội - Kenh14.vn', '2023-12-20 12:37:40'),
(112, 1, 'https://www.google.com', 'Google', '2023-12-20 12:37:41'),
(113, 1, 'https://www.google.com', 'Google', '2023-12-20 12:37:52'),
(114, 1, 'https://www.google.com', 'Google', '2023-12-20 12:37:59'),
(115, 1, 'https://www.google.com', 'Google', '2023-12-20 12:38:03'),
(116, 1, 'https://www.google.com', 'Google', '2023-12-20 12:41:09'),
(117, 1, 'https://www.google.com', 'Google', '2023-12-20 12:41:10'),
(118, 1, 'https://www.google.com', 'Google', '2023-12-20 12:46:05'),
(119, 1, 'https://www.google.com', 'Google', '2023-12-20 12:46:10'),
(120, 1, 'https://www.google.com', 'Google', '2023-12-20 12:48:22'),
(121, 1, 'https://www.google.com', 'Google', '2023-12-20 12:49:25'),
(122, 1, 'https://www.google.com', 'Google', '2023-12-20 12:50:16'),
(123, 1, 'https://www.google.com', 'Google', '2023-12-20 12:50:21'),
(124, 1, 'https://www.google.com', 'Google', '2023-12-20 12:51:18'),
(125, 1, 'https://www.google.com', 'Google', '2023-12-20 12:51:21'),
(126, 1, 'https://www.google.com', 'Google', '2023-12-20 12:51:32'),
(127, 1, 'https://kenh14.vn/', 'Kênh tin tức giải trí - Xã hội - Kenh14.vn', '2023-12-20 12:51:42'),
(128, 1, 'https://www.google.com', 'Google', '2023-12-20 12:52:20'),
(129, 1, 'https://www.google.com', 'Google', '2023-12-20 12:52:24'),
(130, 1, 'https://www.google.com/', 'Google', '2023-12-20 13:32:58'),
(131, 1, 'https://www.google.com', 'Google', '2023-12-20 13:34:35'),
(132, 2, 'https://m.facebook.com/?_rdr', 'Facebook – log in or sign up', '2023-12-20 13:34:58'),
(133, 2, 'https://www.google.com', 'Google', '2023-12-20 13:35:34'),
(134, 2, 'https://m.facebook.com/?_rdr', 'Facebook – log in or sign up', '2023-12-20 13:35:38'),
(157, 10, 'https://www.google.com/sorry/index?continue=https://www.google.com/search%3Fq%3Da&q=EgSr4bknGLuaj6wGIjAXZKwxwMJTWTP2dZvsiewMQ4SuPgM9uFqU44PeLsBnmyg2ZztQOrr_I0H757N-GdUyAXJaAUM', 'https://www.google.com/search?q=a', '2023-12-21 05:29:31'),
(158, 10, 'https://www.google.com', 'Google', '2023-12-21 05:29:35'),
(159, 10, 'https://baomoi.com/', 'Báo Mới - Tin tức 24H, đọc báo mới nhanh nhất hôm nay', '2023-12-21 05:29:47'),
(160, 10, 'https://kenh14.com/', 'The domain name kenh14.com is for sale', '2023-12-21 05:29:51'),
(161, 10, 'https://kenh14.vn/', 'Kênh tin tức giải trí - Xã hội - Kenh14.vn', '2023-12-21 05:30:03'),
(162, 8, 'https://www.google.com', 'Google', '2023-12-21 05:34:22'),
(163, 8, 'https://www.google.com', 'Google', '2023-12-21 05:34:23'),
(164, 8, 'https://www.google.com', 'Google', '2023-12-21 05:34:24'),
(165, 8, 'https://www.google.com', 'Google', '2023-12-21 05:34:27'),
(166, 8, 'https://www.google.com', 'Google', '2023-12-21 05:34:28'),
(167, 10, 'https://www.google.com', 'Google', '2023-12-21 05:41:57'),
(168, 10, 'https://www.google.com', 'Google', '2023-12-21 05:41:59'),
(169, 10, 'https://www.google.com', 'Google', '2023-12-21 05:42:03'),
(170, 10, 'https://www.google.com', 'Google', '2023-12-21 05:42:05'),
(171, 10, 'https://www.google.com', 'Google', '2023-12-21 05:42:10'),
(172, 10, 'https://www.google.com', 'Google', '2023-12-21 05:42:11'),
(173, 10, 'https://www.google.com', 'Google', '2023-12-21 05:42:14'),
(174, 10, 'https://www.google.com', 'Google', '2023-12-21 05:42:16'),
(175, 10, 'https://www.google.com', 'Google', '2023-12-21 05:42:17'),
(176, 10, 'https://www.google.com', 'Google', '2023-12-21 05:42:19'),
(177, 10, 'https://www.google.com', 'Google', '2023-12-21 05:42:20'),
(178, 10, 'https://www.google.com', 'Google', '2023-12-21 05:48:39'),
(179, 10, 'https://www.google.com', 'Google', '2023-12-21 05:48:40'),
(180, 10, 'https://www.google.com/', 'Google', '2023-12-21 05:48:51'),
(181, 10, 'https://www.google.com/', 'Google', '2023-12-21 05:48:53'),
(182, 10, 'https://www.google.com', 'Google', '2023-12-21 05:48:54'),
(183, 10, 'https://baomoi.com/', 'Báo Mới - Tin tức 24H, đọc báo mới nhanh nhất hôm nay', '2023-12-21 05:48:59'),
(184, 10, 'https://www.google.com/', 'Google', '2023-12-21 05:49:00'),
(185, 10, 'https://www.google.com/', 'Google', '2023-12-21 05:49:02'),
(186, 10, 'https://www.google.com/', 'Google', '2023-12-21 05:50:26'),
(187, 10, 'https://kenh14.vn/', 'Kênh tin tức giải trí - Xã hội - Kenh14.vn', '2023-12-21 05:50:29'),
(188, 10, 'https://www.google.com', 'Google', '2023-12-21 05:50:30'),
(189, 10, 'https://baomoi.com/', 'Báo Mới - Tin tức 24H, đọc báo mới nhanh nhất hôm nay', '2023-12-21 05:50:38'),
(190, 10, 'https://kenh14.vn/', 'Kênh tin tức giải trí - Xã hội - Kenh14.vn', '2023-12-21 05:50:51'),
(191, 8, 'https://www.google.com', 'Google', '2023-12-22 04:05:07'),
(192, 8, 'https://www.google.com', 'Google', '2023-12-22 04:05:08'),
(193, 8, 'https://www.google.com/sorry/index?continue=https://www.google.com/search%3Fq%3Da&q=EgSr4bk_GPSVlKwGIjAb5XFoUrcdjLsU3m7QbbatPH_ot1GjfFu5CjxiBwRRugh7aMvGAtHF7W3qlg1k6NoyAXJaAUM', 'https://www.google.com/search?q=a', '2023-12-22 04:05:10'),
(194, 8, 'https://www.google.com/', 'Google', '2023-12-22 04:05:12'),
(195, 8, 'https://www.google.com', 'Google', '2023-12-22 04:05:15'),
(196, 8, 'https://www.google.com', 'Google', '2023-12-22 04:05:17'),
(197, 8, 'https://www.google.com', 'Google', '2023-12-22 04:05:21'),
(198, 8, 'https://www.google.com', 'Google', '2023-12-22 04:05:24'),
(199, 10, 'https://www.google.com', 'Google', '2023-12-22 06:29:49'),
(200, 10, 'https://kenh14.vn/', 'Kênh tin tức giải trí - Xã hội - Kenh14.vn', '2023-12-22 06:29:58'),
(201, 10, 'https://baomoi.com/', 'Báo Mới - Tin tức 24H, đọc báo mới nhanh nhất hôm nay', '2023-12-22 06:30:10'),
(202, 10, 'https://www.google.com', 'Google', '2023-12-22 06:30:20'),
(203, 10, 'https://kenh14.vn/', 'Kênh tin tức giải trí - Xã hội - Kenh14.vn', '2023-12-22 06:30:22'),
(204, 10, 'https://baomoi.com/', 'Báo Mới - Tin tức 24H, đọc báo mới nhanh nhất hôm nay', '2023-12-22 06:30:25'),
(205, 10, 'https://kenh14.vn/', 'Kênh tin tức giải trí - Xã hội - Kenh14.vn', '2023-12-22 06:30:29'),
(206, 10, 'https://kenh14.vn/chung-ta-cua-8-nam-sau-ngay-cang-do-tat-ca-la-tai-huyen-lizzie-20231222103859675.chn', 'Chúng Ta Của 8 Năm Sau ngày càng dở: Tất cả là tại Huyền Lizzie!', '2023-12-22 06:31:22');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` enum('online','offline') NOT NULL DEFAULT 'offline',
  `ip_address` varchar(255) DEFAULT NULL,
  `port_number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `username`, `email`, `password`, `status`, `ip_address`, `port_number`) VALUES
(8, 'hoangpho123', 'ronhoangdn1970@gmail.com', '$2a$10$bMEtam0t4cBOL9ZTjCSXjeSqoYbjpGeCBUIjdZGpeGIXuMSA//IR.', 'online', '192.168.115.113', 8887),
(9, '890', '890@890.com', '$2a$10$6g4KyTNNa48EELtemXCVP.ZqsNIxcf9WDrN0y6BFf1eNnD38Id9iC', 'offline', NULL, 0),
(10, 'hoangpho2705', 'ronhoangdn1970@gmail.com', '$2a$10$ejmnp6DpwvuboyepiKoNhekTvivBNYJa8BuULOf8KjyAyV/GnFTT.', 'online', '192.168.115.113', 8887),
(11, 'ronhoang123', 'abc@abc.com', '$2a$10$2iLxPSYyiUWyCLf/./grpu8eXVPPdSxtiNuES0yoKDefoh7Zd8u5K', 'online', '192.168.1.5', 8887),
(12, 'hoangpho', 'ronhoangdn1970@gmail.com', '$2a$10$pW0RrIE/gSVqKls6SeNqju9rEn1Bt20VEENYsKhHMFQOF7W5hTifO', 'online', '192.168.1.5', 8887);

-- --------------------------------------------------------

--
-- Table structure for table `users_1`
--

CREATE TABLE `users_1` (
  `id_user` int(11) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users_1`
--

INSERT INTO `users_1` (`id_user`, `username`, `email`, `password`) VALUES
(1, 'hoangpho', 'ronhoangdn1970@gmail', 'hoangpho'),
(2, 'ronhoang', 'ronhoangdn1970@gmail', 'hoangpho'),
(3, 'phoprono1', 'ronhoangdn1970@gmail.com', 'hoangpho'),
(4, '123', '123', '123'),
(5, '456', '456', '456');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookmarks`
--
ALTER TABLE `bookmarks`
  ADD PRIMARY KEY (`id_bookmarks`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender_id` (`sender_id`),
  ADD KEY `receiver_id` (`receiver_id`);

--
-- Indexes for table `search_history`
--
ALTER TABLE `search_history`
  ADD PRIMARY KEY (`id_history`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookmarks`
--
ALTER TABLE `bookmarks`
  MODIFY `id_bookmarks` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `search_history`
--
ALTER TABLE `search_history`
  MODIFY `id_history` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=207;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookmarks`
--
ALTER TABLE `bookmarks`
  ADD CONSTRAINT `bookmarks_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE;

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
