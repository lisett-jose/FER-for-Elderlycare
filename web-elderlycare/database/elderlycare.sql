/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.9 : Database - elderlycare
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`elderlycare` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `elderlycare`;

/*Table structure for table `camerainputs` */

DROP TABLE IF EXISTS `camerainputs`;

CREATE TABLE `camerainputs` (
  `input_id` int(11) NOT NULL AUTO_INCREMENT,
  `image_path` varchar(500) DEFAULT NULL,
  `identified_person` varchar(50) DEFAULT NULL,
  `expression_identified` varchar(50) DEFAULT NULL,
  `datetime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`input_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `camerainputs` */

insert  into `camerainputs`(`input_id`,`image_path`,`identified_person`,`expression_identified`,`datetime`) values (1,'static/uploads/unknownf188a776-58ea-4d6d-828e-c77d52931311.jpg','unknown person','happy','2020-04-15 10:07:51');

/*Table structure for table `family_members` */

DROP TABLE IF EXISTS `family_members`;

CREATE TABLE `family_members` (
  `member_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `family_members` */

insert  into `family_members`(`member_id`,`first_name`,`last_name`,`image`,`gender`) values (1,'Mohan','K','static/uploads/306701f3-a7e8-4583-b3cf-e758a4278c8eabc.jpg','Male'),(2,'Vijay','S','static/uploads/b6476019-2603-454b-827b-ca142325ebccabc.jpg','Male');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `user_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`user_type`) values (1,'admin','admin','admin');

/*Table structure for table `notifications` */

DROP TABLE IF EXISTS `notifications`;

CREATE TABLE `notifications` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `input_id` int(11) DEFAULT NULL,
  `datetime` varchar(50) DEFAULT NULL,
  `notification_status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`notification_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `notifications` */

insert  into `notifications`(`notification_id`,`input_id`,`datetime`,`notification_status`) values (1,1,'2020-04-15 09:49:50','pending');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
