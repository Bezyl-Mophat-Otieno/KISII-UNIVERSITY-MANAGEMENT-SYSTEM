-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 16, 2022 at 08:06 AM
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
-- Database: `Student_Registration_System`
--

-- --------------------------------------------------------

--
-- Table structure for table `admissions`
--

CREATE TABLE `admissions` (
  `ID` varchar(150) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(64) DEFAULT NULL,
  `Password` varchar(64) DEFAULT 'password'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admissions`
--

INSERT INTO `admissions` (`ID`, `Name`, `Email`, `Password`) VALUES
('ADM-001', 'Brian Mugo', 'adm-001@kisiiuniversity.ac.ke', 'ADM-001'),
('ADM-013', 'Tanjina Helaly', 'adm-013@kisiiuniversity.ac.ke', 'ADM-013');

-- --------------------------------------------------------

--
-- Table structure for table `courseadvisor`
--

CREATE TABLE `courseadvisor` (
  `ID` varchar(150) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(64) NOT NULL,
  `Password` varchar(150) NOT NULL DEFAULT 'password'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `courseadvisor`
--

INSERT INTO `courseadvisor` (`ID`, `Name`, `Email`, `Password`) VALUES
('CAD-001', 'Md. Joy Muthoni', 'cad001@kisiiuniversity.ac.ke', 'CAD-001'),
('CAD-013', 'Mr. Alex Nyandego', 'cad013@kisiiuniversity.ac.ke', 'CAD-013'),
('CAD-054', 'Dr. Alfred Mutua', 'cad054@kisiiuniversity.ac.ke', 'CAD-054'),
('CAD-106', 'DR.Islam Shamsudin', 'cad106@kisiiuniversity.ac.ke', 'CAD-106');

-- --------------------------------------------------------

--
-- Table structure for table `Courses`
--

CREATE TABLE `Courses` (
  `Course_ID` int(11) NOT NULL,
  `Course_Name` varchar(255) NOT NULL,
  `Std_ID` varchar(255) DEFAULT NULL,
  `Cost_Price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Courses`
--

INSERT INTO `Courses` (`Course_ID`, `Course_Name`, `Std_ID`, `Cost_Price`) VALUES
(1, 'Software Engineering\r\n', 'IN16/0003', '23500.00'),
(2, 'Applied Computer Science', 'IN16/0004', '20500.00'),
(3, 'Journalism', 'IN16/0005', '31000.00'),
(4, 'Computer Science', 'IN16/0006', '28500.00'),
(5, 'Software Engineering', 'IN16/00010', '23500.00'),
(6, 'Software Engineering', 'IN16/00011', '23500.00'),
(7, 'Software Engineering', 'IN16/0007', '23500.00'),
(8, 'Applied Computer SCience', 'IN16/00012', '20500.00'),
(9, 'Applied Computer SCience', 'IN16/0009', '20500.00');

-- --------------------------------------------------------

--
-- Table structure for table `Courses-offered`
--

CREATE TABLE `Courses-offered` (
  `Course_ID` int(11) NOT NULL,
  `Course_Name` varchar(255) NOT NULL,
  `Course_Price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Courses-offered`
--

INSERT INTO `Courses-offered` (`Course_ID`, `Course_Name`, `Course_Price`) VALUES
(1, 'SOEN', '23500.00'),
(2, 'ACMP', '20500.00'),
(3, 'Journalism', '31000.00'),
(4, 'cs', '28500.00');

-- --------------------------------------------------------

--
-- Table structure for table `Course_Units`
--

CREATE TABLE `Course_Units` (
  `Course_ID` int(11) NOT NULL,
  `Unit_Code` varchar(255) NOT NULL,
  `Unit_Name` varchar(255) NOT NULL,
  `Unit_Desc` varchar(255) NOT NULL,
  `Selected` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Course_Units`
--

INSERT INTO `Course_Units` (`Course_ID`, `Unit_Code`, `Unit_Name`, `Unit_Desc`, `Selected`) VALUES
(2, 'ACMP000', 'Introductory Electronics', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(2, 'ACMP201', 'Introductory electronics', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(2, 'ACMP204', 'Introductory electronics', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(1, 'ARCH101', 'Architectural designs', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(2, 'BOT222', 'Biology of HIV/AIDS', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(3, 'COM101', 'Telecommunications ', 'The units talks about the various tehnology used in tele-engineering', NULL),
(4, 'COMP1-101', 'Informatics and Bio Tech', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(2, 'comp104', 'Introductory electronics', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(1, 'COMP201', 'Operating systems ', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(4, 'COMP204', 'Informatics and Bio Tech', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(3, 'COMP205', 'Telecommunications ', 'The units talks about the various tehnology used in tele-engineering', NULL),
(4, 'COMP214', 'Informatics and Bio Tech', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown ', NULL),
(4, 'COMP301', 'Informatics and Bio Tech', 'Information systems as used in statistics', NULL),
(4, 'COMP302', 'Informatics and Bio Tech', 'Information systems as used in statistics', NULL),
(4, 'COMP333', 'Informatics and Bio Tech', 'Information systems as used in statistics', NULL),
(1, 'COMS101', 'Communication skills ', 'commmunication skills for sotware engineers .', NULL),
(4, 'CS000', 'Data  Structures and Algorithms.', 'Through Efficient Data structuring and algorithm consumption  Systems of high quality interms of perfomance can  be developed.', NULL),
(3, 'JOURN000', 'Interview Criteria and Best practices. ', 'The Unit describes the various ways in which interviews should be carried out for betterment of user Experience.\r\n ', NULL),
(3, 'MATH111', 'Telecommunications ', 'The units talks about the various tehnology used in tele-engineering', NULL),
(1, 'MATH112', 'Discrete Mathematics ', 'Mathematical concepts for software engineers.', NULL),
(2, 'MATH201', 'Introductory electronics', 'The power of electronics in computing', NULL),
(3, 'MEDIA111', 'Telecommunications ', 'The units talks about the various tehnology used in tele-engineering', NULL),
(3, 'NET101', 'Telecommunications ', 'The units talks about the various tehnology used in tele-engineering', NULL),
(1, 'SOEN000', 'Software Standards', 'Unit deals with the standards set to a good software  development method', NULL),
(1, 'SOEN104', 'UX/UI designs ', 'UserInterface design using figma and adobe XD .', NULL),
(4, 'SOEN246', 'Informatics and Bio Tech', 'Information systems as used in statistics', NULL),
(3, 'TEL444', 'Telecommunications ', 'The units talks about the various tehnology used in tele-engineering', NULL),
(2, 'UIX201', 'Introductory electronics', 'The power of electronics in computing', NULL),
(2, 'UX201', 'Introductory electronics', 'The power of electronics in computing', NULL),
(1, 'ZOOL101', 'Biology of HIV/AIDS ', 'All about aids  .', NULL),
(2, 'ZOOL104', 'Biology of HIV/AIDS', 'Biology of HIV/AIDS and how it affects the society', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Lecturers`
--

CREATE TABLE `Lecturers` (
  `Staff_No` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Course_Name` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `Register_Units`
--

CREATE TABLE `Register_Units` (
  `Std_ID` varchar(255) NOT NULL,
  `Unit_Name` varchar(255) NOT NULL,
  `Units_Status` varchar(255) NOT NULL,
  `Status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Register_Units`
--

INSERT INTO `Register_Units` (`Std_ID`, `Unit_Name`, `Units_Status`, `Status`) VALUES
('IN16/0003', 'Architectural designs', 'approved', NULL),
('IN16/0003', 'Biology of HIV/AIDS ', 'approved', NULL),
('IN16/0003', 'Communication skills ', 'approved', NULL),
('IN16/0003', 'Discrete Mathematics ', 'approved', NULL),
('IN16/0004', 'Introductory Electronics', 'approved', NULL),
('IN16/0003', 'Operating systems ', 'approved', NULL),
('IN16/0003', 'Software Standards', 'approved', NULL),
('IN16/0005', 'Telecommunications ', 'approved', NULL),
('IN16/0003', 'UX/UI designs ', 'approved', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Registrar`
--

CREATE TABLE `Registrar` (
  `Name` varchar(255) NOT NULL,
  `Staff_No` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `Student`
--

CREATE TABLE `Student` (
  `Std_ID` varchar(255) NOT NULL,
  `Std_Name` varchar(255) NOT NULL,
  `Std_Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Course` varchar(255) NOT NULL,
  `YOS` tinyint(4) NOT NULL,
  `Status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Student`
--

INSERT INTO `Student` (`Std_ID`, `Std_Name`, `Std_Email`, `Password`, `Course`, `YOS`, `Status`) VALUES
('09o8iuytriLKJH', 'jkhgfdSQWDF', '8QSWDFty', 'jghQSWDFff', 'hjhg', 3, 'kujSWDhgfx'),
('edfvbgn', 'poiuythg', 'oioukjyhgf', 'werfgthyj', 'oiuytghgfd', 2, 'wergthyjukl'),
('hgfd', 'kjhgfd', 'iouyuhfg', 'werfgthj', 'kjghf', 1, 'qwefgtryu'),
('IN16/0001', 'NASIR', 'coby@gmail.com', 'IN16/0001', 'SOEN', 3, 'active'),
('IN16/00010', 'CALEB OLesapit', 'calebm@gmail.com', 'IN16/00010', 'SOEN', 1, 'active'),
('IN16/00011', 'ANIS ISMAEL', 'ismaelm@gmail.com', 'IN16/00011', 'SOEN', 2, 'dropped-out'),
('IN16/00012', 'OLESAPIT GUNNER', 'ole@gmail.com', 'IN16/00012', 'ACMP', 1, 'dropped-out'),
('IN16/00013', 'MOHAMMED BIN KHAYIM', 'hisham@gmail.com', 'IN16/00013', 'JOURNALISM', 2, 'active'),
('IN16/0003', 'BEZYL MOPHAT HAROUN OTIENO', 'bezylmophatotieno@gmail.com', 'IN16/0003', 'SOFTWARE ENGINEERING', 1, 'active'),
('IN16/0004', 'JOHN DOE', 'johndoe@gmail.com', 'IN16/0004', 'ACMP', 2, 'dropped-out'),
('IN16/0005', 'Michael Jordan Oreo', 'michaeljordan@gmail.com', 'IN16/0005', 'CS', 3, 'active'),
('IN16/0006', 'Mitchel Obama', 'obama@gmail.com', 'IN16/0006', 'journalism', 4, 'active'),
('IN16/0007', 'MOHAMMED UMAR', 'umarm@gmail.com', 'IN16/000', 'SOEN', 2, 'inactive'),
('IN16/0008', 'MOHAMMED HISHAM', 'hisham@gmail.com', 'IN16/0008', 'JOURNALISM', 2, 'active'),
('IN16/0009', 'OLESAPIT GUNNER', 'ole@gmail.com', 'IN16/0009', 'ACMP', 1, 'inactive'),
('IN17/0001', 'JOSHUA DEEN', 'deen@gmail.com', 'IN17/0001', 'BIT', 4, 'active'),
('IN19/0023', 'OLekaparo olesapit', 'ole@gmail.com', 'IN19/0023', 'Animal science', 1, 'active'),
('ioujhgfgdf', 'ijhgf', 'jhjgfd', 'uyt', 'iouhygtfgd', 2, 'jhgfd'),
('jkhgfx', 'iuyhfg', 'cgfhjhkj', 'poiuytgrfd', 'ujhgfdiuyut', 22, 'iuytygfd'),
('oiouiyu', 'poiuythg', 'oioukjyhgf', 'werfgthyj', 'oiuytghgfd', 2, 'wergthyjukl'),
('oiujhygfd', 'iukjhgfd', 'iikujyhtgf', 'iuytghgfd', 'iujyhfgd', 1, 'kjhgfd'),
('oooi', 'iuytgfd', 'sdvfbgn', 'poiuyhgf', 'sdfvfb', 2, 'werfghb'),
('p[oiuytgf', 'oikujyhgf', '[opiuyhgf', '[poiuytrf', 'iukjghfv', 2, 'iouytytg');

-- --------------------------------------------------------

--
-- Table structure for table `Unit_Results`
--

CREATE TABLE `Unit_Results` (
  `Unit_Code` varchar(255) NOT NULL,
  `Std_ID` varchar(255) NOT NULL,
  `GPA` decimal(2,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Unit_Results`
--

INSERT INTO `Unit_Results` (`Unit_Code`, `Std_ID`, `GPA`) VALUES
('ACMP000', 'IN16/0004', '3.2'),
('ACMP101', 'IN16/0004', '3.9'),
('BOT143', 'IN16/0004', '4.0'),
('COMP120', 'IN16/0003', '3.8'),
('COMS101', 'IN16/0004', '3.9'),
('CS000', 'IN16/0005', '2.9'),
('CSI', 'IN16/0004', '3.0'),
('GEO104', 'IN16/0003', '3.6'),
('JOURN000', 'IN16/0006', '3.2'),
('MATH101', 'IN16/0003', '3.8'),
('MATH110', 'IN16/0003', '3.6'),
('MATH112', 'IN16/0003', '3.6'),
('MATH240', 'IN16/0004', '3.8'),
('MATH260', 'IN16/0003', '3.8'),
('MATHPHY123', 'IN16/0003', '3.6'),
('PHIL104', 'IN16/0003', '3.6'),
('SOEN000', 'IN16/0003', '3.8'),
('SOEN260', 'IN16/0003', '3.4'),
('UI/UX111', 'IN16/0003', '3.8'),
('UX100', 'IN16/0004', '4.0'),
('ZOOL000', 'IN16/0003', '3.8'),
('ZOOL113', 'IN16/0003', '3.8'),
('ZOOL143', 'IN16/0004', '4.0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admissions`
--
ALTER TABLE `admissions`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `courseadvisor`
--
ALTER TABLE `courseadvisor`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Courses`
--
ALTER TABLE `Courses`
  ADD PRIMARY KEY (`Course_ID`),
  ADD KEY `Std_ID` (`Std_ID`);

--
-- Indexes for table `Courses-offered`
--
ALTER TABLE `Courses-offered`
  ADD KEY `Course_ID` (`Course_ID`);

--
-- Indexes for table `Course_Units`
--
ALTER TABLE `Course_Units`
  ADD PRIMARY KEY (`Unit_Code`),
  ADD KEY `Course_ID` (`Course_ID`);

--
-- Indexes for table `Lecturers`
--
ALTER TABLE `Lecturers`
  ADD PRIMARY KEY (`Staff_No`);

--
-- Indexes for table `Register_Units`
--
ALTER TABLE `Register_Units`
  ADD PRIMARY KEY (`Unit_Name`),
  ADD KEY `Std_ID` (`Std_ID`);

--
-- Indexes for table `Student`
--
ALTER TABLE `Student`
  ADD PRIMARY KEY (`Std_ID`);

--
-- Indexes for table `Unit_Results`
--
ALTER TABLE `Unit_Results`
  ADD PRIMARY KEY (`Unit_Code`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Courses`
--
ALTER TABLE `Courses`
  MODIFY `Course_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Courses`
--
ALTER TABLE `Courses`
  ADD CONSTRAINT `Courses_ibfk_1` FOREIGN KEY (`Std_ID`) REFERENCES `Student` (`Std_ID`);

--
-- Constraints for table `Courses-offered`
--
ALTER TABLE `Courses-offered`
  ADD CONSTRAINT `Courses-offered_ibfk_1` FOREIGN KEY (`Course_ID`) REFERENCES `Courses` (`Course_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Course_Units`
--
ALTER TABLE `Course_Units`
  ADD CONSTRAINT `Course_Units_ibfk_1` FOREIGN KEY (`Course_ID`) REFERENCES `Courses` (`Course_ID`);

--
-- Constraints for table `Register_Units`
--
ALTER TABLE `Register_Units`
  ADD CONSTRAINT `Register_Units_ibfk_1` FOREIGN KEY (`Std_ID`) REFERENCES `Student` (`Std_ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
