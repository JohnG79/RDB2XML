-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=`TRADITIONAL,ALLOW_INVALID_DATES`;

-- -----------------------------------------------------
-- Schema university
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `university` ;

-- -----------------------------------------------------
-- Schema university
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `university` DEFAULT CHARACTER SET latin1 ;
USE `university` ;

-- -----------------------------------------------------
-- Table `university`.`department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university`.`department` ;

CREATE TABLE IF NOT EXISTS `university`.`department` (
  `dep_code` VARCHAR(4) CHARACTER SET `utf8` NOT NULL,
  `dep_name` VARCHAR(40) CHARACTER SET `utf8` NOT NULL,
  PRIMARY KEY (`dep_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `dep_name` ON `university`.`department` (`dep_name` ASC);


-- -----------------------------------------------------
-- Table `university`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university`.`course` ;

CREATE TABLE IF NOT EXISTS `university`.`course` (
  `crs_code` VARCHAR(10) CHARACTER SET `utf8` NOT NULL,
  `crs_title` VARCHAR(100) NOT NULL,
  `crs_credits` INT(11) NOT NULL,
  `dep_code` VARCHAR(4) CHARACTER SET `utf8` NOT NULL,
  `crs_description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`crs_code`),
  CONSTRAINT `course_ibfk_1`
    FOREIGN KEY (`dep_code`)
    REFERENCES `university`.`department` (`dep_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `dep_code` ON `university`.`course` (`dep_code` ASC);


-- -----------------------------------------------------
-- Table `university`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university`.`student` ;

CREATE TABLE IF NOT EXISTS `university`.`student` (
  `stu_id` VARCHAR(9) CHARACTER SET `utf8` NOT NULL,
  `stu_fname` VARCHAR(20) CHARACTER SET `utf8` NOT NULL,
  `stu_lname` VARCHAR(20) CHARACTER SET `utf8` NOT NULL,
  PRIMARY KEY (`stu_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `university`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university`.`location` ;

CREATE TABLE IF NOT EXISTS `university`.`location` (
  `loc_code` INT(4) NOT NULL,
  `loc_campus` VARCHAR(45) CHARACTER SET `utf8` NOT NULL,
  `loc_building` VARCHAR(20) CHARACTER SET `utf8` NOT NULL,
  `loc_room` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`loc_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `university`.`instructor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university`.`instructor` ;

CREATE TABLE IF NOT EXISTS `university`.`instructor` (
  `ins_id` VARCHAR(9) CHARACTER SET `utf8` NOT NULL,
  `ins_fname` VARCHAR(20) CHARACTER SET `utf8` NOT NULL,
  `ins_lname` VARCHAR(20) CHARACTER SET `utf8` NOT NULL,
  `dep_code` VARCHAR(4) CHARACTER SET `utf8` NOT NULL,
  PRIMARY KEY (`ins_id`),
  CONSTRAINT `instructor_ibfk_1`
    FOREIGN KEY (`dep_code`)
    REFERENCES `university`.`department` (`dep_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `dep_code` ON `university`.`instructor` (`dep_code` ASC);


-- -----------------------------------------------------
-- Table `university`.`section`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university`.`section` ;

CREATE TABLE IF NOT EXISTS `university`.`section` (
  `section_id` INT(4) NOT NULL,
  `loc_code` INT(4) NOT NULL,
  `time_id` INT NOT NULL,
  `crs_code` VARCHAR(10) CHARACTER SET `utf8` NOT NULL,
  `ins_id` VARCHAR(9) CHARACTER SET `utf8` NOT NULL,
  PRIMARY KEY (`section_id`),
  KEY (`loc_code`, `time_id`, `crs_code`),
  CONSTRAINT `section_ibfk_1`
    FOREIGN KEY (`crs_code`)
    REFERENCES `university`.`course` (`crs_code`),
  CONSTRAINT `fk_section_location1`
    FOREIGN KEY (`loc_code`)
    REFERENCES `university`.`location` (`loc_code`),
  CONSTRAINT `fk_section_time1`
    FOREIGN KEY (`time_id`)
    REFERENCES `university`.`time` (`time_id`),
  CONSTRAINT `fk_section_instructor`
    FOREIGN KEY (`ins_id`)
    REFERENCES `university`.`instructor` (`ins_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `section_id_idx` ON `university`.`section` (`section_id` ASC);

CREATE INDEX `crs_code_idx` ON `university`.`section` (`crs_code` ASC);

CREATE INDEX `fk_section_location1_idx` ON `university`.`section` (`loc_code` ASC);

CREATE INDEX `fk_section_time1_idx` ON `university`.`section` (`time_id` ASC);


-- -----------------------------------------------------
-- Table `university`.`enrolment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university`.`enrolment` ;

CREATE TABLE IF NOT EXISTS `university`.`enrolment` (
  `stu_id` VARCHAR(9) CHARACTER SET `utf8` NOT NULL,
  `section_id` INT(4) NOT NULL,
  `grade_code` VARCHAR(2) CHARACTER SET `utf8` NOT NULL,
  PRIMARY KEY (`stu_id`, `section_id`),
  CONSTRAINT `enrolment_ibfk_1`
    FOREIGN KEY (`stu_id`)
    REFERENCES `university`.`student` (`stu_id`),
  CONSTRAINT `enrolment_ibfk_2`
	 FOREIGN KEY (`section_id`)
    REFERENCES `university`.`section` (`section_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_enrolment_section1_idx` ON `university`.`enrolment` (`stu_id` ASC, `section_id` ASC);


-- -----------------------------------------------------
-- Table `university`.`prerequisite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university`.`prerequisite` ;

CREATE TABLE IF NOT EXISTS `university`.`prerequisite` (
  `crs_code` VARCHAR(10) CHARACTER SET `utf8` NOT NULL,
  `crs_requires` VARCHAR(10) CHARACTER SET `utf8` NOT NULL,
  PRIMARY KEY (`crs_code`, `crs_requires`),
  CONSTRAINT `prerequesite_ibfk_1`
    FOREIGN KEY (`crs_code`)
    REFERENCES `university`.`course` (`crs_code`),
  CONSTRAINT `prerequesite_ibfk_2`
    FOREIGN KEY (`crs_requires`)
    REFERENCES `university`.`course` (`crs_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `prerequesite_ibfk_2` ON `university`.`prerequisite` (`crs_requires` ASC);


-- -----------------------------------------------------
-- Table `university`.`qualified`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university`.`qualified` ;

CREATE TABLE IF NOT EXISTS `university`.`qualified` (
  `ins_id` VARCHAR(9) CHARACTER SET `utf8` NOT NULL,
  `crs_code` VARCHAR(10) CHARACTER SET `utf8` NOT NULL,
  PRIMARY KEY (`ins_id`, `crs_code`),
  CONSTRAINT `qualified_ibfk_1`
    FOREIGN KEY (`ins_id`)
    REFERENCES `university`.`instructor` (`ins_id`),
  CONSTRAINT `qualified_ibfk_2`
    FOREIGN KEY (`crs_code`)
    REFERENCES `university`.`course` (`crs_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `crs_code` ON `university`.`qualified` (`crs_code` ASC);

-- -----------------------------------------------------
-- Table `university`.`time`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `university`.`time` (
  `time_id` INT NOT NULL,
  `time_year` VARCHAR(45) NOT NULL,
  `time_trimester` INT(1) NOT NULL,
  `time_timeOfDay` INT(4) NOT NULL,
  PRIMARY KEY (`time_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- INSERTS
-- -----------------------------------------------------
SET sql_mode = ``;

INSERT INTO `university`.`student` (`stu_id`, `stu_fname`, `stu_lname`) VALUES
('2131382', 'John', 'Graham'),
('2165670', 'Lisa', 'Smith'),
('3165630', 'Anna', 'Newsome'),
('2165777', 'Odessa', 'Benson');

INSERT INTO `location` (`loc_code`, `loc_campus`, `loc_building`, `loc_room`) VALUES
(12, 'Petone', 'T', 'T601' ),
(13, 'Petone', 'T', 'T602' ),
(14, 'Petone', 'T', 'T603' ),
(1, 'Petone', 'T', 'T701' ),
(2, 'Petone', 'T', 'T702' ),
(3, 'Petone', 'T', 'T703' ),
(4, 'Petone', 'B', 'B101' ),
(5, 'Petone', 'B', 'B102' ),
(6, 'Petone', 'B', 'B103' ),
(7, 'Petone', 'B', 'B104' ),
(8, 'Petone', 'B', 'B105' ),
(9, 'Petone', 'C', 'C106' ),
(10, 'Petone', 'C', 'C107' ),
(11, 'Petone', 'C', 'C108' );

INSERT INTO `department` (`dep_code`, `dep_name`) VALUES
('BIT', 'Business and Information Technology');

INSERT INTO `instructor` (`ins_id`, `ins_fname`, `ins_lname`, `dep_code`) VALUES
('PT-BIT124', 'Ian', 'Hunter', 'BIT'),
('PT-BIT142', 'Drew', 'Duncan', 'BIT'),
('PT-BIT178', 'Cory', 'Rademeyer', 'BIT'),
('PT-BIT195', 'Robert', 'Sutcliffe', 'BIT'),
('PT-BIT249', 'Marcia', 'Johnson', 'BIT'),
('PT-BIT261', 'Mick', 'Jays', 'BIT'),
('PT-BIT300', 'Richa', 'Panjabi', 'BIT'),
('PT-BIT338', 'Jeff', 'Echano', 'BIT'),
('PT-BIT381', 'Rhys', 'Owen', 'BIT'),
('PT-BIT386', 'Terry', 'Jeon', 'BIT'),
('PT-BIT393', 'Ian', 'Armstrong', 'BIT'),
('PT-BIT483', 'Steve', 'McKinlay', 'BIT'),
('PT-BIT516', 'Mariki', 'Farrell', 'BIT'),
('PT-BIT520', 'Nathan', 'Humphrey', 'BIT'),
('PT-BIT549', 'Clement', 'Sudhakar', 'BIT'),
('PT-BIT624', 'Leonie', 'Trower', 'BIT'),
('PT-BIT626', 'Anthony', 'Allsobrook', 'BIT'),
('PT-BIT634', 'Paul', 'Bryant', 'BIT'),
('PT-BIT740', 'Nick', 'Tullock', 'BIT'),
('PT-BIT788', 'Evan', 'Keats', 'BIT'),
('PT-BIT872', 'Andrew', 'Eales', 'BIT'),
('PT-BIT924', 'Abdolreza', 'Hajmoosaei', 'BIT'),
('PT-BIT947', 'John', 'Gould', 'BIT'),
('PT-BIT972', 'Aristotle', 'Santos', 'BIT'),
('PT-BIT987', 'David', 'Eyes', 'BIT');

INSERT INTO `time` (`time_id`, `time_year`, `time_trimester`, `time_timeOfDay`) VALUES
('25','2015','1','0900'),
('24','2015','2','1000'),
('23','2015','1','1100'),
('22','2015','2','1200'),
('21','2015','1','1300'),
('20','2015','2','1400'),
('19','2015','1','1500'),
('18','2015','2','1600'),
('17','2015','1','0900'),
('16','2015','2','1000'),
('15','2015','1','1100'),
('14','2015','2','1200'),
('13','2015','1','1300'),
('12','2015','2','1400'),
('11','2015','1','1500'),
('10','2015','2','1600'),
('9','2015','1','0900'),
('8','2015','2','1000'),
('7','2015','1','1100'),
('6','2015','2','1200'),
('5','2015','1','1300'),
('4','2015','2','1400'),
('3','2015','1','1500'),
('2','2015','2','1600'),
('1','2015','1','0900');

INSERT INTO `course` (`crs_code`, `crs_title`, `crs_credits`, `dep_code`, `crs_description`) VALUES
('IT5159', 'Operating Systems Fundamentals', 15, 'BIT', 'Develop an in-depth understanding of the structure and operation of operating systems. Key topics of study include: OS components and resource management, OS Architectures (Monolithic, kernel, distributed, architectural layers) and OS programming (software structures, administration tools, scripting languages, system calls, remote procedure calls in at least 3 different operating systems; DOS, Windows, UNIX).'),
('IT5162', 'Scripting I', 15, 'BIT', 'This ''hands on'' scripting course will develop your ability to design and implement scripts from scratch, or adapt scripts from third parties. At the conclusion of the course you will be a confident script coder. Key topics of study include: Scripting fundamentals, Scripting engines, Report generation, Script housekeeping, Introductory DHTML (Dynamic HTML), Introductory automation of system administration.'),
('IT5169', 'Software Engineering', 15, 'BIT', 'Develop your understanding of modern engineering analysis, design, implementation and integration in relation to the development of software. Key topics of study include: software development, the historical background of software development, current research, problems and issues, Life cycle models, software specification and design, testing, validation and verification and documentation standards.'),
('IT5174', 'Networking I', 15, 'BIT', 'Gain an introduction to fundamental networking concepts and technologies and the basics of network theory. Develop the skills necessary to implement a simple network. Key topics of study include: Network Technology and Network Implementation.'),
('IT5175', 'Networking II', 15, 'BIT', 'Gain an understanding of individual routing protocols and concepts and learn how to analyse, verify and troubleshoot routing operations. You will also learn practical skills, including how to configure RIP, EIGRP and OSPF, and gain an understanding of the logic and algorithms involved in routing traffic. Key topics of study include: Routing Technology and Routing Implementation.'),
('IT5177', 'Emerging Technologies', 15, 'BIT', 'Learn fundamental computing technologies and explore how these have developed into the emerging technologies. Key topics of study include: Input devices - keyboards, mice, cameras, game controllers, Output devices (monitors, projectors, printers, sound and control systems), Storage Device (magnetic , optical, memory, solid state), Processing (CPU families INTEL, TI, ARM), Communications (cabled, wireless, Bluetooth, routers, firewalls, TCP/IP) and Ubiquitous computing (cloud computing, virtualisation, terminal services, smart phones, gaming consoles and web services).'),
('IT5178', 'Communication Studies', 15, 'BIT', 'This course introduces you to theories, principles and practical skills associated with effective intrapersonal, interpersonal, oral and written modes of communication. You will also gain an appreciation of cultural identities within New Zealand society.'),
('IT5185', 'Programming 1A', 15, 'BIT', 'Learn to design software using appropriate methodologies and implement software designs as console applications. Key topics of study include: Program design using top-down-refinement and Practical programming of console applications.'),
('IT5186', 'IT Mathematics', 15, 'BIT', 'This course introduces you to relevant discrete mathematics and statistics for studies in computing. Key topics of study include: Discrete Mathematics for Computing and Statistics for the IT Industry.'),
('IT5187', 'Programming Principles', 15, 'BIT', 'Learn the principles of programming; discover the semantics and syntax of a particular programming language, design programs using simple programming language constructs and implement and test programs as GUI applications. Key topics of study: semantics and syntax, simple programming language constructs and the practical programming of console applications.'),
('IT5190', 'Linux System Administration I LPIC-1', 15, 'BIT', 'This course provides you with a practical introduction to junior-level Linux/Unix system administration.  Key topics of study include: Linux Hardware and Architecture, Linux Installation and Package Management, GNU and Unix commands, Devices, Linux File s'),
('IT5191', 'Desktop Support', 15, 'BIT', 'Learn the fundamental concepts and practices required to support desktops for an enterprise, including client operating system support and applications infrastructure support'),
('IT6221', 'Application Security and Secure Coding', 15, 'BIT', 'This course provides a practical introduction to application security and secure coding practices. Key study topics include: Application Security and Secure Coding Practices.'),
('IT6222', 'Principles of Information Security', 15, 'BIT', 'Gain an understanding of information security, governance, risk and related standards and how these should be interpreted and applied to a business environment. Key study topics include: Topic(s) or summary of content: the Principles of information security management, Security Architecture and Models, Business Continuity Planning and Disaster Recovery Planning, Legal, investigation and ethics, Physical security Controls, Operations security, Access control systems and methodology, Cryptography, Telecommunications, Network and Internet Security and Applications development security.'),
('IT6259', 'Systems Analysis and Design', 15, 'BIT', 'Learn the principles and techniques of systems analysis and design in relation to an information technology project. Key learning topics include: The role of the information analyst, Approaches to Systems Development, The systems development lifecycle (SDLC), Requirements analysis, Modelling requirements, OO Approaches, Evaluating requirements, Current trends in systems development, Contemporary Methodologies, Traditional v OO approaches to design, Database Design and Designing user and system interfaces.'),
('IT6260', 'Software Development', 15, 'BIT', 'Learn a new programming language and discover more about object oriented data structures and collections. Key topics include: The syntax and semantics of a selected programming language and Object oriented programming concepts, data structures and collections.'),
('IT6265', 'Networking III', 15, 'BIT', 'This course provides you with a comprehensive, theoretical and practical approach to technologies and protocols, knowledge necessary to design and implement a converged switched network. Key topics include: Switched and Wireless Technology and Switched and Wireless Implementation.'),
('IT6266', 'Digital Forensics', 15, 'BIT', 'Develop comprehensive understanding and practical skills in computer forensics tools and techniques. The course will develop your understanding and skills in: Digital Computer Forensics, Preserving digital evidence, Investigative reporting and how to write case summaries.'),
('IT6267', 'Management Process', 15, 'BIT', 'This course provides you with an understanding of IT service management and will prepare you for industry certification examinations in IT Service Management. Key topics include: IT Auditing and IT Governance.'),
('IT6270', 'Networking IV', 15, 'BIT', 'Networking IV provides a comprehensive theoretical and practical approach to configuring WAN technologies and network services required by converged applications in enterprise networks. Key topics include: WAN Access Technology and WAN Access Implementation.'),
('IT6276', 'Web Systems Integration', 15, 'BIT', 'Develop a foundational knowledge of Web Technologies and application integrating web technologies. Key topics include: HTTP protocol, Presentation abstractions, Web-markup and display languages, Client-side programming, Server-side programming, Emerging technologies and Standards and standards bodies.'),
('IT6277', 'Microcomputer Systems II', 15, 'BIT', 'Learn the features of microcontroller systems, including aspects such as Microcontroller architecture, Programming (HLL and assembler), Standard interfaces and Application.'),
('IT6281', 'Networking Infrastructure Support', 15, 'BIT', 'An introduction to fundamental networking concepts and technologies and the network theory and skills needed to implement a network infrastructure, including network security, authentication and configuration.');

INSERT INTO `qualified` (`ins_id`, `crs_code`) VALUES
('PT-BIT124', 'IT6277'),
('PT-BIT142', 'IT6265'),
('PT-BIT195', 'IT5177'),
('PT-BIT338', 'IT5191'),
('PT-BIT381', 'IT5190'),
('PT-BIT381', 'IT6270'),
('PT-BIT393', 'IT6281'),
('PT-BIT483', 'IT6259'),
('PT-BIT516', 'IT5178'),
('PT-BIT520', 'IT5175'),
('PT-BIT624', 'IT6222'),
('PT-BIT626', 'IT5185'),
('PT-BIT626', 'IT5186'),
('PT-BIT634', 'IT5174'),
('PT-BIT634', 'IT6266'),
('PT-BIT740', 'IT5159'),
('PT-BIT740', 'IT5169'),
('PT-BIT788', 'IT6267'),
('PT-BIT924', 'IT5162'),
('PT-BIT924', 'IT6276'),
('PT-BIT947', 'IT5187'),
('PT-BIT947', 'IT6221'),
('PT-BIT947', 'IT6260'),
('PT-BIT947', 'IT6276');

INSERT INTO `prerequisite` (`crs_code`, `crs_requires`) VALUES
('IT5162', 'IT5159'),
('IT5162', 'IT5174'),
('IT5162', 'IT5178'),
('IT5162', 'IT5186'),
('IT5175', 'IT5159'),
('IT5175', 'IT5174'),
('IT5175', 'IT5178'),
('IT5175', 'IT5186'),
('IT5190', 'IT5159'),
('IT5190', 'IT5174'),
('IT5190', 'IT5178'),
('IT5190', 'IT5186'),
('IT6222', 'IT5162'),
('IT6222', 'IT5175'),
('IT6222', 'IT5190'),
('IT6259', 'IT5162'),
('IT6259', 'IT5175'),
('IT6259', 'IT5190'),
('IT6265', 'IT5162'),
('IT6265', 'IT5175'),
('IT6265', 'IT5190'),
('IT6267', 'IT5162'),
('IT6267', 'IT5175'),
('IT6267', 'IT5190'),
('IT6270', 'IT6222'),
('IT6270', 'IT6259'),
('IT6270', 'IT6265'),
('IT6270', 'IT6267'),
('IT6281', 'IT6`222'),
('IT6281', 'IT6259'),
('IT6281', 'IT6267');

INSERT INTO `section` (`section_id`, `loc_code`, `time_id`, `crs_code`, `ins_id`) VALUES
(1, 14, 1, 'IT6266','PT-BIT195'),
(2, 13, 1, 'IT5169','PT-BIT947'),
(3, 3, 2, 'IT6276','PT-BIT393'),
(4, 3, 3, 'IT6260','PT-BIT300'),
(5, 2, 3, 'IT5178','PT-BIT520'),
(6, 1, 4, 'IT6222','PT-BIT872'),
(7, 12, 5, 'IT5177','PT-BIT634'),
(8, 11, 5, 'IT5186','PT-BIT195'),
(9, 11, 7, 'IT5175','PT-BIT338'),
(10, 10, 7, 'IT5191','PT-BIT249'),
(11, 9, 7, 'IT6221','PT-BIT483'),
(12, 8, 8, 'IT6259','PT-BIT987'),
(13, 7, 9, 'IT5175','PT-BIT249'),
(14, 7, 17, 'IT6277','PT-BIT947'),
(15, 1, 16, 'IT6267','PT-BIT393'),
(16, 12, 16, 'IT6270','PT-BIT300'),
(17, 13, 15, 'IT6281','PT-BIT520'),
(18, 13, 14, 'IT5190','PT-BIT872'),
(19, 4, 13, 'IT5185','PT-BIT634'),
(20, 3, 13, 'IT5186','PT-BIT195'),
(21, 2, 12, 'IT6265','PT-BIT338'),
(22, 1, 11, 'IT5159','PT-BIT249'),
(23, 1, 10, 'IT5175','PT-BIT987');

INSERT INTO `enrolment` (`stu_id`, `section_id`, `grade_code`) VALUES
('2131382', 10, 'C+'),
('2131382', 11, 'A+'),
('2131382', 12, 'C'),
('2131382', 13, 'C+'),
('2131382', 14, 'C'),
('2131382', 15, 'A'),
('2131382', 16, 'C'),
('2131382', 17, 'C'),
('2165670', 5, 'C'),
('2165670', 6, 'C'),
('2165670', 3, 'A'),
('2165670', 4, 'A'),
('2165670', 1, 'A'),
('2165670', 2, 'C'),
('2165670', 11, 'C+'),
('3165630', 1, 'C'),
('3165630', 8, 'C+'),
('3165630', 7, 'C'),
('3165630', 6, 'B+'),
('3165630', 5, 'A'),
('3165630', 4, 'B'),
('3165630', 3, 'B'),
('3165630', 2, 'C+'),
('3165630', 11, 'C');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
