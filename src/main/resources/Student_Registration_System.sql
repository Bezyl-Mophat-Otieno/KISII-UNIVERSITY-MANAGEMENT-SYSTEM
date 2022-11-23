- phpMyAdmin SQL Dump
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
-- Table structure for table `Registrar`
--

CREATE TABLE `Registrar` (
  `Name` varchar(255) NOT NULL,
  `Staff_No` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




-- --------------------------------------------------------

--
-- Table structure for table `Lecturers`
--

CREATE TABLE `lecturer` (
  `ID` varchar(150) NOT NULL PRIMARY KEY,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(64) NOT NULL,
  `Password` varchar(150) NOT NULL DEFAULT 'password'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `lecturer` (`ID`, `Name`, `Email`, `Password`) VALUES
('LEC-001', 'Ms. Vera Kwamboka', 'lec001@kisiiuniversity.ac.ke', 'LEC-001'),
('LEC-013', 'Mr. Alex Nyandego', 'lec013@kisiiuniversity.ac.ke', 'LEC-013'),
('LEC-054', 'Dr. Alfred Mutua', 'lec054@kisiiuniversity.ac.ke', 'LEC-054'),
('LEC-106', 'Mr. Rasulu Abdek', 'lec106@kisiiuniversity.ac.ke', 'LEC-106');

CREATE TABLE `assigned_units` (
  `ID` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Unit_Code` varchar(255) NOT NULL,
  FOREIGN KEY(ID) REFERENCES lecturer(ID)
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
('IN19/0023', 'OLekaparo olesapit', 'ole@gmail.com', 'IN19/0023', 'Animal science', 1, 'active');

-- --------------------------------------------------------

--
-- Table structure for table `Courses`
--

CREATE TABLE `Courses` (
  `Course_ID` varchar(255) NOT NULL,
  `Course_Name` varchar(255) NOT NULL,
  `Cost_Price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Courses`
--

INSERT INTO `Courses` (`Course_ID`, `Course_Name`,`Cost_Price`) VALUES
('SOEN', 'Software Engineering',23500.00),
('ACMP', 'Applied Computer Science', 20500.00),
('JOURN', 'Journalism', 31000.00),
('COMPSCI', 'Computer Science',28500.00),
('MED', 'Medicine And Surgery',29500.00),
('BIT', 'Information Technology', 21000.00),
('BINM', 'Information Management', 21000.00);

-- --------------------------------------------------------

--
-- Table structure for table `Courses-offered`
--

CREATE TABLE `Courses_Offered` (
  `Course_ID` varchar(255) NOT NULL,
  `Course_Name` varchar(255) NOT NULL,
  `Course_Price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Courses-offered`
--

INSERT INTO `Courses_Offered` (`Course_ID`, `Course_Name`, `Course_Price`) VALUES
('SOEN', 'Software Engineering', '23500.00'),
('ACMP', 'Applied Computer Science', '20500.00'),
('JOURN', 'Journalism', '31000.00'),
('COMPSCI', 'Computer Science', '28500.00');

-- --------------------------------------------------------

--
-- Table structure for table `Course_Units`
--

CREATE TABLE `Course_Units` (
  `Course_ID` varchar(255) NOT NULL,
  `Unit_Code` varchar(255) NOT NULL PRIMARY KEY,
  `Unit_Name` varchar(255) NOT NULL,
  `Unit_Desc` varchar(255) NOT NULL,
  `Selected` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Course_Units`
--

INSERT INTO `Course_Units` (`Course_ID`, `Unit_Code`, `Unit_Name`, `Unit_Desc`, `Selected`) VALUES
('ACMP', 'ACMP000', 'Introductory Electronics', 'The power of electronics in computing', NULL),
('SOEN', 'SOEN201', 'Introductory Electronics', 'The power of electronics in computing', NULL),
('BIT', 'COMPSCI204', 'Introductory Electronics', 'The power of electronics in computing', NULL),
('ACMP', 'COMP104', 'Introductory Electronics', 'The power of electronics in computing', NULL),
('BINM', 'PHYS201', 'Introductory electronics', 'The power of electronics in computing', NULL),

('BIT', 'BIT201', 'UX/UI designs ', 'UserInterface design using figma and adobe XD .' , NULL),
('ACMP', 'ACMP201','UX/UI designs ', 'UserInterface design using figma and adobe XD.', NULL),
('SOEN', 'SOEN104', 'UX/UI designs ', 'UserInterface design using figma and adobe XD.', NULL),

('ARCH', 'ARCH101', 'Architectural designs', 'Fundamentals of Architectural design and planning', NULL),

('SOEN', 'ZOOL104', 'Biology of HIV/AIDS ', 'Biology of HIV/AIDS and how it affects the society', NULL),
('ACMP', 'ZOOL102', 'Biology of HIV/AIDS', 'Biology of HIV/AIDS and how it affects the society', NULL),
('BINM', 'ZOOL103', 'Biology of HIV/AIDS', 'Biology of HIV/AIDS and how it affects the society', NULL),
('BIT', 'ZOOL101', 'Biology of HIV/AIDS', 'Biology of HIV/AIDS and how it affects the society', NULL),
('MED', 'BOT222', 'Biology of HIV/AIDS', 'Biology of HIV/AIDS and how it affects the society', NULL),

('COMPSCI', 'COMP205', 'Telecommunications ', 'The units talks about the various technology used in tele-engineering', NULL),
('BIT', 'NET101', 'Telecommunications ', 'The units talks about the various technology used in tele-engineering', NULL),
('BINM', 'TEL444', 'Telecommunications ', 'The units talks about the various technology used in tele-engineering', NULL),
('SOEN', 'TEL404', 'Telecommunications ', 'The units talks about the various technology used in tele-engineering', NULL),
('MED', 'MED134', 'Informatics and Bio Tech', 'Information systems as used in statistics', NULL),
('COMPSCI', 'SOEN246', 'Informatics and Bio Tech', 'Information systems as used in statistics', NULL),
('ACMP', 'COMP214', 'Informatics and Bio Tech', 'Information systems as used in statistics', NULL),
('JOURN', 'COMP302', 'Informatics and Bio Tech', 'Information systems as used in statistics', NULL),
('BINM', 'COMP333', 'Informatics and Bio Tech', 'Information systems as used in statistics', NULL),

('SOEN', 'COMS101', 'Communication skills ', 'Communication skills for software engineers.', NULL),
('SOEN', 'COMP201', 'Operating systems ', 'Types of Operating Systems, fundamentals, and compiler construction', NULL),
('COMPSCI', 'CS000', 'Data  Structures and Algorithms.', 'Through Efficient Data structuring and algorithm consumption Systems of high quality in terms of performance can  be developed.', NULL),

('JOURN', 'JOURN000', 'Interview Criteria and Best practices. ', 'The Unit describes the various ways in which interviews should be carried out for betterment of user Experience.\r\n ', NULL),
('JOURN', 'COM101', 'Broadcast Journalism ', 'How broadcast journalism affects method of information delivery', NULL),
('JOURN', 'MEDIA111', 'Telecommunications ', 'The units talks about the various technology used in tele-engineering', NULL),

('ACMP', 'MATH111', 'Calculus I ', 'The units talks about differentiation integration and factorization in calculus', NULL),
('SOEN', 'MATH112', 'Discrete Mathematics ', 'Mathematical concepts for software engineers.', NULL),
('SOEN', 'SOEN000', 'Software Standards', 'Unit deals with the standards set to a good software  development method', NULL);



-- --------------------------------------------------------

--
-- Table structure for table `Register_Units`
--

CREATE TABLE `Register_Units` (
  `Std_ID` varchar(255) NOT NULL,
  `Unit_Code` varchar(255) NOT NULL,
  `Unit_Name` varchar(255) NOT NULL,
  `Units_Status` varchar(255) NOT NULL,
  `Status` varchar(255) DEFAULT NULL,
  FOREIGN KEY(Unit_Code) REFERENCES Course_Units(Unit_Code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Register_Units`
--

INSERT INTO `Register_Units` (`Std_ID`,`Unit_Code`, `Unit_Name`, `Units_Status`, `Status`) VALUES
('IN16/0003','ARCH101','Architectural designs', 'approved', NULL),
('IN16/0003','ZOOL104', 'Biology of HIV/AIDS ', 'approved', NULL),
('IN16/0003','COMS101','Communication skills ', 'approved', NULL),
('IN16/0003','MATH112',  'Discrete Mathematics ', 'approved', NULL),
('IN16/0004','SOEN201','Introductory Electronics', 'approved', NULL),
('IN16/0003','COMP201', 'Operating systems ', 'approved', NULL),
('IN16/0003','SOEN000', 'Software Standards', 'approved', NULL),
('IN16/0005','TEL404', 'Telecommunications ', 'approved', NULL),
('IN16/0003','SOEN104','UX/UI designs ', 'approved', NULL);


-- --------------------------------------------------------

--
-- Table structure for table `Unit_Results`
--

CREATE TABLE `Unit_Results` (
  `Unit_Code` varchar(255) NOT NULL,
  `Std_ID` varchar(255) NOT NULL,
  `GPA` decimal(2,1) NOT NULL,
   FOREIGN KEY(Unit_Code) REFERENCES Course_Units(Unit_Code),
   FOREIGN KEY(Unit_Code) REFERENCES Register_Units(Unit_Code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Unit_Results`
--

INSERT INTO `Unit_Results` (`Unit_Code`, `Std_ID`, `GPA`) VALUES
('COMS101', 'IN16/0003', 3.6),
('COMP201', 'IN16/0003', 3.8),
('SOEN104', 'IN16/0003', 3.6),
('SOEN000', 'IN16/0003', 3.8),
('SOEN104', 'IN16/0003', 3.8),
('ZOOL104', 'IN16/0003', 3.8);

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